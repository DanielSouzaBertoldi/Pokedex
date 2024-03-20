package daniel.bertoldi.pokedex.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import daniel.bertoldi.pokedex.R
import daniel.bertoldi.pokedex.presentation.model.BottomSheetLayout
import daniel.bertoldi.pokedex.presentation.model.PokemonUiModel
import daniel.bertoldi.pokedex.presentation.model.filters.FilterOptions
import daniel.bertoldi.pokedex.presentation.model.filters.PokemonFilterUIData
import daniel.bertoldi.pokedex.presentation.viewmodel.MainActivityViewModel
import daniel.bertoldi.pokedex.ui.theme.PokedexTheme
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val lazyPokemonPagingItems = viewModel.pokemonFlow.collectAsLazyPagingItems()
            val sheetContent = viewModel.bottomSheetContent.collectAsState()

            PokedexTheme {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isSystemInDarkTheme()

                DisposableEffect(key1 = systemUiController, key2 = useDarkIcons) {
                    systemUiController.setStatusBarColor(
                        color = Color.Red,
                        darkIcons = useDarkIcons,
                    )

                    onDispose {}
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    MyAppNavHost(
                        lazyPokemonPagingItems = lazyPokemonPagingItems,
                        sheetContent = sheetContent,
                        onIconClick = ::topBarIconClickCallback,
                        filterOptions = viewModel.filterOptions,
                        onFilterClick = ::onFilterClicked,
                    )
                }
            }
        }
    }

    private fun topBarIconClickCallback(iconType: BottomSheetLayout) {
        viewModel.bottomSheetContent.value = iconType
    }

    private fun onFilterClicked(idx: Int, name: String) {
        viewModel.filterOptions.value = viewModel.filterOptions.value.copy(
            mainFilters = viewModel.filterOptions.value.mainFilters.toMutableMap().mapValues {
                if (it.key == name) {
                    it.value.toMutableList().apply {
                        set(
                            index = idx,
                            element = PokemonFilterUIData(
                                isSelected = !this[idx].isSelected,
                                filterUiData = this[idx].filterUiData,
                            )
                        )
                    }
                } else {
                    it.value
                }
            }
        )
    }
}

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestinationName: String = "home",
    lazyPokemonPagingItems: LazyPagingItems<PokemonUiModel>,
    sheetContent: State<BottomSheetLayout>,
    onIconClick: (iconType: BottomSheetLayout) -> Unit = {},
    filterOptions: MutableStateFlow<FilterOptions>,
    onFilterClick: (Int, String) -> Unit = { _, _ -> },
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestinationName,
    ) {
        composable("home") {
            PokemonListComponent(
                lazyPokemonPagingItems = lazyPokemonPagingItems,
                sheetContent = sheetContent,
                onIconClick = onIconClick,
                filterOptions = filterOptions,
                onFilterClick = onFilterClick,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PokemonListComponent(
    lazyPokemonPagingItems: LazyPagingItems<PokemonUiModel>,
    sheetContent: State<BottomSheetLayout>,
    onIconClick: (iconType: BottomSheetLayout) -> Unit = {},
    filterOptions: MutableStateFlow<FilterOptions>,
    onFilterClick: (Int, String) -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            if (sheetContent.value == BottomSheetLayout.Generations) {
                Spacer(modifier = Modifier.height(50.dp))
                Text("You've opened the GENERATIONS option!")
                Spacer(modifier = Modifier.height(50.dp))
            } else if (sheetContent.value == BottomSheetLayout.Sort) {
                Spacer(modifier = Modifier.height(50.dp))
                Text("You've opened the SORT option!")
                Spacer(modifier = Modifier.height(50.dp))
            } else {
                FilterComponent(
                    filterOptions = filterOptions,
                    onFilterClicked = onFilterClick,
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                PokedexTopAppBar(
                    scope = scope,
                    modalBottomSheetState = modalBottomSheetState,
                    onIconClick = onIconClick,
                )
            }) { _ ->
            LazyColumn {
                if (lazyPokemonPagingItems.loadState.refresh == LoadState.Loading) {
                    item {
                        RefreshScreen()
                    }
                }

                items(lazyPokemonPagingItems) { pokemonUiModel ->
                    PokemonCardComponent(pokemonUiModel = pokemonUiModel)
                }

                if (lazyPokemonPagingItems.loadState.append == LoadState.Loading) {
                    item {
                        AppendingScreen()
                    }
                }
            }
        }
    }
}

@Composable
private fun RefreshScreen() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(200.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.pokeball)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
        Text(
            text = "Loading Pokémons!",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun AppendingScreen() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.pikachu_running)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
    }
}
