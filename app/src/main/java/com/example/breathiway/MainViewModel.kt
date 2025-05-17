package com.example.breathiway

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breathiway.ui.shared.components.HikingTrail
import com.example.breathiway.ui.shared.components.WeatherData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    
    // 검색 관련 상태
    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    // 날씨 데이터
    private val _weatherData = MutableStateFlow(
        WeatherData(
            temperature = 23,
            humidity = 45,
            windSpeed = 3.2,
            rainChance = 10,
            airQuality = 55,
            airQualityStatus = "보통"
        )
    )
    val weatherData: StateFlow<WeatherData> = _weatherData.asStateFlow()
    
    // 하이킹 트레일 목록
    private val _hikingTrails = MutableStateFlow(
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
    )
    val hikingTrails: StateFlow<List<HikingTrail>> = _hikingTrails.asStateFlow()
    
    // 선택된 트레일
    private val _selectedTrail = MutableStateFlow<HikingTrail?>(null)
    val selectedTrail: StateFlow<HikingTrail?> = _selectedTrail.asStateFlow()
    
    // BottomSheet 관련 상태 (필요에 따라 사용)
    private val _isBottomSheetExpanded = MutableStateFlow(false)
    val isBottomSheetExpanded: StateFlow<Boolean> = _isBottomSheetExpanded.asStateFlow()
    
    // 검색 활성화/비활성화 토글
    fun toggleSearch() {
        _isSearchActive.value = !_isSearchActive.value
    }
    
    // 검색어 변경
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    // 검색 초기화
    fun clearSearch() {
        _searchQuery.value = ""
    }
    
    // 트레일 선택
    fun selectTrail(trail: HikingTrail) {
        _selectedTrail.value = trail
    }
    
    // BottomSheet 확장하기
    fun expandBottomSheet() {
        _isBottomSheetExpanded.value = true
    }
    
    // 날씨 데이터 업데이트 (실제로는 API 호출이나 다른 데이터 소스에서 가져올 것)
    fun updateWeatherData() {
        viewModelScope.launch {
            // 실제 구현에서는 여기서 API 호출이나 데이터베이스에서 데이터를 가져옴
            // 현재는 더미 데이터로 대체
            _weatherData.value = WeatherData(
                temperature = 23,
                humidity = 45,
                windSpeed = 3.2,
                rainChance = 10,
                airQuality = 55,
                airQualityStatus = "보통"
            )
        }
    }
    
    // 하이킹 트레일 데이터 가져오기 (실제로는 API 호출이나 다른 데이터 소스에서 가져올 것)
    fun fetchHikingTrails() {
        viewModelScope.launch {
            // 실제 구현에서는 여기서 API 호출이나 데이터베이스에서 데이터를 가져옴
            // 현재는 더미 데이터로 유지
        }
    }
    
    // 위치 기반 트레일 검색 (검색어나 현재 위치에 따라 필터링)
    fun searchTrailsByLocation(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                // 검색어가 비어있으면 모든 트레일 표시
                fetchHikingTrails()
            } else {
                // 검색어에 따라 필터링 (이름에 검색어가 포함된 트레일만 필터링)
                val filteredTrails = _hikingTrails.value.filter { trail ->
                    trail.name.contains(query, ignoreCase = true)
                }
                _hikingTrails.value = filteredTrails
            }
        }
    }
}