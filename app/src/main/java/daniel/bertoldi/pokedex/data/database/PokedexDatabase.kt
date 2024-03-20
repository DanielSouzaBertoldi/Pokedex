package daniel.bertoldi.pokedex.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import daniel.bertoldi.pokedex.data.database.converters.EffectEntryConverter
import daniel.bertoldi.pokedex.data.database.converters.FlavorTextConverter
import daniel.bertoldi.pokedex.data.database.converters.PokemonEntryConverter
import daniel.bertoldi.pokedex.data.database.dao.AbilitiesDao
import daniel.bertoldi.pokedex.data.database.model.Abilities
import daniel.bertoldi.pokedex.data.database.model.Pokemon

private const val DATABASE_VERSION = 1

@Database(
    entities = [
        Pokemon::class,
        Abilities::class,
    ],
    version = DATABASE_VERSION,
)
@TypeConverters(
    EffectEntryConverter::class,
    FlavorTextConverter::class,
    PokemonEntryConverter::class,
)
abstract class PokedexDatabase : RoomDatabase() {

    abstract fun abilitiesDao(): AbilitiesDao
}