package daniel.bertoldi.pokedex.presentation.model.filters

data class FilterOptions(
    var mainFilters: Map<String, List<PokemonFilterUIData>>,
    val numberRange: ClosedFloatingPointRange<Float> = 1F..1100F,
)

data class PokemonFilterUIData(
    var isSelected: Boolean = false,
    val filterUiData: FilterUiData,
)
