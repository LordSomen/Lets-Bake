package lordsomen.android.com.letsbake.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

@Dao
public interface BakingAppDao {

    @Insert
    public void insert(BakingAppData bakingAppData);

    @Update
    public void update(BakingAppData bakingAppData);


    @Query("SELECT * FROM baking_data")
    Cursor selectAll();

    @Query("SELECT * FROM baking_data WHERE id =:id")
    Cursor selectById(int id);

    @Query("SELECT COUNT(*) FROM baking_data")
    int count();

    @Query("DELETE FROM baking_data WHERE id = :id")
    int deleteById(long id);

}
