package zed.rainxch.augustminichallenges.heartbeat.presentation.model

import androidx.compose.ui.graphics.Color
import zed.rainxch.augustminichallenges.ui.theme.HeartbeatColors

sealed class StationStatus(
    val color: Color,
) {
    data object Online : StationStatus(HeartbeatColors.primary)
    data class Offline(val timeInSecond: Double) : StationStatus(HeartbeatColors.error)
}

data class Station(
    val id: Int = 0,
    val title: String,
    val status: StationStatus,
    val heartbeatInterval: Long,
)
