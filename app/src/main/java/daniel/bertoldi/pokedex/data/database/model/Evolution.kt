package daniel.bertoldi.pokedex.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "evolutions",
)
data class Evolution(
    @ColumnInfo(name = "evolution_name") @PrimaryKey val evolutionName: String,
    @ColumnInfo(name = "evolution_id") val evolutionId: Int,
    @ColumnInfo(name = "is_baby") val isBaby: Boolean,
    @ColumnInfo(name = "held_item") val heldItem: String?,
    @ColumnInfo(name = "known_move") val knownMove: String?,
    @ColumnInfo(name = "known_move_type") val knownMoveType: String?,
    @ColumnInfo(name = "min_level") val minLevel: Int?,
    @ColumnInfo(name = "min_happiness") val minHappiness: Int?,
    @ColumnInfo(name = "min_beauty") val minBeauty: Int?,
    @ColumnInfo(name = "min_affection") val minAffection: Int?,
    @ColumnInfo(name = "needs_overworld_rain") val needsOverworldRain: Boolean,
    @ColumnInfo(name = "party_species") val partySpecies: String?,
    @ColumnInfo(name = "party_type") val partyType: String?,
    @ColumnInfo(name = "relative_physical_stats") val relativePhysicalStats: Int?,
    @ColumnInfo(name = "time_of_day") val timeOfDay: String,
    @ColumnInfo(name = "turn_upside_down") val turnUpsideDown: Boolean,
    val location: String?,
    val trigger: String?,
    val item: String?,
    val gender: Int?,
)