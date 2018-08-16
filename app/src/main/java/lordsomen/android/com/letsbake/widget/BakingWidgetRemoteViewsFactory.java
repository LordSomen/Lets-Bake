package lordsomen.android.com.letsbake.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import lordsomen.android.com.letsbake.R;
import lordsomen.android.com.letsbake.data.BakingAppData;
import lordsomen.android.com.letsbake.data.BakingContentProvider;
import lordsomen.android.com.letsbake.pojos.Ingredient;

public class BakingWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;

    private Cursor mCursor;

    private List<Ingredient> ingredientList;
//    @BindView(R.id.widget_progress_bar)
//    ProgressBar mProgressBar;
//    @BindView(R.id.widget_error_textView)
//    TextView mErrorTextView;
//    @BindView(R.id.widget_ingredients_list)
//    ListView mListView;

    public BakingWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {

        mContext = applicationContext;
    }


    @Override
    public void onCreate() {
//        Uri uri = BakingContentProvider.bakingUri;
//
//        mCursor = mContext.getContentResolver().query(uri,
//                null,
//                null,
//                null,
//                null);
//
//        mCursor.moveToFirst();
//        String mData = mCursor.getString(mCursor.getColumnIndex("ingredients"));
//        Type type = new TypeToken<List<Ingredient>>() {
//        }.getType();
//        Gson gson = new Gson();
//        ingredientList = gson.fromJson(mData, type);
    }

    @Override
    public void onDataSetChanged() {

//        if (mCursor != null) {
//            mCursor.close();
//        }

        final long identityToken = Binder.clearCallingIdentity();
        Uri uri = BakingContentProvider.bakingUri;
        mCursor = mContext.getContentResolver().query(uri,
                null,
                null,
                null,
                null);
        if (null != mCursor) {
//            mCursor.moveToFirst();
            int i = mCursor.getPosition();
            int count = mCursor.getColumnCount();
            int countr = mCursor.getCount();
            mCursor.moveToPosition(0);
            String mData = mCursor.getString(mCursor.getColumnIndex(BakingAppData.COLUMN_INGREDIENTS));
            Type type = new TypeToken<List<Ingredient>>() {
            }.getType();
            Gson gson = new Gson();
            ingredientList = gson.fromJson(mData, type);
            Binder.restoreCallingIdentity(identityToken);
        }
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        if(ingredientList != null){
            return ingredientList.size();
        }else return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION || null == ingredientList) {
            return null;
        }

        Ingredient ingredient = ingredientList.get(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.ingredients_item);
        Double quantity = ingredient.getQuantity();
        rv.setTextViewText(R.id.ingredients_name_textView, ingredient.getIngredient());
        rv.setTextViewText(R.id.ingredients_quantity_textView, Double.toString
                (ingredient.getQuantity()) + " " + ingredient.getMeasure());

        return rv;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
       // return mCursor.moveToPosition(position) ? mCursor.getLong(0) : position;
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

//    private  class DownloadFilesTask extends AsyncTask<Void, Cursor, Cursor> {
//
//
//        @Override
//        protected void onPreExecute() {
//
//        }
//
//        @Override
//        protected Cursor doInBackground(Void... voids) {
//            Uri uri = BakingContentProvider.bakingUri;
//
//            try {
//                mCursor = mContext.getContentResolver().query(uri,
//                        null,
//                        null,
//                        null,
//                        null);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return mCursor;
//        }
//
//        @Override
//        protected void onPostExecute(Cursor cursor) {
//            super.onPostExecute(cursor);
//            if (cursor != null) {
//                onDataSetChanged();
//                getCount();
//            }
//
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//    }
}
