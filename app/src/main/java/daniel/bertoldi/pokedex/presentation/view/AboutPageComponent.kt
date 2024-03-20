package daniel.bertoldi.pokedex.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import daniel.bertoldi.pokedex.presentation.model.Effectiveness
import daniel.bertoldi.pokedex.presentation.model.PokemonCompleteUiModel
import daniel.bertoldi.pokedex.presentation.model.PokemonUiAbility
import daniel.bertoldi.pokedex.ui.theme.BgWhite
import daniel.bertoldi.pokedex.ui.theme.PokemonUIData
import daniel.bertoldi.pokedex.ui.theme.TextBlack
import daniel.bertoldi.pokedex.ui.theme.TextGrey
import daniel.bertoldi.pokedex.ui.theme.Typography

@Composable
internal fun AboutPage(
    pokemonDetails: PokemonCompleteUiModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
            .padding(start = 40.dp, end = 40.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.fillMaxWidth().height(30.dp))
        Text(
            text = pokemonDetails.pokedexEntry,
            color = TextGrey,
            style = Typography.body1,
        )
        PokedexDataComponent(
            speciesName = pokemonDetails.species.name,
            height = pokemonDetails.height,
            weight = pokemonDetails.weight,
            abilities = pokemonDetails.abilities,
            weaknesses = pokemonDetails.typeEffectiveness.getATypeEffectiveness(Effectiveness.WEAK),
        )
        TrainingComponent(
            catchRate = pokemonDetails.species.catchRate,
            baseExp = pokemonDetails.baseExperience,
            growthRate = pokemonDetails.species.growthRate,
        )
        BreedingComponent(
            gender = pokemonDetails.species.genderRate,
            eggGroups = pokemonDetails.species.eggGroups,
            eggCycles = pokemonDetails.species.eggCycles,
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(50.dp))
    }
}

@Composable
private fun PokedexDataComponent(
    speciesName: String,
    height: AnnotatedString,
    weight: AnnotatedString,
    abilities: List<PokemonUiAbility>,
    weaknesses: List<String>,
) {
    SectionTitleComponent(modifier = Modifier.padding(top = 30.dp), title = "Pokédex Data")

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SimpleRow("Species", speciesName)
        SimpleRow("Height", detailAnnotated = height)
        SimpleRow("Weight", detailAnnotated = weight)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(0.4f),
                text = "Abilities",
                style = Typography.subtitle2,
                color = TextBlack,
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
            ) {
                abilities.filterNot { it.isHidden }.forEach {
                    Text(
                        text = "${it.slot}. ${it.name}",
                        style = Typography.body1,
                        color = TextGrey,
                    )
                }
                abilities.filter { it.isHidden }.forEach {
                    Text(
                        text = "${it.name} (hidden ability)",
                        style = Typography.subtitle1,
                        color = TextGrey,
                    )
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(0.4f),
                text = "Weaknesses",
                style = Typography.subtitle2,
                color = TextBlack,
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                weaknesses.forEach { weaknessType ->
                    PokemonUIData.findTypeUiData(weaknessType)?.let { uiData ->
                        Box(
                            modifier = Modifier
                                .size(25.dp)
                                .background(
                                    color = uiData.typeColor,
                                    shape = RoundedCornerShape(3.dp)
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                modifier = Modifier.size(15.dp),
                                painter = painterResource(id = uiData.icon),
                                contentDescription = null,
                                tint = BgWhite,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TrainingComponent(
    catchRate: AnnotatedString,
    baseExp: Int,
    growthRate: String,
) {
    SectionTitleComponent(modifier = Modifier.padding(top = 30.dp), title = "Training")

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Missing EV Yield (?)
        SimpleRow(title = "Catch Rate", detailAnnotated = catchRate)
        // Missing Base Friendship (??)
        SimpleRow(title = "Base Exp", detail = baseExp.toString())
        SimpleRow(title = "Growth Rate", detail = growthRate)
    }
}

@Composable
private fun BreedingComponent(
    gender: AnnotatedString,
    eggGroups: String,
    eggCycles: AnnotatedString
) {
    SectionTitleComponent(modifier = Modifier.padding(top = 30.dp), title = "Breeding")

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SimpleRow(title = "Gender", detailAnnotated = gender)
        SimpleRow(title = "Egg Groups", detail = eggGroups)
        SimpleRow(title = "Egg Cycles", detailAnnotated = eggCycles)
    }
}