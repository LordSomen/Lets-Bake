package lordsomen.android.com.letsbake.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import lordsomen.android.com.letsbake.R;
import lordsomen.android.com.letsbake.data.AddToDatabase;
import lordsomen.android.com.letsbake.pojos.BakingData;
import lordsomen.android.com.letsbake.utils.GlideApp;

/**
 * Created by soumyajit on 7/3/18.
 */

public class BakingAdapter extends RecyclerView.Adapter<BakingAdapter.BakingAdapterViewHolder> {

    private static final String TAG = BakingAdapter.class.getSimpleName();
    private static final String SHARED_PREF_BUTTON = "shared_pref_button";
    private static final String POS = "position";
    public static int CURRENT_ID_WIDGET;
    private List<BakingData> mBakingDataList;
    private Context mContext;
    private BakingItemSelector mBakingItemSelector;

    public BakingAdapter(Context context, BakingItemSelector bakingItemSelector) {
        this.mContext = context;
        mBakingItemSelector = bakingItemSelector;
    }

    @Override
    public BakingAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BakingAdapterViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BakingAdapterViewHolder holder, int position) {
        BakingData bakingData = mBakingDataList.get(position);
        if (bakingData != null) {
            String videoUrl = bakingData.getSteps()
                    .get(bakingData.getSteps().size() - 1).getVideoURL();
            Log.d(TAG, "imgUrl" + videoUrl);

            GlideApp.with(mContext)
                    .load(videoUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.mMainItemImageView);

            holder.mRecipeName.setText(bakingData.getName());
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_BUTTON
                    , Context.MODE_PRIVATE);
            if (sharedPreferences.contains(POS + position)) {
                holder.mWidgetButton.setBackground(ContextCompat
                        .getDrawable(mContext, R.drawable.button_shape_filled));
                CURRENT_ID_WIDGET = bakingData.getId();
            } else {
                holder.mWidgetButton.setBackground(ContextCompat
                        .getDrawable(mContext, R.drawable.button_shape));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mBakingDataList == null) return 0;
        else return mBakingDataList.size();
    }

    public void ifDataChanged(List<BakingData> bakingDataList) {
        mBakingDataList = bakingDataList;
        notifyDataSetChanged();
    }

    public interface BakingItemSelector {
        void onBakingItemSelected(BakingData bakingData);
    }

    public class BakingAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.main_item_thumbnail_image)
        ImageView mMainItemImageView;
        @BindView(R.id.main_item_recipe_name)
        TextView mRecipeName;
        @BindView(R.id.main_item_widget_button)
        Button mWidgetButton;

        public BakingAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            mWidgetButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (v.getId() == mWidgetButton.getId()) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_BUTTON
                        , Context.MODE_PRIVATE);
                if (!sharedPreferences.contains(POS + getAdapterPosition())) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Map<String, ?> allEntries = sharedPreferences.getAll();
                    int pos = -1;
                    for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                        String posS = entry.getValue().toString();
                        pos = Integer.parseInt(posS);
                        notifyItemChanged(pos);
                    }
                    editor.clear();
                    editor.putInt(POS + getAdapterPosition(), getAdapterPosition());
                    editor.apply();
                    mWidgetButton.setBackground(ContextCompat
                            .getDrawable(mContext, R.drawable.button_shape_filled));

                    CURRENT_ID_WIDGET = mBakingDataList.get(getAdapterPosition()).getId();
                }
                AddToDatabase addToDatabase = new AddToDatabase();
                addToDatabase.add(mBakingDataList
                        .get(getAdapterPosition()), mContext);
            } else {
                mBakingItemSelector.onBakingItemSelected(mBakingDataList.get(getAdapterPosition()));
            }
        }
    }
}
