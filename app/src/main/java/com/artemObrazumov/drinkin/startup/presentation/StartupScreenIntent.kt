package com.artemObrazumov.drinkin.startup.presentation

sealed class StartupScreenIntent {

    data object Onboarding: StartupScreenIntent()
    data object Authorization: StartupScreenIntent()
    data object Dashboard: StartupScreenIntent()
}