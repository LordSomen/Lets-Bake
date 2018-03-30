package lordsomen.android.com.letsbake.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lordsomen.android.com.letsbake.R;
import lordsomen.android.com.letsbake.pojos.Step;

/**
 * Created by soumyajit on 25/3/18.
 */

public class StepDetailsFragment extends Fragment {

    public static final String VAL = "val";
    @BindView(R.id.fragment_step_description)
    public TextView stepDescription;
    @BindView(R.id.fragment_video_url)
    public TextView videoDescription;
    private Step stepData;


    public static StepDetailsFragment init(Step step) {
        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        // Supply val input as an argument. 

        Bundle args = new Bundle();
        args.putParcelable(VAL, step);
        stepDetailsFragment.setArguments(args);
        return stepDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepData = getArguments().getParcelable(VAL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details,container,false);
        ButterKnife.bind(this,rootView);
        stepDescription.setText(stepData.getDescription());
        videoDescription.setText(stepData.getVideoURL());
        return rootView;
    }
}
