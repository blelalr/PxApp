package com.android.pxapp.ui.message

sealed class ToolbarState {

    object EditState : ToolbarState()

    object DefaultViewState: ToolbarState()
}