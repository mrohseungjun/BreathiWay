package com.example.breathiway.ui.shared.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 데이터 클래스들
data class WeatherData(
    val temperature: Int,
    val humidity: Int,
    val windSpeed: Double,
    val rainChance: Int,
    val airQuality: Int,
    val airQualityStatus: String
)

data class HikingTrail(
    val id: Int,
    val name: String,
    val distance: Double,
    val duration: Int,
    val difficulty: String,
    val crowdness: String,
    val rating: Double
)

// 미세먼지 상태에 따른 색상 반환 함수
@Composable
fun getAirQualityColor(status: String): Color {
    return when (status) {
        "좋음" -> Color(0xFF4CAF50)
        "보통" -> Color(0xFFFFB300)
        "나쁨" -> Color(0xFFFF9800)
        "매우 나쁨" -> Color(0xFFF44336)
        else -> Color.Gray
    }
}
