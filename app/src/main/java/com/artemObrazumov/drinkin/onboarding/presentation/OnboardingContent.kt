package com.artemObrazumov.drinkin.onboarding.presentation

import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.core.data.Resource

object OnboardingContent {

    // TODO: put onboarding pages
    val pages = listOf(
        OnboardingPage(
            title = Resource.StringResource(R.string.cancel),
            subtitle = Resource.StringResource(R.string.cancel)
        )
    )
}

data class OnboardingPage(
    val title: Resource.StringResource,
    val subtitle: Resource.StringResource
)