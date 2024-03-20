package daniel.bertoldi.pokedex.presentation.mapper

import daniel.bertoldi.pokedex.domain.model.*
import daniel.bertoldi.pokedex.presentation.model.*
import daniel.bertoldi.pokedex.ui.theme.PokemonUIData
import java.util.Locale
import javax.inject.Inject

class PokemonModelToUiModelMapper @Inject constructor() {

    fun mapFrom(pokemonModel: PokemonModel) = PokemonUiModel(
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

    private fun mapAbilities(ability: Ability) = PokemonUiAbility(
        name = ability.name,
        isHidden = ability.isHidden,
        slot = ability.slot,
        effectEntry = ability.effectEntry,
        shortEffectEntry = ability.shortEffectEntry,
        flavorText = ability.flavorText,
        generation = ability.generation,
        isMainSeries = ability.isMainSeries,
    )

    private fun mapSprites(sprites: Sprites) = UiSprites(
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
            name = types.type.name.capitalize(),
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

    private fun String.capitalize() = this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
    }
}
