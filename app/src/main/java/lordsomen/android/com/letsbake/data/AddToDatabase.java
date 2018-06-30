package lordsomen.android.com.letsbake.data;

import android.content.Context;

import com.google.gson.Gson;

import java.util.List;

import lordsomen.android.com.letsbake.pojos.BakingData;
import lordsomen.android.com.letsbake.pojos.Ingredient;
import lordsomen.android.com.letsbake.utils.AppExecutors;

public class AddToDatabase {

    private BakingAppDatabase bakingAppDatabase ;

    public void add(BakingData bakingData, Context context){
        bakingAppDatabase = BakingAppDatabase.getDataInstance(context) ;

        int id = bakingData.getId();
        List<Ingredient> ingredientList = bakingData.getIngredients();

        Gson gson = new Gson();

        String ingredients = gson.toJson(ingredientList);

        final BakingAppData bakingAppData = new BakingAppData(id,ingredients);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                bakingAppDatabase.BakingAppDao().insert(bakingAppData);
            }
        });
    }

}
