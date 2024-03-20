package daniel.bertoldi.pokedex.presentation.view

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import daniel.bertoldi.pokedex.presentation.model.filters.FilterOptions
import daniel.bertoldi.pokedex.presentation.model.filters.FilterUiData
import daniel.bertoldi.pokedex.presentation.model.filters.PokemonFilterUIData
import daniel.bertoldi.pokedex.presentation.model.filters.SortOptions
import daniel.bertoldi.pokedex.ui.theme.BgDefaultInput
import daniel.bertoldi.pokedex.ui.theme.PokemonUIData
import daniel.bertoldi.pokedex.ui.theme.TextBlack
import daniel.bertoldi.pokedex.ui.theme.TextGrey
import daniel.bertoldi.pokedex.ui.theme.TextWhite
import daniel.bertoldi.pokedex.ui.theme.Typography
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SortComponent(
    filterOptions: MutableStateFlow<FilterOptions>,
    onButtonClicked: (String) -> Unit,
) {
    val currentSortOption = filterOptions.collectAsState().value.sortOption
    val modifier = Modifier.padding(start = 40.dp, end = 40.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
            )
            .padding(top = 30.dp)
    ) {
        Text(
            modifier = modifier,
            color = TextBlack,
            style = Typography.h3,
            text = "Sort",
        )
        Text(
            modifier = modifier.padding(top = 5.dp, bottom = 35.dp),
            color = TextGrey,
            style = Typography.body1,
            text = "Sort Pokémons alphabetically or by National Pokédex number!",
        )

        SortOptions.values().forEachIndexed { idx, sortOption ->
            SortButton(
                modifier = modifier,
                sortOption = sortOption,
                isButtonSelected = currentSortOption.text == sortOption.text,
                onButtonClicked = onButtonClicked,
            )
        }
    }
}

@Composable
private fun SortButton(
    modifier: Modifier = Modifier,
    sortOption: SortOptions,
    isButtonSelected: Boolean = false,
    onButtonClicked: (String) -> Unit,
) {
    val sortTransition = updateTransition(
        targetState = isButtonSelected,
        label = "sortTransition",
    )
    val buttonColor by sortTransition.animateColor(
        transitionSpec = { tween(500) },
        label = "sortButtonColor",
    ) { isSelected ->
        if (isSelected) PokemonUIData.PSYCHIC.typeColor else BgDefaultInput
    }
    val textColor by sortTransition.animateColor(
        transitionSpec = { tween(500) },
        label = "sortTextColor",
    ) { isSelected ->
        if (isSelected) TextWhite else TextGrey
    }
    val elevation by sortTransition.animateDp(
        transitionSpec = { tween(500) },
        label = "sortElevationDp",
    ) { isSelected ->
        if (isSelected) 10.dp else 0.dp
    }

    Button(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
            .height(60.dp)
            .shadow(
                elevation = elevation,
                ambientColor = PokemonUIData.PSYCHIC.typeColor,
                spotColor = PokemonUIData.PSYCHIC.typeColor,
            ),
        onClick = { onButtonClicked(sortOption.name) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor,
            contentColor = textColor,
        ),
        shape = RoundedCornerShape(10),
        elevation = ButtonDefaults.elevation(
            defaultElevation = elevation,
        ),
    ) {
        Text(
            modifier = Modifier,
            text = sortOption.text,
            style = Typography.body1,
        )
    }
}
