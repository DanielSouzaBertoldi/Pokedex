package daniel.bertoldi.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import daniel.bertoldi.pokedex.data.database.model.Species

@Dao
interface SpeciesDao {

    @Insert
    suspend fun insertSpecies(species: Species)

    @Query("SELECT EXISTS (SELECT * FROM species WHERE speciesId = :speciesId)")
    suspend fun isSpeciesInDatabase(speciesId: Int): Boolean

    @Query("SELECT * FROM species WHERE speciesId = :speciesId")
    suspend fun getSpeciesData(speciesId: Int): Species
}