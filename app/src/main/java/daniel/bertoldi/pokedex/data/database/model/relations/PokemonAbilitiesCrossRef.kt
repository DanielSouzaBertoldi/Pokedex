package daniel.bertoldi.pokedex.data.database.model.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "pokemon_abilities_cross_ref",
    primaryKeys = ["ability_id", "pokemon_id"]
)
data class PokemonAbilitiesCrossRef(
    @ColumnInfo(name = "ability_id") val abilityId: Int,
    @ColumnInfo(name = "pokemon_id") val pokemonId: Int,
    @ColumnInfo(name = "is_hidden") val isHidden: Boolean,
    val slot: Int,
)
