package daniel.bertoldi.pokedex.domain.mapper

import daniel.bertoldi.pokedex.data.api.response.*
import daniel.bertoldi.pokedex.data.database.model.TypeEffectiveness
import daniel.bertoldi.pokedex.domain.model.*
import javax.inject.Inject

class PokemonResponseToCompleteModelMapper @Inject constructor() {

    fun mapFrom(
        pokemonEntity: PokemonResponse,
        abilitiesResponse: List<AbilityResponse>,
        speciesResponse: PokemonSpeciesResponse,
        typeEffectiveness: TypeEffectiveness,
        evolutionChain: EvolutionChainResponse,
    ) = PokemonCompleteModel(
        id = pokemonEntity.id,
        baseExperience = pokemonEntity.baseExperience ?: -1,
        height = pokemonEntity.height,
        isDefault = pokemonEntity.isDefault,
        name = pokemonEntity.name,
        sprites = mapSprites(pokemonEntity.sprites),
        types = pokemonEntity.types.map { mapTypes(it) },
        weight = pokemonEntity.weight,
        abilities = mapAbilities(abilitiesResponse),
        stats = mapStats(pokemonEntity.stats),
        species = mapSpecies(speciesResponse),
        typeEffectiveness = mapTypeEffectiveness(typeEffectiveness),
        evolutionChain = mapEvolutionChain(evolutionChain)
    )

    private fun mapAbilities(abilities: List<AbilityResponse>) = abilities.map {
        val effectEntry = it.effectEntries.filter { effectEntry ->
            effectEntry.language.name == "en"
        }.maxBy { effect -> effect.effect.length }

        AbilityModel(
            id = it.id,
            name = it.name,
            isHidden = it.isHidden,
            slot = it.slot,
            effectEntry = effectEntry.effect,
            shortEffectEntry = effectEntry.shortEffect,
            flavorText = it.flavorTextEntries.filter { flavorEntry ->
                flavorEntry.language.name == "en"
            }.maxBy { flavor -> flavor.flavorText.length }.flavorText,
            generation = it.generation.name,
            isMainSeries = it.isMainSeries,
        )
    }

    private fun mapSprites(sprites: SpritesResponse) = SpritesModel(
        backDefaultImageUrl = sprites.backDefault,
        backShinyImageUrl = sprites.backShiny,
        frontDefaultImageUrl = sprites.frontDefault,
        frontShinyImageUrl = sprites.frontShiny,
        artworkImageUrl = sprites.otherSprites.officialArtworkSprites.frontDefault.orEmpty(),
    )

    private fun mapTypes(types: TypesResponse) = Types(
        slot = types.slot,
        type = Type(
            name = types.type.name,
            url = types.type.url,
        )
    )

    private fun mapStats(stats: List<StatsResponse>) = StatsModel(
        // duplicated logic
        hp = stats.getBaseStat("hp"),
        attack = stats.getBaseStat("attack"),
        defense = stats.getBaseStat("defense"),
        specialAttack = stats.getBaseStat("specialAttack"),
        specialDefense = stats.getBaseStat("specialDefense"),
        speed = stats.getBaseStat("speed"),
        accuracy = stats.getBaseStat("accuracy"),
        evasion = stats.getBaseStat("evasion"),
    )

    private fun mapSpecies(speciesResponse: PokemonSpeciesResponse) =
        SpeciesModel( // duplicated logic
            baseHappiness = speciesResponse.baseHappiness,
            captureRate = speciesResponse.captureRate,
            eggGroups = speciesResponse.eggGroups.map { it.name },
            genderRate = speciesResponse.genderRate,
            pokedexEntry = speciesResponse.flavorTextEntries.filter { flavorEntry ->
                flavorEntry.language.name == "en"
            }.maxBy { it.flavorText.length }.flavorText.replace("\n", " "),
            growthRate = formatGrowthRate(speciesResponse.growthRate.name), // TODO: should I be formating it here? Isn't it best to format it in Model -> Ui layer? hmm
            isBaby = speciesResponse.isBaby,
            isLegendary = speciesResponse.isLegendary,
            isMythical = speciesResponse.isMythical,
            hatchCounter = speciesResponse.hatchCounter,
            name = speciesResponse.genera.firstOrNull { generaEntry ->
                generaEntry.language.name == "en"
            }?.genus.orEmpty()
        )

    private fun mapTypeEffectiveness(
        typeEffectivenessEntity: TypeEffectiveness,
    ) = TypeEffectivenessModel(
        normal = typeEffectivenessEntity.normal,
        fighting = typeEffectivenessEntity.fighting,
        flying = typeEffectivenessEntity.flying,
        poison = typeEffectivenessEntity.poison,
        ground = typeEffectivenessEntity.ground,
        rock = typeEffectivenessEntity.rock,
        bug = typeEffectivenessEntity.bug,
        ghost = typeEffectivenessEntity.ghost,
        steel = typeEffectivenessEntity.steel,
        fire = typeEffectivenessEntity.fire,
        water = typeEffectivenessEntity.water,
        grass = typeEffectivenessEntity.grass,
        electric = typeEffectivenessEntity.electric,
        psychic = typeEffectivenessEntity.psychic,
        ice = typeEffectivenessEntity.ice,
        dragon = typeEffectivenessEntity.dragon,
        dark = typeEffectivenessEntity.dark,
        fairy = typeEffectivenessEntity.fairy,
        unknown = typeEffectivenessEntity.unknown,
        shadow = typeEffectivenessEntity.shadow,
    )

    private fun mapEvolutionChain(
        evolutionChain: EvolutionChainResponse,
    ) = evolutionChain.chain.addEvolution()

    private fun ChainLinkResponse.addEvolution() = EvolutionChainDetailsModel(
        name = this.species.name,
        isBaby = this.isBaby,
        heldItem = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem?.name,
        knownMove = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem?.name,
        knownMoveType = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem?.name,
        minLevel = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.minLevel,
        minHappiness = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.minHappiness,
        minBeauty = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.minBeauty,
        minAffection = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.minAffection,
        needsOverworldRain = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.needsOverworldRain ?: false,
        partySpecies = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem?.name,
        partyType = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem?.name,
        relativePhysicalStats = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.relativePhysicalStats,
        timeOfDay = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.timeOfDay ?: "",
        turnUpsideDown = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.turnUpsideDown ?: false,
        location = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem?.name,
        trigger = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem?.name,
        item = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem?.name,
        gender = this.chain.firstOrNull()?.evolutionDetails?.firstOrNull()?.gender,
        nextEvolution = getNextChainLayer(this.chain),
    )

    private fun getNextChainLayer(
        chain: List<ChainLinkResponse>,
    ): List<EvolutionChainDetailsModel> = chain.map {
        it.addEvolution()
    }


    private fun formatGrowthRate(growthRate: String) = growthRate
        .split("-")
        .joinToString(" ") {
            it.replaceFirstChar { firstChar -> firstChar.uppercase() }
        }

    private fun List<StatsResponse>.getBaseStat(stat: String) =
        this.firstOrNull { it.stat.name == stat }?.baseStat
}