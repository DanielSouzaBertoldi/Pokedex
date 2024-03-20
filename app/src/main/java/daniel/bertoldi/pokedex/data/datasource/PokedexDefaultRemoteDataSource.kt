package daniel.bertoldi.pokedex.data.datasource

import daniel.bertoldi.pokedex.data.api.PokeApi
import daniel.bertoldi.pokedex.data.api.response.AbilityResponse
import daniel.bertoldi.pokedex.domain.mapper.PokemonResponseToModelMapper
import daniel.bertoldi.pokedex.domain.model.PokemonModel
import javax.inject.Inject

private val fetchIdRegex = Regex("([\\d]+)")

class PokedexDefaultRemoteDataSource @Inject constructor(
    private val pokeApi: PokeApi,
    private val pokemonResponseToModelMapper: PokemonResponseToModelMapper,
) : PokedexRemoteDataSource {

    override suspend fun getPokemon(pokemonId: Int): PokemonModel {
        val pokemonResponse = pokeApi.getPokemon(pokemonId)
        val abilitiesResponse = mutableListOf<AbilityResponse>().apply {
            pokemonResponse.abilities.forEach {
                val abilityId = fetchIdRegex.findAll(it.ability.url).last().value.toInt()

                add(pokeApi.getAbility(abilityId))
            }
        }.toList()


        return pokemonResponseToModelMapper.mapFrom(pokemonResponse, abilitiesResponse)
    }
}
