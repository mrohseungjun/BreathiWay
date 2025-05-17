package com.example.breathiway.ui.map

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons

/**
 * 네이버 지도를 Jetpack Compose에서 사용하기 위한 컴포저블 함수
 * 
 * @param modifier 지도 뷰에 적용할 모디파이어
 * @param latitude 초기 위도 (기본값: 서울시청)
 * @param longitude 초기 경도 (기본값: 서울시청)
 * @param zoomLevel 초기 줌 레벨 (기본값: 14)
 * @param onMapReady 지도가 준비되었을 때 호출되는 콜백
 * @param markers 지도에 표시할 마커 목록
 */
@Composable
fun NaverMapView(
    modifier: Modifier = Modifier,
    latitude: Double = 37.5666791,
    longitude: Double = 126.9782914,
    zoomLevel: Double = 14.0,
    onMapReady: (NaverMap) -> Unit = {},
    markers: List<MapMarker> = emptyList()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentOnMapReady by rememberUpdatedState(onMapReady)
    val currentMarkers by rememberUpdatedState(markers)
    
    var naverMap by remember { mutableStateOf<NaverMap?>(null) }
    val mapViewRef = remember { MapViewRef() }
    
    // 네이버 지도 뷰 생성 및 설정
    AndroidView(
        modifier = modifier,
        factory = { context ->
            MapView(context).apply {
                mapViewRef.mapView = this
                getMapAsync(object : OnMapReadyCallback {
                    override fun onMapReady(map: NaverMap) {
                        naverMap = map
                        
                        // 초기 카메라 위치 설정
                        val cameraPosition = CameraPosition(
                            LatLng(latitude, longitude),
                            zoomLevel
                        )
                        map.cameraPosition = cameraPosition
                        
                        // 콜백 호출
                        currentOnMapReady(map)
                    }
                })
            }
        },
        update = { mapView ->
            // 마커 업데이트
            naverMap?.let { map ->
                updateMarkers(map, currentMarkers)
            }
        }
    )
    
    // 라이프사이클 관리
    DisposableEffect(lifecycleOwner) {
        val mapView = mapViewRef.mapView
        
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView?.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView?.onStart()
                Lifecycle.Event.ON_RESUME -> mapView?.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView?.onPause()
                Lifecycle.Event.ON_STOP -> mapView?.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView?.onDestroy()
                else -> {}
            }
        }
        
        lifecycleOwner.lifecycle.addObserver(observer)
        
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView?.onDestroy()
        }
    }
}

/**
 * 지도에 표시할 마커 정보
 */
data class MapMarker(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val title: String = "",
    val snippet: String = "",
    val isClickable: Boolean = true,
    val onClick: () -> Unit = {}
)

/**
 * MapView 참조를 저장하기 위한 클래스
 */
private class MapViewRef {
    var mapView: MapView? = null
}

/**
 * 지도에 마커를 업데이트하는 함수
 */
private fun updateMarkers(naverMap: NaverMap, markers: List<MapMarker>) {
    // 기존 마커 제거
    naverMap.overlays.filterIsInstance<Marker>().forEach { it.map = null }
    
    // 새 마커 추가
    markers.forEach { markerData ->
        Marker().apply {
            position = LatLng(markerData.latitude, markerData.longitude)
            icon = MarkerIcons.RED
            width = 50
            height = 80
            
            if (markerData.title.isNotEmpty()) {
                captionText = markerData.title
            }
            
            isClickable = markerData.isClickable
            
            if (markerData.isClickable) {
                setOnClickListener {
                    markerData.onClick()
                    true
                }
            }
            
            map = naverMap
        }
    }
}
