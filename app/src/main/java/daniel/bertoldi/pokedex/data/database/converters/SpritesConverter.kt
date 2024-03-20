package daniel.bertoldi.pokedex.data.database.converters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import daniel.bertoldi.pokedex.data.database.model.Sprites

@OptIn(ExperimentalStdlibApi::class)
class SpritesConverter {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @TypeConverter
    fun fromSpriteEntry(sprite: Sprites): String {
        val jsonAdapter: JsonAdapter<Sprites> = moshi.adapter()

        return jsonAdapter.toJson(sprite)
    }

    @TypeConverter
    fun toSpriteEntry(json: String): Sprites {
        val jsonAdapter: JsonAdapter<Sprites> = moshi.adapter()

        return jsonAdapter.fromJson(json)!!
    }
}