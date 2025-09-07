package zed.rainxch.augustminichallenges.live_tracker.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zed.rainxch.augustminichallenges.ui.theme.LiveTrackerColors
import zed.rainxch.augustminichallenges.ui.theme.ParcelPigeonRaceColors
import zed.rainxch.augustminichallenges.ui.theme.hostGrotesk

@Composable
fun LiveTrackerButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    containerColor: Color? = null,
    contentColor: Color? = null,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor ?: ParcelPigeonRaceColors.primary,
            contentColor = contentColor ?: Color.White,
            disabledContainerColor = ParcelPigeonRaceColors.surfaceHighest,
            disabledContentColor = ParcelPigeonRaceColors.textDisabled
        ),
        enabled = enabled,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = text
                )
            }

            Text(
                text = text,
                fontSize = 17.sp,
                fontFamily = hostGrotesk,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
private fun LiveTrackButtonPreview() {
    LiveTrackerButton(
        text = "Reset",
        onClick = { },
        icon = Icons.Outlined.PlayArrow
    )
}

@Preview
@Composable
private fun LiveTrackButtonPreviewDisabled() {
    LiveTrackerButton(
        icon = Icons.Outlined.AccountBox,
        text = "Tracking...",
        enabled = false,
        onClick = { }
    )
}

@Preview
@Composable
private fun LiveTrackButtonPreviewCustomColor() {
    LiveTrackerButton(
        icon = Icons.Outlined.AccountBox,
        text = "Tracking...",
        containerColor = LiveTrackerColors.surfaceHighest,
        contentColor = LiveTrackerColors.textPrimary,
        onClick = { }
    )
}