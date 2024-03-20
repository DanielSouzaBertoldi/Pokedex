package daniel.bertoldi.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import daniel.bertoldi.pokedex.data.database.model.TypeEffectiveness

@Dao
interface TypeEffectivenessDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTypeEffectiveness(typeEffectiveness: TypeEffectiveness)

    @Query(
        "SELECT * FROM type_effectiveness " +
        "WHERE first_type = :firstType AND second_type = :secondType"
    )
    suspend fun getTypeEffectiveness(firstType: String, secondType: String): TypeEffectiveness

    @Query(
        "SELECT EXISTS (" +
                "SELECT * FROM type_effectiveness " +
                "WHERE first_type = :firstType AND second_type = :secondType" +
        ")"
    )
    suspend fun isTypeEffectivenessInDataBase(firstType: String, secondType: String): Boolean
}