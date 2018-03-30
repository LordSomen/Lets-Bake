package lordsomen.android.com.letsbake.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lordsomen.android.com.letsbake.R;
import lordsomen.android.com.letsbake.activities.StepDetailsActivity;
import lordsomen.android.com.letsbake.adapters.RecipesAdapter;
import lordsomen.android.com.letsbake.pojos.BakingData;
import lordsomen.android.com.letsbake.pojos.Step;


public class RecipeFragment extends Fragment implements RecipesAdapter.StepSelector {


    //    private OnFragmentInteractionListener mListener;
    @BindView(R.id.frag_recipes_recycler_view)
    RecyclerView mRecyclerView;
    private RecipesAdapter mRecipesAdapter;
    private BakingData mBakingData;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);
        mBakingData = getArguments().getParcelable(BakingData.BAKINGDATA);
        mRecipesAdapter = new RecipesAdapter(getActivity().getApplicationContext(), this);
        mRecyclerView.setAdapter(mRecipesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()
                , LinearLayoutManager.VERTICAL, false));
        if (mBakingData != null) {
            List<Step> mStepList = mBakingData.getSteps();
            if (mStepList != null) {
                mRecipesAdapter.ifDataChanged(mStepList);
            }
        }
        return view;
    }

    @Override
    public void onStepSelected(Step step,int position) {
        Intent recipeIntent = new Intent(getActivity().getApplicationContext(), StepDetailsActivity.class);
        Bundle mBundle = new Bundle();
//        mBundle.putParcelable(Step.RECIPE_STEPS, step);
        mBundle.putParcelable(BakingData.BAKINGDATA, mBakingData);
        mBundle.putInt(Step.POSITION,position);
        recipeIntent.putExtras(mBundle);
        startActivity(recipeIntent);
    }

//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        void onFragmentInteraction(Uri uri);
//    }
}
