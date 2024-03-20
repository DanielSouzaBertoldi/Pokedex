package daniel.bertoldi.pokedex.di

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import daniel.bertoldi.pokedex.data.repository.PokedexDefaultRepository
import daniel.bertoldi.pokedex.data.repository.PokedexRepository

@Module
@InstallIn(ViewModelComponent::class)
interface PokedexRepositoryModule {

    @[Binds Reusable]
    fun providesPokedexRepository(
        repository: PokedexDefaultRepository
    ): PokedexRepository
}