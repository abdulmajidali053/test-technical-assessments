package uk.co.bbc.application.utils

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import uk.co.bbc.application.TEST_TAG_LOADING_SPINNER

object ComposeActions {

    fun performClick(composeTestRule: ComposeTestRule, testTag: String) {
        composeTestRule.onNodeWithTag(testTag).performClick()
    }
    fun performClickOnText(composeTestRule: ComposeTestRule, text: String) {
        composeTestRule.onNodeWithText(text).performClick()
    }
    fun enterText(composeTestRule: ComposeTestRule, testTag: String, text: String) {
        composeTestRule.onNodeWithTag(testTag).performTextInput(text)
    }

    fun scrollToTag(composeTestRule: ComposeTestRule, testTag: String) {
        composeTestRule.onNodeWithTag(testTag).performScrollTo()
    }
    fun scrollToText(composeTestRule: ComposeTestRule, text: String) {
        composeTestRule.onNodeWithText(text, true, true).performScrollTo()
    }
    fun waitUntilTagIsDisplayed(composeTestRule: ComposeTestRule, testTag: String, duration: Long = 0){
        composeTestRule.waitUntil(duration){
            composeTestRule.onNodeWithTag(testTag).isDisplayed()
        }
    }
    fun waitUntilTagIsNotDisplayed(composeTestRule: ComposeTestRule, testTag: String, duration: Long = 0){
        composeTestRule.waitUntil(duration){
            composeTestRule.onNodeWithTag(testTag).isNotDisplayed()
        }
    }
}