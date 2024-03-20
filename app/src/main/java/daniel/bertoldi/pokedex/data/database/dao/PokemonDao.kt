package daniel.bertoldi.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import daniel.bertoldi.pokedex.data.database.model.Pokemon
import daniel.bertoldi.pokedex.data.database.model.PokemonIdAndSprites

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemon(pokemon: Pokemon)

    @Query("SELECT EXISTS(SELECT * FROM pokemons WHERE pokemonId = :pokemonId)")
    suspend fun isPokemonInDatabase(pokemonId: Int): Boolean

    @Query("SELECT * FROM pokemons WHERE pokemonId = :pokemonId")
    suspend fun getPokemonById(pokemonId: Int): Pokemon

    @Query("SELECT has_complete_data FROM pokemons WHERE pokemonId = :pokemonId")
    suspend fun pokemonHasCompleteData(pokemonId: Int): Boolean

    @Query("UPDATE pokemons SET has_complete_data = 1 WHERE pokemonId = :pokemonId")
    suspend fun updatePokedexEntry(pokemonId: Int)

    @Query("SELECT pokemonId, sprites FROM pokemons WHERE name = :name")
    suspend fun getPokemonIdAndSprites(name: String): PokemonIdAndSprites
}