package lordsomen.android.com.letsbake;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import lordsomen.android.com.letsbake.activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    public static final int recyclerViewId = R.id.main_recycler_view;

    @Rule
   public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * in this method we are testing is the recyclerview is clickable or not
     */
//    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
//        return new RecyclerViewMatcher(recyclerViewId);
//    }

    @Test
    public void recyclerViewItemClickTest() {
       onView(ViewMatchers.withId(recyclerViewId))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }

    @Test
    public  void scrollAndTapRecyclerViewItem() {
        onView(ViewMatchers.withId(recyclerViewId)).perform(RecyclerViewActions.scrollToPosition(3));
//        onView(withRecyclerView(recyclerViewId).atPosition(position)).perform(click());
    }

    @Test
    public void allItemsDisplayed(){
        onView(withRecyclerView(recyclerViewId)
                .atPositionOnView(1, R.id.main_item_widget_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        onView(withRecyclerView(recyclerViewId)
                .atPositionOnView(1, R.id.main_item_thumbnail_image))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {

        return new RecyclerViewMatcher(recyclerViewId);
    }


}
