package daniel.bertoldi.pokedex.usecase

import androidx.datastore.core.DataStore
import daniel.bertoldi.pokedex.data.repository.PokedexRepository
import daniel.bertoldi.pokedex.domain.model.GenerationData
import daniel.bertoldi.pokedex.domain.model.GenerationsData
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetPokemonGenerationsDefault @Inject constructor(
    private val pokedexRepository: PokedexRepository,
    private val generationsDataStore: DataStore<GenerationsData>,
) : GetPokemonGenerationsUseCase {
    override suspend fun invoke() {
        if (generationsDataStore.data.first().generation.isEmpty()) {
            addGenerationsToDataStore()
        }
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
                pokedexRepository.getPokemons(pokemonId)
            }
        }
    }
}