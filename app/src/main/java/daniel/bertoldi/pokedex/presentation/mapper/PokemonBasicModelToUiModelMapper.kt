package daniel.bertoldi.pokedex.presentation.mapper

import daniel.bertoldi.pokedex.domain.model.PokemonBasicModel
import daniel.bertoldi.pokedex.domain.model.Sprites
import daniel.bertoldi.pokedex.domain.model.Types
import daniel.bertoldi.pokedex.presentation.model.BackgroundColors
import daniel.bertoldi.pokedex.presentation.model.PokemonBasicUiModel
import daniel.bertoldi.pokedex.presentation.model.UiSprites
import daniel.bertoldi.pokedex.presentation.model.UiType
import daniel.bertoldi.pokedex.ui.theme.PokemonUIData
import java.util.Locale
import javax.inject.Inject

class PokemonBasicModelToUiModelMapper @Inject constructor() {

    fun mapFrom(pokemonBasicModel: PokemonBasicModel) = PokemonBasicUiModel(
        height = pokemonBasicModel.height,
        id = pokemonBasicModel.id,
        pokedexNumber = String.format(Locale.ROOT, "#%03d", pokemonBasicModel.id),
        isDefault = pokemonBasicModel.isDefault,
        name = pokemonBasicModel.name.capitalize(),
        uiSprites = mapSprites(pokemonBasicModel.sprites),
        types = pokemonBasicModel.types.map { mapTypes(it) },
        weight = pokemonBasicModel.weight,
        backgroundColors = mapCardBackgroundColors(pokemonBasicModel.types.first().type.name.uppercase())
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

    private fun String.capitalize() = this.replaceFirstChar {
        if (it.isLowerCase()) it.uppercase() else it.toString()
    }
}