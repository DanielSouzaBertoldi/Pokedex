package daniel.bertoldi.pokedex.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import daniel.bertoldi.pokedex.presentation.mapper.PokemonCompleteModelToUiModelMapper
import daniel.bertoldi.pokedex.presentation.model.PokemonBasicUiModel
import daniel.bertoldi.pokedex.presentation.model.PokemonCompleteUiModel
import daniel.bertoldi.pokedex.usecase.GetPokemonDefaultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPokemon: GetPokemonDefaultUseCase,
    private val pokemonCompleteToUiModelMapper: PokemonCompleteModelToUiModelMapper,
) : ViewModel() {

    val detailsScreenState  = MutableStateFlow(DetailsScreenState.LOADING)
    val pokemonDetails = MutableStateFlow<PokemonCompleteUiModel?>(null)

    init {
        val pokemonId: Int = checkNotNull(savedStateHandle["pokemonId"])
        getPokemonDetails(pokemonId)
    }

    private fun getPokemonDetails(pokemonId: Int) {
        if (pokemonId != -1) {
            viewModelScope.launch {
                pokemonDetails.value = pokemonCompleteToUiModelMapper.mapFrom(
                    getPokemon.completeData(pokemonId)
                )
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