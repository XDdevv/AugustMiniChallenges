package zed.rainxch.augustminichallenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import zed.rainxch.augustminichallenges.thermemoter_trek.presentation.ThermemoterTrekScreenRoot
import zed.rainxch.augustminichallenges.ui.theme.AugustMiniChallengesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AugustMiniChallengesTheme {
                ThermemoterTrekScreenRoot()
            }
        }
    }
}