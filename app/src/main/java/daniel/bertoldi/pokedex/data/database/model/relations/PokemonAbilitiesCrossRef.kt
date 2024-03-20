package daniel.bertoldi.pokedex.data.database.model.relations

import androidx.room.Entity

@Entity(primaryKeys = ["abilityId", "pokemonId"])
data class PokemonAbilitiesCrossRef(
    val abilityId: Int,
    val pokemonId: Int,
)
