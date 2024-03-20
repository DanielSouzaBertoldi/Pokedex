package daniel.bertoldi.pokedex.data.datasource

import daniel.bertoldi.pokedex.data.api.PokeApi
import daniel.bertoldi.pokedex.data.api.response.AbilityResponse
import daniel.bertoldi.pokedex.data.database.dao.AbilitiesDao
import daniel.bertoldi.pokedex.data.database.dao.PokemonAbilitiesCrossRefDao
import daniel.bertoldi.pokedex.data.database.model.Abilities
import daniel.bertoldi.pokedex.data.database.model.EffectEntry
import daniel.bertoldi.pokedex.data.database.model.FlavorTextEntry
import daniel.bertoldi.pokedex.data.database.model.relations.PokemonAbilitiesCrossRef
import daniel.bertoldi.pokedex.domain.mapper.PokemonResponseToModelMapper
import daniel.bertoldi.pokedex.domain.model.PokemonModel
import javax.inject.Inject

private val fetchIdRegex = Regex("([\\d]+)")

class PokedexDefaultRemoteDataSource @Inject constructor(
    private val pokeApi: PokeApi,
    private val pokemonResponseToModelMapper: PokemonResponseToModelMapper,
    private val abilitiesDao: AbilitiesDao,
    private val pokemonAbilitiesCrossRefDao: PokemonAbilitiesCrossRefDao,
) : PokedexRemoteDataSource {

    override suspend fun getPokemon(pokemonId: Int): PokemonModel {
        val pokemonResponse = pokeApi.getPokemon(pokemonId)
        pokemonResponse.abilities.forEach {
            val abilityId = fetchIdRegex.findAll(it.ability.url).last().value.toInt()

            if (abilitiesDao.getAbilityById(abilityId) == null) {
                val abilityResponse = pokeApi.getAbility(abilityId)
                addAbilityToRoom(abilityResponse)
            }
        }

        return pokemonResponseToModelMapper.mapFrom(pokemonResponse)
    }

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
//                    pokemonName = pokemon.data.name,
//                    isHidden = pokemon.isHidden,
//                    slot = pokemon.slot,
                )
            )
        }
    }
}
