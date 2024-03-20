package daniel.bertoldi.pokedex.presentation.view

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import daniel.bertoldi.design.system.*
import daniel.bertoldi.pokedex.R
import daniel.bertoldi.pokedex.presentation.model.filters.FilterOptions
import daniel.bertoldi.pokedex.presentation.model.filters.GenerationUIData
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Math.PI
import java.lang.Math.cos
import java.lang.Math.min
import java.lang.Math.sin
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun GenerationsComponent(
    filterOptions: MutableStateFlow<FilterOptions>,
    onGenerationClicked: (GenerationUIData) -> Unit,
) {
    val filterOptionsState by filterOptions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
            )
    ) {

        LazyVerticalGrid(
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 30.dp, bottom = 50.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    color = TextBlack,
                    style = Typography.h3,
                    text = "Generations",
                )
            }

            item(span = { GridItemSpan(2) }) {
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 21.dp),
                    color = TextGrey,
                    style = Typography.body1,
                    text = "Use search for generations to explore your Pokémon!",
                )
            }

            items(filterOptionsState.generationOption) {
                GenerationComponent(
                    generationUIData = it,
                    onGenerationClicked = onGenerationClicked,
                )
            }
        }
    }
}

@Composable
fun GenerationComponent(
    generationUIData: GenerationUIData,
    onGenerationClicked: (GenerationUIData) -> Unit,
) {
    val generationTransition = updateTransition(
        targetState = generationUIData.isSelected,
        label = "generationTransition"
    )
    val cardBackgroundColor = generationTransition.animateColor(
        transitionSpec = { tween(400) },
        label = "generationCardBackgroundColor",
    ) { isSelected ->
        if (isSelected) PokemonUIData.PSYCHIC.typeColor else BgDefaultInput
    }
    val textColor = generationTransition.animateColor(
        transitionSpec = { tween(400) },
        label = "generationTextColor"
    ) { isSelected ->
        if (isSelected) TextWhite else TextGrey
    }

    Box(
        modifier = Modifier
            .width(160.dp)
            .wrapContentHeight()
            .padding(top = 14.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(cardBackgroundColor.value, cardBackgroundColor.value),
                )
            )
            .clickable {
                onGenerationClicked(generationUIData)
            },
        contentAlignment = Alignment.Center,
    ) {
        BackgroundDots(generationTransition)
        BackgroundPokeBall(generationTransition)
        Row(
            modifier = Modifier.align(Alignment.TopCenter)
                .padding(top = 25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            generationUIData.currentPokemonsImage.forEach { url ->
                AsyncImage(
                    modifier = Modifier.size(45.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(url)
                        .build(),
                    contentDescription = null,
                    placeholder = rememberAsyncImagePainter(
                        model = R.drawable.pokeball,
                    ),
                    error = painterResource(
                        id = R.drawable.missingno
                    ),
                )
            }
        }
        Text(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.BottomCenter),
            text = generationUIData.generationName,
            color = textColor.value,
            style = Typography.body1,
        )
    }
}

@Composable
fun BoxScope.BackgroundDots(generationTransition: Transition<Boolean>) {
    val dotsStartColor = generationTransition.animateColor(
        transitionSpec = { tween(400) },
        label = "generationDotsStartColor"
    ) { isSelected ->
        if (isSelected) BgWhite.copy(alpha = 0.3f) else GradientGrey
    }
    val dotsEndColor = generationTransition.animateColor(
        transitionSpec = { tween(400) },
        label = "generationDotsEndColor"
    ) { isSelected ->
        if (isSelected) BgWhite.copy(alpha = 0.0f) else GradientLightWhite.copy(alpha = 0.0f)
    }

    Image(
        modifier = Modifier
            .width(80.dp)
            .height(35.dp)
            .padding(top = 10.dp, start = 15.dp)
            .align(Alignment.TopStart)
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            .gradientBackground(
                colorStops = arrayOf(0.3f to dotsEndColor.value, 0.5f to dotsStartColor.value),
                angle = 101F,
            ),
        painter = painterResource(id = R.drawable.ic_card_dots),
        contentDescription = null,
    )
}

@Composable
fun BoxScope.BackgroundPokeBall(generationTransition: Transition<Boolean>) {
    val pokeballStartColor = generationTransition.animateColor(
        transitionSpec = { tween(400) },
        label = "generationPokeballStartColor"
    ) { isSelected ->
        if (isSelected) BgWhite.copy(alpha = 0.0f) else GradientLightWhite
    }
    val pokeballEndColor = generationTransition.animateColor(
        transitionSpec = { tween(400) },
        label = "generationPokeballEndColor"
    ) { isSelected ->
        if (isSelected) BgWhite.copy(alpha = 0.1f) else GradientLightGrey
    }


    Image(
        modifier = Modifier
            .size(110.dp)
            .align(Alignment.BottomEnd)
            .offset(x = 15.dp, y = 48.dp)
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            .gradientBackground(
                colorStops = arrayOf(
                    0.1f to pokeballStartColor.value,
                    0.8f to pokeballEndColor.value,
                ),
                angle = 135F,
            ),
        painter = painterResource(id = R.drawable.ic_pokeball_background),
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )
}

@Preview
@Composable
fun GenerationComponentPreview() {
    GenerationComponent(
        generationUIData = GenerationUIData(
            generationName = "Generation I",
            isSelected = false,
            currentPokemonsImage = listOf(
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/official-artwork/1",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/official-artwork/2",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/official-artwork/3",
            ),
        ),
        onGenerationClicked = {},
    )
}

fun Modifier.gradientBackground(colorStops: Array<Pair<Float, Color>>, angle: Float) = this.then(
    Modifier.drawWithContent {
        drawContent()

        val angleRad = angle / 180f * PI
        val x = cos(angleRad).toFloat() //Fractional x
        val y = sin(angleRad).toFloat() //Fractional y

        val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
        val offset = center + Offset(x * radius, y * radius)

        val exactOffset = Offset(
            x = min(offset.x.coerceAtLeast(0f), size.width),
            y = size.height - min(offset.y.coerceAtLeast(0f), size.height)
        )

        drawRect(
            brush = Brush.linearGradient(
                colorStops = colorStops,
                start = Offset(size.width, size.height) - exactOffset,
                end = exactOffset,
            ),
            blendMode = BlendMode.SrcIn,
        )
    }
)