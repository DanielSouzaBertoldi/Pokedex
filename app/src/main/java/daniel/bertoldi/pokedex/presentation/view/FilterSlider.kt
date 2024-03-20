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
import daniel.bertoldi.design.system.BgDefaultInput
import daniel.bertoldi.design.system.PokemonUIData
import daniel.bertoldi.design.system.TextBlack
import daniel.bertoldi.design.system.TextGrey
import daniel.bertoldi.design.system.Typography

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
        modifier = modifier.padding(top = 35.dp, bottom = 20.dp),
        text = "Number Range",
        color = daniel.bertoldi.design.system.TextBlack,
        style = daniel.bertoldi.design.system.Typography.h4,
    )

    Column(modifier = modifier) {
        RangeSlider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            onValueChangeFinished = { /* Do something here */ },
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                thumbColor = daniel.bertoldi.design.system.PokemonUIData.PSYCHIC.typeColor.compositeOver(Color.White),
                activeTrackColor = daniel.bertoldi.design.system.PokemonUIData.PSYCHIC.typeColor,
                inactiveTrackColor = daniel.bertoldi.design.system.BgDefaultInput,
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
                boxWidth = if (sliderPosition.endInclusive > 1090F) maxWidth - 5.dp else maxWidth, // honestly no ideia why this kinda works
                labelWidth = labelMinWidth,
            )

            if (sliderPosition.start > valueRange.start) {
                SliderLabel(
                    modifier = Modifier.padding(start = offset),
                    label = sliderPosition.start.toInt().toString(),
                    minWidth = labelMinWidth,
                )
            } else {
                SliderLabel(label = valueRange.start.toInt().toString(), minWidth = labelMinWidth)
            }

            if (sliderPosition.endInclusive > valueRange.start) {
                SliderLabel(
                    modifier = Modifier.padding(start = endOffset),
                    label = sliderPosition.endInclusive.toInt().toString(),
                    minWidth = labelMinWidth,
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
        style = daniel.bertoldi.design.system.Typography.subtitle2,
        color = daniel.bertoldi.design.system.TextGrey,
    )
}