package daniel.bertoldi.pokedex.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "stats",
    foreignKeys = [
        ForeignKey(
            entity = Pokemon::class,
            parentColumns = ["pokemonId"],
            childColumns = ["statPokemonId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        )
    ]
)
data class Stats(
    @PrimaryKey val statPokemonId: Int,
    val hp: Int? = null,
    val attack: Int? = null,
    val defense: Int? = null,
    @ColumnInfo(name = "special_attack") val specialAttack: Int? = null,
    @ColumnInfo(name = "special_defense") val specialDefense: Int? = null,
    val speed: Int? = null,
    val accuracy: Int? = null,
    val evasion: Int? = null,
)
