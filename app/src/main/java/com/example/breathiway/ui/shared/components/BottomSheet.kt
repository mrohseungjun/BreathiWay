package com.example.breathiway.ui.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    sheetState: BottomSheetScaffoldState,
    sheetContent: @Composable () -> Unit,
    topBar: (@Composable () -> Unit)? = null,
    topBarHeightPx: Int,
    bottomHeightPx: Int
) {
    BottomSheetScaffold(
        topBar = {
            if(topBar != null) {
                topBar()
            }
        },
        sheetContent = {
            val config = LocalConfiguration.current
            val screenHeight = config.screenHeightDp.dp
            val density = LocalDensity.current
            val topBarHeight = with(density) { topBarHeightPx.toDp() }
            Box(
                modifier = Modifier
                    .heightIn(max = screenHeight - topBarHeight)
                    .padding(bottom = 16.dp)
            ) {
                sheetContent()
            }
        },
        scaffoldState = sheetState,
        sheetPeekHeight = bottomHeightPx.dp,
        sheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetShadowElevation = 8.dp,
        contentColor = MaterialTheme.colorScheme.onBackground,
        content = {}
    )
}