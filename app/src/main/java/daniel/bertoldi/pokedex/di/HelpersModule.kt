package daniel.bertoldi.pokedex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import daniel.bertoldi.pokedex.presentation.model.filters.PokemonFilterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HelpersModule {

    @Singleton
    @Provides
    fun providePokemonFilterFactory() = PokemonFilterFactory
}