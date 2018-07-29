package lordsomen.android.com.letsbake;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import lordsomen.android.com.letsbake.activities.BakingDetailsActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BakingDetailsActivityTest {
    @Rule
    public ActivityTestRule<BakingDetailsActivity> bakingActivityTestRule = new ActivityTestRule<>
            (BakingDetailsActivity.class);

    @Before
    public void init(){
        bakingActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }
  
    @Test
    public void allItemsDisplayedTest(){
//        init();
//        onView(allOf(withId(R.id.button), isDisplayed())).perform(click());
//        onView(withRecyclerView(R.id.frag_ingredients_recycler_view)
//                .atPositionOnView(1,R.id.ingredients_name_textView))
//                .check(matches(isDisplayed()));

    }
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
