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
            parentColumns = ["id"],
            childColumns = ["pokemonId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        )
    ]
)
data class Stats(
    @PrimaryKey val pokemonId: Int,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    @ColumnInfo(name = "special_attack") val specialAttack: Int,
    @ColumnInfo(name = "special_defense") val specialDefense: Int,
    val speed: Int,
    val accuracy: Int? = null,
    val evasion: Int? = null,
)
