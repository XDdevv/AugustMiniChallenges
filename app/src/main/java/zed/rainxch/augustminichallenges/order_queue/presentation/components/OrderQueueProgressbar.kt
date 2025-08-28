package zed.rainxch.augustminichallenges.order_queue.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zed.rainxch.augustminichallenges.ui.theme.OrderQueueColors
import zed.rainxch.augustminichallenges.ui.theme.hostGrotesk

@Composable
fun OrderQueueProgressbar(
    progressPercentage: Float,
    progress: Int,
    segments: Int,
    trackColor: Color,
    progressColor: Color,
    overflow: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier
                .height(12.dp)
                .clip(CircleShape)
                .drawBehind {
                    val segmentWidth = size.width / segments
                    for (i in 0 until segments) {
                        // Each segment track
                        drawRoundRect(
                            color = trackColor,
                            topLeft = Offset(x = i * segmentWidth, 0f),
                            size = Size(segmentWidth - 4.dp.toPx(), size.height), // 4.dp gap
                            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                        )
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            LinearProgressIndicator(
                progress = { progressPercentage },
                color = progressColor,
                trackColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                strokeCap = StrokeCap.Square
            )

            if (progress > overflow) {
                Spacer(
                    Modifier
                        .height(12.dp)
                        .width(1.dp)
                        .background(OrderQueueColors.surfaceHigher)
                )

                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .width(((progress - 20) * 4).dp)
                        .background(OrderQueueColors.overload)
                )
            }
        }

        if (progress > overflow) {
            Text(
                text = "100%",
                fontFamily = hostGrotesk,
                fontWeight = FontWeight.Medium,
                color = OrderQueueColors.textDisabled,
                fontSize = 13.sp,
                modifier = Modifier.padding(end = ((progress - 20) * 2).dp)
            )
        }
    }
}