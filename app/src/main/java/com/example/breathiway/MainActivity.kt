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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.breathiway.ui.location.LocationSheetContent
import com.example.breathiway.ui.map.HikingTrail
import com.example.breathiway.ui.map.WeatherData
import com.example.breathiway.ui.shared.components.BottomBar
import com.example.breathiway.ui.shared.components.BottomSheet
import com.example.breathiway.ui.shared.components.TopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
fun MainScreen() {
    val navHostController = rememberNavController()
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,        // 초기 상태 설정
        skipHiddenState = false                  // Hidden 상태 스킵 여부
    )

    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState,
        snackbarHostState = remember { SnackbarHostState() }
    )
    // 코루틴 스코프
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }
    var topBarHeightPx by remember { mutableIntStateOf(0) }

    val weatherData = remember {
        WeatherData(
            temperature = 23,
            humidity = 45,
            windSpeed = 3.2,
            rainChance = 10,
            airQuality = 55,
            airQualityStatus = "보통"
        )
    }
    val hikingTrails = remember {
        listOf(
            HikingTrail(
                id = 1,
                name = "한강 공원 산책로",
                distance = 2.5,
                duration = 30,
                difficulty = "쉬움",
                crowdness = "보통",
                rating = 4.5
            ),
            HikingTrail(
                id = 2,
                name = "남산 둘레길",
                distance = 4.2,
                duration = 60,
                difficulty = "중간",
                crowdness = "한산",
                rating = 4.8
            ),
            HikingTrail(
                id = 3,
                name = "북한산 숲길",
                distance = 3.7,
                duration = 45,
                difficulty = "중간",
                crowdness = "혼잡",
                rating = 4.2
            )
        )
    }

    LaunchedEffect(topBarHeightPx) {
        // 실제 변수명에 맞게 bottomSheetState.bottomSheetState 사용
        if (topBarHeightPx > 0) {
            scope.launch {
                // 이미 정의된 sheetState 직접 사용 (StandardBottomSheetState 타입)
                sheetState.partialExpand() // 먼저 부분 확장
                delay(100)
                sheetState.expand() // 완전 확장
            }
        }
    }

    MaterialTheme {
        Scaffold(
            bottomBar = {
                BottomBar()
            },
            modifier = Modifier.fillMaxSize(),
        ) { _ ->
            if (showBottomSheet) {
                BottomSheet(
                    sheetState = bottomSheetState, // 올바른 변수명 사용
                    topBarHeightPx = topBarHeightPx,
                    topBar = @Composable {
                        // TopBar에는 modifier 파라미터가 없으므로 Box로 감싸서 높이 측정
                        Box(
                            modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                                topBarHeightPx = layoutCoordinates.size.height
                            }
                        ) {
                            TopBar(
                                weatherData = weatherData,
                                isSearchActive = isSearchActive,
                                searchQuery = searchQuery,
                                onSearchToggle = { isSearchActive = !isSearchActive },
                                onSearchQueryChange = { searchQuery = it },
                                onClearSearch = { searchQuery = "" }
                            )
                        }
                    },
                    sheetContent = {
                        LocationSheetContent(
                            weatherData = weatherData,
                            hikingTrails = hikingTrails,
                            onExpandSheet = {},
                            onTrailSelect = {}
                        )
                    }
                )
            }
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