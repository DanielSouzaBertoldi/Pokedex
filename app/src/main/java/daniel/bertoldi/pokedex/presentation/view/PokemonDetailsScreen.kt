package daniel.bertoldi.pokedex.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import daniel.bertoldi.pokedex.R
import daniel.bertoldi.pokedex.presentation.model.PokemonUiModel
import daniel.bertoldi.pokedex.ui.theme.Typography
import daniel.bertoldi.pokedex.ui.theme.pokemonFont

@Composable
fun PokemonDetailsScreen(
    onBackClicked: () -> Unit,
) {
    val viewModel = hiltViewModel<PokemonDetailsViewModel>()
    val screenState by viewModel.detailsScreenState.collectAsState()
    val pokemonDetails by viewModel.pokemonDetails.collectAsState()

    when (screenState) {
        DetailsScreenState.LOADING -> LoadingState()
        DetailsScreenState.ERROR -> ErrorState()
        else -> SuccessState(pokemonDetails!!, onBackClicked)
    }
}

@Composable
private fun LoadingState() {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "To caregando calma"
    )
}

@Composable
private fun ErrorState() {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "Ja era, quebrou alguma coisa..."
    )
}

@Composable
private fun SuccessState(
    pokemonDetails: PokemonUiModel,
    onBackClicked: () -> Unit,
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth(),
        color = pokemonDetails.backgroundColors.backgroundTypeColor,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = pokemonDetails.backgroundColors.backgroundTypeColor),
            ) {
                BackgroundPokemonName(name = pokemonDetails.name)
                Icon(
                    modifier = Modifier
                        .padding(start = 40.dp, top = 40.dp)
                        .clickable { onBackClicked() },
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Go back",
                )
                PokemonRow(pokemonDetails)
                Image(
                    modifier = Modifier
                        .offset(x = 100.dp)
                        .padding(top = 187.dp)
                        .align(Alignment.BottomEnd)
                        .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                        .gradientBackground(
                            colorStops = arrayOf(
                                0.2f to Color.Transparent,
                                1f to Color.White.copy(alpha = 0.3f)
                            ),
                            angle = 90f,
                        ),
                    painter = painterResource(id = R.drawable.ic_background_dots),
                    contentDescription = null,
                )
            }
            SheetContent()
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun BoxScope.BackgroundPokemonName(name: String) {
    Text(
        modifier = Modifier
            .padding(top = 10.dp)
            .offset(x = (-86).dp) // calculate offset given text size + screen size maybe?
            .align(Alignment.TopCenter),
        text = name.uppercase(),
        style = TextStyle.Default.copy(
            fontSize = 100.sp,
            fontFamily = pokemonFont,
            fontWeight = FontWeight.Bold,
            drawStyle = Stroke(
                miter = 10f,
                width = 5f,
                join = StrokeJoin.Round,
            ),
            brush = Brush.verticalGradient(
                colorStops = arrayOf(
                    0.2f to Color.Transparent,
                    1f to Color.White.copy(alpha = 0.5f),
                ),
                startY = Float.POSITIVE_INFINITY,
                endY = 0f,
                tileMode = TileMode.Decal,
            )
        ),
        overflow = TextOverflow.Visible,
        softWrap = false,
    )
}

@Composable
private fun PokemonRow(pokemonDetails: PokemonUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 40.dp, top = 95.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PokemonImage(pokemonDetails.uiSprites.artwork)
        PokemonMainDetails(pokemonDetails)
    }
}

@Composable
private fun PokemonImage(artwork: String) {
    AsyncImage(
        modifier = Modifier
            .size(125.dp)
            .drawBehind {
                drawCircle(
                    brush = Brush.linearGradient(
                        colorStops = arrayOf(
                            0.45f to Color.Transparent,
                            1f to Color.White.copy(alpha = 0.5f),
                        ),
                    ),
                    style = Stroke(
                        miter = 10f,
                        width = 15f,
                        join = StrokeJoin.Round,
                    )
                )
            },
        model = ImageRequest.Builder(LocalContext.current)
            .data(artwork)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        placeholder = rememberAsyncImagePainter(
            model = R.drawable.pokeball,
        ),
        error = painterResource(
            id = R.drawable.missingno
        ),
    )
}

@Composable
private fun PokemonMainDetails(pokemonDetails: PokemonUiModel) {
    Column(
        modifier = Modifier
            .padding(start = 25.dp)
            .wrapContentHeight()
    ) {
        PokemonInfo(
            pokemonUiModel = pokemonDetails,
            numberStyle = Typography.h4,
            nameStyle = Typography.h2,
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun SheetContent() {
    val pageCount = 3
    val pagerState = rememberPagerState()
    val pagerTitle = remember {
        listOf("About", "Stats", "Evolution")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(top = 15.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(pageCount) {
            val textStyle = if (it == pagerState.currentPage) Typography.h4 else Typography.body1
            Box(
                modifier = Modifier.width(100.dp),
            ) {
                if (it == pagerState.currentPage) {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                            .gradientBackground(
                                arrayOf(
                                    0.3f to Color.White.copy(alpha = 0.5f),
                                    1f to Color.Transparent,
                                ),
                                angle = 340f,
                            ),
                        painter = painterResource(id = R.drawable.ic_pokeball_background),
                        contentDescription = null,
                        alpha = 0.5f,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.TopCenter,
                    )
                }

                Text(
                    modifier = Modifier
                        .width(100.dp)
                        .align(Alignment.Center),
                    text = pagerTitle[it],
                    style = textStyle,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        count = 3,
        state = pagerState,
        itemSpacing = 5.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .padding(start = 40.dp, end = 40.dp, top = 40.dp, bottom = 50.dp)
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Salve vadalismo",
                color = Color.Black,
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
                color = Color.Black,
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
                color = Color.Black,
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
                color = Color.Black,
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
                color = Color.Black,
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
                color = Color.Black,
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
                color = Color.Black,
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
                color = Color.Black,
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
                color = Color.Black,
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
                color = Color.Black,
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Salve vadalismo",
            )
        }
    }
}

private fun Color.darken() = ColorUtils.blendARGB(this.toArgb(), Color.Black.toArgb(), .5f)