package lordsomen.android.com.letsbake.network;

import java.util.List;

import lordsomen.android.com.letsbake.pojos.BakingData;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by soumyajit on 3/3/18.
 */

public interface ApiInterface {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<BakingData>> getAllBakingData();

}
