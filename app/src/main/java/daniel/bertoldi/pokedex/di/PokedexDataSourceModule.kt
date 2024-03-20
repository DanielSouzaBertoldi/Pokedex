package daniel.bertoldi.pokedex.di

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import daniel.bertoldi.pokedex.data.datasource.PokedexDefaultLocalDataSource
import daniel.bertoldi.pokedex.data.datasource.PokedexDefaultRemoteDataSource
import daniel.bertoldi.pokedex.data.datasource.PokedexLocalDataSource
import daniel.bertoldi.pokedex.data.datasource.PokedexRemoteDataSource

@Module
@InstallIn(ViewModelComponent::class)
interface PokedexDataSourceModule {

    @[Binds Reusable]
    fun providePokedexRemoteDataSource(
        pokedexRemoteDataSource: PokedexDefaultRemoteDataSource,
    ): PokedexRemoteDataSource

    @[Binds Reusable]
    fun providePokedexLocalDataSource(
        pokedexLocalDataSource: PokedexDefaultLocalDataSource,
    ): PokedexLocalDataSource
}
