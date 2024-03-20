package daniel.bertoldi.pokedex.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import daniel.bertoldi.pokedex.ui.theme.*

@Composable
fun FilterComponent() {
    val modifier = Modifier.padding(start = 40.dp, end = 40.dp)
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

        TypeListComponent(modifier, title = "Types")
        TypeListComponent(modifier, title = "Weaknesses")
        HeightListComponent(modifier)
        WeightListComponent(modifier)
        FilterSlider(
            modifier = modifier,
            valueRange = 1F..1100F,
        )

        Row(
            modifier = modifier.padding(top = 50.dp)) {
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
private fun TypeListComponent(
    modifier: Modifier,
    title: String,
) {
    Text(
        modifier = modifier.padding(top = 35.dp),
        color = TextBlack,
        style = Typography.h4,
        text = title,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        PokemonUIData.values().forEachIndexed { idx, pokemonUiData ->
            if (idx == 0) { // pulo do gato rawr
                BoxFiller(22.dp)
            }
            Icon(
                modifier = Modifier.padding(30.dp),
                painter = painterResource(id = pokemonUiData.icon),
                contentDescription = null,
                tint = pokemonUiData.typeColor,
            )
        }
    }
}

@Composable
private fun HeightListComponent(
    modifier: Modifier,
) {
    Text(
        modifier = modifier.padding(top = 35.dp),
        color = TextBlack,
        style = Typography.h4,
        text = "Height",
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        HeightUIData.values().forEachIndexed { idx, heightUiData ->
            if (idx == 0) { // pulo do gato rawr
                BoxFiller(26.dp)
            }
            Icon(
                modifier = Modifier.padding(30.dp),
                painter = painterResource(id = heightUiData.iconUnselected),
                contentDescription = null,
                tint = heightUiData.color,
            )
        }
    }
}

@Composable
private fun WeightListComponent(
    modifier: Modifier,
) {
    Text(
        modifier = modifier.padding(top = 35.dp),
        color = TextBlack,
        style = Typography.h4,
        text = "Weight",
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        WeightUIData.values().forEachIndexed { idx, weightUiData ->
            if (idx == 0) { // pulo do gato rawr
                BoxFiller(26.dp)
            }
            Icon(
                modifier = Modifier.padding(30.dp),
                painter = painterResource(id = weightUiData.iconUnselected),
                contentDescription = null,
                tint = weightUiData.color,
            )
        }
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
    // add callback parameter
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
        FilterComponent()
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