package daniel.bertoldi.pokedex.usecase

import daniel.bertoldi.pokedex.presentation.model.filters.GenerationUIData

interface GetPokemonGenerationsUIUseCase {

    suspend operator fun invoke(): List<GenerationUIData>
}