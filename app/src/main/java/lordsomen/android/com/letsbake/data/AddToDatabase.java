package lordsomen.android.com.letsbake.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.List;

import lordsomen.android.com.letsbake.pojos.BakingData;
import lordsomen.android.com.letsbake.pojos.Ingredient;
import lordsomen.android.com.letsbake.utils.AppExecutors;
import lordsomen.android.com.letsbake.widget.BakingAppWidget;

public class AddToDatabase {

    public static final String ID_ = "db_id_";
    public static final String SHARED_PREF = "shared_pref_id";
    public static int CURRENT_ID;
    private BakingAppDatabase bakingAppDatabase;
    private BakingData bakingData;
    private Context context;


    public void add(BakingData bakingData, final Context context) {
        bakingAppDatabase = BakingAppDatabase.getDataInstance(context);
        int id = bakingData.getId();
        CURRENT_ID = id;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF
                , Context.MODE_PRIVATE);
        if (!sharedPreferences.contains(ID_ + CURRENT_ID)) {
            List<Ingredient> ingredientList = bakingData.getIngredients();

            Gson gson = new Gson();

            String ingredients = gson.toJson(ingredientList);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(ID_ + CURRENT_ID, CURRENT_ID);
            editor.apply();
            final BakingAppData bakingAppData = new BakingAppData(CURRENT_ID, ingredients);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    bakingAppDatabase.BakingAppDao().insert(bakingAppData);
                }
            });
        }
        BakingAppWidget.sendRefreshBroadcast(context);
    }
}

