package daniel.bertoldi.pokedex.data.database.converters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import daniel.bertoldi.pokedex.data.database.model.ChainDetails

@OptIn(ExperimentalStdlibApi::class)
class ChainDetailsConverter {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @TypeConverter
    fun fromChainDetails(chainDetails: ChainDetails): String {
        val jsonAdapter: JsonAdapter<ChainDetails> = moshi.adapter()

        return jsonAdapter.toJson(chainDetails)
    }

    @TypeConverter
    fun toChainDetails(json: String): ChainDetails? {
        val jsonAdapter: JsonAdapter<ChainDetails> = moshi.adapter()

        return jsonAdapter.fromJson(json)
    }
}