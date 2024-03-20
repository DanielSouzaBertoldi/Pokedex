package daniel.bertoldi.pokedex.domain.model

import androidx.datastore.core.Serializer as DataStoreSerializer
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class GenerationsData(
    @Serializable(with = PersistentListSerializer::class)
    val generation: PersistentList<GenerationData> = persistentListOf(),
)

@Serializable
data class GenerationData(
    val id: Int,
    @Serializable(with = IntRangeSerializer::class)
    val range: IntRange,
    val currentPokemons: List<Int>,
)

object GenerationsDataSerializer : DataStoreSerializer<GenerationsData> {
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

object IntRangeSerializer : KSerializer<IntRange> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("IntRange") {
        element<Int>("minValue")
        element<Int>("maxValue")
    }

    override fun deserialize(decoder: Decoder): IntRange = decoder.decodeStructure(descriptor) {
        var minValue = 0
        var maxValue = 0
        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> minValue = decodeIntElement(descriptor, 0)
                1 -> maxValue = decodeIntElement(descriptor, 1)
                CompositeDecoder.DECODE_DONE -> break
                else -> error("Unexpected index: $index")
            }
        }
        IntRange(minValue, maxValue)
    }

    override fun serialize(encoder: Encoder, value: IntRange) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor, 0, value.first)
            encodeIntElement(descriptor, 1, value.last)
        }
    }
}


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = PersistentList::class)
class PersistentListSerializer(private val dataSerializer: KSerializer<String>) : KSerializer<PersistentList<String>> {
    private class PersistentListDescriptor : SerialDescriptor by serialDescriptor<List<String>>() {
        override val serialName: String = "kotlinx.serialization.immutable.persistentList"
    }
    override val descriptor: SerialDescriptor = PersistentListDescriptor()

    override fun serialize(encoder: Encoder, value: PersistentList<String>) {
        return ListSerializer(dataSerializer).serialize(encoder, value.toList())
    }

    override fun deserialize(decoder: Decoder): PersistentList<String> {
        return ListSerializer(dataSerializer).deserialize(decoder).toPersistentList()
    }
}