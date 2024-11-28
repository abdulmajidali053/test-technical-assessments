package uk.co.bbc.application.utils

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import uk.co.bbc.application.TEST_TAG_LAST_UPDATED

object ComposeAssertions {

    fun isDisplayed(composeTestRule: ComposeTestRule, testTag: String) {
        composeTestRule.onNodeWithTag(testTag).assertIsDisplayed()
    }
    fun isNotDisplayed(composeTestRule: ComposeTestRule, testTag: String) {
        composeTestRule.onNodeWithTag(testTag).isNotDisplayed()
    }
    fun areDisplayed(composeTestRule: ComposeTestRule, text: String, index: Int) {
        composeTestRule.onAllNodesWithContentDescription(text)[index]
    }
    fun isDisplayedWithText(composeTestRule: ComposeTestRule, text: String) {
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }
    fun isDisplayedWithTextSubstring(composeTestRule: ComposeTestRule, text: String){
        composeTestRule.onNodeWithText(text, true).assertIsDisplayed()
    }
    fun containsTextByTag(composeTestRule: ComposeTestRule, testTag: String, text: String) {
        composeTestRule.onNodeWithTag(testTag).assertTextContains(text)
    }
    fun isTextEqual(textA: String, textB: String) {
        assert(textA == textB)
    }
    fun isTextNotEqual(textA: String, textB: String) {
        assert(textA != textB)
    }
    fun isTitleDisplayed(composeTestRule: ComposeTestRule, title: String) {
        composeTestRule.onAllNodesWithText(title)[0].assertIsDisplayed()
    }
    fun isChildWithTextDisplayed(composeTestRule: ComposeTestRule, parentTestTag: String, childText: String) {
        composeTestRule.onNodeWithTag(parentTestTag)
            .onChildren()
            .filter(hasText(childText))
            .assertAny(hasText(childText))
    }
}