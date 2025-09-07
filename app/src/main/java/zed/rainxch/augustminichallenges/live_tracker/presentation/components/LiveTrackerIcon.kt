package zed.rainxch.augustminichallenges.live_tracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zed.rainxch.augustminichallenges.ui.theme.LiveTrackerColors

enum class LiveTrackerIconType {
    FILL,
    FILL_BUBBLE,
    LINE
}

@Composable
fun LiveTrackerIcon(
    icon: ImageVector,
    type: LiveTrackerIconType,
    primaryColor: Color,
    modifier: Modifier = Modifier,
) {
    val iconModifier = when (type) {
        LiveTrackerIconType.FILL -> {
            Modifier
                .clip(CircleShape)
                .background(primaryColor)
        }

        LiveTrackerIconType.FILL_BUBBLE -> {
            Modifier
                .clip(CircleShape)
                .background(primaryColor.copy(alpha = .3f))
                .padding(2.dp)
                .clip(CircleShape)
                .background(primaryColor)
                .padding(2.dp)
        }

        LiveTrackerIconType.LINE -> {
            Modifier.padding(2.dp)
        }
    }
    val iconTint = when (type) {
        LiveTrackerIconType.FILL, LiveTrackerIconType.FILL_BUBBLE -> LiveTrackerColors.onPrimary
        LiveTrackerIconType.LINE -> primaryColor
    }

    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = iconTint,
        modifier = modifier
            .size(24.dp)
            .then(iconModifier)
    )
}

@Preview
@Composable
private fun LiveTrackerIconFillPreview() {
    LiveTrackerIcon(
        icon = Icons.Default.Add,
        type = LiveTrackerIconType.FILL,
        primaryColor = Color.Green
    )
}

@Preview
@Composable
private fun LiveTrackerIconFillBubblePreview() {
    LiveTrackerIcon(
        icon = Icons.Default.Add,
        type = LiveTrackerIconType.FILL_BUBBLE,
        primaryColor = Color.Green
    )
}

@Preview
@Composable
private fun LiveTrackerIconLinePreview() {
    LiveTrackerIcon(
        icon = Icons.Default.Add,
        type = LiveTrackerIconType.LINE,
        primaryColor = Color.Green
    )
}