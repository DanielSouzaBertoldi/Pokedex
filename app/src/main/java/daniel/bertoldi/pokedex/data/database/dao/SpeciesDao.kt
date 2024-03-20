package daniel.bertoldi.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import daniel.bertoldi.pokedex.data.database.model.Species

@Dao
interface SpeciesDao {

    @Insert
    suspend fun insertSpecies(species: Species)
}