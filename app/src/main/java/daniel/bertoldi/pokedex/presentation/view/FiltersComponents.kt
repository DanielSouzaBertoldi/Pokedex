package daniel.bertoldi.pokedex.presentation.view

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.shadow
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
    onConfirmClicked: () -> Unit,
//    onResetClicked: () -> Unit,
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

        filterOptionsState.miscFilters.forEach { (key, filter) ->
            Text(
                modifier = modifier.padding(top = 35.dp),
                color = TextBlack,
                style = Typography.h4,
                text = key.replaceFirstChar { it.uppercase() },
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(26.dp),
            ) {
                filter.forEachIndexed { idx, pokemon ->
                    if (idx == 0) { // pulo do gato rawr
                        BoxFiller(15.dp)
                    }
                    FilterOptionButton(
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
                onConfirmClicked = onConfirmClicked,
            )
            FilterButton(
                buttonText = "Apply",
                textColor = TextWhite,
                buttonColor = PokemonUIData.PSYCHIC.typeColor,
                elevation = 7.dp,
                onConfirmClicked = onConfirmClicked,
            )
        }
    }
}

@Composable
private fun FilterOptionButton(
    idx: Int,
    filterType: String,
    filterData: PokemonFilterUIData,
    onFilterClicked: (Int, String) -> Unit,
) {
    val filterTransition = updateTransition(
        targetState = filterData.isSelected,
        label = "filterTransition",
    )
    val backgroundColor by filterTransition.animateColor(
        transitionSpec = { tween(400) },
        label = "filterBackgroundColor",
    ) { isSelected ->
        if (isSelected) filterData.filterUiData.foregroundColor else BgWhite
    }
    val iconFillColor by filterTransition.animateColor(
        transitionSpec = { tween(400) },
        label = "iconFillColor"
    ) { isSelected ->
        if (isSelected) BgWhite else filterData.filterUiData.foregroundColor
    }
    val elevationDp by filterTransition.animateDp(
        transitionSpec = { tween(400) },
        label = "elevationDp"
    ) { isSelected ->
        if (isSelected) 7.dp else 0.dp
    }


    IconButton(
        modifier = Modifier
            .size(50.dp)
            .padding(top = 10.dp)
            .shadow(
                elevation = elevationDp,
                shape = CircleShape,
                ambientColor = backgroundColor,
                spotColor = backgroundColor,
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(100),
            ),
        onClick = { onFilterClicked(idx, filterType) }
    ) {
        Icon(
            modifier = Modifier
                .background(
                    color = Color.Transparent,
                ),
            painter = painterResource(id = filterData.filterUiData.icon),
            contentDescription = null,
            tint = iconFillColor,
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
    elevation: Dp = 0.dp,
    onConfirmClicked: () -> Unit = {},
) {
    Button(
        modifier = modifier
            .width(160.dp)
            .height(60.dp)
            .shadow(
                elevation = elevation,
                ambientColor = PokemonUIData.PSYCHIC.typeColor,
                spotColor = PokemonUIData.PSYCHIC.typeColor,
            ),
        onClick = { onConfirmClicked() },
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
            text = buttonText,
            style = Typography.body1,
        )
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