package daniel.bertoldi.pokedex.usecase

import androidx.datastore.core.DataStore
import daniel.bertoldi.pokedex.data.repository.PokedexRepository
import daniel.bertoldi.pokedex.domain.model.GenerationData
import daniel.bertoldi.pokedex.domain.model.GenerationsData
import daniel.bertoldi.pokedex.presentation.mapper.GenerationsDataToUIMapper
import daniel.bertoldi.pokedex.presentation.model.filters.GenerationUIData
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetPokemonGenerationsUIDefault @Inject constructor(
    private val pokedexRepository: PokedexRepository,
    private val generationsDataStore: DataStore<GenerationsData>,
    private val generationsDataToUIMapper: GenerationsDataToUIMapper,
) : GetPokemonGenerationsUIUseCase {
    override suspend fun invoke(): List<GenerationUIData> {
        val dataStoreData = generationsDataStore.data.first()
        if (dataStoreData.generation.isEmpty()) {
            addGenerationsToDataStore()
        }
        return generationsDataToUIMapper.mapFrom(dataStoreData.generation)
    }

    private suspend fun addGenerationsToDataStore() {
        val listOfGenerations = pokedexRepository.fetchListOfGenerations()
        generationsDataStore.updateData { generationsSettings ->
            generationsSettings.copy(
                generation = generationsSettings.generation.mutate {
                    it.addAll(
                        listOfGenerations
                    )
                }
            )
        }
        loadCurrentSelectedPokemons(listOfGenerations)
    }

    private suspend fun loadCurrentSelectedPokemons(listOfGenerations: List<GenerationData>) {
        listOfGenerations.forEach {
            it.currentPokemons.forEach { pokemonId ->
                pokedexRepository.getBasicPokemons(pokemonId)
            }
        }
    }
}