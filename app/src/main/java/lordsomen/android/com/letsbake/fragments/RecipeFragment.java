package lordsomen.android.com.letsbake.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lordsomen.android.com.letsbake.R;
import lordsomen.android.com.letsbake.activities.StepDetailsActivity;
import lordsomen.android.com.letsbake.adapters.RecipesAdapter;
import lordsomen.android.com.letsbake.pojos.BakingData;
import lordsomen.android.com.letsbake.pojos.Step;


public class RecipeFragment extends Fragment implements RecipesAdapter.StepSelector {


    private static final String SAVED_LAYOUT_MANAGER = "layout-manager-state";
    @BindView(R.id.frag_recipes_recycler_view)
    RecyclerView mRecyclerView;
    FrameLayout mStepDetailContainer;
    private RecipesAdapter mRecipesAdapter;
    private BakingData mBakingData;
    private Parcelable onSavedInstanceState = null;

    private boolean mTwoPane = false;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments())
            mBakingData = getArguments().getParcelable(BakingData.BAKINGDATA);
        if (null != getActivity()) {
            mRecipesAdapter = new RecipesAdapter(getActivity().getApplicationContext(), this);
            mRecyclerView.setAdapter(mRecipesAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()
                    , LinearLayoutManager.VERTICAL, false));
        }
        if (savedInstanceState != null) {
            onSavedInstanceState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
        }
        if (mBakingData != null) {
            List<Step> mStepList = mBakingData.getSteps();
            if (mStepList != null) {
                mRecipesAdapter.ifDataChanged(mStepList);
            }
            if (onSavedInstanceState != null) {
                mRecyclerView.getLayoutManager().onRestoreInstanceState(onSavedInstanceState);
            }
        }
        mStepDetailContainer = view.findViewById(R.id.step_detail_container);
        mTwoPane = mStepDetailContainer != null;

        return view;
    }

    @Override
    public void onStepSelected(Step step, int position) {
        if(!mTwoPane) {
            Intent recipeIntent = new Intent(getActivity().getApplicationContext(), StepDetailsActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putParcelable(BakingData.BAKINGDATA, mBakingData);
            mBundle.putInt(Step.POSITION, position);
            recipeIntent.putExtras(mBundle);
            startActivity(recipeIntent);
        }else {
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            Bundle arguments = new Bundle();
            arguments.putParcelable(StepDetailsFragment.VAL, step);
            stepDetailsFragment.setArguments(arguments);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container,stepDetailsFragment)
                    .commit();
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_LAYOUT_MANAGER, mRecyclerView.getLayoutManager()
                .onSaveInstanceState());
    }
}
