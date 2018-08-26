package lordsomen.android.com.letsbake.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lordsomen.android.com.letsbake.R;
import lordsomen.android.com.letsbake.adapters.StepSlideAdapter;
import lordsomen.android.com.letsbake.pojos.BakingData;
import lordsomen.android.com.letsbake.pojos.Step;

public class StepDetailsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    public static int NO_OF_STEPS = 1;
    @BindView(R.id.step_details_viewpager)
    ViewPager mViewPager;
    private BakingData mBakingData;
    private List<Step> mStepList;
    //    private Step step;
    private StepSlideAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        int position = 0;
        if (null != bundle) {
//            step = bundle.getParcelable(Step.RECIPE_STEPS);
            mBakingData = bundle.getParcelable(BakingData.BAKINGDATA);
            position = bundle.getInt(Step.POSITION);
            if (null != mBakingData) {
                mStepList = mBakingData.getSteps();
                NO_OF_STEPS = mStepList.size();
            }
        }
        mPagerAdapter = new StepSlideAdapter(getSupportFragmentManager());
        mPagerAdapter.setStepData(mBakingData);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(position);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageSelected(int position) {
        Step step = mStepList.get(position);
        // stepDescription.setText(step.getDescription());
        //videoDescription.setText(step.getVideoURL());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
