package daniel.bertoldi.pokedex.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import dagger.hilt.android.AndroidEntryPoint
import daniel.bertoldi.pokedex.R
import daniel.bertoldi.pokedex.presentation.viewmodel.MainActivityViewModel
import daniel.bertoldi.pokedex.ui.theme.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getPokemons()

        setContent {
            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyAppNavHost()
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
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestinationName,
    ) {
        composable("home") {
            PokemonCard()
        }
    }
}

@Preview
@Composable
fun PokemonCard() {
    Card(
        modifier = Modifier.padding(10.dp),
        elevation = 5.dp,
        backgroundColor = BgTypeGrass,
        shape = Shapes.medium,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp),
        ) {
            Image(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = 20.dp),
                painter = painterResource(id = R.drawable.ic_pokeball_background),
                contentDescription = null,
                alpha = 0.2f,
                colorFilter = ColorFilter.tint(color = Color.White),
                contentScale = ContentScale.Crop,
            )
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .offset(x = 90.dp, y = (-20).dp),
                painter = painterResource(id = R.drawable.ic_card_dots),
                contentDescription = null,
                alpha = 0.2f,
                colorFilter = ColorFilter.tint(color = Color.White),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Text(
                        text = "#001",
                        color = TextNumber,
                        style = Typography.subtitle1,
                    )
                    Text(
                        text = "Bulbasaur",
                        color = TextWhite,
                        style = Typography.h3,
                    )
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(shape = Shapes.small)
                                .background(color = TypeGrass)
                                .padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(15.dp),
                                painter = painterResource(id = R.drawable.ic_grass_type),
                                contentDescription = null,
                                tint = Color.White,
                            )
                            Text(
                                modifier = Modifier.padding(start = 5.dp),
                                text = "Grass",
                                style = Typography.subtitle2,
                                color = TextWhite,
                            )
                        }
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(shape = Shapes.small)
                                .background(color = TypePoison)
                                .padding(5.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(15.dp),
                                painter = painterResource(id = R.drawable.ic_poison_type),
                                contentDescription = null,
                                tint = Color.White,
                            )
                            Text(
                                modifier = Modifier.padding(start = 5.dp),
                                text = "Poison",
                                style = Typography.subtitle2,
                                color = TextWhite,
                            )
                        }
                    }
                }
            }
            AsyncImage(
                modifier = Modifier
                    .size(130.dp)
                    .padding(end = 10.dp)
                    .align(Alignment.CenterEnd),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png")
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