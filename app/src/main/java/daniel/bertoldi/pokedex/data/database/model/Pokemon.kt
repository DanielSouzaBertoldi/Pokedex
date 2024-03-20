package daniel.bertoldi.pokedex.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class Pokemon(
    @PrimaryKey val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    @ColumnInfo(name = "is_default") val isDefault: Boolean,
)
