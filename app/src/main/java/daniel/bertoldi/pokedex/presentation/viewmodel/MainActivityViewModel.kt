package daniel.bertoldi.pokedex.presentation.viewmodel

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import dagger.hilt.android.lifecycle.HiltViewModel
import daniel.bertoldi.pokedex.data.repository.PokemonPagingSource
import daniel.bertoldi.pokedex.domain.model.GenerationsData
import daniel.bertoldi.pokedex.presentation.mapper.PokemonModelToUiModelMapper
import daniel.bertoldi.pokedex.presentation.model.BottomSheetLayout
import daniel.bertoldi.pokedex.presentation.model.filters.FilterOptions
import daniel.bertoldi.pokedex.presentation.model.filters.PokemonFilterFactory
import daniel.bertoldi.pokedex.usecase.GetPokemonUseCase
import daniel.bertoldi.pokedex.usecase.GetPokemonGenerationsUIUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getPokemon: GetPokemonUseCase,
    private val pokemonModelToUiModelMapper: PokemonModelToUiModelMapper,
    private val pokemonFilterFactory: PokemonFilterFactory,
    private val getPokemonGenerationsUIUseCase: GetPokemonGenerationsUIUseCase,
    private val generationsDataStore: DataStore<GenerationsData>,
    ) : ViewModel() {

    var errorScreen = false

    private val _bottomSheetContent = MutableStateFlow<BottomSheetLayout>(BottomSheetLayout.Filter)
    val bottomSheetContent: MutableStateFlow<BottomSheetLayout> = _bottomSheetContent

    val filterOptions = MutableStateFlow(
        FilterOptions(
            pokemonFilterFactory.make(),
        )
    )

    // Improve this error handling!
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is IOException, is HttpException -> {
                errorScreen = true
            }
        }
    }

    val pokemonFlow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        PokemonPagingSource(getPokemon, pokemonModelToUiModelMapper)
    }.flow.cachedIn(viewModelScope).combine(filterOptions) { pageSource, filters ->
        val selectedFilters = filters.miscFilters["types"]?.filter { it.isSelected }
        if (selectedFilters?.isNotEmpty() == true) {
            pageSource.filter { pokemonUiModel ->
                pokemonUiModel.types.any {  uiType ->
                    selectedFilters.any { it.filterUiData.name in uiType.name }
                }
            }
        } else {
            pageSource
        }
    }

    fun getPokemons() {
//        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
//            pokemonData.value = pokemonModelToUiModelMapper.mapFrom(getPokemon(1))
//        }
    }

    fun init() {
        viewModelScope.launch {
            filterOptions.value = filterOptions.value.copy(
                generationOption = getPokemonGenerationsUIUseCase()
            )
        }
    }

    fun applyFilters() {
        val enabledFilters = filterOptions.value.miscFilters["types"]?.filter { it.isSelected }

        if (enabledFilters != null) {

        }
    }
}
