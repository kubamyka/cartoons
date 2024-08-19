package com.kmcoding.cartoons.util

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule

object CartoonsTestHelper {
    fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringIdTag(
        @StringRes
        id: Int,
    ): SemanticsNodeInteraction = onNodeWithTag(activity.getString(id))

    fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringIdContentDescription(
        @StringRes
        id: Int,
    ): SemanticsNodeInteraction = onNodeWithContentDescription(activity.getString(id))

    fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringIdText(
        @StringRes
        id: Int,
    ): SemanticsNodeInteraction = onNodeWithText(activity.getString(id))
}
