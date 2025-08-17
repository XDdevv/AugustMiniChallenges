package zed.rainxch.augustminichallenges.parcel_pigeon_race.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zed.rainxch.augustminichallenges.ui.theme.ParcelPigeonRaceColors
import zed.rainxch.augustminichallenges.ui.theme.ThermemoterColors
import zed.rainxch.augustminichallenges.ui.theme.hostGrotesk

@Composable
fun ParcelButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ParcelPigeonRaceColors.primary,
            contentColor = Color.White,
            disabledContainerColor = ParcelPigeonRaceColors.surfaceHighest,
            disabledContentColor = ParcelPigeonRaceColors.textDisabled
        ),
        enabled = enabled,
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 17.sp,
            fontFamily = hostGrotesk,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
private fun ParcelButtonPreview() {
    ParcelButton(
        text = "Reset",
        onClick = { }
    )
}

@Preview
@Composable
private fun ThermemoterButtonPreviewDisabled() {
    ParcelButton(
        text = "Tracking...",
        enabled = false,
        onClick = { }
    )
}