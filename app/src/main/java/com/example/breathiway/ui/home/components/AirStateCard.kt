package com.example.breathiway.ui.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.breathiway.R


@Composable
fun AirStateCard(
    dateText: String,
    locationText: String,
    zoneTitle: String,
    zoneSubtitle: String,
    onZoneClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 1) 날짜/시간 Row
        Row (verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF555555)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = dateText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(Modifier.height(8.dp))
        // 2) 위치 Row
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF555555)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = locationText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(Modifier.height(12.dp))
        // 3) 미세먼지 존 카드
        Card (
            onClick = onZoneClick,
            colors = CardDefaults.cardColors(containerColor = Color(0xFFDFF3EC)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                // 커스텀 아이콘 리소스나 벡터 사용
                Icon(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        text = zoneTitle,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = zoneSubtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF555555)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        AirStateCard(
            dateText = "4월 20일 (토) 15:00",
            locationText = "서울 성북구",
            zoneTitle = "미세먼지 좋음",
            zoneSubtitle = "기준 18㎍/㎥ · 25분",
            onZoneClick = { /* 지도 보기 등 */ }
        )
    }
}