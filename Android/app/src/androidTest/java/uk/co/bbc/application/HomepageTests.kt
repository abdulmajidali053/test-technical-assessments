package uk.co.bbc.application

import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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
    fun testHomePageDisplays(){
        mainActivityScenario.use {
            composeTestRule.onAllNodesWithContentDescription("BBC Logo")[0].assertIsDisplayed()
            composeTestRule.onAllNodesWithContentDescription("BBC Logo")[1].assertIsDisplayed()
            composeTestRule.onAllNodes(hasText("content", substring = true, ignoreCase = true))[0].assertIsDisplayed()
            composeTestRule.onNodeWithTag(TEST_TAG_LAST_UPDATED).assertIsDisplayed()
            composeTestRule.onNodeWithTag(TEST_TAG_REFRESH_BUTTON).assertIsDisplayed()
            composeTestRule.onNodeWithTag(TEST_TAG_GO_TO_BUTTON).assertIsDisplayed()
            composeTestRule.onNodeWithTag(TEST_TAG_BREAKING_NEWS_BUTTON).assertIsDisplayed()
            composeTestRule.onNodeWithTag(TEST_TAG_BREAKING_NEWS_BUTTON).assertTextEquals("Breaking News")
            composeTestRule.onNodeWithText("Politics").assertIsDisplayed()
            composeTestRule.onNodeWithText("Politics").performClick()
            composeTestRule.onNodeWithTag("dropdown menu").assertIsDisplayed()
        }
    }
    @Test /*Scenario 2*/
    fun testRefreshButtonUpdates(){
        mainActivityScenario.use {
            val node = composeTestRule.onNodeWithTag(TEST_TAG_LAST_UPDATED).fetchSemanticsNode()
            val initialLastUpdatedText = node.config.getOrNull(Text)
            Thread.sleep(5000)
            composeTestRule.onNodeWithTag(TEST_TAG_REFRESH_BUTTON).performClick()
            val dayFormat = SimpleDateFormat("dd MMM yyyy")
            val timeFormat = SimpleDateFormat("hh:mm:ss")
            val day = dayFormat.format(Date())
            val time = timeFormat.format(Date())
            composeTestRule.onNodeWithTag(TEST_TAG_LOADING_SPINNER).assertIsDisplayed()
            composeTestRule.waitUntil(5000){
                composeTestRule.onNodeWithTag(TEST_TAG_LOADING_SPINNER).isNotDisplayed()
            }
            val updatedNode = composeTestRule.onNodeWithTag(TEST_TAG_LAST_UPDATED).fetchSemanticsNode()
            val updatedLastUpdatedText = updatedNode.config.getOrNull(Text)
            assert(initialLastUpdatedText != updatedLastUpdatedText)
            composeTestRule.onNodeWithTag(TEST_TAG_LAST_UPDATED).assertTextContains("Last updated: $day at $time")

        }
    }
}