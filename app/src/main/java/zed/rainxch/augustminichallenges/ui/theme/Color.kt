package zed.rainxch.augustminichallenges.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

object ThermemoterColors {

    val surface = Color(0xffF4F6F6)
    val surfaceHigher = Color(0xffFFFFFF)
    val shadow = Color(0x2E364214)
    val textPrimary = Color(0xff2E3642)
    val textDisabled = Color(0xffB4BDCA)
    val textSecondary = Color(0xff66707F)
    val primary = Color(0xff37B98B)
}

object OrderQueueColors {

    val surface = Color(0xffF4F6F6)
    val surfaceHigher = Color(0xffFFFFFF)
    val surfaceHighest = Color(0xffE2E5E9)
    val shadow = Color(0x2E364214)
    val textPrimary = Color(0xff2E3642)
    val textDisabled = Color(0xffB4BDCA)
    val textSecondary = Color(0xff66707F)
    val overload = Color(0xffA60A36)
    val primary = Color(0xff37B98B)
    val warning = Color(0xffFEB43C)
}

object ParcelPigeonRaceColors {
    val primary = Color(0xff37b98b)
    val textPrimary = Color(0xff2E3642)
    val textSecondary = Color(0xff66707F)
    val textDisabled = Color(0xffB4BDCA)
    val surface = Color(0xffF4F6F6)
    val surfaceHigher = Color(0xffFFFFFF)
    val surfaceHighest = Color(0xffE2E5E9)
    val outline = Brush.linearGradient(
        listOf(
            Color(0xffFFFFFF).copy(alpha = .25f),
            Color(0xffFFFFFF).copy(alpha = 0f)
        )
    )
    val outlineImg = Color(0xff2E3642).copy(alpha = .05f)
    val loadingImg = Color(0xff2E3642).copy(alpha = .5f)
}

object LiveTrackerColors {
    val primary = Color(0xff37b98b)
    val textPrimary = Color(0xff2E3642)
    val textSecondary = Color(0xff66707F)
    val textDisabled = Color(0xffB4BDCA)
    val surface = Color(0xffF4F6F6)
    val surfaceHigher = Color(0xffFFFFFF)
    val surfaceHighest = Color(0xffE2E5E9)
    val onPrimary = Color(0xffffffff)
    val outline = Brush.linearGradient(
        listOf(
            Color(0xffFFFFFF).copy(alpha = .25f),
            Color(0xffFFFFFF).copy(alpha = 0f)
        )
    )
    val error = Color(0xffF9465A)
}

object HeartbeatColors {
    val primary = Color(0xff37b98b)
    val textPrimary = Color(0xff2E3642)
    val textSecondary = Color(0xff66707F)
    val onPrimary = Color(0xffffffff)
    val onPrimaryAlt = Color(0xffffffff).copy(alpha = .75f)
    val surface = Color(0xfff4f6f6)
    val surfaceHigher = Color(0xffffffff)
    val error = Color(0xffF9465A)
}