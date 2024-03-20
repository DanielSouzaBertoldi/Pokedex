package daniel.bertoldi.pokedex.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "abilities")
data class Abilities(
    @PrimaryKey val abilityId: Int,
    val name: String,
    @ColumnInfo(name = "effect_entries") val effectEntries: List<EffectEntry>,
    @ColumnInfo(name = "flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>,
    @ColumnInfo(name = "generation_name") val generationName: String,
    @ColumnInfo(name = "is_main_series") val isMainSeries: Boolean,
)

data class EffectEntry(
    val effect: String,
    val language: String,
    @ColumnInfo(name = "short_effect") val shortEffect: String,
)

data class FlavorTextEntry(
    @ColumnInfo(name = "flavor_text") val flavorText: String,
    val language: String,
    @ColumnInfo(name = "version_group_name") val versionGroupName: String? = null,
)