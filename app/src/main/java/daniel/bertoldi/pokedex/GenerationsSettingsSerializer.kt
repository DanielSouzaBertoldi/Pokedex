package daniel.bertoldi.pokedex

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object GenerationsSettingsSerializer : Serializer<GenerationsData> {
    override val defaultValue: GenerationsData
        get() = GenerationsData()

    override suspend fun readFrom(input: InputStream): GenerationsData {
        return try {
            Json.decodeFromString(
                deserializer = GenerationsData.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: GenerationsData, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = GenerationsData.serializer(),
                value = t,
            ).encodeToByteArray()
        )
    }

}