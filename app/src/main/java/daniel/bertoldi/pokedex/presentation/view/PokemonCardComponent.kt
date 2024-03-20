package daniel.bertoldi.pokedex.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import daniel.bertoldi.pokedex.R
import daniel.bertoldi.pokedex.presentation.model.PokemonUiModel
import daniel.bertoldi.pokedex.ui.theme.Shapes
import daniel.bertoldi.pokedex.ui.theme.TextNumber
import daniel.bertoldi.pokedex.ui.theme.TextWhite
import daniel.bertoldi.pokedex.ui.theme.Typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PokemonCardComponent(
    pokemonUiModel: PokemonUiModel?,
    onNavigateToDetails: () -> Unit,
) {
    Card(
        modifier = Modifier.padding(10.dp),
        elevation = 5.dp,
        backgroundColor = pokemonUiModel!!.backgroundColors.backgroundTypeColor,
        shape = Shapes.medium,
        onClick = { onNavigateToDetails()  }
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
                    PokemonInfo(
                        pokemonUiModel = pokemonUiModel,
                        numberStyle = Typography.subtitle1,
                        nameStyle = Typography.h3,
                    )
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
fun PokemonInfo(
    pokemonUiModel: PokemonUiModel,
    numberStyle: TextStyle,
    nameStyle: TextStyle,
) {
    Text(
        text = pokemonUiModel.pokedexNumber,
        color = TextNumber,
        style = numberStyle,
    )
    Text(
        text = pokemonUiModel.name,
        color = TextWhite,
        style = nameStyle,
    )
    LazyRow(
        modifier = Modifier.wrapContentSize()
            .padding(top = 5.dp),
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