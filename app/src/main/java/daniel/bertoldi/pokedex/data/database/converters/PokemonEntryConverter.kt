package daniel.bertoldi.pokedex.data.database.converters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import daniel.bertoldi.pokedex.data.database.model.PokemonEntry

@OptIn(ExperimentalStdlibApi::class)
class PokemonEntryConverter {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @TypeConverter
    fun fromPokemonEntry(list: List<PokemonEntry>): String {
        val jsonAdapter: JsonAdapter<List<PokemonEntry>> = moshi.adapter()

        return jsonAdapter.toJson(list)
    }

    @TypeConverter
    fun toPokemonEntry(json: String): List<PokemonEntry> {
        val jsonAdapter: JsonAdapter<List<PokemonEntry>> = moshi.adapter()

        return jsonAdapter.fromJson(json).orEmpty()
    }
}