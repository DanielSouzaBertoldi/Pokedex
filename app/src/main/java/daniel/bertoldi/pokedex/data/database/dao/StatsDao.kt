package daniel.bertoldi.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import daniel.bertoldi.pokedex.data.database.model.Stats

@Dao
interface StatsDao {

    @Insert
    suspend fun insertStat(stat: Stats)

    @Query("SELECT * FROM stats WHERE statPokemonId = :pokemonId")
    suspend fun getPokemonStat(pokemonId: Int): Stats
}