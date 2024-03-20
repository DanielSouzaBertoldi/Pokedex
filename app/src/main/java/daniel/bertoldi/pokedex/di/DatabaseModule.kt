package daniel.bertoldi.pokedex.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import daniel.bertoldi.pokedex.data.database.PokedexDatabase
import javax.inject.Singleton

private const val DATABASE_NAME = "PokedexDatabase"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context,
    ): PokedexDatabase = Room.databaseBuilder(
        app,
        PokedexDatabase::class.java,
        DATABASE_NAME,
    ).build()

    @Provides
    fun provideAbilitiesDao(pokedexDatabase: PokedexDatabase) = pokedexDatabase.abilitiesDao()

    @Provides
    fun pokemonDao(pokedexDatabase: PokedexDatabase) = pokedexDatabase.pokemonDao()

    @Provides
    fun providePokemonAbilitiesCrossRefDao(pokedexDatabase: PokedexDatabase) =
        pokedexDatabase.pokemonAbilitiesCrossRef()

    @Provides
    fun provideStatsDao(pokedexDatabase: PokedexDatabase) = pokedexDatabase.statsDao()

    @Provides
    fun provideSpeciesDao(pokedexDatabase: PokedexDatabase) = pokedexDatabase.speciesDao()
}