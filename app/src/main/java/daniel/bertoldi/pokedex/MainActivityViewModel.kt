package daniel.bertoldi.pokedex

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import daniel.bertoldi.pokedex.data.api.PokeApi
import daniel.bertoldi.pokedex.data.api.response.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val pokemonApi: PokeApi,
) : ViewModel() {

    // Needless to say this should be refactored soon.
    // Create the repository/data source layer then call this logic through an usecase.
    fun getPokemons() {
        var pokemonList: PokemonResponse?
        viewModelScope.launch(Dispatchers.IO) {
            pokemonList = pokemonApi.getPokemonList(1)
            Log.d("pokemon", pokemonList.toString())
        }
    }
}