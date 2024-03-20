package daniel.bertoldi.pokedex.data.database.converters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import daniel.bertoldi.pokedex.data.api.response.GenericObject

@OptIn(ExperimentalStdlibApi::class)
class GenericObjectConverter {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @TypeConverter
    fun fromGenericObjectList(list: List<GenericObject>): String {
        val jsonAdapter: JsonAdapter<List<GenericObject>> = moshi.adapter()

        return jsonAdapter.toJson(list)
    }

    @TypeConverter
    fun toGenericObjectList(json: String): List<GenericObject> {
        val jsonAdapter: JsonAdapter<List<GenericObject>> = moshi.adapter()

        return jsonAdapter.fromJson(json).orEmpty()
    }

    @TypeConverter
    fun fromGenericObject(genericObject: GenericObject): String {
        val jsonAdapter: JsonAdapter<GenericObject> = moshi.adapter()

        return jsonAdapter.toJson(genericObject)
    }

    @TypeConverter
    fun toGenericObject(json: String): GenericObject {
        val jsonAdapter: JsonAdapter<GenericObject> = moshi.adapter()

        return jsonAdapter.fromJson(json)!!
    }
}