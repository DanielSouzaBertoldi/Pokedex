package daniel.bertoldi.pokedex.domain.mapper

import daniel.bertoldi.pokedex.data.api.response.GenericObject
import daniel.bertoldi.pokedex.data.database.model.Pokemon
import daniel.bertoldi.pokedex.data.database.model.Sprites
import daniel.bertoldi.pokedex.domain.model.PokemonBasicModel
import daniel.bertoldi.pokedex.domain.model.Type
import daniel.bertoldi.pokedex.domain.model.Types
import javax.inject.Inject
import daniel.bertoldi.pokedex.domain.model.SpritesModel as SpritesModel

class PokemonEntityToBasicModelMapper @Inject constructor() {

    fun mapFrom(pokemonEntity: Pokemon) = PokemonBasicModel(
        height = pokemonEntity.height,
        id = pokemonEntity.pokemonId,
        isDefault = pokemonEntity.isDefault,
        name = pokemonEntity.name,
        sprites = mapSprites(pokemonEntity.sprites),
        types = mapTypes(pokemonEntity.types),
        weight = pokemonEntity.weight,
    )

    private fun mapSprites(sprites: Sprites) = SpritesModel(
        backDefaultImageUrl = sprites.backDefault,
        backShinyImageUrl = sprites.backShiny,
        frontDefaultImageUrl = sprites.frontDefault,
        frontShinyImageUrl = sprites.frontShiny,
        artworkImageUrl = sprites.officialArtwork.orEmpty(),
    )

    private fun mapTypes(types: List<GenericObject>) = types.map { type ->
        Types(
            slot = 1, // mocked
            type = Type(
                name = type.name,
                url = type.url,
            )
        )
    }
}