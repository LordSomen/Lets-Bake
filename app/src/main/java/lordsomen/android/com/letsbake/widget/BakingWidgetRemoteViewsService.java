package lordsomen.android.com.letsbake.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class BakingWidgetRemoteViewsService extends RemoteViewsService {



    @Override
    public BakingWidgetRemoteViewsFactory onGetViewFactory(Intent intent) {

        return new BakingWidgetRemoteViewsFactory(this.getApplicationContext(), intent);

    }

}
