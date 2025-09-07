package zed.rainxch.augustminichallenges.live_tracker.presentation

sealed interface LiveTrackerAction {
    object OnPauseClick : LiveTrackerAction
    object OnResumeClick : LiveTrackerAction
    object OnBreakXETRAClick : LiveTrackerAction
}