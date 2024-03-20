package daniel.bertoldi.pokedex.presentation.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import daniel.bertoldi.pokedex.R
import daniel.bertoldi.pokedex.presentation.viewmodel.BottomSheetLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PokedexTopAppBar(
    scope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    onIconClick: (iconType: BottomSheetLayout) -> Unit = {},
) {
    TopAppBar {
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                scope.launch {
                    modalBottomSheetState.apply {
                        onIconClick(BottomSheetLayout.Generations)
                        if (isVisible) hide() else show()
                    }
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_generations),
                contentDescription = null
            )
        }
        IconButton(
            onClick = {
                scope.launch {
                    modalBottomSheetState.apply {
                        onIconClick(BottomSheetLayout.Sort)
                        if (isVisible) hide() else show()
                    }
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_sort),
                contentDescription = null
            )
        }
        IconButton(
            onClick = {
                scope.launch {
                    modalBottomSheetState.apply {
                        onIconClick(BottomSheetLayout.Filter)
                        if (isVisible) hide() else show()
                    }
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filters),
                contentDescription = null
            )
        }
    }
}