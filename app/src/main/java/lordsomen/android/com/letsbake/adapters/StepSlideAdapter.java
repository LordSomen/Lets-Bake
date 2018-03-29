package lordsomen.android.com.letsbake.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;

import java.util.List;

import lordsomen.android.com.letsbake.fragments.StepDetailsFragment;
import lordsomen.android.com.letsbake.pojos.BakingData;
import lordsomen.android.com.letsbake.pojos.Step;

/**
 * Created by soumyajit on 26/3/18.
 */

public class StepSlideAdapter extends FragmentStatePagerAdapter {

    private List<Step> mStepDataList;
    private LayoutInflater layoutInflater;
    private Context mContext;
    private StepDetailsFragment stepDetailsFragment;

    public StepSlideAdapter(FragmentManager fm) {
        super(fm);
//        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return StepDetailsFragment.init(mStepDataList.get(position));
    }

    @Override
    public int getCount() {
        return mStepDataList.size();
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        layoutInflater =(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.fragment_step_details,container,false);
//        Step stepData = mStepDataList.get(position);
//        stepDetailsFragment.stepDescription.setText(stepData.getDescription());
//        stepDetailsFragment.videoDescription.setText(stepData.getVideoURL());
//        return view;
//    }

    public void setStepData(BakingData bakingData) {
        mStepDataList = bakingData.getSteps();
        notifyDataSetChanged();
    }
}
