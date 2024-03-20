package daniel.bertoldi.pokedex.data.database.converters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import daniel.bertoldi.pokedex.data.database.model.EffectEntry

@OptIn(ExperimentalStdlibApi::class)
class EffectEntryConverter {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @TypeConverter
    fun fromEffectEntries(list: List<EffectEntry>): String {
        val jsonAdapter: JsonAdapter<List<EffectEntry>> = moshi.adapter()

        return jsonAdapter.toJson(list)
    }

    @TypeConverter
    fun toEffectEntry(json: String): List<EffectEntry> {
        val jsonAdapter: JsonAdapter<List<EffectEntry>> = moshi.adapter()

        return jsonAdapter.fromJson(json).orEmpty()
    }
}