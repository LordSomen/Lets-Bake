package lordsomen.android.com.letsbake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lordsomen.android.com.letsbake.R;
import lordsomen.android.com.letsbake.adapters.BakingAdapter;
import lordsomen.android.com.letsbake.network.ApiClient;
import lordsomen.android.com.letsbake.network.ApiInterface;
import lordsomen.android.com.letsbake.pojos.BakingData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BakingAdapter.BakingItemSelector{

    private final static String TAG = MainActivity.class.getSimpleName();
    private ApiInterface mApiInterface;
    private List<BakingData> mNetworkDataList;
    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_error_text_view)
    TextView mErrorTextView;
    @BindView(R.id.main_progress_bar)
    ProgressBar mProgressBar;
    private BakingAdapter mBakingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mApiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        mBakingAdapter = new BakingAdapter(this,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setAdapter(mBakingAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // getting the data from api using retrofit interface ApiInterface
        final Call<List<BakingData>> listCall = mApiInterface.getAllBakingData();
        // now binding the data in the pojo class
        listCall.enqueue(new Callback<List<BakingData>>() {
            //if data is successfully binded from json to the pojo class onResponse is called
            @Override
            public void onResponse(Call<List<BakingData>> call,
                                   Response<List<BakingData>> response) {

                Log.d(TAG, "Response : " + response.code());
                mNetworkDataList = response.body();
                if (null != mNetworkDataList) {
                    showBakingList();
                    mBakingAdapter.ifDataChanged(mNetworkDataList);
                } else {
                    showError();
                }
            }

            //if data binding is not successful onFailed called
            @Override
            public void onFailure(Call<List<BakingData>> call, Throwable t) {
                //cancelling the GET data request
                listCall.cancel();
            }
        });
    }

    /**
     * this method is for showing the error textview and making all other views gone
     */
    private void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    /**
     * this method is for showing the recyclerview and making all other views gone
     */
    private void showBakingList() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mErrorTextView.setVisibility(View.GONE);
    }

    @Override
    public void onBakingItemSelected(BakingData bakingData) {
        Intent intent = new Intent(this,BakingDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(BakingData.BAKINGDATA,bakingData);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
