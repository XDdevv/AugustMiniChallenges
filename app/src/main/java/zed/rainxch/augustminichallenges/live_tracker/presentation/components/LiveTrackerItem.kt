package zed.rainxch.augustminichallenges.live_tracker.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zed.rainxch.augustminichallenges.live_tracker.presentation.LiveTrackerStatus
import zed.rainxch.augustminichallenges.live_tracker.presentation.model.DifferenceType
import zed.rainxch.augustminichallenges.live_tracker.presentation.model.TrackItem
import zed.rainxch.augustminichallenges.ui.theme.LiveTrackerColors
import zed.rainxch.augustminichallenges.ui.theme.hostGrotesk

@Composable
fun LiveTrackerItem(
    item: TrackItem,
    status: LiveTrackerStatus,
    modifier: Modifier = Modifier,
) {
    val borderModifier = if (item.isFeedDown) {
        Modifier.border(2.dp, LiveTrackerColors.error, RoundedCornerShape(16.dp))
    } else Modifier

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .dropShadow(RoundedCornerShape(16.dp), shadow = Shadow(8.dp))
            .background(LiveTrackerColors.surfaceHigher)
            .then(borderModifier)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = item.currency,
                fontFamily = hostGrotesk,
                color = if (item.isFeedDown) LiveTrackerColors.textSecondary else LiveTrackerColors.textPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(5.dp))

            Row {
                Text(
                    text = item.latestUpdateTime,
                    fontFamily = hostGrotesk,
                    color = if (item.isFeedDown) LiveTrackerColors.error else LiveTrackerColors.textSecondary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal
                )

                if (item.isFeedDown) {
                    Text(
                        text = ", Feed down",
                        fontFamily = hostGrotesk,
                        color = LiveTrackerColors.error,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }

        Row(
            modifier = Modifier.animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LiveTrackerIcon(
                icon = ImageVector.vectorResource(item.differenceType.drawableId),
                type = when (status) {
                    LiveTrackerStatus.RUNNING -> {
                        when (item.differenceType) {
                            DifferenceType.INCREASE -> LiveTrackerIconType.FILL_BUBBLE
                            DifferenceType.DECREASE -> LiveTrackerIconType.FILL_BUBBLE
                            DifferenceType.EQUAL -> LiveTrackerIconType.LINE
                        }
                    }

                    LiveTrackerStatus.PAUSED -> {
                        when (item.differenceType) {
                            DifferenceType.INCREASE -> LiveTrackerIconType.LINE
                            DifferenceType.DECREASE -> LiveTrackerIconType.LINE
                            DifferenceType.EQUAL -> LiveTrackerIconType.LINE
                        }
                    }
                },
                primaryColor = if (item.isFeedDown) {
                    LiveTrackerColors.textDisabled
                } else {
                    when (item.differenceType) {
                        DifferenceType.INCREASE -> LiveTrackerColors.primary
                        DifferenceType.DECREASE -> LiveTrackerColors.error
                        DifferenceType.EQUAL -> LiveTrackerColors.textDisabled
                    }
                },
            )

            Text(
                text = "${item.value} USD",
                fontFamily = hostGrotesk,
                color = if (item.isFeedDown) LiveTrackerColors.textSecondary else LiveTrackerColors.textPrimary,
                fontSize = 19.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
private fun LiveTrackerItemPreview() {
    LiveTrackerItem(
        item = TrackItem(
            currency = "BSE",
            latestUpdateTime = "12:04:13",
            value = 295.10,
            differenceType = DifferenceType.INCREASE,
            updateDelayMs = 0L
        ),
        status = LiveTrackerStatus.RUNNING
    )
}

@Preview
@Composable
private fun LiveTrackerItem2Preview() {
    LiveTrackerItem(
        item = TrackItem(
            currency = "BSE",
            latestUpdateTime = "12:04:13",
            value = 295.10,
            differenceType = DifferenceType.DECREASE,
            updateDelayMs = 0L
        ),
        status = LiveTrackerStatus.RUNNING
    )
}

@Preview
@Composable
private fun LiveTrackerItemFeedDownPreview() {
    LiveTrackerItem(
        item = TrackItem(
            currency = "BSE",
            latestUpdateTime = "12:04:13",
            value = 295.10,
            differenceType = DifferenceType.DECREASE,
            isFeedDown = true,
            updateDelayMs = 0L
        ),
        status = LiveTrackerStatus.PAUSED
    )
}

@Preview
@Composable
private fun LiveTrackerItemPausedPreview() {
    LiveTrackerItem(
        item = TrackItem(
            currency = "BSE",
            latestUpdateTime = "12:04:13",
            value = 295.10,
            differenceType = DifferenceType.DECREASE,
            updateDelayMs = 0L
        ),
        status = LiveTrackerStatus.PAUSED
    )
}