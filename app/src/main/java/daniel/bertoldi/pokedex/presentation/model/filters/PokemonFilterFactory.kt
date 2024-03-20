package daniel.bertoldi.pokedex.presentation.model.filters

import daniel.bertoldi.design.system.HeightUIData
import daniel.bertoldi.design.system.PokemonUIData
import daniel.bertoldi.design.system.WeightUIData

object PokemonFilterFactory {
    fun make(): Map<String, List<PokemonFilterUIData>> {
        return buildMap {
            put("types", createTypeList())
            put("weakness", createTypeList())
            put("height", createHeightList())
            put("weight", createWeightList())
        }
    }

    private fun createTypeList() = buildList {
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
                    filterUiData = FilterUiData.Height(
                        heightLimit = it.heightLimit,
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
                    filterUiData = FilterUiData.Weight(
                        weightLimit = it.weightLimit,
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
