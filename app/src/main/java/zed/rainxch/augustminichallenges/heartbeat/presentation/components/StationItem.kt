package zed.rainxch.augustminichallenges.heartbeat.presentation.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zed.rainxch.augustminichallenges.heartbeat.presentation.model.Station
import zed.rainxch.augustminichallenges.heartbeat.presentation.model.StationStatus
import zed.rainxch.augustminichallenges.ui.theme.HeartbeatColors
import zed.rainxch.augustminichallenges.ui.theme.hostGrotesk

@Composable
fun StationItem(
    station: Station,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = HeartbeatColors.textPrimary
            )
            .background(HeartbeatColors.surfaceHigher)
            .padding(vertical = 24.dp)
            .padding(top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val boxAlphaInfiniteTransition = rememberInfiniteTransition("circle-anim-${station.title}")
        val boxAlphaAnim by boxAlphaInfiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = station.heartbeatInterval.toInt(),
                    easing = LinearOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

        if (station.status == StationStatus.Online) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(station.status.color)
                    .alpha(boxAlphaAnim)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(station.status.color)
            )
        }


        Text(
            text = station.title,
            fontFamily = hostGrotesk,
            fontWeight = FontWeight.Medium,
            fontSize = 19.sp,
            color = HeartbeatColors.textPrimary
        )
    }
}

@Preview
@Composable
private fun StationItemOnlinePreview() {
    StationItem(
        station = Station(
            title = "Station A",
            status = StationStatus.Online,
            heartbeatInterval = 300
        )
    )
}

@Preview
@Composable
private fun StationItemOfflinePreview() {
    StationItem(
        station = Station(
            title = "Station B",
            status = StationStatus.Offline(timeInSecond = 3.2),
            heartbeatInterval = 300
        )
    )
}