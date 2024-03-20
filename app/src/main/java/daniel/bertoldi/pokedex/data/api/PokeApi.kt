package daniel.bertoldi.pokedex.data.api

import daniel.bertoldi.pokedex.data.api.response.AbilityResponse
import daniel.bertoldi.pokedex.data.api.response.GenerationResponse
import daniel.bertoldi.pokedex.data.api.response.GenerationsListResponse
import daniel.bertoldi.pokedex.data.api.response.PokemonResponse
import daniel.bertoldi.pokedex.data.api.response.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon/{pokemonId}")
    suspend fun getPokemon(
        @Path("pokemonId") pokemonId: Int,
    ): PokemonResponse

    @GET("ability/{abilityId}")
    suspend fun getAbility(
        @Path("abilityId") abilityId: Int,
    ): AbilityResponse

    @GET("generation/")
    suspend fun getGenerations(): GenerationsListResponse

    @GET("generation/{generationId}")
    suspend fun getGeneration(
        @Path("generationId") generationId: Int,
    ): GenerationResponse

    @GET("pokemon-species/{speciesId}")
    suspend fun getPokemonSpecies(
        @Path("speciesId") speciesId: Int,
    ): PokemonSpeciesResponse
}
