package zed.rainxch.augustminichallenges.heartbeat.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zed.rainxch.augustminichallenges.R
import zed.rainxch.augustminichallenges.ui.theme.HeartbeatColors
import zed.rainxch.augustminichallenges.ui.theme.hostGrotesk

@Composable
fun HeartbeatSnack(
    message: String,
    containerColor: Color,
    isSuccess: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = if (isSuccess) {
                    Icons.Outlined.CheckCircle
                } else ImageVector.vectorResource(R.drawable.ic_close_circle),
                contentDescription = message,
                tint = HeartbeatColors.onPrimaryAlt,
                modifier = Modifier
                    .size(20.dp)
            )

            Text(
                text = message,
                color = HeartbeatColors.onPrimary,
                fontFamily = hostGrotesk,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
private fun HeartbeatSnackPreview() {
    HeartbeatSnack(
        message = "Station C is offline",
        containerColor = Color.Red,
        isSuccess = false
    )
}

@Preview
@Composable
private fun HeartbeatSnack2Preview() {
    HeartbeatSnack(
        message = "Station C is online",
        containerColor = Color.Green,
        isSuccess = true
    )
}