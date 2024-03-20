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
        growthRate = formatGrowthRate(speciesEntity.growthRate),
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

    private fun ChainLinkResponse.parseEvolution() = EvolutionChainDetailsModel(
        name = this.species.name,
        isBaby = this.isBaby,
        heldItem = this.evolutionDetails.firstOrNull()?.heldItem?.name,
        knownMove = this.evolutionDetails.firstOrNull()?.heldItem?.name,
        knownMoveType = this.evolutionDetails.firstOrNull()?.heldItem?.name,
        minLevel = this.evolutionDetails.firstOrNull()?.minLevel,
        minHappiness = this.evolutionDetails.firstOrNull()?.minHappiness,
        minBeauty = this.evolutionDetails.firstOrNull()?.minBeauty,
        minAffection = this.evolutionDetails.firstOrNull()?.minAffection,
        needsOverworldRain = this.evolutionDetails.firstOrNull()?.needsOverworldRain ?: false,
        partySpecies = this.evolutionDetails.firstOrNull()?.heldItem?.name,
        partyType = this.evolutionDetails.firstOrNull()?.heldItem?.name,
        relativePhysicalStats = this.evolutionDetails.firstOrNull()?.relativePhysicalStats,
        timeOfDay = this.evolutionDetails.firstOrNull()?.timeOfDay ?: "",
        turnUpsideDown = this.evolutionDetails.firstOrNull()?.turnUpsideDown ?: false,
        location = this.evolutionDetails.firstOrNull()?.heldItem?.name,
        trigger = this.evolutionDetails.firstOrNull()?.heldItem?.name,
        item = this.evolutionDetails.firstOrNull()?.heldItem?.name,
        gender = this.evolutionDetails.firstOrNull()?.gender,
        nextEvolution = getNextChainLayer(this.chain),
    )

    private fun getNextChainLayer(
        chain: List<ChainLinkResponse>,
    ): List<EvolutionChainDetailsModel> = chain.map {
        it.parseEvolution()
    }

    private fun ChainDetails.parseEvolution() = EvolutionChainDetailsModel(
        name = this.evolutionName,
        isBaby = this.isBaby,
        heldItem = this.evolutionDetails.firstOrNull()?.heldItem, // I shouldn't be using first or Null actually. I should parse them all!!!
        knownMove = this.evolutionDetails.firstOrNull()?.heldItem,
        knownMoveType = this.evolutionDetails.firstOrNull()?.heldItem,
        minLevel = this.evolutionDetails.firstOrNull()?.minLevel,
        minHappiness = this.evolutionDetails.firstOrNull()?.minHappiness,
        minBeauty = this.evolutionDetails.firstOrNull()?.minBeauty,
        minAffection = this.evolutionDetails.firstOrNull()?.minAffection,
        needsOverworldRain = this.evolutionDetails.firstOrNull()?.needsOverworldRain ?: false,
        partySpecies = this.evolutionDetails.firstOrNull()?.heldItem,
        partyType = this.evolutionDetails.firstOrNull()?.heldItem,
        relativePhysicalStats = this.evolutionDetails.firstOrNull()?.relativePhysicalStats,
        timeOfDay = this.evolutionDetails.firstOrNull()?.timeOfDay ?: "",
        turnUpsideDown = this.evolutionDetails.firstOrNull()?.turnUpsideDown ?: false,
        location = this.evolutionDetails.firstOrNull()?.heldItem,
        trigger = this.evolutionDetails.firstOrNull()?.heldItem,
        item = this.evolutionDetails.firstOrNull()?.heldItem,
        gender = this.evolutionDetails.firstOrNull()?.gender,
        nextEvolution = getNextChainDetailsLayer(this.evolvesTo),
    )

    private fun getNextChainDetailsLayer(
        chain: List<ChainDetails>,
    ): List<EvolutionChainDetailsModel> = chain.map {
        it.parseEvolution()
    }

    private fun formatGrowthRate(growthRate: String) = growthRate // duped logic
        .split("-")
        .joinToString(" ") {
            it.replaceFirstChar { firstChar -> firstChar.uppercase() }
        }
}