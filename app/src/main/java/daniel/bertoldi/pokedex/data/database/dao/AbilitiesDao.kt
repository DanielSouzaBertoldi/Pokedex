package daniel.bertoldi.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import daniel.bertoldi.pokedex.data.database.model.Abilities

@Dao
interface AbilitiesDao {

    @Query("SELECT * FROM abilities WHERE id = :abilityId")
    suspend fun getAbilityById(abilityId: Int): Abilities?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbility(ability: Abilities)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAbilities(ability: List<Abilities>)
}