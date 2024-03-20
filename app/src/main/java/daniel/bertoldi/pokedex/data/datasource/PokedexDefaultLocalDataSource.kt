package daniel.bertoldi.pokedex.data.datasource

import daniel.bertoldi.pokedex.data.database.dao.PokemonDao
import daniel.bertoldi.pokedex.domain.mapper.PokemonEntityToModelMapper
import javax.inject.Inject

class PokedexDefaultLocalDataSource @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val pokemonEntityToModelMapper: PokemonEntityToModelMapper,
) : PokedexLocalDataSource {

    override suspend fun getPokemon(pokemonId: Int) = pokemonEntityToModelMapper.mapFrom(
        pokemonDao.getPokemonById(pokemonId)
    )
}