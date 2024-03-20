package daniel.bertoldi.pokedex.data.repository

import daniel.bertoldi.pokedex.data.database.dao.PokemonDao
import daniel.bertoldi.pokedex.data.datasource.PokedexLocalDataSource
import daniel.bertoldi.pokedex.data.datasource.PokedexRemoteDataSource
import daniel.bertoldi.pokedex.domain.model.GenerationData
import javax.inject.Inject

class PokedexDefaultRepository @Inject constructor(
    private val remoteDataSource: PokedexRemoteDataSource,
    private val pokemonDao: PokemonDao,
    private val localDataSource: PokedexLocalDataSource,
) : PokedexRepository {

    override suspend fun getBasicPokemons(pokemonId: Int) =
        if (pokemonDao.isPokemonInDatabase(pokemonId)) {
            localDataSource.getBasicPokemon(pokemonId)
        } else {
            remoteDataSource.getBasicPokemonInfo(pokemonId)
        }

    override suspend fun getCompletePokemon(pokemonId: Int) =
        if (pokemonDao.isPokemonInDatabase(pokemonId)) {
            localDataSource.getCompletePokemon(pokemonId)
        } else {
            remoteDataSource.getCompletePokemonInfo(pokemonId)
        }

    override suspend fun fetchListOfGenerations() = mutableListOf<GenerationData>().apply {
        repeat(remoteDataSource.getNumberOfGenerations()) {
            this.add(remoteDataSource.getGeneration(it + 1))
        }
    }
}
