package daniel.bertoldi.pokedex.di

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import daniel.bertoldi.pokedex.usecase.GetPokemon
import daniel.bertoldi.pokedex.usecase.GetPokemonUseCase

@Module
@InstallIn(ViewModelComponent::class)
interface PokedexUseCaseModule {

    @[Binds Reusable]
    fun bindsGetPokemonUseCase(
        getPokemon: GetPokemon
    ): GetPokemonUseCase
}
