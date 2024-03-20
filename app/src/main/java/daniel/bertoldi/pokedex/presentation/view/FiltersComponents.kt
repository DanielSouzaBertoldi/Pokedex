package daniel.bertoldi.pokedex.presentation.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import daniel.bertoldi.pokedex.presentation.model.filters.FilterOptions
import daniel.bertoldi.pokedex.presentation.model.filters.PokemonFilterUIData
import daniel.bertoldi.pokedex.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun FilterComponent(
    filterOptions: MutableStateFlow<FilterOptions>,
    onFilterClicked: (Int, String) -> Unit,
) {
    val modifier = Modifier.padding(start = 40.dp, end = 40.dp)
    val filterOptionsState by filterOptions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
            )
            .padding(
                vertical = 30.dp,
            )
    ) {
        Text(
            modifier = modifier,
            color = TextBlack,
            style = Typography.h3,
            text = "Filters",
        )
        Text(
            modifier = modifier.padding(top = 5.dp),
            color = TextGrey,
            style = Typography.body1,
            text = "Use advanced search to explore Pokémon by type, weakness, height and more!",
        )

        filterOptionsState.mainFilters.forEach { (key, filter) ->
            Text(
                modifier = modifier.padding(top = 35.dp),
                color = TextBlack,
                style = Typography.h4,
                text = key,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                filter.forEachIndexed { idx, pokemon ->
                    if (idx == 0) { // pulo do gato rawr
                        BoxFiller(22.dp)
                    }
                    FilterMaluco(
                        modifier = modifier,
                        idx = idx,
                        filterType = key,
                        filterData = pokemon,
                        onFilterClicked = onFilterClicked,
                    )
                }
            }
        }

        FilterSlider(
            modifier = modifier,
            valueRange = 1F..1100F,
        )

        Row(
            modifier = modifier.padding(top = 50.dp)
        ) {
            FilterButton(
                modifier = Modifier.padding(end = 14.dp),
                buttonText = "Reset",
            )
            FilterButton(
                buttonText = "Apply",
                textColor = TextWhite,
                buttonColor = PokemonUIData.PSYCHIC.typeColor,
            )
        }
    }
}

@Composable
private fun FilterMaluco(
    modifier: Modifier = Modifier,
    idx: Int,
    filterType: String,
    filterData: PokemonFilterUIData,
    onFilterClicked: (Int, String) -> Unit,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (filterData.isSelected) {
            filterData.filterUiData.foregroundColor
        } else BgWhite,
        animationSpec = tween(400),
    )
    val iconFill by animateColorAsState(
        targetValue = if (filterData.isSelected) {
            BgWhite
        } else filterData.filterUiData.foregroundColor,
        animationSpec = tween(400),
    )

    IconButton(
        onClick = { onFilterClicked(idx, filterType.lowercase()) }
    ) {
        Icon(
            modifier = Modifier
                .padding(30.dp)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(50)
                ),
            painter = painterResource(id = filterData.filterUiData.icon),
            contentDescription = null,
            tint = iconFill,
        )
    }
}

@Composable
private fun BoxFiller(width: Dp) {
    Box(
        modifier = Modifier.width(width)
    )
}

@Composable
private fun FilterButton(
    modifier: Modifier = Modifier,
    buttonColor: Color = BgDefaultInput,
    textColor: Color = TextGrey,
    buttonText: String,
) {
    Button(
        modifier = modifier
            .width(160.dp)
            .height(60.dp),
        onClick = { /* Add callback */ },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor,
            contentColor = textColor,
        ),
        shape = RoundedCornerShape(4.dp),
    ) {
        Text(
            modifier = Modifier,
            text = buttonText,
            style = Typography.body1,
        )
    }
}

@Preview(backgroundColor = 0)
@Composable
fun FilterComponentPreview() {
    PokedexTheme {
//        FilterComponent(MutableStateFlow(FilterOptions(numberRange = 1F..1100F)))
    }
}

@Preview
@Composable
fun FilterSliderPreview() {
    FilterSlider(
        Modifier.padding(horizontal = 40.dp),
        valueRange = 1F..1200F,
    )
}