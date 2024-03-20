package daniel.bertoldi.pokedex.presentation.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import daniel.bertoldi.pokedex.data.database.dao.PokemonDao
import daniel.bertoldi.pokedex.domain.model.*
import daniel.bertoldi.pokedex.presentation.model.*
import daniel.bertoldi.design.system.PokemonUIData
import java.math.RoundingMode
import java.util.Locale
import javax.inject.Inject
import kotlin.math.floor

private const val POKEDEX_NUMBER_FORMAT = "#%03d"

class PokemonCompleteModelToUiModelMapper @Inject constructor(
    private val pokemonDao: PokemonDao,
) {

    suspend fun mapFrom(pokemonModel: PokemonCompleteModel): PokemonCompleteUiModel {
        return PokemonCompleteUiModel(
            abilities = pokemonModel.abilities.map { mapAbilities(it) }.sortedBy { it.slot },
            height = pokemonModel.height.calculateHeight(),
            id = pokemonModel.id,
            pokedexNumber = String.format(Locale.ROOT, POKEDEX_NUMBER_FORMAT, pokemonModel.id),
            pokedexEntry = pokemonModel.species.pokedexEntry,
            isDefault = pokemonModel.isDefault,
            name = pokemonModel.name.capitalize(),
            uiSprites = mapSprites(pokemonModel.sprites),
            types = pokemonModel.types.map { mapTypes(it) },
            weight = pokemonModel.weight.calculateWeight(),
            backgroundColors = mapCardBackgroundColors(
                pokemonModel.types.first().type.name.uppercase()
            ),
            baseExperience = pokemonModel.baseExperience,
            stats = mapStats(pokemonModel.stats),
            species = mapSpecies(pokemonModel.species),
            typeEffectiveness = mapTypeEffectiveness(pokemonModel.typeEffectiveness),
            evolutionChain = pokemonModel.evolutionChain.mapEvolutionChain(),
        )
    }

    private fun mapAbilities(abilityModel: AbilityModel) = PokemonUiAbility(
        name = abilityModel.name.replaceFirstChar { it.uppercase() },
        isHidden = abilityModel.isHidden,
        slot = abilityModel.slot,
        effectEntry = abilityModel.effectEntry,
        shortEffectEntry = abilityModel.shortEffectEntry,
        flavorText = abilityModel.flavorText,
        generation = abilityModel.generation,
        isMainSeries = abilityModel.isMainSeries,
    )

    private fun mapSprites(sprites: SpritesModel) = UiSprites(
        backDefault = sprites.backDefaultImageUrl,
        backShiny = sprites.backShinyImageUrl,
        frontDefault = sprites.frontDefaultImageUrl,
        frontShiny = sprites.frontShinyImageUrl,
        artwork = sprites.artworkImageUrl,
    )

    private fun mapTypes(types: Types): UiType { // duped logic
        val typeUiData = PokemonUIData.findTypeUiData(types.type.name)
        return UiType(
            slot = types.slot,
            name = types.type.name.replaceFirstChar { it.uppercase() },
            url = types.type.url,
            backgroundColor = typeUiData?.typeColor
                ?: Color.Transparent, // TODO: maybe throw an error instead of this?
            icon = typeUiData?.icon ?: -1,
            typeColor = typeUiData?.typeColor ?: Color.Transparent,
        )
    }

    private fun mapCardBackgroundColors(
        type: String,
    ) = BackgroundColors(
        typeColor = PokemonUIData.values().first { it.name == type }.typeColor,
        backgroundTypeColor = PokemonUIData.values().first { it.name == type }.backgroundColor,
    )

    private fun mapStats(stats: StatsModel) = StatsUiModel(
        hp = stats.hp?.let { getStatDetail(it, true) },
        attack = stats.attack?.let { getStatDetail(it) },
        defense = stats.defense?.let { getStatDetail(it) },
        specialAttack = stats.specialAttack?.let { getStatDetail(it) },
        specialDefense = stats.specialDefense?.let { getStatDetail(it) },
        speed = stats.speed?.let { getStatDetail(it) },
        accuracy = stats.accuracy?.let { getStatDetail(it) },
        evasion = stats.evasion?.let { getStatDetail(it) },
        total = ((stats.hp ?: 0) + (stats.attack ?: 0) + (stats.defense ?: 0) +
                (stats.specialAttack ?: 0) + (stats.specialDefense ?: 0) + (stats.speed ?: 0) +
                (stats.accuracy ?: 0) + (stats.evasion
            ?: 0)).toString() // TODO: ok, this is just filthy.
    )

    private fun getStatDetail(
        baseStat: Int,
        isHealthStat: Boolean = false
    ) = StatDetailUiModel(
        baseStat = baseStat.toString(),
        baseStatFloat = baseStat.toFloat() / 100,
        minMaxStat = baseStat.calculateStat(isHealthStat = isHealthStat),
        maxStat = baseStat.calculateStat(maxed = true, isHealthStat = isHealthStat),
    )

    private fun mapSpecies(species: SpeciesModel) = SpeciesUiModel(
        catchRate = getCatchRate(species.captureRate),
        genderRate = getGenderRate(species.genderRate),
        pokedexEntry = species.pokedexEntry,
        growthRate = species.growthRate.formatGrowthRate(),
        isBaby = species.isBaby,
        isLegendary = species.isLegendary,
        isMythical = species.isMythical,
        eggCycles = getEggCycles(species.hatchCounter),
        eggGroups = species.eggGroups.joinToString(separator = " ") { it.replaceFirstChar { firstChar -> firstChar.uppercase() } },
        name = species.name,
    )

    private fun getCatchRate(captureRate: Int) = subscriptText(
        mainText = "$captureRate",
        smallerText = "(5.9% with Pokéball, full HP)"// TODO: idk how to calculate this
    )

    private fun getGenderRate(genderRate: Int): AnnotatedString {
        return if (genderRate != -1) {
            val femaleRate = (genderRate.toDouble() / 8) * 100
            val maleRate = 100 - femaleRate

            buildAnnotatedString {
                withStyle(style = SpanStyle(color = PokemonUIData.FLYING.typeColor)) {
                    append("♀ $maleRate%")
                }
                append(", ")
                withStyle(style = SpanStyle(color = PokemonUIData.FAIRY.typeColor)) {
                    append("♂ $femaleRate%")
                }
            }
        } else {
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = PokemonUIData.ROCK.typeColor)) {
                    append("⚧ Genderless")
                }
            }
        }
    }

    private fun getEggCycles(hatchCounter: Int): AnnotatedString {
        val minimumSteps = 128 * hatchCounter // for Pokemon Sword/Shild/Scarlet/Violet
        val maxSteps = 257 * hatchCounter // for Gen V and VI

        return subscriptText(
            mainText = "$hatchCounter",
            smallerText = "($minimumSteps - $maxSteps)",
        )
    }

    private fun mapTypeEffectiveness(typeEffectiveness: TypeEffectivenessModel) =
        TypeEffectivenessUiModel(
            normal = typeEffectiveness.normal?.getTypeEffectiveness(),
            fighting = typeEffectiveness.fighting?.getTypeEffectiveness(),
            flying = typeEffectiveness.flying?.getTypeEffectiveness(),
            poison = typeEffectiveness.poison?.getTypeEffectiveness(),
            ground = typeEffectiveness.ground?.getTypeEffectiveness(),
            rock = typeEffectiveness.rock?.getTypeEffectiveness(),
            bug = typeEffectiveness.bug?.getTypeEffectiveness(),
            ghost = typeEffectiveness.ghost?.getTypeEffectiveness(),
            steel = typeEffectiveness.steel?.getTypeEffectiveness(),
            fire = typeEffectiveness.fire?.getTypeEffectiveness(),
            water = typeEffectiveness.water?.getTypeEffectiveness(),
            grass = typeEffectiveness.grass?.getTypeEffectiveness(),
            electric = typeEffectiveness.electric?.getTypeEffectiveness(),
            psychic = typeEffectiveness.psychic?.getTypeEffectiveness(),
            ice = typeEffectiveness.ice?.getTypeEffectiveness(),
            dragon = typeEffectiveness.dragon?.getTypeEffectiveness(),
            dark = typeEffectiveness.dark?.getTypeEffectiveness(),
            fairy = typeEffectiveness.fairy?.getTypeEffectiveness(),
            unknown = typeEffectiveness.unknown?.getTypeEffectiveness(),
            shadow = typeEffectiveness.shadow?.getTypeEffectiveness(),
        )

    private suspend fun EvolutionChainDetailsModel.mapEvolutionChain(): EvolutionChainUiModel {
        val pokemonData = pokemonDao.getPokemonIdAndSprites(name)
        return EvolutionChainUiModel(
            name = name.replaceFirstChar { it.uppercase() },
            pokedexNumber = String.format(Locale.ROOT, POKEDEX_NUMBER_FORMAT, pokemonData.pokemonId),
            imageUrl = pokemonData.sprites.officialArtwork.orEmpty(),
            isBaby = isBaby,
            heldItem = heldItem,
            knownMove = knownMove,
            knownMoveType = knownMoveType,
            minLevel = minLevel,
            minHappiness = minHappiness,
            minBeauty = minBeauty,
            minAffection = minAffection,
            needsOverworldRain = needsOverworldRain,
            partySpecies = partySpecies,
            partyType = partyType,
            relativePhysicalStats = relativePhysicalStats,
            timeOfDay = timeOfDay,
            turnUpsideDown = turnUpsideDown,
            location = location,
            trigger = trigger,
            item = item,
            gender = gender,
            nextEvolutions = nextEvolution.map { it.mapEvolutionChain() },
        )
    }

    private fun Float.getTypeEffectiveness() = when {
        this == 0.25f -> Effectiveness.STRONG
        this == 0.5f -> Effectiveness.KINDA_STRONG
        this == 1f -> Effectiveness.NORMAL
        this == 2.0f -> Effectiveness.WEAK
        else -> Effectiveness.NONE
    }

    private fun Int.calculateStat(maxed: Boolean = false, isHealthStat: Boolean): String {
        val ev = if (maxed) 252 else 0
        val iv = if (maxed) 31 else 0
        val nmod = if (maxed) 1.1 else 0.9
        val baseCalculation = (this * 2 + iv + (ev / 4)) * 100 / 100
        return if (isHealthStat) {
            (baseCalculation + 10 + 100).toString()
        } else {
            floor((baseCalculation + 5) * nmod).toInt().toString()
        }
    }

    private fun String.capitalize() = this.replaceFirstChar { it.uppercase() }

    private fun Int.calculateHeight(): AnnotatedString {
        val meters = this.toDouble() / 10
        val length = 100 * meters / 2.54
        val feet = (length / 12).toBigDecimal().setScale(1, RoundingMode.HALF_UP).toInt()
        val inch = (length - 12 * feet).toBigDecimal().setScale(0, RoundingMode.HALF_UP).toInt()
        return subscriptText(
            mainText = "${meters}m",
            smallerText =
            "(${feet}'${inch.toString().padStart(2, '0')}\")"
        )
    }

    private fun Int.calculateWeight(): AnnotatedString {
        val kg = this.toDouble() / 10
        val pounds = "%.1f".format(kg / 0.45359237)
        return subscriptText(
            mainText = "${kg.toString().padStart(2, '0')}kg",
            smallerText = "($pounds)",
        )
    }

    private fun subscriptText(mainText: String, smallerText: String) = buildAnnotatedString {
        append(mainText)
        append(" ")
        withStyle(SpanStyle(fontSize = 13.sp)) {
            append(smallerText)
        }
    }

    private fun String.formatGrowthRate() = this
        .split("-")
        .joinToString(" ") {
            it.replaceFirstChar { firstChar -> firstChar.uppercase() }
        }
}