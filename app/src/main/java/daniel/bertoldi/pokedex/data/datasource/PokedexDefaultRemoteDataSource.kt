package daniel.bertoldi.pokedex.data.datasource

import android.util.Log
import daniel.bertoldi.pokedex.data.api.PokeApi
import daniel.bertoldi.pokedex.data.api.response.*
import daniel.bertoldi.pokedex.data.database.dao.AbilitiesDao
import daniel.bertoldi.pokedex.data.database.dao.PokemonAbilitiesCrossRefDao
import daniel.bertoldi.pokedex.data.database.dao.PokemonDao
import daniel.bertoldi.pokedex.data.database.dao.SpeciesDao
import daniel.bertoldi.pokedex.data.database.dao.StatsDao
import daniel.bertoldi.pokedex.data.database.dao.TypeEffectivenessDao
import daniel.bertoldi.pokedex.data.database.model.*
import daniel.bertoldi.pokedex.data.database.model.Pokemon
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
    private val speciesDao: SpeciesDao,
    private val typeEffectivenessDao: TypeEffectivenessDao,
) : PokedexRemoteDataSource {

    override suspend fun getBasicPokemonInfo(pokemonId: Int): PokemonBasicModel {
        val pokemonResponse = pokeApi.getPokemon(pokemonId)

        addPokemonToRoom(pokemonResponse)

        return pokemonResponseToBasicModelMapper.mapFrom(pokemonResponse)
    }

    override suspend fun getCompletePokemonInfo(pokemonId: Int): PokemonCompleteModel {
        val pokemonResponse = pokeApi.getPokemon(pokemonId)
        val abilitiesResponse = getPokemonAbilities(pokemonResponse.abilities)
        val speciesResponse = getPokemonSpecies(pokemonResponse.species.url)
        val typeEffectivenessResponse = getPokemonTypeEffectiveness(pokemonResponse.types)
        val evolutionChain = getEvolutionChain(speciesResponse.evolutionChain.url.fetchIdFromUrl())
        pokemonDao.updatePokedexEntry(pokemonId)

        return pokemonResponseToCompleteModelMapper.mapFrom(
            pokemonEntity = pokemonResponse,
            abilitiesResponse = abilitiesResponse,
            speciesResponse = speciesResponse,
            typeEffectiveness = typeEffectivenessResponse,
        )
    }

    override suspend fun getNumberOfGenerations() = pokeApi.getGenerations().count

    override suspend fun getGeneration(id: Int) = generationsResponseToModelMapper.mapFrom(
        pokeApi.getGeneration(id)
    )

    private suspend fun addPokemonToRoom(pokemon: PokemonResponse) {
        pokemonDao.insertPokemon(
            Pokemon(
                pokemonId = pokemon.id,
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
                },
                hasCompleteData = false,
                baseExperience = pokemon.baseExperience ?: -1, // create logic to deal with this
                speciesId = pokemon.species.url.fetchIdFromUrl(),
            )
        )

        addPokemonStatsToRoom(pokemon.id, pokemon.stats)
    }

    private suspend fun addPokemonStatsToRoom(pokemonId: Int, statsResponse: List<StatsResponse>) {
        statsDao.insertStat(
            Stats(
                statPokemonId = pokemonId,
                hp = statsResponse.getBaseStat("hp"),
                attack = statsResponse.getBaseStat("attack"),
                defense = statsResponse.getBaseStat("defense"),
                specialAttack = statsResponse.getBaseStat("special-attack"),
                specialDefense = statsResponse.getBaseStat("special-defense"),
                speed = statsResponse.getBaseStat("speed"),
                accuracy = statsResponse.getBaseStat("accuracy"),
                evasion = statsResponse.getBaseStat("evasion"),
            )
        )
    }

    private suspend fun getPokemonAbilities(
        pokemonAbilites: List<PokemonAbilitiesResponse>,
    ): List<AbilityResponse> {
        val abilitiesResponse = mutableListOf<AbilityResponse>()
        pokemonAbilites.forEach {
            val abilityId = it.ability.url.fetchIdFromUrl()
            val abilityResponse = pokeApi.getAbility(abilityId)
            abilitiesResponse.add(abilityResponse.copy(isHidden = it.isHidden, slot = it.slot))
            addAbilityToRoom(abilityResponse)
        }
        return abilitiesResponse
    }

    private suspend fun addAbilityToRoom(abilityResponse: AbilityResponse) {
        abilitiesDao.insertAbility(
            Abilities(
                abilityId = abilityResponse.id,
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
                        versionGroupName = flavorEntry.versionGroup?.name.orEmpty(),
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
                    pokemonId = pokemon.data.url.fetchIdFromUrl(),
                    isHidden = pokemon.isHidden,
                    slot = pokemon.slot,
                )
            )
        }
    }

    private suspend fun getPokemonSpecies(speciesUrl: String): PokemonSpeciesResponse {
        val speciesId = speciesUrl.fetchIdFromUrl()
        val speciesResponse = pokeApi.getPokemonSpecies(speciesId)

        if (speciesDao.isSpeciesInDatabase(speciesId).not()) {
            speciesDao.insertSpecies(
                Species(
                    speciesId = speciesResponse.id,
                    baseHappiness = speciesResponse.baseHappiness,
                    captureRate = speciesResponse.captureRate,
                    eggGroups = speciesResponse.eggGroups.map { it.name },
                    genderRate = speciesResponse.genderRate,
                    pokedexEntry = speciesResponse.flavorTextEntries.filter { flavorEntry ->
                        flavorEntry.language.name == "en"
                    }.maxBy { it.flavorText.length }.flavorText.replace("\n", " "),
                    growthRate = speciesResponse.growthRate.name,
                    isBaby = speciesResponse.isBaby,
                    isLegendary = speciesResponse.isLegendary,
                    isMythical = speciesResponse.isMythical,
                    hatchCounter = speciesResponse.hatchCounter,
                    genera = speciesResponse.genera.firstOrNull {
                        it.language.name == "en"
                    }?.genus.orEmpty(),
                )
            )
        }
        return speciesResponse
    }

    private suspend fun getPokemonTypeEffectiveness(types: List<TypesResponse>): TypeEffectiveness {
        val typeNames = types.map { it.type.name }
        val multipliers = mutableMapOf(
            "defense" to mutableMapOf<String, Float>(),
            "attack" to mutableMapOf()
        )
        types.forEach { type ->
            val typeInfo = pokeApi.getPokemonTypeInfo(type.type.url.fetchIdFromUrl())
            typeInfo.damageRelations.defenseDouble.forEach {
                multipliers.calculateTypeEffectiveness(2f, "defense", it.name)
            }
            typeInfo.damageRelations.attackDouble.forEach {
                multipliers.calculateTypeEffectiveness(2f, "attack", it.name)
            }
            typeInfo.damageRelations.defenseHalf.forEach {
                multipliers.calculateTypeEffectiveness(0.5f, "defense", it.name)
            }
            typeInfo.damageRelations.attackHalf.forEach {
                multipliers.calculateTypeEffectiveness(0.5f, "attack", it.name)
            }
            typeInfo.damageRelations.defenseZero.forEach {
                multipliers.calculateTypeEffectiveness(0f, "defense", it.name)
            }
            typeInfo.damageRelations.attackZero.forEach {
                multipliers.calculateTypeEffectiveness(0f, "attack", it.name)
            }
        }

        val typeEffectiveness = TypeEffectiveness(
            firstType = typeNames[0],
            secondType = typeNames.getOrElse(1) { "" },
            normal = multipliers["defense"]?.get("normal"),
            fighting = multipliers["defense"]?.get("fighting"),
            flying = multipliers["defense"]?.get("flying"),
            poison = multipliers["defense"]?.get("poison"),
            ground = multipliers["defense"]?.get("ground"),
            rock = multipliers["defense"]?.get("rock"),
            bug = multipliers["defense"]?.get("bug"),
            ghost = multipliers["defense"]?.get("ghost"),
            steel = multipliers["defense"]?.get("steel"),
            fire = multipliers["defense"]?.get("fire"),
            water = multipliers["defense"]?.get("water"),
            grass = multipliers["defense"]?.get("grass"),
            electric = multipliers["defense"]?.get("electric"),
            psychic = multipliers["defense"]?.get("psychic"),
            ice = multipliers["defense"]?.get("ice"),
            dragon = multipliers["defense"]?.get("dragon"),
            dark = multipliers["defense"]?.get("dark"),
            fairy = multipliers["defense"]?.get("fairy"),
            unknown = multipliers["defense"]?.get("unknown"),
            shadow = multipliers["defense"]?.get("shadow"),
        )

        if (
            !typeEffectivenessDao.isTypeEffectivenessInDataBase(
                typeNames[0],
                typeNames.getOrElse(1) { "" })
        ) {
            typeEffectivenessDao.insertTypeEffectiveness(typeEffectiveness)
        }
        return typeEffectiveness
    }

    private suspend fun getEvolutionChain(evolutionChainId: Int) {
        val evolutionChainResponse = pokeApi.getEvolutionChain(evolutionChainId)
        Log.d("EVOLUTION-CHAIN", evolutionChainResponse.toString())
    }

    private fun List<StatsResponse>.getBaseStat(stat: String) =
        this.firstOrNull { it.stat.name == stat }?.baseStat

    private fun String.fetchIdFromUrl() = fetchIdRegex.findAll(this).last().value.toInt()

    private fun MutableMap<String, MutableMap<String, Float>>.calculateTypeEffectiveness(
        multiplier: Float,
        key: String,
        type: String,
    ) {
        val value = this[key]?.get(type)?.times(multiplier) ?: multiplier
        this[key]?.put(type, value)
    }
}
