package new_task


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.lolo.io.onelist.MainActivity
import com.lolo.io.onelist.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class newTask {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    //In this test we are going to add a new task to the Tuto ? list
    fun newTask() {
        // here we are putting the location for the input box in a variable
        val addNewTask = onView(
            allOf(
                //the input element is a child of the item layout in position with the index 0
                withId(R.id.addItemEditText),
                childAtPosition(
                    allOf(
                        //the item layout is a child of the class Relative Layout with the index 1
                        withId(R.id.addItemLayout),
                        childAtPosition(
                            withClassName(`is`("android.widget.RelativeLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        //the perform method is chainable to the locator for typing the new task and closing the keyboard
        addNewTask.perform(replaceText("NEW_TASK"), closeSoftKeyboard())

        //once written the task need to click a button for the creation and displayed on the list
        val validateTutoTask = onView(
            allOf(
                withId(R.id.validate), withContentDescription("Validate"),
                childAtPosition(
                    allOf(
                        withId(R.id.addItemLayout),
                        childAtPosition(
                            withClassName(`is`("android.widget.RelativeLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        //we perform the click in the validation button
        validateTutoTask.perform(click())
    }
    //Here we create a function to search the locations of the elements by child
    //child position
    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
