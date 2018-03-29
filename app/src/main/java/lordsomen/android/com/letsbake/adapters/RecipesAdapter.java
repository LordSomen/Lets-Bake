package lordsomen.android.com.letsbake.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lordsomen.android.com.letsbake.R;
import lordsomen.android.com.letsbake.pojos.Step;

/**
 * Created by soumyajit on 23/3/18.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {

    private Context mContext;
    private List<Step> mRecipesList;
    public StepSelector mStepSelector;

    public RecipesAdapter(Context context,StepSelector selector){
        mContext = context;
        mStepSelector = selector;
    }

    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new RecipesAdapterViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipes_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecipesAdapterViewHolder holder, int position) {
        Step step = mRecipesList.get(position);
        if(step != null){
            holder.stepDescription.setText(step.getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        if(mRecipesList == null) return 0;
        else return mRecipesList.size();
    }

    public void ifDataChanged(List<Step> recipesList){
        mRecipesList = recipesList;
        notifyDataSetChanged();
    }

    public interface StepSelector {
        void onStepSelected(Step step);
    }

    public class RecipesAdapterViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.frag_recipes_step_description)
        TextView stepDescription;

        public RecipesAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mStepSelector.onStepSelected(mRecipesList.get(getAdapterPosition()));

        }
    }
}
