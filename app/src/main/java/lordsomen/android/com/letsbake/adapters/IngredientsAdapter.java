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
import lordsomen.android.com.letsbake.pojos.Ingredient;

/**
 * Created by soumyajit on 11/3/18.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private Context mContext;
    private List<Ingredient> mIngredientList;

    public IngredientsAdapter(Context context){
        mContext = context;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_item,parent,false));
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        Ingredient ingredient = mIngredientList.get(position);
        if(ingredient != null){
            holder.ingredientsNameTextView.setText(ingredient.getIngredient());
            holder.ingredientsQuantityTextView
                    .setText(ingredient.getQuantity() + "  "+ingredient.getMeasure());
        }
    }

    @Override
    public int getItemCount() {
        if(mIngredientList == null) return 0;
        else return mIngredientList.size();
    }

    public void ifDataChanged(List<Ingredient> ingredientList){
        mIngredientList = ingredientList;
        notifyDataSetChanged();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredients_name_textView)
        TextView ingredientsNameTextView;
        @BindView(R.id.ingredients_quantity_textView)
        TextView ingredientsQuantityTextView;
        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
