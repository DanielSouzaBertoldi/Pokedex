package daniel.bertoldi.pokedex.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import daniel.bertoldi.pokedex.data.api.response.GenericObject

@Entity(
    tableName = "evolutions",
)
data class Evolution(
    @ColumnInfo(name = "evolution_chain_id") @PrimaryKey val evolutionChainId: Int,
    val chainDetails: ChainDetails,
)

data class ChainDetails(
    val evolutionName: String,
    val isBaby: Boolean,
    val evolutionDetails: List<EvolutionDetails>,
    val evolvesTo: List<ChainDetails>
)

data class EvolutionDetails(
    val item: String?,
    val trigger: String?,
    val gender: Int?,
    val knownMove: String?,
    val heldItem: String?,
    val knownMoveType: String?,
    val location: String?,
    val minLevel: Int?,
    val minHappiness: Int?,
    val minBeauty: Int?,
    val minAffection: Int?,
    val needsOverworldRain: Boolean,
    val partySpecies: String?,
    val partyType: String?,
    val relativePhysicalStats: Int?,
    val timeOfDay: String,
    val turnUpsideDown: Boolean,
)