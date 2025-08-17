package zed.rainxch.augustminichallenges.heartbeat.presentation

import zed.rainxch.augustminichallenges.heartbeat.presentation.model.Station

data class HeartbeatState(
    val stations: List<Station> = emptyList(),
    val offlineStation: Station? = null,
)