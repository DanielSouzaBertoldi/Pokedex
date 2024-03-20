package daniel.bertoldi.pokedex.domain.mapper

import daniel.bertoldi.pokedex.data.api.response.ChainLinkResponse
import daniel.bertoldi.pokedex.data.api.response.GenericObject
import daniel.bertoldi.pokedex.data.database.model.*
import daniel.bertoldi.pokedex.domain.model.*
import javax.inject.Inject

class PokemonEntityToCompleteModelMapper @Inject constructor() {

    fun mapFrom(
        pokemonEntity: Pokemon,
        pokemonAbilities: List<PokemonAbility>,
        statsEntity: Stats,
        speciesEntity: Species,
        typeEffectivenessEntity: TypeEffectiveness,
        evolutionChain: Evolution,
    ) = PokemonCompleteModel(
        id = pokemonEntity.pokemonId,
        baseExperience = pokemonEntity.baseExperience,
        height = pokemonEntity.height,
        isDefault = pokemonEntity.isDefault,
        name = pokemonEntity.name,
        sprites = mapSprites(pokemonEntity.sprites),
        types = mapTypes(pokemonEntity.types),
        weight = pokemonEntity.weight,
        abilities = mapAbilities(pokemonAbilities),
        stats = mapStats(statsEntity),
        species = mapSpecies(speciesEntity),
        typeEffectiveness = mapTypeEffectiveness(typeEffectivenessEntity),
        evolutionChain = evolutionChain.chainDetails.parseEvolution()
    )

    private fun mapSprites(sprites: Sprites) = SpritesModel(
        backDefaultImageUrl = sprites.backDefault,
        backShinyImageUrl = sprites.backShiny,
        frontDefaultImageUrl = sprites.frontDefault,
        frontShinyImageUrl = sprites.frontShiny,
        artworkImageUrl = sprites.officialArtwork.orEmpty(),
    )

    private fun mapTypes(types: List<GenericObject>) = types.map { type ->
        Types(
            slot = 1, // mocked
            type = Type(
                name = type.name,
                url = type.url,
            )
        )
    }

    private fun mapAbilities(abilities: List<PokemonAbility>) = abilities.map {
        val effectEntry = it.effectEntries.filter { effectEntry ->
            effectEntry.language == "en"
        }.maxBy { effect -> effect.effect.length }
        AbilityModel(
            id = it.abilityId,
            name = it.name,
            isHidden = it.isHidden,
            slot = it.slot,
            effectEntry = effectEntry.effect,
            shortEffectEntry = effectEntry.shortEffect,
            flavorText = it.flavorTextEntries.filter { flavorEntry ->
                flavorEntry.language == "en"
            }.maxBy { flavor -> flavor.flavorText.length }.flavorText,
            generation = it.generationName,
            isMainSeries = it.isMainSeries
        )
    }

    private fun mapStats(statsEntity: Stats) = StatsModel(
        hp = statsEntity.hp,
        attack = statsEntity.attack,
        defense = statsEntity.defense,
        specialAttack = statsEntity.specialAttack,
        specialDefense = statsEntity.specialDefense,
        speed = statsEntity.speed,
        accuracy = statsEntity.accuracy,
        evasion = statsEntity.evasion,
    )

    private fun mapSpecies(speciesEntity: Species) = SpeciesModel(
        baseHappiness = speciesEntity.baseHappiness,
        captureRate = speciesEntity.captureRate,
        eggGroups = speciesEntity.eggGroups,
        genderRate = speciesEntity.genderRate,
        pokedexEntry = speciesEntity.pokedexEntry,
        growthRate = speciesEntity.growthRate,
        isBaby = speciesEntity.isBaby,
        isLegendary = speciesEntity.isLegendary,
        isMythical = speciesEntity.isMythical,
        hatchCounter = speciesEntity.hatchCounter,
        name = speciesEntity.genera,
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

    private fun ChainDetails.parseEvolution() = EvolutionChainDetailsModel(
        name = this.evolutionName,
        isBaby = this.isBaby,
        heldItem = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem, // I shouldn't be using first or Null actually. I should parse them all!!!
        knownMove = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem,
        knownMoveType = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem,
        minLevel = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.minLevel,
        minHappiness = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.minHappiness,
        minBeauty = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.minBeauty,
        minAffection = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.minAffection,
        needsOverworldRain = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.needsOverworldRain ?: false,
        partySpecies = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem,
        partyType = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem,
        relativePhysicalStats = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.relativePhysicalStats,
        timeOfDay = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.timeOfDay ?: "",
        turnUpsideDown = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.turnUpsideDown ?: false,
        location = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem,
        trigger = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem,
        item = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.heldItem,
        gender = this.evolvesTo.firstOrNull()?.evolutionDetails?.firstOrNull()?.gender,
        nextEvolution = getNextChainDetailsLayer(this.evolvesTo),
    )

    private fun getNextChainDetailsLayer(
        chain: List<ChainDetails>,
    ): List<EvolutionChainDetailsModel> = chain.map {
        it.parseEvolution()
    }
}