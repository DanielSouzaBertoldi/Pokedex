package daniel.bertoldi.pokedex.data.datasource

import daniel.bertoldi.pokedex.data.api.PokeApi
import daniel.bertoldi.pokedex.data.api.response.AbilityResponse
import daniel.bertoldi.pokedex.data.api.response.GenericObject
import daniel.bertoldi.pokedex.data.api.response.PokemonResponse
import daniel.bertoldi.pokedex.data.api.response.StatsResponse
import daniel.bertoldi.pokedex.data.database.dao.AbilitiesDao
import daniel.bertoldi.pokedex.data.database.dao.PokemonAbilitiesCrossRefDao
import daniel.bertoldi.pokedex.data.database.dao.PokemonDao
import daniel.bertoldi.pokedex.data.database.dao.StatsDao
import daniel.bertoldi.pokedex.data.database.model.Abilities
import daniel.bertoldi.pokedex.data.database.model.EffectEntry
import daniel.bertoldi.pokedex.data.database.model.FlavorTextEntry
import daniel.bertoldi.pokedex.data.database.model.Pokemon
import daniel.bertoldi.pokedex.data.database.model.Sprites
import daniel.bertoldi.pokedex.data.database.model.Stats
import daniel.bertoldi.pokedex.data.database.model.relations.PokemonAbilitiesCrossRef
import daniel.bertoldi.pokedex.domain.mapper.GenerationResponseToModelMapper
import daniel.bertoldi.pokedex.domain.mapper.PokemonResponseToBasicModelMapper
import daniel.bertoldi.pokedex.domain.mapper.PokemonResponseToCompleteModelMapper
import daniel.bertoldi.pokedex.domain.model.PokemonCompleteModel
import daniel.bertoldi.pokedex.domain.model.PokemonBasicModel
import javax.inject.Inject

private val fetchIdRegex = Regex("([\\d]+)")

class PokedexDefaultRemoteDataSource @Inject constructor(
    private val pokeApi: PokeApi,
    private val pokemonResponseToBasicModelMapper: PokemonResponseToBasicModelMapper,
    private val abilitiesDao: AbilitiesDao,
    private val pokemonAbilitiesCrossRefDao: PokemonAbilitiesCrossRefDao,
    private val pokemonDao: PokemonDao,
    private val statsDao: StatsDao,
    private val generationsResponseToModelMapper: GenerationResponseToModelMapper,
    private val pokemonResponseToCompleteModelMapper: PokemonResponseToCompleteModelMapper,
) : PokedexRemoteDataSource {

    override suspend fun getBasicPokemonInfo(pokemonId: Int): PokemonBasicModel {
        val pokemonResponse = pokeApi.getPokemon(pokemonId)

        addPokemonToRoom(pokemonResponse)

        return pokemonResponseToBasicModelMapper.mapFrom(pokemonResponse)
    }

    override suspend fun getCompletePokemonInfo(pokemonId: Int): PokemonCompleteModel {
        val pokemonResponse = pokeApi.getPokemon(pokemonId)
        pokemonResponse.abilities.forEach {
            val abilityId = it.ability.url.fetchIdFromUrl()

            if (abilitiesDao.getAbilityById(abilityId) == null) {
                val abilityResponse = pokeApi.getAbility(abilityId)
                addAbilityToRoom(abilityResponse)
            }
        }

        addPokemonToRoom(pokemonResponse)

        return pokemonResponseToCompleteModelMapper.mapFrom(pokemonResponse)
    }

    override suspend fun getNumberOfGenerations() = pokeApi.getGenerations().count

    override suspend fun getGeneration(id: Int) = generationsResponseToModelMapper.mapFrom(
        pokeApi.getGeneration(id)
    )

    private suspend fun addAbilityToRoom(abilityResponse: AbilityResponse) {
        abilitiesDao.insertAbility(
            Abilities(
                id = abilityResponse.id,
                name = abilityResponse.name,
                effectEntries = abilityResponse.effectEntries.map { effectEntry ->
                    EffectEntry(
                        effect = effectEntry.effect,
                        language = effectEntry.language.name,
                        shortEffect = effectEntry.shortEffect,
                    )
                },
                flavorTextEntries = abilityResponse.flavorTextEntries.map { flavorEntry ->
                    FlavorTextEntry(
                        flavorText = flavorEntry.flavorText,
                        language = flavorEntry.language.name,
                        versionGroupName = flavorEntry.versionGroup.name,
                    )
                },
                generationName = abilityResponse.generation.name,
                isMainSeries = abilityResponse.isMainSeries,
            )
        )

        abilityResponse.pokemon.map { pokemon ->
            pokemonAbilitiesCrossRefDao.insertPokemonAbilityCrossRef(
                PokemonAbilitiesCrossRef(
                    abilityId = abilityResponse.id,
                    pokemonId = fetchIdRegex.findAll(pokemon.data.url).last().value.toInt(),
                )
            )
        }
    }

    private suspend fun addPokemonToRoom(pokemon: PokemonResponse) {
        pokemonDao.insertPokemon(
            Pokemon(
                id = pokemon.id,
                name = pokemon.name,
                height = pokemon.height,
                weight = pokemon.weight,
                isDefault = pokemon.isDefault,
                sprites = Sprites(
                    backDefault = pokemon.sprites.backDefault,
                    backShiny = pokemon.sprites.backShiny,
                    frontDefault = pokemon.sprites.frontDefault,
                    frontShiny = pokemon.sprites.frontShiny,
                    officialArtwork = pokemon.sprites.otherSprites.officialArtworkSprites.frontDefault,
                ),
                types = pokemon.types.map {
                    GenericObject(
                        name = it.type.name,
                        url = it.type.url,
                    )
                }
            )
        )

        addPokemonStatsToRoom(pokemon.id, pokemon.stats)
    }

    private suspend fun addPokemonStatsToRoom(pokemonId: Int, statsResponse: List<StatsResponse>) {
        statsDao.insertStat(
            Stats(
                pokemonId = pokemonId,
                hp = statsResponse.getBaseStat("hp"),
                attack = statsResponse.getBaseStat("attack"),
                defense = statsResponse.getBaseStat("defense"),
                specialAttack = statsResponse.getBaseStat("special-attack"),
                specialDefense = statsResponse.getBaseStat("special-defense"),
                speed = statsResponse.getBaseStat("speed"),
                accuracy = statsResponse.getBaseStat( "accuracy"),
                evasion = statsResponse.getBaseStat("evasion"),
            )
        )
    }

    private fun List<StatsResponse>.getBaseStat(stat: String) =
        this.firstOrNull { it.stat.name == stat }?.baseStat

    private fun String.fetchIdFromUrl() = fetchIdRegex.findAll(this).last().value.toInt()
}
