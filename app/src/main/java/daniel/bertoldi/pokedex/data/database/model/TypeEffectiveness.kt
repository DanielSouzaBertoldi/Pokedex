package daniel.bertoldi.pokedex.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "type_effectiveness",
    primaryKeys = ["first_type", "second_type"]
)
class TypeEffectiveness(
    @ColumnInfo(name = "first_type") val firstType: String,
    @ColumnInfo(name = "second_type") val secondType: String,
    val normal: Float?,
    val fighting: Float?,
    val flying: Float?,
    val poison: Float?,
    val ground: Float?,
    val rock: Float?,
    val bug: Float?,
    val ghost: Float?,
    val steel: Float?,
    val fire: Float?,
    val water: Float?,
    val grass: Float?,
    val electric: Float?,
    val psychic: Float?,
    val ice: Float?,
    val dragon: Float?,
    val dark: Float?,
    val fairy: Float?,
    val unknown: Float?,
    val shadow: Float?,
)