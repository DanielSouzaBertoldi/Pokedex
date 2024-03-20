package daniel.bertoldi.pokedex.presentation.model.filters

import daniel.bertoldi.pokedex.ui.theme.HeightUIData
import daniel.bertoldi.pokedex.ui.theme.PokemonUIData
import daniel.bertoldi.pokedex.ui.theme.WeightUIData

object PokemonFilterFactory {
    fun make(): Map<String, List<PokemonFilterUIData>> {
        return buildMap {
            put("types", createTypeList())
            put("weakness", createTypeList())
            put("height", createHeightList())
            put("weight", createWeightList())
        }
    }

    // rename Type to something more cool
    private fun createTypeList() = buildList<PokemonFilterUIData> {
        PokemonUIData.values().forEach {
            add(
                PokemonFilterUIData(
                    filterUiData = FilterUiData.Type(
                        foregroundColor = it.typeColor,
                        backgroundColor = it.backgroundColor,
                        name = it.name,
                        icon = it.icon,
                    )
                )
            )
        }
    }

    private fun createHeightList() = buildList {
        HeightUIData.values().forEach {
            add(
                PokemonFilterUIData(
                    filterUiData = FilterUiData.Size(
                        limit = it.heightLimit,
                        foregroundColor = it.color,
                        backgroundColor = it.color,
                        name = it.name,
                        icon = it.iconUnselected,
                    )
                )
            )
        }
    }

    private fun createWeightList() = buildList {
        WeightUIData.values().forEach {
            add(
                PokemonFilterUIData(
                    filterUiData = FilterUiData.Size(
                        limit = it.weightLimit,
                        foregroundColor = it.color,
                        backgroundColor = it.color,
                        name = it.name,
                        icon = it.iconUnselected,
                    )
                )
            )
        }
    }
}
