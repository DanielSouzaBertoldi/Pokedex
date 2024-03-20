package daniel.bertoldi.pokedex.data.database.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class StringListConverter {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @TypeConverter
    fun fromListString(list: List<String>) = Json.encodeToString(list)

    @TypeConverter
    fun toListString(encodedList: String) = Json.decodeFromString<List<String>>(encodedList)
}