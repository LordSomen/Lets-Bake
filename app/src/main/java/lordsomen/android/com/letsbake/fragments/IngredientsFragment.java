package lordsomen.android.com.letsbake.fragments;

import android.os.Bundle;
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

    @BindView(R.id.frag_ingredients_recycler_view)
    RecyclerView mRecyclerView;
    private IngredientsAdapter mIngredientsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragments_ingredients,container,false);
        ButterKnife.bind(this,view);
        BakingData bakingData = getArguments().getParcelable(BakingData.BAKINGDATA);
        mIngredientsAdapter = new IngredientsAdapter(getActivity().getApplicationContext());
        mRecyclerView.setAdapter(mIngredientsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()
                ,LinearLayoutManager.VERTICAL,false));
        if(bakingData!= null) {
            List<Ingredient> ingredient = bakingData.getIngredients();
            if(ingredient != null){
                mIngredientsAdapter.ifDataChanged(ingredient);
            }
        }
        return view;
    }
}
