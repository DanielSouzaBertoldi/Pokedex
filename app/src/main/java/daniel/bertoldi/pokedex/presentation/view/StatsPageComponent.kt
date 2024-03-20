package daniel.bertoldi.pokedex.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import daniel.bertoldi.pokedex.presentation.model.Effectiveness
import daniel.bertoldi.pokedex.presentation.model.PokemonCompleteUiModel
import daniel.bertoldi.pokedex.presentation.model.StatDetailUiModel
import daniel.bertoldi.pokedex.presentation.model.TypeEffectivenessUiModel
import daniel.bertoldi.pokedex.ui.theme.BgWhite
import daniel.bertoldi.pokedex.ui.theme.PokemonUIData
import daniel.bertoldi.pokedex.ui.theme.TextBlack
import daniel.bertoldi.pokedex.ui.theme.TextGrey
import daniel.bertoldi.pokedex.ui.theme.Typography

@Composable
fun StatsPage(pokemonDetails: PokemonCompleteUiModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
            .padding(start = 40.dp, end = 40.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )
        SectionTitleComponent(title = "Base Stats")
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            val pokemonTypeColor = pokemonDetails.types[0].typeColor // TODO: check if this gets called when recomposing
            pokemonDetails.stats.hp?.let { StatRow("HP", it, pokemonTypeColor) }
            pokemonDetails.stats.attack?.let { StatRow("Attack", it,  pokemonTypeColor) }
            pokemonDetails.stats.defense?.let { StatRow("Defense", it, pokemonTypeColor) }
            pokemonDetails.stats.specialAttack?.let {
                StatRow("Sp. Atk", it, pokemonTypeColor)
            }
            pokemonDetails.stats.specialDefense?.let {
                StatRow("Sp. Def", it, pokemonTypeColor)
            }
            pokemonDetails.stats.speed?.let { StatRow("Speed", it, pokemonTypeColor) }
            pokemonDetails.stats.accuracy?.let {
                StatRow("Accuracy", it, pokemonTypeColor)
            }
            pokemonDetails.stats.evasion?.let { StatRow("Evasion", it, pokemonTypeColor) }
            StatRow(
                statName = "Total",
                pokemonTypeColor = Color.Transparent,
                total = pokemonDetails.stats.total,
            )
        }
        Text(
            modifier = Modifier.padding(vertical = 20.dp),
            text = "The ranges shown on the right are for a level 100 Pokémon. Maximum values are based on a beneficial nature, 252 EVs, 31 IVs; minimum values are based on a hindering nature, 0 EVs, 0 IVs.",
            style = Typography.subtitle2,
            color = TextGrey,
        )

        SectionTitleComponent(title = "Type Defenses")
        TypeEffectivenessComponent(pokemonDetails.name, pokemonDetails.typeEffectiveness)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
    }
}

@Composable
private fun StatRow(
    statName: String,
    statDetailUiModel: StatDetailUiModel? = null,
    pokemonTypeColor: Color,
    total: String = "",
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(0.3f),
            text = statName,
            style = Typography.subtitle1,
            color = TextBlack,
        )
        Text(
            modifier = Modifier.weight(0.21f),
            text = statDetailUiModel?.baseStat ?: total,
            style = if (statDetailUiModel?.baseStat != null) Typography.body1 else Typography.h4,
            color = TextGrey,
            textAlign = TextAlign.End,
        )
        LinearProgressIndicator(
            modifier = Modifier
                .weight(1f),
            color = pokemonTypeColor,
            backgroundColor = Color.Transparent,
            progress = statDetailUiModel?.baseStatFloat ?: 0f,
            strokeCap = StrokeCap.Round,
        )
        Text(
            modifier = Modifier.weight(0.2f),
            text = statDetailUiModel?.minMaxStat ?: "Min",
            style = if (statDetailUiModel?.minMaxStat != null) {
                Typography.body1
            } else {
                Typography.subtitle1
            },
            color = if (statDetailUiModel?.minMaxStat != null) TextGrey else TextBlack,
            textAlign = TextAlign.End,
        )
        Text(
            modifier = Modifier.weight(0.2f),
            text = statDetailUiModel?.maxStat ?: "Max",
            style = if (statDetailUiModel?.maxStat != null) {
                Typography.body1
            } else {
                Typography.subtitle1
            },
            color = if (statDetailUiModel?.minMaxStat != null) TextGrey else TextBlack,
            textAlign = TextAlign.End,
        )
    }
}

@Composable
private fun TypeEffectivenessComponent(
    pokemonName: String,
    typeEffectivenessUiModel: TypeEffectivenessUiModel,
) {
    Text(
        modifier = Modifier.padding(bottom = 20.dp),
        text = "The effectiveness of each type on ${pokemonName}.",
        style = Typography.body1,
        color = TextGrey,
    )
    val pokemonTypes = PokemonUIData.values() // should this be remembered?
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth().height(128.dp),
        columns = GridCells.Fixed(9),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(pokemonTypes) { pokemonType ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    modifier = Modifier
                        .size(25.dp)
                        .background(
                            color = pokemonType.typeColor,
                            shape = RoundedCornerShape(3.dp),
                        )
                        .padding(5.dp),
                    painter = painterResource(id = pokemonType.icon),
                    contentDescription = null,
                    tint = BgWhite,
                )
                val effectiveness = typeEffectivenessUiModel.getTypeEffectivenessByName(
                    name = pokemonType.name,
                )
                val text = when (effectiveness) {
                    Effectiveness.WEAK -> "2"
                    Effectiveness.NORMAL -> ""
                    Effectiveness.KINDA_STRONG -> "½"
                    Effectiveness.STRONG -> "¼"
                    else -> null
                }
                Text(
                    text = text ?: "",
                    style = Typography.body1,
                    color = TextGrey,
                )
            }
        }
    }
}