package com.example.breathiway

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.breathiway.ui.nearlyplacescreen.LocationSheetContent
import com.example.breathiway.ui.shared.components.BottomBar
import com.example.breathiway.ui.shared.components.BottomSheet
import com.example.breathiway.ui.shared.components.TopBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val navHostController = rememberNavController()

    var topBarHeightPx by remember { mutableIntStateOf(0) }
    var bottomBarHeightPx by remember { mutableIntStateOf(0) }

    // ViewModel에서 상태 수집
    val isSearchActive by viewModel.isSearchActive.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val weatherData by viewModel.weatherData.collectAsState()
    val hikingTrails by viewModel.hikingTrails.collectAsState()

    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true,
    )
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState,
    )

    MaterialTheme {
        Scaffold(
            bottomBar = {
                Box(
                    modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                        bottomBarHeightPx = layoutCoordinates.size.height
                    }
                ) {
                    BottomBar()
                }
            },
            modifier = Modifier.fillMaxSize(),
        ) { _ ->
            BottomSheet(
                sheetState = bottomSheetState,
                topBarHeightPx = topBarHeightPx,
                bottomHeightPx = bottomBarHeightPx,
                topBar = {
                    Box(
                        modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                            topBarHeightPx = layoutCoordinates.size.height
                        }
                    ) {
                        TopBar(
                            weatherData = weatherData,
                            isSearchActive = isSearchActive,
                            searchQuery = searchQuery,
                            onSearchToggle = { viewModel.toggleSearch() },
                            onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                            onClearSearch = { viewModel.clearSearch() }
                        )
                    }
                },
                sheetContent = {
                    LocationSheetContent(
                        weatherData = weatherData,
                        hikingTrails = hikingTrails,
                        onExpandSheet = { viewModel.expandBottomSheet() },
                        onTrailSelect = { viewModel.selectTrail(it) }
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
        MainScreen()
    }
}