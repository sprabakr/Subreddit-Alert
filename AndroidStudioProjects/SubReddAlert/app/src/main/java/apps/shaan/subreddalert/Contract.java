package apps.shaan.subreddalert;

/**
 * Created by shaan on 6/24/15.
 */
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public final class Contract {

    public Contract() {}
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";





    }



}
