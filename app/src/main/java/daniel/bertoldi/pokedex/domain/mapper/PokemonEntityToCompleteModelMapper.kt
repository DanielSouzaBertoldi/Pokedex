package daniel.bertoldi.pokedex.domain.mapper

import daniel.bertoldi.pokedex.data.api.response.GenericObject
import daniel.bertoldi.pokedex.data.database.model.Pokemon
import daniel.bertoldi.pokedex.data.database.model.Sprites
import daniel.bertoldi.pokedex.domain.model.Ability
import daniel.bertoldi.pokedex.domain.model.PokemonCompleteModel
import daniel.bertoldi.pokedex.domain.model.Type
import daniel.bertoldi.pokedex.domain.model.Types
import javax.inject.Inject
import kotlin.random.Random

class PokemonEntityToCompleteModelMapper @Inject constructor() {

    fun mapFrom(pokemonEntity: Pokemon) = PokemonCompleteModel(
        abilities = mapAbilities(),
        height = pokemonEntity.height,
        id = pokemonEntity.id,
        isDefault = pokemonEntity.isDefault,
        name = pokemonEntity.name,
        sprites = mapSprites(pokemonEntity.sprites),
        types = mapTypes(pokemonEntity.types),
        weight = pokemonEntity.weight,
    )

    // why is this mocked??
    private fun mapAbilities() = mutableListOf<Ability>().apply {
        repeat(3) { // mocked
            add(
                Ability(
                    id = Random.nextInt(),
                    name = "mock",
                    isHidden = Random.nextBoolean(),
                    slot = Random.nextInt(),
                    effectEntry = "mock",
                    shortEffectEntry = "mock",
                    flavorText = "mock",
                    generation = "mock",
                    isMainSeries = Random.nextBoolean(),
                )
            )
        }
    }

    private fun mapSprites(sprites: Sprites) = daniel.bertoldi.pokedex.domain.model.Sprites(
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