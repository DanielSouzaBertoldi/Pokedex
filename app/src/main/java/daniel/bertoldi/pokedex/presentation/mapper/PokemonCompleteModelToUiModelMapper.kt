package daniel.bertoldi.pokedex.presentation.mapper

import daniel.bertoldi.pokedex.domain.model.AbilityModel
import daniel.bertoldi.pokedex.domain.model.PokemonCompleteModel
import daniel.bertoldi.pokedex.domain.model.SpritesModel
import daniel.bertoldi.pokedex.domain.model.Types
import daniel.bertoldi.pokedex.presentation.model.BackgroundColors
import daniel.bertoldi.pokedex.presentation.model.PokemonCompleteUiModel
import daniel.bertoldi.pokedex.presentation.model.PokemonUiAbility
import daniel.bertoldi.pokedex.presentation.model.UiSprites
import daniel.bertoldi.pokedex.presentation.model.UiType
import daniel.bertoldi.pokedex.ui.theme.PokemonUIData
import java.util.Locale
import javax.inject.Inject

class PokemonCompleteModelToUiModelMapper @Inject constructor() {

    fun mapFrom(pokemonModel: PokemonCompleteModel) = PokemonCompleteUiModel(
        abilities = pokemonModel.abilities.map { mapAbilities(it) },
        height = pokemonModel.height,
        id = pokemonModel.id,
        pokedexNumber = String.format(Locale.ROOT, "#%03d", pokemonModel.id),
        isDefault = pokemonModel.isDefault,
        name = pokemonModel.name.capitalize(),
        uiSprites = mapSprites(pokemonModel.sprites),
        types = pokemonModel.types.map { mapTypes(it) },
        weight = pokemonModel.weight,
        backgroundColors = mapCardBackgroundColors(pokemonModel.types.first().type.name.uppercase())
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

    private fun String.capitalize() = this.replaceFirstChar { it.uppercase() }
}