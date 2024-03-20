package daniel.bertoldi.pokedex.domain.mapper

import daniel.bertoldi.pokedex.data.api.response.*
import daniel.bertoldi.pokedex.domain.model.*
import javax.inject.Inject

// the api actually returns the artwork URL so I don't need this const.
private const val SPRITES_BASE_URL =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
private const val DEFAULT_LANGUAGE = "en"

class PokemonResponseToModelMapper @Inject constructor() {

    fun mapFrom(pokemonResponse: PokemonResponse, abilities: List<AbilityResponse>) = PokemonModel(
        abilities = abilities.map { mapAbilities(it, pokemonResponse.name) },
        height = pokemonResponse.height,
        id = pokemonResponse.id,
        isDefault = pokemonResponse.isDefault,
        name = pokemonResponse.name,
        sprites = mapSprites(pokemonResponse.sprites, pokemonResponse.id),
        stats = pokemonResponse.stats.map { mapStats(it) },
        types = pokemonResponse.types.map { mapTypes(it) },
        weight = pokemonResponse.weight,
    )

    private fun mapAbilities(ability: AbilityResponse, pokemonName: String): Ability {
        val effectEntry = ability.effectEntries.first { it.language.name == DEFAULT_LANGUAGE }
        val pokemonAbilityData = ability.pokemon.first { it.data.name == pokemonName }

        return Ability(
            id = ability.id,
            name = ability.name,
            isHidden = pokemonAbilityData.isHidden,
            slot = pokemonAbilityData.slot,
            effectEntry = effectEntry.effect,
            shortEffectEntry = effectEntry.shortEffect,
            flavorText = ability.flavorTextEntries.first {
                it.language.name == DEFAULT_LANGUAGE
            }.flavorText,
            generation = ability.generation.name,
            isMainSeries = ability.isMainSeries,
        )
    }
    private fun mapSprites(sprites: SpritesResponse, id: Int) = Sprites(
        backDefaultImageUrl = sprites.backDefault,
        backShinyImageUrl = sprites.backShiny,
        frontDefaultImageUrl = sprites.frontDefault,
        frontShinyImageUrl = sprites.frontShiny,
        artworkImageUrl = "$SPRITES_BASE_URL/other/official-artwork/$id.png",
    )

    private fun mapStats(stats: StatsResponse): Stats {
        return Stats(
            baseStat = stats.baseStat,
            effort = stats.effort,
            stat = Stat(
                name = stats.stat.name,
                url = stats.stat.url,
            )
        )
    }

    private fun mapTypes(types: TypesResponse) = Types(
        slot = types.slot,
        type = Type(
            name = types.type.name,
            url = types.type.url,
        )
    )
}
