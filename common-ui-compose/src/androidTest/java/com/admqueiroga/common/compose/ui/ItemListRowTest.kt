import androidx.compose.material.Text
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.admqueiroga.common.compose.model.Item
import com.admqueiroga.common.compose.ui.ItemListRow
import org.junit.Rule
import org.junit.Test

class ItemListRowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEmptyList() {
        composeTestRule.setContent {
            ItemListRow(title = "Empty", items = emptyList(), onItemClick = {}, onShowMoreClick = {})
        }
        composeTestRule.onNodeWithTag(TYPE_ITEM).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TYPE_MORE).assertDoesNotExist()
    }

    @Test
    fun testPartialList() {
        val items = listOf(
            Item(id = 1, title = "Item 1"),
            Item(id = 2, title = "Item 2")
        )
        composeTestRule.setContent {
            ItemListRow(title = "Partial", items = items, onItemClick = {}, onShowMoreClick = {})
        }
        items.forEach {
            composeTestRule.onNodeWithText("Partial", true)
                .performScrollToNode(hasTestTag(ItemListRow.tagForItem(it)))
                .assertIsDisplayed()

        }
        composeTestRule.onNodeWithTag(TYPE_MORE).assertDoesNotExist()
    }

    @Test
    fun testFullList() {
        val items = (1..10).map { Item(id = it.toLong(), title = "Item $it") }
        composeTestRule.setContent {
            ItemListRow(title = "Full", items = items, maxItemsToDisplay = 10, onItemClick = {}, onShowMoreClick = {})
        }
        items.forEach {
            composeTestRule.onNodeWithText("Full", true)
                .performScrollToNode(hasTestTag(ItemListRow.tagForItem(it)))
                .assertIsDisplayed()

        }
        composeTestRule.onNodeWithTag(TYPE_MORE).assertDoesNotExist()
    }

    @Test
    fun testOverflowList() {
        val items = (1..15).map { Item(id = it.toLong(), title = "Item $it") }
        composeTestRule.setContent {
            ItemListRow(
                title = "Overflow",
                items = items, maxItemsToDisplay = 10, onItemClick = {}, onShowMoreClick = {})
        }
        items.forEach {
            composeTestRule.onNodeWithText("Overflow", true)
                .performScrollToNode(hasTestTag(ItemListRow.tagForItem(it)))
                .assertIsDisplayed()

        }
        composeTestRule.onNodeWithTag(TYPE_MORE).assertIsDisplayed()
    }

    @Test
    fun testWithTitle() {
        composeTestRule.setContent {
            ItemListRow(
                title = { Text("My Items") },
                items = emptyList(),
                onItemClick = {},
                onShowMoreClick = {}
            )
        }
        composeTestRule.onNodeWithText("My Items").assertIsDisplayed()
    }

    @Test
    fun testWithoutTitle() {
        composeTestRule.setContent {
            ItemListRow(items = emptyList(), onItemClick = {}, onShowMoreClick = {})
        }
        composeTestRule.onNodeWithText("My Items").assertDoesNotExist() // Assuming no item has this text
    }

    @Test
    fun testItemClick() {
        var clickedItem: Item? = null
        val items = listOf(
            Item(id = 1, title = "Item 1"),
            Item(id = 2, title = "Item 2")
        )
        composeTestRule.setContent {
            ItemListRow(items = items, onItemClick = { clickedItem = it }, onShowMoreClick = {})
        }
        composeTestRule.onNodeWithText("Item 1").performClick()
        assert(clickedItem == items[0])
    }

    @Test
    fun testMoreClick() {
        var moreClicked = false
        composeTestRule.setContent {
            ItemListRow(
                items = (1..15).map { Item(id = it.toLong(), title = "Item $it") },
                maxItemsToDisplay = 10,
                onItemClick = {},
                onShowMoreClick = { moreClicked = true }
            )
        }
        composeTestRule.onNodeWithTag(TYPE_MORE).performClick()
        assert(moreClicked)
    }

    companion object {
        const val TYPE_ITEM = "item_card"
        const val TYPE_MORE = "more_button"
    }
}