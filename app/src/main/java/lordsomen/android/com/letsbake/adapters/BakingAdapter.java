package lordsomen.android.com.letsbake.adapters;

import android.content.Context;
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
    private List<BakingData> mBakingDataList;
    private Context mContext;
    private BakingItemSelector mBakingItemSelector;

    public BakingAdapter(Context context,BakingItemSelector bakingItemSelector){
        this.mContext = context;
        mBakingItemSelector = bakingItemSelector;
    }

    @Override
    public BakingAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BakingAdapterViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_item,parent,false));
    }

    @Override
    public void onBindViewHolder(BakingAdapterViewHolder holder, int position) {
        BakingData bakingData = mBakingDataList.get(position);
        if(bakingData != null) {
            String videoUrl = bakingData.getSteps()
                    .get(bakingData.getSteps().size() - 1).getVideoURL();
            Log.d(TAG,"imgUrl" + videoUrl);
//            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//            mediaMetadataRetriever .setDataSource(videoUrl, new HashMap<String, String>());
//            Bitmap bmFrame = mediaMetadataRetriever.getFrameAtTime(10^7); //unit in microsecond
//            holder.mMainItemImageView.setImageBitmap(bmFrame);

//            Picasso.with(mContext).load(bmFrame.)
//                    .placeholder(R.drawable.ic_launcher_background)
//                    .error(R.drawable.ic_launcher_background)
//                    .into(holder.mMainItemImageView);
//            BitmapPool bitmapPool = Glide.get(mContext).getBitmapPool();
//            int microSecond = 6000000;// 6th second as an example
//            VideoBitmapDecoder videoBitmapDecoder = new VideoBitmapDecoder(microSecond);
////            FileDescriptorBitmapDecoder fileDescriptorBitmapDecoder = new FileDescriptorBitmapDecoder(videoBitmapDecoder, bitmapPool, DecodeFormat.PREFER_ARGB_8888);
            GlideApp.with(mContext)
                    .load(videoUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.mMainItemImageView);
//            holder.mMainItemImageView.setVideoURI(Uri.parse(videoUrl));
//
//            holder.mMainItemImageView.seekTo(100);
//            holder.mRecipeName.setText(bakingData.getName());
        }
    }

    @Override
    public int getItemCount() {
        if(mBakingDataList == null) return 0;
        else return mBakingDataList.size();
    }

    public void ifDataChanged(List<BakingData> bakingDataList){
        mBakingDataList = bakingDataList;
        notifyDataSetChanged();
    }

    public interface BakingItemSelector {
        void onBakingItemSelected(BakingData bakingData);
    }

    public class BakingAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.main_item_thumbnail_image)
        ImageView mMainItemImageView;
        @BindView(R.id.main_item_recipe_name)
        TextView mRecipeName;
        @BindView(R.id.main_item_widget_button)
        Button mWidgetButton;

        public BakingAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
            mWidgetButton.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            if(v.getId() == mWidgetButton.getId()){
                AddToDatabase addToDatabase = new AddToDatabase();
                addToDatabase.add(mBakingDataList
                        .get(getAdapterPosition()),mContext);
            }else {
                mBakingItemSelector.onBakingItemSelected(mBakingDataList.get(getAdapterPosition()));
            }
        }
    }
}
