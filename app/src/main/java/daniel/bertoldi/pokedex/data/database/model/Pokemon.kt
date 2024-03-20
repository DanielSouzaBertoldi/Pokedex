package daniel.bertoldi.pokedex.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import daniel.bertoldi.pokedex.data.api.response.GenericObject

@Entity(tableName = "pokemons")
data class Pokemon(
    @PrimaryKey val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    @ColumnInfo(name = "is_default") val isDefault: Boolean,
    val sprites: Sprites,
    val types: List<GenericObject>,
)

data class Sprites(
    val backDefault: String?,
    val backShiny: String?,
    val frontDefault: String?,
    val frontShiny: String?,
    val officialArtwork: String?,
)