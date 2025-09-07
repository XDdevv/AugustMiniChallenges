package zed.rainxch.augustminichallenges.live_tracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zed.rainxch.augustminichallenges.ui.theme.LiveTrackerColors
import zed.rainxch.augustminichallenges.ui.theme.hostGrotesk

@Composable
fun LiveTrackerTextChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(LiveTrackerColors.surfaceHighest)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            color = LiveTrackerColors.textSecondary,
            fontSize = 16.sp,
            fontFamily = hostGrotesk
        )
    }
}