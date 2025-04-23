package com.example.breathiway.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.breathiway.ui.home.components.AirStateCard

@Composable
fun HomeScreen() {
    Column (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AirStateCard(
            dateText = "4월 20일 (토) 15:00",
            locationText = "서울 성북구",
            zoneTitle = "미세먼지 좋음",
            zoneSubtitle = "기준 18㎍/㎥ · 25분",
            onZoneClick = { /* 지도 보기 등 */ }
        )
        
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    MaterialTheme {
        HomeScreen()
    }
}