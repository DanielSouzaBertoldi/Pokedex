package daniel.bertoldi.pokedex.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import daniel.bertoldi.pokedex.R
import daniel.bertoldi.pokedex.presentation.model.EvolutionChainUiModel
import daniel.bertoldi.pokedex.presentation.model.PokemonCompleteUiModel
import daniel.bertoldi.pokedex.ui.theme.GradientGrey
import daniel.bertoldi.pokedex.ui.theme.GradientLightWhite
import daniel.bertoldi.pokedex.ui.theme.TextBlack
import daniel.bertoldi.pokedex.ui.theme.TextGrey
import daniel.bertoldi.pokedex.ui.theme.Typography

@Composable
fun EvolutionPage(pokemonDetails: PokemonCompleteUiModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
            .padding(start = 40.dp, end = 40.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )
        SectionTitleComponent(title = "Evolution Chart")
        PokemonEvolutionRow(evolutionData = pokemonDetails.evolutionChain)

//        LazyVerticalGrid(
//            modifier = Modifier.fillMaxWidth(),
//            columns = GridCells.Fixed(count = 10),
//            verticalArrangement = Arrangement.spacedBy(5.dp),
//            horizontalArrangement = Arrangement.spacedBy(5.dp)
//        ) {
//            addPokemonEvolutionRow(this, pokemonDetails.evolutionChain)
//        }
    }
}

@Composable
private fun PokemonEvolutionRow(
    modifier: Modifier = Modifier,
    evolutionData: EvolutionChainUiModel
) { // scope: LazyGridScope,
//    if (evolutionData.nextEvolutions.isNotEmpty()) {
//        scope.apply {
//            item {
//                PokemonBox(evolutionChainUiModel = evolutionData)
//                PokemonBox(evolutionChainUiModel = evolutionData.nextEvolutions.first())
//            }
//        }
//        evolutionData.nextEvolutions.forEach {
//            addPokemonEvolutionRow(scope, it)
//        }
//    }
    if (evolutionData.nextEvolutions.isNotEmpty()) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            PokemonBox(evolutionChainUiModel = evolutionData)
            EvolutionDetails(evolutionData.minLevel)
            PokemonBox(evolutionChainUiModel = evolutionData.nextEvolutions.first())
        }
        evolutionData.nextEvolutions.forEach { nextEvolutions ->
            PokemonEvolutionRow(
                modifier = Modifier.padding(top = 30.dp),
                evolutionData = nextEvolutions,
            )
        }
    }
}

@Composable
private fun RowScope.PokemonBox(evolutionChainUiModel: EvolutionChainUiModel) {
    Column(
        modifier = Modifier
            .weight(0.6f)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                    .gradientBackground(
                        colorStops = arrayOf(
                            0f to GradientGrey,
                            0.8f to Color.Transparent,
                        ),
                        angle = 330f,
                    ),
                painter = painterResource(id = R.drawable.ic_pokeball_background),
                contentDescription = null,
            )
            AsyncImage(
                modifier = Modifier.size(75.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(evolutionChainUiModel.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                placeholder = rememberAsyncImagePainter(model = R.drawable.pokeball),
                error = rememberAsyncImagePainter(model = R.drawable.missingno),
            )
        }
        Text(
            color = TextGrey,
            style = Typography.subtitle2,
            text = evolutionChainUiModel.pokedexNumber,
        )
        Text(
            color = TextBlack,
            style = Typography.h4,
            text = evolutionChainUiModel.name,
        )
    }
}

@Composable
private fun RowScope.EvolutionDetails(minLevel: Int?) {
    Column(
        modifier = Modifier.weight(.5f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = painterResource(id = R.drawable.ic_evolves_to), contentDescription = null)
        minLevel?.let {
            Text(
                modifier = Modifier.padding(top = 5.dp),
                color = TextBlack,
                style = Typography.h4,
                text = "(Level $minLevel)",
            )
        }
    }
}