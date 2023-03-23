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
    fun TODO_TASK() {
        val appCompatTODOText = onView(
            allOf(
                withId(2131296721), withParentIndex(1), isDisplayed()
            )
        )
        appCompatTODOText.perform(click())

        val appCompatEditTextNew = onView(
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
        appCompatEditTextNew.perform(replaceText("NEW_TASK_TODO"), closeSoftKeyboard())

        val appCompatImageButton = onView(
            allOf(
                withId(R.id.validate), withContentDescription("Validate"),
                childAtPosition(
                    allOf(
                        withId(R.id.addItemLayout),
                        childAtPosition(
                            withClassName(Matchers.`is`("android.widget.RelativeLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())
    }


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
