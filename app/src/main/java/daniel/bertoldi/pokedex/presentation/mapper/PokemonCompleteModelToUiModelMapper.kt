package daniel.bertoldi.pokedex.presentation.mapper

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import daniel.bertoldi.pokedex.domain.model.*
import daniel.bertoldi.pokedex.presentation.model.*
import daniel.bertoldi.pokedex.ui.theme.PokemonUIData
import java.math.RoundingMode
import java.util.Locale
import javax.inject.Inject
import kotlin.math.floor

class PokemonCompleteModelToUiModelMapper @Inject constructor() {

    fun mapFrom(pokemonModel: PokemonCompleteModel) = PokemonCompleteUiModel(
        abilities = pokemonModel.abilities.map { mapAbilities(it) },
        height = pokemonModel.height.calculateHeight(),
        id = pokemonModel.id,
        pokedexNumber = String.format(Locale.ROOT, "#%03d", pokemonModel.id),
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
        typeEffectiveness = mapTypeEffectiveness(pokemonModel.typeEffectiveness)
    )

    private fun mapAbilities(abilityModel: AbilityModel) = PokemonUiAbility(
        name = abilityModel.name,
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

    private fun mapTypes(types: Types): UiType {
        val typeUiData = PokemonUIData.values().first { it.name == types.type.name.uppercase() }
        return UiType(
            slot = types.slot,
            name = types.type.name.uppercase(),
            url = types.type.url,
            backgroundColor = typeUiData.typeColor,
            icon = typeUiData.icon,
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
    )

    private fun getStatDetail(
        baseStat: Int,
        isHealthStat: Boolean = false
    ) = StatDetailUiModel(
        baseStat = baseStat,
        minMaxStat = baseStat.calculateStat(isHealthStat = isHealthStat),
        maxStat = baseStat.calculateStat(maxed = true, isHealthStat = isHealthStat),
    )

    private fun mapSpecies(species: SpeciesModel) = SpeciesUiModel(
        catchRate = getCatchRate(species.captureRate),
        genderRate = getGenderRate(species.genderRate),
        pokedexEntry = species.pokedexEntry,
        growthRate = species.growthRate,
        isBaby = species.isBaby,
        isLegendary = species.isLegendary,
        isMythical = species.isMythical,
        eggCycles = getEggCycles(species.hatchCounter),
        name = species.name,
    )

    private fun getCatchRate(captureRate: Int) = DuoTextUi(
        mainText = "$captureRate",
        secondaryText = "(5.9% with Pokéball, full HP)" // idk how to calculate this
    )

    private fun getGenderRate(genderRate: Int): AnnotatedString {
        return if (genderRate != -1) {
            val femaleRate = (genderRate / 1) * 100
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

    private fun getEggCycles(hatchCounter: Int): DuoTextUi {
        val minimumSteps = 128 * hatchCounter // for Pokemon Sword/Shild/Scarlet/Violet
        val maxSteps = 257 * hatchCounter // for Gen V and VI

        return DuoTextUi(
            mainText = "$hatchCounter",
            secondaryText = "($minimumSteps - $maxSteps)"
        )
    }

    private fun mapTypeEffectiveness(typeEffectiveness: TypeEffectivenessModel) =
        TypeEffectivenessUiModel(
            normal = typeEffectiveness.normal?.getTypeEffectiveness(),
            fighting = typeEffectiveness.normal?.getTypeEffectiveness(),
            flying = typeEffectiveness.normal?.getTypeEffectiveness(),
            poison = typeEffectiveness.normal?.getTypeEffectiveness(),
            ground = typeEffectiveness.normal?.getTypeEffectiveness(),
            rock = typeEffectiveness.normal?.getTypeEffectiveness(),
            bug = typeEffectiveness.normal?.getTypeEffectiveness(),
            ghost = typeEffectiveness.normal?.getTypeEffectiveness(),
            steel = typeEffectiveness.normal?.getTypeEffectiveness(),
            fire = typeEffectiveness.normal?.getTypeEffectiveness(),
            water = typeEffectiveness.normal?.getTypeEffectiveness(),
            grass = typeEffectiveness.normal?.getTypeEffectiveness(),
            electric = typeEffectiveness.normal?.getTypeEffectiveness(),
            psychic = typeEffectiveness.normal?.getTypeEffectiveness(),
            ice = typeEffectiveness.normal?.getTypeEffectiveness(),
            dragon = typeEffectiveness.normal?.getTypeEffectiveness(),
            dark = typeEffectiveness.normal?.getTypeEffectiveness(),
            fairy = typeEffectiveness.normal?.getTypeEffectiveness(),
            unknown = typeEffectiveness.normal?.getTypeEffectiveness(),
            shadow = typeEffectiveness.normal?.getTypeEffectiveness(),
        )

    private fun Float.getTypeEffectiveness() = when {
        this == 0.25f -> Effectiveness.STRONG
        this == 0.5f -> Effectiveness.KINDA_STRONG
        this == 1f -> Effectiveness.NORMAL
        this == 2.0f -> Effectiveness.WEAK
        else -> Effectiveness.NONE
    }

    private fun Int.calculateStat(maxed: Boolean = false, isHealthStat: Boolean): Int {
        val ev = if (maxed) 252 else 0
        val iv = if (maxed) 31 else 0
        val nmod = if (maxed) 1.1 else 0.9
        val baseCalculation = (this * 2 + iv + (ev / 4)) * 100 / 100
        return if (isHealthStat) {
            baseCalculation + 10 + 100
        } else {
            floor((baseCalculation + 5) * nmod).toInt()
        }
    }

    private fun String.capitalize() = this.replaceFirstChar { it.uppercase() }

    private fun Int.calculateHeight(): DuoTextUi {
        val length = 100 * (this.toDouble() / 10) / 2.54
        val feet = (length / 12).toBigDecimal().setScale(1, RoundingMode.HALF_UP).toInt()
        val inch = (length - 12 * feet).toBigDecimal().setScale(0, RoundingMode.HALF_UP).toInt()
        return DuoTextUi(
            mainText = "${this}m",
            secondaryText = "(${feet}'${inch.toString().padStart(2, '0')}\")"
        )
    }

    private fun Int.calculateWeight(): DuoTextUi {
        val kg = this.toDouble() / 10
        val pounds = "%.1f".format(kg / 0.45359237)
        return DuoTextUi(
            mainText = "${kg}kg",
            secondaryText = "($pounds)"
        )
    }
}