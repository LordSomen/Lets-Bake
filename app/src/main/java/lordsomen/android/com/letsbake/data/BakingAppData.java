package lordsomen.android.com.letsbake.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "baking_data")
public class BakingAppData {

    public static final String TABLE_NAME = "baking_data";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_ID_ = "id";

    @PrimaryKey
    private int id;

    private String ingredients;


    public BakingAppData(int id, String ingredients) {

        this.id = id;
        this.ingredients = ingredients;

    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }


}
