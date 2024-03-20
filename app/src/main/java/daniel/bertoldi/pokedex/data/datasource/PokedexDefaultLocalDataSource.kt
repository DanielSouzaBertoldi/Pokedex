package daniel.bertoldi.pokedex.data.datasource

import daniel.bertoldi.pokedex.data.database.dao.AbilitiesDao
import daniel.bertoldi.pokedex.data.database.dao.PokemonDao
import daniel.bertoldi.pokedex.data.database.dao.SpeciesDao
import daniel.bertoldi.pokedex.data.database.dao.StatsDao
import daniel.bertoldi.pokedex.data.database.dao.TypeEffectivenessDao
import daniel.bertoldi.pokedex.domain.mapper.PokemonEntityToBasicModelMapper
import daniel.bertoldi.pokedex.domain.mapper.PokemonEntityToCompleteModelMapper
import daniel.bertoldi.pokedex.domain.model.PokemonCompleteModel
import javax.inject.Inject

class PokedexDefaultLocalDataSource @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val abilitiesDao: AbilitiesDao,
    private val statsDao: StatsDao,
    private val speciesDao: SpeciesDao,
    private val typeEffectivenessDao: TypeEffectivenessDao,
    private val pokemonEntityToBasicModelMapper: PokemonEntityToBasicModelMapper,
    private val pokemonEntityToCompleteModelMapper: PokemonEntityToCompleteModelMapper,
) : PokedexLocalDataSource {

    override suspend fun getBasicPokemon(pokemonId: Int) = pokemonEntityToBasicModelMapper.mapFrom(
        pokemonDao.getPokemonById(pokemonId)
    )

    override suspend fun getCompletePokemon(pokemonId: Int): PokemonCompleteModel {
        val pokemonBasicInfo = pokemonDao.getPokemonById(pokemonId)
        val abilities = abilitiesDao.getPokemonAbilities(pokemonId)
        val stats = statsDao.getPokemonStat(pokemonId)
        val species = speciesDao.getSpeciesData(pokemonBasicInfo.speciesId)
        val typeEffectiveness = typeEffectivenessDao.getTypeEffectiveness(
            pokemonBasicInfo.types[0].name,
            pokemonBasicInfo.types.getOrNull(1)?.name ?: "",
        )

        return pokemonEntityToCompleteModelMapper.mapFrom(
            pokemonEntity = pokemonBasicInfo,
            pokemonAbilities = abilities,
            statsEntity = stats,
            speciesEntity = species,
            typeEffectivenessEntity = typeEffectiveness,
        )
    }
}