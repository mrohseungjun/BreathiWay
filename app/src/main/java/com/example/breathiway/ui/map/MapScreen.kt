package com.example.breathiway.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.naver.maps.map.NaverMap
import com.naver.maps.map.util.FusedLocationSource

/**
 * 네이버 지도를 표시하는 화면
 * 
 * @param onBackClick 뒤로가기 버튼 클릭 시 호출되는 콜백
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val locationSource = remember { FusedLocationSource(context, LOCATION_PERMISSION_REQUEST_CODE) }
    var naverMap by remember { mutableStateOf<NaverMap?>(null) }
    var markers by remember { mutableStateOf<List<MapMarker>>(emptyList()) }
    
    // 예시 마커 데이터
    LaunchedEffect(Unit) {
        markers = listOf(
            MapMarker(
                id = "1",
                latitude = 37.5666791,
                longitude = 126.9782914,
                title = "서울시청",
                onClick = { /* 마커 클릭 시 처리 */ }
            ),
            MapMarker(
                id = "2",
                latitude = 37.5707,
                longitude = 126.9756,
                title = "광화문",
                onClick = { /* 마커 클릭 시 처리 */ }
            )
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("지도") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로가기"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 네이버 지도 표시
            NaverMapView(
                modifier = Modifier.fillMaxSize(),
                markers = markers,
                onMapReady = { map ->
                    naverMap = map
                    
                    // 위치 소스 설정
                    map.locationSource = locationSource
                    
                    // 위치 추적 모드 활성화
                    map.locationTrackingMode = com.naver.maps.map.LocationTrackingMode.NoFollow
                    
                    // UI 설정
                    map.uiSettings.apply {
                        isLocationButtonEnabled = false
                        isZoomControlEnabled = true
                        isCompassEnabled = true
                    }
                }
            )
            
            // 현재 위치 버튼
            FloatingActionButton(
                onClick = {
                    naverMap?.locationTrackingMode = com.naver.maps.map.LocationTrackingMode.Follow
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = "내 위치"
                )
            }
        }
    }
}

private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
