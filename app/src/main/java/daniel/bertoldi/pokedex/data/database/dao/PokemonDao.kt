package daniel.bertoldi.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import daniel.bertoldi.pokedex.data.database.model.Pokemon

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemon(pokemon: Pokemon)

    @Query("SELECT EXISTS(SELECT * FROM pokemons WHERE id = :pokemonId)")
    suspend fun isPokemonInDatabase(pokemonId: Int): Boolean

    @Query("SELECT * FROM pokemons WHERE id = :pokemonId")
    suspend fun getPokemonById(pokemonId: Int): Pokemon

    @Query("SELECT has_complete_data FROM pokemons WHERE id = :pokemonId")
    suspend fun pokemonHasCompleteData(pokemonId: Int): Boolean

    @Update(entity = Pokemon::class)
    suspend fun updatePokedexEntry(pokemon: Pokemon)
}