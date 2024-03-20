package daniel.bertoldi.pokedex.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import daniel.bertoldi.pokedex.presentation.mapper.PokemonModelToUiModelMapper
import daniel.bertoldi.pokedex.presentation.model.PokemonUiModel
import daniel.bertoldi.pokedex.usecase.GetPokemonUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getPokemon: GetPokemonUseCase,
    private val pokemonModelToUiModelMapper: PokemonModelToUiModelMapper,
) : ViewModel() {

    var errorScreen = false
    var pokemonData = MutableStateFlow<PokemonUiModel?>(null)

    // Improve this error handling!
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is Exception -> {
                errorScreen = true
            }
        }
    }

    fun getPokemons() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            pokemonData.value = pokemonModelToUiModelMapper.mapFrom(getPokemon(1))
            Log.d("pokemon", pokemonData.value.toString())
        }
    }
}