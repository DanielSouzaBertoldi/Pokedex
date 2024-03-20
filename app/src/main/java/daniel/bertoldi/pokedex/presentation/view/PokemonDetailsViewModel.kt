package daniel.bertoldi.pokedex.presentation.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import daniel.bertoldi.pokedex.presentation.mapper.PokemonModelToUiModelMapper
import daniel.bertoldi.pokedex.presentation.model.PokemonUiModel
import daniel.bertoldi.pokedex.usecase.GetPokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPokemon: GetPokemon,
    private val pokemonModelToUiModelMapper: PokemonModelToUiModelMapper,
) : ViewModel() {

    val detailsScreenState  = MutableStateFlow(DetailsScreenState.LOADING)
    val pokemonDetails = MutableStateFlow<PokemonUiModel?>(null)

    init {
        val pokemonId: Int = checkNotNull(savedStateHandle["pokemonId"])
        getPokemonDetails(pokemonId)
    }

    // maybe have another usecase that'll return the full pokemon data, not only what's
    // shown in the home list.
    private fun getPokemonDetails(pokemonId: Int) {
        if (pokemonId != -1) {
            viewModelScope.launch {
                pokemonDetails.value = pokemonModelToUiModelMapper.mapFrom(getPokemon(pokemonId))
                detailsScreenState.value = DetailsScreenState.SUCCESS
            }
        } else {
            detailsScreenState.value = DetailsScreenState.ERROR
        }
    }
}

enum class DetailsScreenState {
    LOADING,
    SUCCESS,
    ERROR
}