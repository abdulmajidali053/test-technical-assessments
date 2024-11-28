package uk.co.bbc.application

import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.semantics.text
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.core.app.ActivityScenario
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.bbc.application.support.HomepageHelper
import uk.co.bbc.application.utils.ComposeActions
import uk.co.bbc.application.utils.ComposeAssertions
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.concurrent.thread

class HomepageTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mainActivityScenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        mainActivityScenario = ActivityScenario.launch(MainActivity::class.java)
    }
    @Test /*Scenario 1*/
    fun testHomePageDisplaysAndLoadsEssentialElements(){
        mainActivityScenario.use {
            ComposeAssertions.areDisplayed(composeTestRule,"BBC Logo", 0)
            ComposeAssertions.areDisplayed(composeTestRule,"BBC Logo", 1)
            ComposeAssertions.isDisplayedWithTextSubstring(composeTestRule, "favourite content")
            ComposeAssertions.isDisplayed(composeTestRule, TEST_TAG_LAST_UPDATED)
            ComposeAssertions.isDisplayed(composeTestRule, TEST_TAG_REFRESH_BUTTON)
            ComposeAssertions.isDisplayed(composeTestRule, TEST_TAG_GO_TO_BUTTON)
            ComposeAssertions.isDisplayed(composeTestRule, TEST_TAG_BREAKING_NEWS_BUTTON)
            ComposeAssertions.isDisplayedWithTextSubstring(composeTestRule, "Go to Politics")
        }
    }
    @Test /*Scenario 2*/
    fun testRefreshButtonUpdates(){
        mainActivityScenario.use {
            val initialLastUpdatedNodeText = HomepageHelper.getCurrentNodeStateTextByTag(composeTestRule, TEST_TAG_LAST_UPDATED)
            Thread.sleep(5000)
            ComposeActions.performClick(composeTestRule, TEST_TAG_REFRESH_BUTTON)
            val currentTimeAndDate = HomepageHelper.currentTimeAndDateFormatted()
            ComposeAssertions.isDisplayed(composeTestRule, TEST_TAG_LOADING_SPINNER)
            ComposeActions.waitUntilTagIsNotDisplayed(composeTestRule, TEST_TAG_LOADING_SPINNER, 5000)
            val updatedLastUpdatedNodeText = HomepageHelper.getCurrentNodeStateTextByTag(composeTestRule, TEST_TAG_LAST_UPDATED)
            ComposeAssertions.isTextNotEqual(initialLastUpdatedNodeText, updatedLastUpdatedNodeText)
            ComposeAssertions.containsTextByTag(composeTestRule, TEST_TAG_LAST_UPDATED, "Last updated: ${currentTimeAndDate[0]} at ${currentTimeAndDate[1]}")
        }
    }
    @Test /*Scenario 3*/
    fun testGoToLinkTextUpdates(){
        ComposeAssertions.isDisplayedWithText(composeTestRule,"Politics")
        ComposeActions.performClickOnText(composeTestRule, "Politics")
        ComposeActions.waitUntilTagIsDisplayed(composeTestRule, TEST_TAG_DROPDOWN_MENU)
        ComposeActions.performClickOnText(composeTestRule, "Technology")
        val actualLinkText = HomepageHelper.getCurrentNodeStateTextByTag(composeTestRule, TEST_TAG_GO_TO_BUTTON)
        assert(actualLinkText.contains("Go to Technology"))
    }
    @Test /*Scenario 4*/
    fun testGoToLinkNavigatesToCorrectPage(){
        val currentTopic = "Politics"
        ComposeAssertions.isDisplayedWithText(composeTestRule, currentTopic)
        ComposeActions.performClickOnText(composeTestRule, currentTopic)
        ComposeActions.waitUntilTagIsDisplayed(composeTestRule, TEST_TAG_DROPDOWN_MENU)
        val newTopic = "Technology"
        ComposeActions.performClickOnText(composeTestRule, newTopic)
        ComposeActions.performClick(composeTestRule, TEST_TAG_GO_TO_BUTTON)
        ComposeAssertions.isDisplayed(composeTestRule, TEST_TAG_BACK_BUTTON)
        ComposeAssertions.isTitleDisplayed(composeTestRule, newTopic)
        ComposeActions.scrollToText(composeTestRule, "end of the placeholder")
        ComposeActions.performClick(composeTestRule, TEST_TAG_BACK_BUTTON)
        ComposeAssertions.areDisplayed(composeTestRule,"BBC Logo", 0)
        ComposeAssertions.areDisplayed(composeTestRule,"BBC Logo", 1)
        ComposeAssertions.isDisplayedWithTextSubstring(composeTestRule, "favourite content")
    }
    @Test /*Scenario 5*/
    fun testTVGuideAlertPickNo(){
        val currentTopic = "Politics"
        ComposeAssertions.isDisplayedWithText(composeTestRule, currentTopic)
        ComposeActions.performClickOnText(composeTestRule, currentTopic)
        ComposeActions.waitUntilTagIsDisplayed(composeTestRule, TEST_TAG_DROPDOWN_MENU)
        val newTopic = "TV Guide"
        ComposeActions.performClickOnText(composeTestRule, newTopic)
        ComposeActions.performClick(composeTestRule, TEST_TAG_GO_TO_BUTTON)
        composeTestRule.onNodeWithText("Do you have a TV license ?", true, true).assertIsDisplayed()
        ComposeActions.performClickOnText(composeTestRule, "No")
        composeTestRule.waitUntil(2000) {
            composeTestRule.onNodeWithText("Do you have a TV license ?", true, true).isNotDisplayed()
        }
        ComposeAssertions.areDisplayed(composeTestRule,"BBC Logo", 0)
        ComposeAssertions.areDisplayed(composeTestRule,"BBC Logo", 1)
        ComposeAssertions.isDisplayedWithTextSubstring(composeTestRule, "favourite content")
    }
    @Test /*Scenario 6*/
    fun testTVGuideAlertPickYes(){
        val currentTopic = "Politics"
        ComposeAssertions.isDisplayedWithText(composeTestRule, currentTopic)
        ComposeActions.performClickOnText(composeTestRule, currentTopic)
        ComposeActions.waitUntilTagIsDisplayed(composeTestRule, TEST_TAG_DROPDOWN_MENU)
        val newTopic = "TV Guide"
        ComposeActions.performClickOnText(composeTestRule, newTopic)
        ComposeActions.performClick(composeTestRule, TEST_TAG_GO_TO_BUTTON)
        composeTestRule.onNodeWithText("Do you have a TV license ?", true, true).assertIsDisplayed()
        ComposeActions.performClickOnText(composeTestRule, "Yes")
        composeTestRule.waitUntil(2000) {
            composeTestRule.onNodeWithText("Do you have a TV license ?", true, true).isNotDisplayed()
        }
        ComposeAssertions.isDisplayed(composeTestRule, TEST_TAG_BACK_BUTTON)
        ComposeAssertions.isTitleDisplayed(composeTestRule, newTopic)
        ComposeActions.performClick(composeTestRule, TEST_TAG_BACK_BUTTON)
        ComposeAssertions.areDisplayed(composeTestRule,"BBC Logo", 0)
        ComposeAssertions.areDisplayed(composeTestRule,"BBC Logo", 1)
        ComposeAssertions.isDisplayedWithTextSubstring(composeTestRule, "favourite content")
    }
    @Test /*Scenario 7*/
    fun testBreakingNewsButtonError(){
        ComposeActions.performClick(composeTestRule, TEST_TAG_BREAKING_NEWS_BUTTON)
        ComposeAssertions.isDisplayed(composeTestRule, TEST_TAG_ALERT_CONFIRM_BUTTON)
        ComposeActions.performClick(composeTestRule, TEST_TAG_ALERT_CONFIRM_BUTTON)
        ComposeAssertions.isNotDisplayed(composeTestRule, TEST_TAG_ALERT_CONFIRM_BUTTON)
        ComposeAssertions.areDisplayed(composeTestRule,"BBC Logo", 0)
        ComposeAssertions.areDisplayed(composeTestRule,"BBC Logo", 1)
        ComposeAssertions.isDisplayedWithTextSubstring(composeTestRule, "favourite content")
    }
}