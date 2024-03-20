package daniel.bertoldi.pokedex.presentation.mapper

import daniel.bertoldi.pokedex.domain.model.GenerationData
import daniel.bertoldi.pokedex.presentation.model.filters.GenerationUIData
import javax.inject.Inject

private const val ARTWORK_BASE_URL =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
private const val IMAGE_EXT = ".png"
private val NUMBERS = listOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
private val ROMAN_NUMERALS =
    listOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")

class GenerationsDataToUIMapper @Inject constructor() {

    fun mapFrom(generations: List<GenerationData>) = generations.map {
        GenerationUIData(
            generationName = it.id.toRomanNumeral(),
            currentPokemonsImage = it.currentPokemons.map { pokemonId ->
                ARTWORK_BASE_URL + pokemonId + IMAGE_EXT
            }
        )
    }

    private fun Int.toRomanNumeral(): String {
        var str = "Generation "
        var n = this

        for (i in NUMBERS.indices) {
            while (n >= NUMBERS[i]) {
                n -= NUMBERS[i]
                str += ROMAN_NUMERALS[i]
            }
        }
        return str
    }
}