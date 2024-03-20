package daniel.bertoldi.pokedex.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pokemons")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    @ColumnInfo(name = "is_default") val isDefault: Boolean,
)
