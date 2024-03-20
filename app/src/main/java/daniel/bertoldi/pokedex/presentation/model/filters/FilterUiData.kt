package daniel.bertoldi.pokedex.presentation.model.filters

import androidx.compose.ui.graphics.Color

sealed class FilterUiData {
    abstract val foregroundColor: Color
    abstract val backgroundColor: Color
    abstract val name: String
    abstract val icon: Int

    data class Type(
        override val foregroundColor: Color,
        override val backgroundColor: Color,
        override val name: String,
        override val icon: Int,
    ) : FilterUiData()

    data class Height(
        val heightLimit: Int,
        override val foregroundColor: Color,
        override val backgroundColor: Color,
        override val name: String,
        override val icon: Int,
    ) : FilterUiData()

    data class Weight(
        val weightLimit: Int,
        override val foregroundColor: Color,
        override val backgroundColor: Color,
        override val name: String,
        override val icon: Int,
    ) : FilterUiData()
}
