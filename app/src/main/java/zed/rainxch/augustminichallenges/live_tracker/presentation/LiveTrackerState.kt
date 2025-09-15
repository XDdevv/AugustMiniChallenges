package zed.rainxch.augustminichallenges.live_tracker.presentation

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import zed.rainxch.augustminichallenges.live_tracker.presentation.model.TrackItem

data class LiveTrackerState(
    val status: LiveTrackerStatus = LiveTrackerStatus.RUNNING,
    val items: ImmutableList<TrackItem> = persistentListOf(),
    val tickerRate: String = "4/s",
    val updateUi: Boolean = true,
)

enum class LiveTrackerStatus(
    val text: String,
) {
    RUNNING("Pause"),
    PAUSED("Resume")
}