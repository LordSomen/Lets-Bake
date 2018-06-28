package lordsomen.android.com.letsbake.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "baking_data")
public class BakingAppData {

    @PrimaryKey
    private int id;

    private double quantity;

    private String measure;

    private String ingredient;

    public BakingAppData(int id , double quantity , String measure , String ingredient){

        this.id = id ;
        this.quantity = quantity ;
        this.measure = measure ;
        this.ingredient = ingredient;

    }

    public Double getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
