package zed.rainxch.augustminichallenges.thermemoter_trek.presentation.components

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
import zed.rainxch.augustminichallenges.ui.theme.ThermemoterColors
import zed.rainxch.augustminichallenges.ui.theme.hostGrotesk

@Composable
fun ThermemoterButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ThermemoterColors.primary,
            contentColor = Color.White,
            disabledContainerColor = ThermemoterColors.surface,
            disabledContentColor = ThermemoterColors.textDisabled
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
private fun ThermemoterButtonPreview() {
    ThermemoterButton(
        text = "Reset",
        onClick = { }
    )
}

@Preview
@Composable
private fun ThermemoterButtonPreviewDisabled() {
    ThermemoterButton(
        text = "Tracking...",
        enabled = false,
        onClick = { }
    )
}