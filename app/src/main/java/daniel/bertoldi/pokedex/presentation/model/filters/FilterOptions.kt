package daniel.bertoldi.pokedex.presentation.model.filters

data class FilterOptions(
    var miscFilters: Map<String, List<PokemonFilterUIData>>,
    val numberRange: ClosedFloatingPointRange<Float> = 1F..1100F,
    val sortOption: SortOptions = SortOptions.SMALLEST_FIRST,
    var generationOption: List<GenerationUIData> = emptyList(),
)

data class PokemonFilterUIData(
    var isSelected: Boolean = false,
    val filterUiData: FilterUiData,
)

enum class SortOptions(val text: String) {
    SMALLEST_FIRST("Smallest number first"),
    HIGHEST_FIRST("Highest number first"),
    ASCENDING("A-Z"),
    DESCENDING("Z-A");

    companion object {
        fun parse(name: String) = values().find { it.name == name } ?: SMALLEST_FIRST
    }
}

data class GenerationUIData(
    val generationName: String,
    var isSelected: Boolean = false,
    val currentPokemonsImage: List<String>,
)
