package daniel.bertoldi.pokedex.presentation.model

sealed class BottomSheetLayout {
    object Generations : BottomSheetLayout()
    object Sort : BottomSheetLayout()
    object Filter : BottomSheetLayout()
}
