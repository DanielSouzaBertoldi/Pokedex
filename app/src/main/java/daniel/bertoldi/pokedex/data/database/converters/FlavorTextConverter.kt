package daniel.bertoldi.pokedex.data.database.converters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import daniel.bertoldi.pokedex.data.database.model.FlavorTextEntry

@OptIn(ExperimentalStdlibApi::class)
class FlavorTextConverter {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @TypeConverter
    fun fromFlavorTextEntry(list: List<FlavorTextEntry>): String {
        val jsonAdapter: JsonAdapter<List<FlavorTextEntry>> = moshi.adapter()

        return jsonAdapter.toJson(list)
    }

    @TypeConverter
    fun toFlavorTextEntry(json: String): List<FlavorTextEntry> {
        val jsonAdapter: JsonAdapter<List<FlavorTextEntry>> = moshi.adapter()

        return jsonAdapter.fromJson(json).orEmpty()
    }
}