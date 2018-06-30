package lordsomen.android.com.letsbake.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class BakingContentProvider extends ContentProvider {


    private static final String AUTHORITY = "lordsomen.android.com.letsbake.data";

    public static final Uri bakingUri = Uri.parse("content://" + AUTHORITY + "/" +
            BakingAppData.TABLE_NAME);

    private static final int BAKING_DIR_CODE = 100;

    private static final int BAKING_ITEM_CODE = 101;


    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, BakingAppData.TABLE_NAME, BAKING_DIR_CODE);
        MATCHER.addURI(AUTHORITY, BakingAppData.TABLE_NAME + "/*", BAKING_ITEM_CODE);
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        final int code = MATCHER.match(uri);
        if (code == BAKING_DIR_CODE || code == BAKING_ITEM_CODE) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            BakingAppDao bakingAppDao = BakingAppDatabase.getDataInstance(context).BakingAppDao();
            final Cursor cursor;
            if (code == BAKING_DIR_CODE) {
                cursor = bakingAppDao.selectAll();
            } else {
                cursor = bakingAppDao.selectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
