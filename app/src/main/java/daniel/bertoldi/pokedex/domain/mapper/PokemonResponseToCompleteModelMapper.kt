package daniel.bertoldi.pokedex.domain.mapper

import daniel.bertoldi.pokedex.data.api.response.PokemonResponse
import daniel.bertoldi.pokedex.data.api.response.SpritesResponse
import daniel.bertoldi.pokedex.data.api.response.TypesResponse
import daniel.bertoldi.pokedex.domain.model.Ability
import daniel.bertoldi.pokedex.domain.model.PokemonCompleteModel
import daniel.bertoldi.pokedex.domain.model.Sprites
import daniel.bertoldi.pokedex.domain.model.Type
import daniel.bertoldi.pokedex.domain.model.Types
import javax.inject.Inject
import kotlin.random.Random

class PokemonResponseToCompleteModelMapper @Inject constructor() {

    fun mapFrom(pokemonResponse: PokemonResponse) = PokemonCompleteModel(
        abilities = mapAbilities(),
        height = pokemonResponse.height,
        id = pokemonResponse.id,
        isDefault = pokemonResponse.isDefault,
        name = pokemonResponse.name,
        sprites = mapSprites(pokemonResponse.sprites),
        types = pokemonResponse.types.map { mapTypes(it) },
        weight = pokemonResponse.weight,
    )

    private fun mapAbilities(): List<Ability> {
//        val effectEntry = ability.effectEntries.first { it.language.name == DEFAULT_LANGUAGE }
//        val pokemonAbilityData = ability.pokemon.first { it.data.name == pokemonName }

        return listOf(
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