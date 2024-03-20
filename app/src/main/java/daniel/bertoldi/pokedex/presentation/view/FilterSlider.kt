package daniel.bertoldi.pokedex.presentation.view

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import daniel.bertoldi.pokedex.ui.theme.BgDefaultInput
import daniel.bertoldi.pokedex.ui.theme.PokemonUIData
import daniel.bertoldi.pokedex.ui.theme.TextBlack
import daniel.bertoldi.pokedex.ui.theme.TextGrey
import daniel.bertoldi.pokedex.ui.theme.Typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterSlider(
    modifier: Modifier,
    valueRange: ClosedFloatingPointRange<Float>,
    labelMinWidth: Dp = 24.dp,
) {
    var sliderPosition by remember {
        mutableStateOf(valueRange.start..valueRange.endInclusive)
    }

    Text(
        modifier = modifier.padding(bottom = 20.dp),
        text = "Number Range",
        color = TextBlack,
        style = Typography.h4,
    )

    Column(modifier = modifier) {
        RangeSlider(
            values = sliderPosition,
            onValueChange = { sliderPosition = it },
            onValueChangeFinished = { /* Do something here */ },
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                thumbColor = PokemonUIData.PSYCHIC.typeColor.compositeOver(Color.White),
                activeTrackColor = PokemonUIData.PSYCHIC.typeColor,
                inactiveTrackColor = BgDefaultInput,
            ),
        )

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val offset = getSliderOffset(
                value = sliderPosition.start,
                valueRange = valueRange,
                boxWidth = maxWidth,
                labelWidth = labelMinWidth,
            )

            val endOffset = getSliderOffset(
                value = sliderPosition.endInclusive,
                valueRange = valueRange,
                boxWidth = maxWidth,
                labelWidth = labelMinWidth,
            )

            if (sliderPosition.start > valueRange.start) {
                SliderLabel(
                    label = sliderPosition.start.toInt().toString(),
                    minWidth = labelMinWidth,
                    modifier = Modifier.padding(start = offset)
                )
            } else {
                SliderLabel(label = valueRange.start.toInt().toString(), minWidth = labelMinWidth)
            }

            if (sliderPosition.endInclusive > valueRange.start) {
                SliderLabel(
                    label = sliderPosition.endInclusive.toInt().toString(),
                    minWidth = labelMinWidth + 180.dp,
                    modifier = Modifier.padding(start = endOffset)
                )
            }
        }
    }
}

private fun getSliderOffset(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    boxWidth: Dp,
    labelWidth: Dp,
): Dp {
    val coerced = value.coerceIn(valueRange.start, valueRange.endInclusive)
    val positionFraction = calcFraction(valueRange.start, valueRange.endInclusive, coerced)

    return (boxWidth - labelWidth) * positionFraction
}

// Calculate the 0..1 fraction that `pos` value represents between `a` and `b`
private fun calcFraction(a: Float, b: Float, pos: Float) =
    (if (b - a == 0f) 0f else (pos - a) / (b - a)).coerceIn(0f, 1f)

@Composable
private fun SliderLabel(
    modifier: Modifier = Modifier,
    label: String,
    minWidth: Dp,
) {
    Text(
        modifier = modifier.defaultMinSize(minWidth = minWidth),
        text = label,
        textAlign = TextAlign.Center,
        style = Typography.subtitle2,
        color = TextGrey,
    )
}