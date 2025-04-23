package com.example.breathiway.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar() {
    NavigationBar (
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { /* TODO */ },
            icon = {
                Icon(Icons.Default.Home, contentDescription = "홈")
            },
            label = { Text("홈") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* TODO */ },
            icon = {
                Icon(Icons.Default.Menu, contentDescription = "지도")
            },
            label = { Text("지도") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* TODO */ },
            icon = {
                Icon(Icons.Default.Favorite, contentDescription = "즐겨찾기")
            },
            label = { Text("즐겨찾기") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* TODO */ },
            icon = {
                Icon(Icons.Default.Settings, contentDescription = "설정")
            },
            label = { Text("설정") }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewBottomBar(){
    MaterialTheme {
        BottomBar()
    }
}