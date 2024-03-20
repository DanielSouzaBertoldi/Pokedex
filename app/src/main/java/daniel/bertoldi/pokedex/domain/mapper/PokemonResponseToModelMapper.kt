package daniel.bertoldi.pokedex.domain.mapper

import daniel.bertoldi.pokedex.data.api.response.*
import daniel.bertoldi.pokedex.data.database.dao.AbilitiesDao
import daniel.bertoldi.pokedex.domain.model.*
import javax.inject.Inject
import kotlin.random.Random

// the api actually returns the artwork URL so I don't need this const.
private const val SPRITES_BASE_URL =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"

class PokemonResponseToModelMapper @Inject constructor(
    private val abilitiesDao: AbilitiesDao,
) {

    fun mapFrom(pokemonResponse: PokemonResponse) = PokemonModel(
        abilities = mapAbilities(),
        height = pokemonResponse.height,
        id = pokemonResponse.id,
        isDefault = pokemonResponse.isDefault,
        name = pokemonResponse.name,
        sprites = mapSprites(pokemonResponse.sprites, pokemonResponse.id),
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

    private fun mapSprites(sprites: SpritesResponse, id: Int) = Sprites(
        backDefaultImageUrl = sprites.backDefault,
        backShinyImageUrl = sprites.backShiny,
        frontDefaultImageUrl = sprites.frontDefault,
        frontShinyImageUrl = sprites.frontShiny,
        artworkImageUrl = "$SPRITES_BASE_URL/other/official-artwork/$id.png",
    )

    private fun mapTypes(types: TypesResponse) = Types(
        slot = types.slot,
        type = Type(
            name = types.type.name,
            url = types.type.url,
        )
    )
}
