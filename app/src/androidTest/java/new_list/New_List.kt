package new_list


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
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
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class new_List {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    // In this test we are going to create a new list, display the more options,
    // and create a new task inside the created list
    fun new_List() {
        //look on the screen for the element with an id,
        // then check for all the instances of the element are available and clickable
        onView(withId(R.id.buttonAddList)).check(matches(allOf(isEnabled(), isClickable())))
            .perform(
                //then perform an override of the restrictions for an element not fully viewed
                object : ViewAction {
                    override fun getConstraints(): Matcher<View> {
                        return isEnabled() // no constraints, they are checked above
                    }

                    override fun getDescription(): String {
                        return "click plus button" //the action for clicking
                    }

                    override fun perform(uiController: UiController, view: View) {
                        view.performClick() //next the perform method will make the click
                    }
                }
            )
        //the locator for the input box for list title element is kept in a variable
        val newlistTitleText = onView(
            allOf(
                withId(R.id.listTitle),
                childAtPosition( //we use a created method to search in the component tree by index of child
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0 //define the position in which the child is located
                        )
                    ),
                    0
                ),
                isDisplayed() //the element we are going to interact needs to be displayed
            )
        )
        //we call the locator chained to an action of typing text and closing the keyboard
        newlistTitleText.perform(replaceText("NEW_LIST"), closeSoftKeyboard())

        //the locator for the more options element is saved in the
        val moreOptionsbutton = onView(
            allOf(
                withId(R.id.moreOptionsLayout),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(  //the class name is found a the top of the component tree
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        //we call the locator chained to an action clicking
        moreOptionsbutton.perform(click())

        //the button for creating a list is saved in a variable
        val validateListButton = onView(
            allOf(                             //the content description help us narrowing down more the locator
                withId(R.id.validateEditList), withContentDescription("Validate"),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        //we call the locator chained to an action clicking
        validateListButton.perform(click())

        //the locator for the input box to add a new task to the list
        val newtaskTitleText = onView(
            allOf(
                withId(R.id.addItemEditText),
                childAtPosition(
                    allOf(
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
        //we call the locator chained to an action of typing text and closing the keyboard
        newtaskTitleText.perform(replaceText("New_Task"), closeSoftKeyboard())

        //we save the location for the validate task button
        val validateTaskButton = onView(
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
        // the action perform to this button is a click()
        validateTaskButton.perform(click())
    }

    @Test
    // In this test we are going to navigate the TO DO list and try to create a new task
    // we seeded the input box locator to fail
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
                            withClassName(`is`("android.widget.RelativeLayout")),
                            1
                        )
                    ),
                    2 //the error is seeded here, the actual position is 0
                ),
                isDisplayed()
            )
        )
        //the typing is not going to be able to perform as is a wrong locator
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
