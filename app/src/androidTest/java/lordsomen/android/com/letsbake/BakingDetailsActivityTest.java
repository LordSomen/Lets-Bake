package lordsomen.android.com.letsbake;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import lordsomen.android.com.letsbake.activities.BakingDetailsActivity;

import static android.support.test.espresso.Espresso.onView;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BakingDetailsActivityTest {
    @Rule
    public ActivityTestRule<BakingDetailsActivity> bakingActivityTestRule = new ActivityTestRule<>
            (BakingDetailsActivity.class);

    @Test
    public void allItemsDisplayedTest(){
        onView(withRecyclerView(R.id.frag_ingredients_recycler_view)
                .atPositionOnView(1,R.id.ingredients_name_textView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
