package daniel.bertoldi.pokedex.domain.mapper

import daniel.bertoldi.pokedex.data.api.response.*
import daniel.bertoldi.pokedex.domain.model.*
import javax.inject.Inject

private const val SPRITES_BASE_URL =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"

class PokemonResponseToModelMapper @Inject constructor() {

    fun mapFrom(pokemonResponse: PokemonResponse) = PokemonModel(
        abilities = pokemonResponse.abilities.map { mapAbilities(it) },
        height = pokemonResponse.height,
        id = pokemonResponse.id,
        isDefault = pokemonResponse.isDefault,
        name = pokemonResponse.name,
        sprites = mapSprites(pokemonResponse.sprites, pokemonResponse.id),
        stats = pokemonResponse.stats.map { mapStats(it) },
        types = pokemonResponse.types.map { mapTypes(it) },
        weight = pokemonResponse.weight,
    )

    private fun mapAbilities(abilities: AbilitiesResponse) = Abilities(
        ability = Ability(
            name = abilities.ability.name,
            url = abilities.ability.url,
        ),
        isHidden = abilities.isHidden,
        slot = abilities.slot,
    )

    private fun mapSprites(sprites: SpritesResponse, id: Int) = Sprites(
        backDefault = sprites.backDefault,
        backShiny = sprites.backShiny,
        frontDefault = sprites.frontDefault,
        frontShiny = sprites.frontShiny,
        artwork = "$SPRITES_BASE_URL/other/official-artwork/$id.png",
    )

    private fun mapStats(stats: StatsResponse) = Stats(
        baseStat = stats.baseStat,
        effort = stats.effort,
        stat = Stat(
            name = stats.stat.name,
            url = stats.stat.url,
        )
    )

    private fun mapTypes(types: TypesResponse) = Types(
        slot = types.slot,
        type = Type(
            name = types.type.name,
            url = types.type.url,
        )
    )
}