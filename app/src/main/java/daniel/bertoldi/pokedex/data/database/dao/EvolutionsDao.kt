package daniel.bertoldi.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import daniel.bertoldi.pokedex.data.database.model.Evolution

@Dao
interface EvolutionsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvolution(evolution: Evolution)

    @Query("SELECT EXISTS (SELECT * FROM evolutions WHERE evolution_id = :evolutionId)")
    suspend fun isEvolutionInDatabase(evolutionId: Int): Boolean

    @Query(
        "SELECT * FROM evolutions WHERE evolution_id = :evolutionId"
    )
    fun getEvolutionsOfAPokemon(evolutionId: Int): List<Evolution>
}