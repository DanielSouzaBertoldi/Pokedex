package daniel.bertoldi.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import daniel.bertoldi.pokedex.data.database.model.relations.PokemonAbilitiesCrossRef

@Dao
interface PokemonAbilitiesCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemonAbilityCrossRef(join: PokemonAbilitiesCrossRef)
}