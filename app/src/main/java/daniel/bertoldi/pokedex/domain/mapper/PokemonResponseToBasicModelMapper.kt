package daniel.bertoldi.pokedex.domain.mapper

import daniel.bertoldi.pokedex.data.api.response.*
import daniel.bertoldi.pokedex.data.database.dao.AbilitiesDao
import daniel.bertoldi.pokedex.domain.model.*
import javax.inject.Inject

class PokemonResponseToBasicModelMapper @Inject constructor() {

    fun mapFrom(pokemonResponse: PokemonResponse) = PokemonBasicModel(
        height = pokemonResponse.height,
        id = pokemonResponse.id,
        isDefault = pokemonResponse.isDefault,
        name = pokemonResponse.name,
        sprites = mapSprites(pokemonResponse.sprites),
        types = pokemonResponse.types.map { mapTypes(it) },
        weight = pokemonResponse.weight,
    )

    private fun mapSprites(sprites: SpritesResponse) = Sprites(
        backDefaultImageUrl = sprites.backDefault,
        backShinyImageUrl = sprites.backShiny,
        frontDefaultImageUrl = sprites.frontDefault,
        frontShinyImageUrl = sprites.frontShiny,
        artworkImageUrl = sprites.otherSprites.officialArtworkSprites.frontDefault.orEmpty(),
    )

    private fun mapTypes(types: TypesResponse) = Types(
        slot = types.slot,
        type = Type(
            name = types.type.name,
            url = types.type.url,
        )
    )
}
