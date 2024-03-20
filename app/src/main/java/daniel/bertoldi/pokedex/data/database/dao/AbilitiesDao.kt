package daniel.bertoldi.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import daniel.bertoldi.pokedex.data.database.model.Abilities
import daniel.bertoldi.pokedex.data.database.model.PokemonAbility

@Dao
interface AbilitiesDao {

    @Query("SELECT * FROM abilities WHERE ability_id = :abilityId")
    suspend fun getAbilityById(abilityId: Int): Abilities?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbility(ability: Abilities)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAbilities(ability: List<Abilities>)

    @Transaction
    @Query(
        "SELECT ab.ability_id, name, effect_entries, flavor_text_entries, generation_name, is_main_series, int.is_hidden, int.slot " +
        "FROM pokemon_abilities_cross_ref as int " +
        "LEFT JOIN abilities as ab " +
        "ON ab.ability_id = int.ability_id " +
        "WHERE int.pokemon_id = :pokemonId"
    )
    suspend fun getPokemonAbilities(pokemonId: Int): List<PokemonAbility>
}