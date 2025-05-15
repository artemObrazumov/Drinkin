package com.artemObrazumov.drinkin.onboarding.presentation

import com.artemObrazumov.drinkin.R
import com.artemObrazumov.drinkin.core.data.Resource

object OnboardingContent {

    val pages = listOf(
        OnboardingPage(
            title = Resource.StringResource(R.string.onboarding_perfect_coffee_title),
            subtitle = Resource.StringResource(R.string.onboarding_perfect_coffee_subtitle),
            imageRes = R.drawable.cup
        ),
        OnboardingPage(
            title = Resource.StringResource(R.string.onboarding_explore_menu_title),
            subtitle = Resource.StringResource(R.string.onboarding_explore_menu_subtitle),
            imageRes = R.drawable.coffee_multiple_cups
        ),
        OnboardingPage(
            title = Resource.StringResource(R.string.onboarding_easy_ordering_title),
            subtitle = Resource.StringResource(R.string.onboarding_easy_ordering_subtitle),
            imageRes = R.drawable.hand_phone
        )
    )
}

data class OnboardingPage(
    val title: Resource.StringResource,
    val subtitle: Resource.StringResource,
    val imageRes: Int
)