package daniel.bertoldi.pokedex.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import daniel.bertoldi.pokedex.R
import daniel.bertoldi.pokedex.presentation.model.BottomSheetLayout
import daniel.bertoldi.pokedex.presentation.model.PokemonUiModel
import daniel.bertoldi.pokedex.presentation.model.filters.FilterOptions
import daniel.bertoldi.pokedex.presentation.model.filters.GenerationUIData
import daniel.bertoldi.pokedex.presentation.model.filters.PokemonFilterUIData
import daniel.bertoldi.pokedex.presentation.model.filters.SortOptions
import daniel.bertoldi.pokedex.presentation.viewmodel.MainActivityViewModel
import daniel.bertoldi.pokedex.ui.theme.PokedexTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init()

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
                        onFilterClick = ::onMainFiltersClicked,
                        onSortClick = ::onSortClicked,
                        onGenerationClicked = ::onGenerationClicked,
                        onFilterApply = ::onFilterApply,
                    )
                }
            }
        }
    }

    private fun topBarIconClickCallback(iconType: BottomSheetLayout) {
        viewModel.bottomSheetContent.value = iconType
    }

    private fun onMainFiltersClicked(idx: Int, name: String) {
        viewModel.filterOptions.value = viewModel.filterOptions.value.copy(
            miscFilters = viewModel.filterOptions.value.miscFilters.toMutableMap().mapValues {
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

    private fun onSortClicked(sortOptionName: String) {
        viewModel.filterOptions.value = viewModel.filterOptions.value.copy(
            sortOption = SortOptions.parse(sortOptionName)
        )
    }

    private fun onGenerationClicked(generationClicked: GenerationUIData) {
        viewModel.filterOptions.value = viewModel.filterOptions.value.copy(
            generationOption = viewModel.filterOptions.value.generationOption.toMutableList().apply {
                set(
                    index = this.indexOf(generationClicked),
                    element = generationClicked.copy(
                        isSelected = !generationClicked.isSelected
                    )
                )
            }
        )
    }

    private fun onFilterApply() {
        viewModel.applyFilters()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberAnimatedNavController(),
    startDestinationName: String = "home",
    lazyPokemonPagingItems: LazyPagingItems<PokemonUiModel>,
    sheetContent: State<BottomSheetLayout>,
    onIconClick: (iconType: BottomSheetLayout) -> Unit = {},
    filterOptions: MutableStateFlow<FilterOptions>,
    onFilterClick: (Int, String) -> Unit = { _, _ -> },
    onSortClick: (String) -> Unit,
    onGenerationClicked: (GenerationUIData) -> Unit,
    onFilterApply: () -> Unit,
) {
    AnimatedNavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestinationName,
    ) {
        composable(
            route = "home",
            enterTransition = {
                when (initialState.destination.route) {
                    "details" ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "details" ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
        ) {
            PokemonListComponent(
                lazyPokemonPagingItems = lazyPokemonPagingItems,
                sheetContent = sheetContent,
                onIconClick = onIconClick,
                filterOptions = filterOptions,
                onMainFilterClick = onFilterClick,
                onSortClick = onSortClick,
                onGenerationClicked = onGenerationClicked,
                onFilterApply = onFilterApply,
                navHostController = navHostController,
            )
        }
        composable(
            route = "details",
            enterTransition = {
                when (initialState.destination.route) {
                    "home" ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = spring(4f))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "home" ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = spring(4f))
                    else -> null
                }
            }
        ) {
            PokemonDetailsScreen()
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
    onMainFilterClick: (Int, String) -> Unit,
    onSortClick: (String) -> Unit,
    onGenerationClicked: (GenerationUIData) -> Unit,
    onFilterApply: () -> Unit,
    navHostController: NavHostController,
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            when (sheetContent.value) {
                BottomSheetLayout.Generations -> {
                    GenerationsComponent(
                        filterOptions = filterOptions,
                        onGenerationClicked = onGenerationClicked,
                    )
                }
                BottomSheetLayout.Sort -> {
                    SortComponent(
                        filterOptions = filterOptions,
                        onButtonClicked = onSortClick,
                    )
                }
                else -> {
                    FilterComponent(
                        filterOptions = filterOptions,
                        onFilterClicked = onMainFilterClick,
                        onConfirmClicked = {
                            scope.launch {
                                onFilterApply()
                                modalBottomSheetState.hide()
                            }
                        }
                    )
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
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
                    PokemonCardComponent(
                        pokemonUiModel = pokemonUiModel,
                        onNavigateToDetails = {
                            navHostController.navigate("details") { launchSingleTop = true }
                        }
                    )
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

@Composable
private fun PokemonDetailsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Olá, sou a tela de detalhes do pokemon!"
        )
    }
}
