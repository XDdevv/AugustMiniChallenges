package zed.rainxch.augustminichallenges.live_tracker.presentation.model

import zed.rainxch.augustminichallenges.R
import java.util.UUID

data class TrackItem(
    val id: String = UUID.randomUUID().toString(),
    val currency: String,
    val latestUpdateTime: String,
    val value: Double,
    val differenceType: DifferenceType,
    val updateDelayMs: Long,
    val isFeedDown: Boolean = false,
)

enum class DifferenceType(
    val drawableId: Int,
) {
    INCREASE(R.drawable.arrow_narrow_up_right),
    DECREASE(R.drawable.arrow_narrow_down_right),
    EQUAL(R.drawable.equal),
}
