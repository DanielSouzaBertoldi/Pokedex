package daniel.bertoldi.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import daniel.bertoldi.pokedex.data.database.model.Pokemon

@Dao
interface PokemonDao {

    @Insert
    suspend fun insertPokemon(pokemon: Pokemon)
}