package manjeet_hooda.movies.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by manjeet on 17/4/16.
 */
public class moviesDbHelper extends SQLiteOpenHelper {

    private static moviesDbHelper moviesDbHelper = null;
    private static final String DB_NAME = "movies_db";
    private static final int DB_VERSION = 1;

    protected moviesDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    protected static moviesDbHelper getHelper(Context context){
        if(moviesDbHelper == null) {
            moviesDbHelper = new moviesDbHelper(context);
        }

        return moviesDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        create_table(movieSchema.TABLE_BOX_OFFICE, db);
        create_table(movieSchema.TABLE_UPCOMING, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(" DROP TABLE " + movieSchema.TABLE_BOX_OFFICE + " IF EXISTS;");
            db.execSQL(" DROP TABLE " + movieSchema.TABLE_UPCOMING + " IF EXISTS;");
            onCreate(db);
        } catch (SQLiteException exception) {

        }
    }

    private void create_table(String TB_NAME, SQLiteDatabase db){
        final String CREATE_TABLE = "CREATE TABLE " + TB_NAME + " (" +
                movieSchema.COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                movieSchema.COLUMN_TITLE + " TEXT," +
                movieSchema.COLUMN_RELEASE_DATE + " INTEGER," +
                movieSchema.COLUMN_AUDIENCE_SCORE + " INTEGER," +
                movieSchema.COLUMN_SYNOPSIS + " TEXT," +
                movieSchema.COLUMN_URL_THUMBNAIL + " TEXT," +
                movieSchema.COLUMN_URL_SELF + " TEXT," +
                movieSchema.COLUMN_URL_CAST + " TEXT," +
                movieSchema.COLUMN_URL_REVIEWS + " TEXT," +
                movieSchema.COLUMN_URL_SIMILAR + " TEXT" +
                ");";

        try{
            db.execSQL(CREATE_TABLE);
        }catch (SQLException e){

        }

    }
}
