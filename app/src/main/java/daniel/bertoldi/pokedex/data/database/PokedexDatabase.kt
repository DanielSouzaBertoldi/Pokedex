package daniel.bertoldi.pokedex.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import daniel.bertoldi.pokedex.data.database.converters.EffectEntryConverter
import daniel.bertoldi.pokedex.data.database.converters.FlavorTextConverter
import daniel.bertoldi.pokedex.data.database.converters.GenericObjectConverter
import daniel.bertoldi.pokedex.data.database.converters.SpritesConverter
import daniel.bertoldi.pokedex.data.database.dao.AbilitiesDao
import daniel.bertoldi.pokedex.data.database.dao.PokemonAbilitiesCrossRefDao
import daniel.bertoldi.pokedex.data.database.model.Abilities
import daniel.bertoldi.pokedex.data.database.model.Pokemon
import daniel.bertoldi.pokedex.data.database.model.relations.PokemonAbilitiesCrossRef

private const val DATABASE_VERSION = 1

@Database(
    entities = [
        Pokemon::class,
        Abilities::class,
        PokemonAbilitiesCrossRef::class,
    ],
    version = DATABASE_VERSION,
)
@TypeConverters(
    EffectEntryConverter::class,
    FlavorTextConverter::class,
    SpritesConverter::class,
    GenericObjectConverter::class,
)
abstract class PokedexDatabase : RoomDatabase() {

    abstract fun abilitiesDao(): AbilitiesDao

    abstract fun pokemonAbilitiesCrossRef(): PokemonAbilitiesCrossRefDao
}