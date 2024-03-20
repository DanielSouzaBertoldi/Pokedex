package daniel.bertoldi.pokedex.data.datasource

import daniel.bertoldi.pokedex.data.database.dao.PokemonDao
import daniel.bertoldi.pokedex.domain.mapper.PokemonEntityToBasicModelMapper
import daniel.bertoldi.pokedex.domain.mapper.PokemonEntityToCompleteModelMapper
import javax.inject.Inject

class PokedexDefaultLocalDataSource @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val pokemonEntityToBasicModelMapper: PokemonEntityToBasicModelMapper,
    private val pokemonEntityToCompleteModelMapper: PokemonEntityToCompleteModelMapper,
) : PokedexLocalDataSource {

    override suspend fun getBasicPokemon(pokemonId: Int) = pokemonEntityToBasicModelMapper.mapFrom(
        pokemonDao.getPokemonById(pokemonId)
    )

    override suspend fun getCompletePokemon(pokemonId: Int) =
        pokemonEntityToCompleteModelMapper.mapFrom(
            pokemonDao.getPokemonById(pokemonId)
        )
}