package com.example.breathiway.ui.shared.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
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
                Icon(Icons.Default.Place, contentDescription = "주변")
            },
            label = { Text("주변") }
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