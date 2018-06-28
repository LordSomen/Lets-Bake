package lordsomen.android.com.letsbake.data;

import android.content.Context;

import lordsomen.android.com.letsbake.pojos.BakingData;

public class AddToDatabase {

    private BakingAppDatabase bakingAppDatabase ;

    private void add(BakingData bakingDataList , Context context){
        bakingAppDatabase = BakingAppDatabase.getDataInstance(context) ;

    }


}
