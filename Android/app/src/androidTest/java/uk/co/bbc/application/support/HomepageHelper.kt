package uk.co.bbc.application.support

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import uk.co.bbc.application.TEST_TAG_LAST_UPDATED
import uk.co.bbc.application.utils.ComposeAssertions
import uk.co.bbc.application.utils.ComposeActions
import java.text.SimpleDateFormat
import java.util.Date

object HomepageHelper {

    private const val mainActivityHeaderTestTag = "go to button"
    private const val dropDown = "testTag2"

    fun waitForMainActivityToLoad(composeTestRule: ComposeTestRule) {
        composeTestRule.waitForIdle()
        ComposeAssertions.isChildWithTextDisplayed(composeTestRule, mainActivityHeaderTestTag, "Go to Politics")
    }
    fun getCurrentNodeStateTextByTag(composeTestRule: ComposeTestRule, testTag: String): String {
        val semanticNodes = composeTestRule.onNodeWithTag(testTag).fetchSemanticsNode()
        val nodeText = semanticNodes.config.getOrNull(SemanticsProperties.Text)
        return nodeText.toString()
    }
    fun currentTimeAndDateFormatted(): Array<String> {
        val dayFormat = SimpleDateFormat("dd MMM yyyy")
        val timeFormat = SimpleDateFormat("hh:mm:ss")
        val day = dayFormat.format(Date())
        val time = timeFormat.format(Date())
        return arrayOf(day, time)
    }
}