package daniel.bertoldi.pokedex.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import daniel.bertoldi.pokedex.domain.model.PokemonModel
import daniel.bertoldi.pokedex.usecase.GetPokemonUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getPokemon: GetPokemonUseCase,
) : ViewModel() {

    var errorScreen = false

    // Improve this error handling!
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is Exception -> {
                errorScreen = true
            }
        }
    }

    fun getPokemons() {
        var pokemonList: PokemonModel
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            pokemonList = getPokemon(1)
            Log.d("pokemon", pokemonList.toString())
        }
    }
}