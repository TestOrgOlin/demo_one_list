package todo_list


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
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class TODO_TASK {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    // In this test we are going to navigate the TO DO list and create a new task
    fun TODO_TASK() {
        //the locator for the list button is save in a variable
        val todoList = onView(
            allOf(
                withId(2131296721),
                withParentIndex(1),
                isDisplayed()
            )
        )
        //we perform a click on the list
        todoList.perform(click())

        val newTaskTODOList = onView(
            allOf(
                withId(R.id.addItemEditText),
                childAtPosition(
                    allOf(
                        withId(R.id.addItemLayout),
                        childAtPosition(
                            withClassName(Matchers.`is`("android.widget.RelativeLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        //we type at the input box locator
        newTaskTODOList.perform(replaceText("NEW_TASK"), closeSoftKeyboard())
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
