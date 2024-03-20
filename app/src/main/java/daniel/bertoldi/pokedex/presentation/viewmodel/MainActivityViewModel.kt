package daniel.bertoldi.pokedex.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import dagger.hilt.android.lifecycle.HiltViewModel
import daniel.bertoldi.pokedex.data.repository.PokemonPagingSource
import daniel.bertoldi.pokedex.presentation.mapper.PokemonBasicModelToUiModelMapper
import daniel.bertoldi.pokedex.presentation.model.BottomSheetLayout
import daniel.bertoldi.pokedex.presentation.model.PokemonBasicUiModel
import daniel.bertoldi.pokedex.presentation.model.filters.FilterOptions
import daniel.bertoldi.pokedex.presentation.model.filters.FilterUiData
import daniel.bertoldi.pokedex.presentation.model.filters.PokemonFilterFactory
import daniel.bertoldi.pokedex.usecase.GetPokemonGenerationsUIUseCase
import daniel.bertoldi.pokedex.usecase.GetPokemonUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getPokemon: GetPokemonUseCase,
    private val pokemonBasicModelToUiModelMapper: PokemonBasicModelToUiModelMapper,
    private val getPokemonGenerationsUIUseCase: GetPokemonGenerationsUIUseCase,
    pokemonFilterFactory: PokemonFilterFactory,
) : ViewModel() {

    private var errorScreen = false

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
        PokemonPagingSource(getPokemon, pokemonBasicModelToUiModelMapper)
    }.flow.cachedIn(viewModelScope).combine(filterOptions) { pageSource, filters ->
        var pagingDataFilter: PagingData<PokemonBasicUiModel> = pageSource
        val typeFilter = filters.miscFilters["types"]?.filter { it.isSelected }
        val heightFilter = filters.miscFilters["height"]?.filter { it.isSelected }
        val weightFilter = filters.miscFilters["weight"]?.filter { it.isSelected }
        val pokedexRange = filters.numberRange
        if (typeFilter?.isNotEmpty() == true) {
            pagingDataFilter = pagingDataFilter.filter { pokemonUiModel ->
                pokemonUiModel.types.any {  uiType ->
                    typeFilter.any { it.filterUiData.name in uiType.name.uppercase() }
                }
            }
        }
        if (heightFilter?.isNotEmpty() == true) {
            pagingDataFilter = pagingDataFilter.filter { pokemonUiModel ->
                heightFilter.any { pokemonUiModel.height <= (it.filterUiData as FilterUiData.Height).heightLimit }
            }
        }
        if (weightFilter?.isNotEmpty() == true) {
            pagingDataFilter = pagingDataFilter.filter { pokemonUiModel ->
                weightFilter.any { pokemonUiModel.height <= (it.filterUiData as FilterUiData.Weight).weightLimit }
            }
        }
        // currently not working
        pagingDataFilter = pagingDataFilter.filter { pokemonUiModel ->
            pokemonUiModel.id >= pokedexRange.start && pokemonUiModel.id <= pokedexRange.endInclusive
        }
        pagingDataFilter
    }

    fun init() {
        viewModelScope.launch {
            filterOptions.value = filterOptions.value.copy(
                generationOption = getPokemonGenerationsUIUseCase()
            )
        }
    }
}
