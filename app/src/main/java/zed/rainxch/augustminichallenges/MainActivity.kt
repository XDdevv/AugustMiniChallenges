package zed.rainxch.augustminichallenges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import zed.rainxch.augustminichallenges.order_queue.presentation.OrderQueueOutpostRoot
import zed.rainxch.augustminichallenges.ui.theme.AugustMiniChallengesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AugustMiniChallengesTheme {
                OrderQueueOutpostRoot()
            }
        }
    }
}