package daniel.bertoldi.pokedex.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import dagger.hilt.android.AndroidEntryPoint
import daniel.bertoldi.pokedex.R
import daniel.bertoldi.pokedex.presentation.model.PokemonUiModel
import daniel.bertoldi.pokedex.presentation.viewmodel.MainActivityViewModel
import daniel.bertoldi.pokedex.ui.theme.PokedexTheme
import daniel.bertoldi.pokedex.ui.theme.Shapes
import daniel.bertoldi.pokedex.ui.theme.TextNumber
import daniel.bertoldi.pokedex.ui.theme.TextWhite
import daniel.bertoldi.pokedex.ui.theme.Typography

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val lazyPokemonPagingItems = viewModel.pokemonFlow.collectAsLazyPagingItems()

            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    MyAppNavHost(lazyPokemonPagingItems = lazyPokemonPagingItems)
                }
            }
        }
    }
}

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestinationName: String = "home",
    lazyPokemonPagingItems: LazyPagingItems<PokemonUiModel>,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestinationName,
    ) {
        composable("home") {
            PokemonCard(lazyPokemonPagingItems)
        }
    }
}

@Composable
fun PokemonCard(lazyPokemonPagingItems: LazyPagingItems<PokemonUiModel>) {
    LazyColumn {
        if (lazyPokemonPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(200.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.pokeball)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                    )
                    Text(
                        text = "Loading Pokémons!",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }

        items(lazyPokemonPagingItems) { pokemonUiModel ->
            Card(
                modifier = Modifier.padding(10.dp),
                elevation = 5.dp,
                backgroundColor = pokemonUiModel!!.backgroundColors.backgroundTypeColor,
                shape = Shapes.medium,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(115.dp),
                ) {
                    BackgroundPokeball(
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.CenterEnd)
                    )
                    BackgroundDots(modifier = Modifier)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 20.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                        ) {
                            PokemonInfo(pokemonUiModel)
                        }
                    }
                    AsyncImage(
                        modifier = Modifier
                            .size(130.dp)
                            .padding(end = 10.dp)
                            .align(Alignment.CenterEnd),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(pokemonUiModel.uiSprites.artwork)
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
            }
        }

        if (lazyPokemonPagingItems.loadState.append == LoadState.Loading) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(100.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(R.drawable.pikachu_running)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                    )
                }
            }
        }
    }
}

@Composable
private fun BackgroundPokeball(modifier: Modifier) {
    Image(
        modifier = modifier
            .offset(x = 10.dp),
        painter = painterResource(id = R.drawable.ic_pokeball_background),
        contentDescription = null,
        alpha = 0.2f,
        colorFilter = ColorFilter.tint(color = Color.White),
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun BackgroundDots(modifier: Modifier) {
    Image(
        modifier = modifier
            .size(100.dp)
            .offset(x = 90.dp, y = -20.dp),
        painter = painterResource(id = R.drawable.ic_card_dots),
        contentDescription = null,
        alpha = 0.2f,
        colorFilter = ColorFilter.tint(color = Color.White),
    )
}

@Composable
private fun PokemonInfo(pokemonUiModel: PokemonUiModel) {
    Text(
        text = pokemonUiModel.pokedexNumber,
        color = TextNumber,
        style = Typography.subtitle1,
    )
    Text(
        text = pokemonUiModel.name,
        color = TextWhite,
        style = Typography.h3,
    )
    LazyRow(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        items(pokemonUiModel.types) { typeUiData ->
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(shape = Shapes.small)
                    .background(color = typeUiData.backgroundColor)
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(id = typeUiData.icon),
                    contentDescription = null,
                    tint = Color.White,
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = typeUiData.name,
                    style = Typography.subtitle2,
                    color = TextWhite,
                )
            }
        }
    }
}
