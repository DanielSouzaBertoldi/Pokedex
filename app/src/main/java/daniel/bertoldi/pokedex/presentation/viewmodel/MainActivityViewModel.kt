package daniel.bertoldi.pokedex.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import daniel.bertoldi.pokedex.data.repository.PokemonPagingSource
import daniel.bertoldi.pokedex.presentation.mapper.PokemonModelToUiModelMapper
import daniel.bertoldi.pokedex.presentation.model.PokemonUiModel
import daniel.bertoldi.pokedex.usecase.GetPokemonUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import java.io.IOException
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
            is IOException, is HttpException -> {
                errorScreen = true
            }
        }
    }

    val pokemonFlow = Pager(
        PagingConfig(pageSize = 15)
    ) {
        PokemonPagingSource(getPokemon, pokemonModelToUiModelMapper)
    }.flow
        .cachedIn(viewModelScope)

    fun getPokemons() {
//        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
//            pokemonData.value = pokemonModelToUiModelMapper.mapFrom(getPokemon(1))
//        }
    }
}
