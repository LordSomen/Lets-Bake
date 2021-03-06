package lordsomen.android.com.letsbake.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import lordsomen.android.com.letsbake.adapters.IngredientsAdapter;
import lordsomen.android.com.letsbake.pojos.BakingData;
import lordsomen.android.com.letsbake.pojos.Ingredient;

/**
 * Created by soumyajit on 9/3/18.
 */

public class IngredientsFragment extends Fragment {

    private static final String SAVED_LAYOUT_MANAGER = "layout-manager-state";
    @BindView(R.id.frag_ingredients_recycler_view)
    RecyclerView mRecyclerView;
    private IngredientsAdapter mIngredientsAdapter;
    private Parcelable onSavedInstanceState = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragments_ingredients, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            BakingData bakingData = getArguments().getParcelable(BakingData.BAKINGDATA);
            if (null != getActivity()) {
                mIngredientsAdapter = new IngredientsAdapter(getActivity().getApplicationContext());
                mRecyclerView.setAdapter(mIngredientsAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()
                        , LinearLayoutManager.VERTICAL, false));
                if (savedInstanceState != null) {
                    onSavedInstanceState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
                }
                if (bakingData != null) {
                    List<Ingredient> ingredient = bakingData.getIngredients();
                    if (ingredient != null) {
                        mIngredientsAdapter.ifDataChanged(ingredient);
                    }
                    if (onSavedInstanceState != null) {
                        mRecyclerView.getLayoutManager().onRestoreInstanceState(onSavedInstanceState);
                    }
                }
                return view;
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_LAYOUT_MANAGER, mRecyclerView.getLayoutManager()
                .onSaveInstanceState());
    }
}
