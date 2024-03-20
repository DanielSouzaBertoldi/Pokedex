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
//    val abilities: List<Ability>,
)

data class Sprites(
    val backDefault: String?,
    val backShiny: String?,
    val frontDefault: String?,
    val frontShiny: String?,
    val officialArtwork: String?,
)

//data class Types(
//    val slot: Int,
//    val type: GenericObject,
//)

//data class Ability(
//    val id: Int,
//    val isHidden: Boolean,
//    val slot: Int,
//)