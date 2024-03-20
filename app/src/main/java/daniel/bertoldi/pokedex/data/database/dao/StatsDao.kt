package daniel.bertoldi.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import daniel.bertoldi.pokedex.data.database.model.Stats

@Dao
interface StatsDao {

    @Insert
    suspend fun insertStat(stat: Stats)
}