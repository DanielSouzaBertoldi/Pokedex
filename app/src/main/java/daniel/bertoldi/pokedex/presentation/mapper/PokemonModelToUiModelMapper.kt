package daniel.bertoldi.pokedex.presentation.mapper

import daniel.bertoldi.pokedex.domain.model.*
import daniel.bertoldi.pokedex.presentation.model.*
import javax.inject.Inject

class PokemonModelToUiModelMapper @Inject constructor() {

    fun mapFrom(pokemonModel: PokemonModel) = PokemonUiModel(
        abilities = pokemonModel.abilities.map { mapAbilities(it) },
        height = pokemonModel.height,
        id = pokemonModel.id,
        isDefault = pokemonModel.isDefault,
        name = pokemonModel.name,
        uiSprites = mapSprites(pokemonModel.sprites),
        stats = pokemonModel.stats.map { mapStats(it) },
        types = pokemonModel.types.map { mapTypes(it) },
        weight = pokemonModel.weight,
    )

    private fun mapAbilities(abilities: Abilities) = UiAbilities(
        uiAbility = UiAbility(
            name = abilities.ability.name,
            url = abilities.ability.url,
        ),
        isHidden = abilities.isHidden,
        slot = abilities.slot,
    )

    private fun mapSprites(sprites: Sprites) = UiSprites(
        backDefault = sprites.backDefaultImageUrl,
        backShiny = sprites.backShinyImageUrl,
        frontDefault = sprites.frontDefaultImageUrl,
        frontShiny = sprites.frontShinyImageUrl,
        artwork = sprites.artworkImageUrl,
    )

    private fun mapStats(stats: Stats) = UiStats(
        baseStat = stats.baseStat,
        effort = stats.effort,
        uiStat = UiStat(
            name = stats.stat.name,
            url = stats.stat.url,
        )
    )

    private fun mapTypes(types: Types) = UiTypes(
        slot = types.slot,
        uiType = UiType(
            name = types.type.name,
            url = types.type.url,
        )
    )
}