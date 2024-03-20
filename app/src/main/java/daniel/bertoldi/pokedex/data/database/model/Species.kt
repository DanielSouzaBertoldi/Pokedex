package daniel.bertoldi.pokedex.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "species",
)
data class Species(
    @PrimaryKey val speciesId: Int,
    @ColumnInfo(name = "base_hapiness") val baseHappiness: Int,
    @ColumnInfo(name = "capture_rate") val captureRate: Int,
    @ColumnInfo(name = "egg_groups") val eggGroups: List<String>,
    @ColumnInfo(name = "gender_rate") val genderRate: Int,
    @ColumnInfo(name = "flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>,
    @ColumnInfo(name = "growth_rate") val growthRate: String,
    @ColumnInfo(name = "is_baby") val isBaby: Boolean,
    @ColumnInfo(name = "is_legendary") val isLegendary: Boolean,
    @ColumnInfo(name = "is_mythical") val isMythical: Boolean,
    @ColumnInfo(name = "hatch_counter") val hatchCounter: Int,
)