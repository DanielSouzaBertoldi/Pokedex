package daniel.bertoldi.pokedex.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import daniel.bertoldi.pokedex.data.database.converters.EvolutionChainConverter
import daniel.bertoldi.pokedex.data.database.converters.EffectEntryConverter
import daniel.bertoldi.pokedex.data.database.converters.FlavorTextConverter
import daniel.bertoldi.pokedex.data.database.converters.GenericObjectConverter
import daniel.bertoldi.pokedex.data.database.converters.SpritesConverter
import daniel.bertoldi.pokedex.data.database.converters.StringListConverter
import daniel.bertoldi.pokedex.data.database.dao.*
import daniel.bertoldi.pokedex.data.database.model.Abilities
import daniel.bertoldi.pokedex.data.database.model.Evolution
import daniel.bertoldi.pokedex.data.database.model.Pokemon
import daniel.bertoldi.pokedex.data.database.model.Species
import daniel.bertoldi.pokedex.data.database.model.Stats
import daniel.bertoldi.pokedex.data.database.model.TypeEffectiveness
import daniel.bertoldi.pokedex.data.database.model.relations.PokemonAbilitiesCrossRef

private const val DATABASE_VERSION = 1

@Database(
    entities = [
        Pokemon::class,
        Abilities::class,
        PokemonAbilitiesCrossRef::class,
        Stats::class,
        Species::class,
        TypeEffectiveness::class,
        Evolution::class,
    ],
    version = DATABASE_VERSION,
)
@TypeConverters(
    EffectEntryConverter::class,
    FlavorTextConverter::class,
    SpritesConverter::class,
    GenericObjectConverter::class,
    StringListConverter::class,
    EvolutionChainConverter::class,
)
abstract class PokedexDatabase : RoomDatabase() {

    abstract fun abilitiesDao(): AbilitiesDao

    abstract fun pokemonDao(): PokemonDao

    abstract fun pokemonAbilitiesCrossRef(): PokemonAbilitiesCrossRefDao

    abstract fun statsDao(): StatsDao

    abstract fun speciesDao(): SpeciesDao

    abstract fun typeEffectivenessDao(): TypeEffectivenessDao

    abstract fun evolutionsDao(): EvolutionsDao
}