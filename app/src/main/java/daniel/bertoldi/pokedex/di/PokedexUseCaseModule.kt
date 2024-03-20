package daniel.bertoldi.pokedex.di

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import daniel.bertoldi.pokedex.usecase.GetPokemonGenerationsUIDefault
import daniel.bertoldi.pokedex.usecase.GetPokemonDefaultUseCase
import daniel.bertoldi.pokedex.usecase.GetPokemonUseCase
import daniel.bertoldi.pokedex.usecase.GetPokemonGenerationsUIUseCase

@Module
@InstallIn(ViewModelComponent::class)
interface PokedexUseCaseModule {

    @[Binds Reusable]
    fun bindsGetPokemonUseCase(
        getPokemonDefaultUseCase: GetPokemonDefaultUseCase
    ): GetPokemonUseCase

    @[Binds Reusable]
    fun bindsSetupDataBaseUseCase(
        setupDataBase: GetPokemonGenerationsUIDefault,
    ): GetPokemonGenerationsUIUseCase
}
