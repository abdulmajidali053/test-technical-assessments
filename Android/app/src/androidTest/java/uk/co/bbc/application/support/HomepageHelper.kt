package uk.co.bbc.application.support

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import uk.co.bbc.application.utils.ComposeAssertions
import uk.co.bbc.application.utils.ComposeActions

object HomepageHelper {

    private const val mainActivityHeaderTestTag = "go to button"
    private const val dropDown = "testTag2"

    fun waitForMainActivityToLoad(composeTestRule: ComposeTestRule) {
        composeTestRule.waitForIdle()
        ComposeAssertions.isChildWithTextDisplayed(composeTestRule, mainActivityHeaderTestTag, "Go to Politics")
    }
    fun doSomethingElse(composeTestRule: ComposeTestRule) {
        ComposeAssertions.isDisplayedWithText(composeTestRule, "Something")
        ComposeActions.performClick(composeTestRule, "testTag")
        composeTestRule.waitForIdle()
    }
}