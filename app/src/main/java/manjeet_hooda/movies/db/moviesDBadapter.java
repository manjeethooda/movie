package manjeet_hooda.movies.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

import manjeet_hooda.movies.global.Movie;
import manjeet_hooda.movies.global.MyApplication;

/**
 * Created by manjeet on 17/4/16.
 */
public class moviesDBadapter extends moviesDbHelper {

    private moviesDbHelper mHelper;
    private SQLiteDatabase mDatabase;

    public moviesDBadapter(Context context) {
        super(context);
        mHelper = manjeet_hooda.movies.db.moviesDbHelper.getHelper(context);
        if(mDatabase == null)
                mDatabase = mHelper.getWritableDatabase();
        //MyApplication.display("moviesDBadapter");
    }

    public void insertMovies(int tableNum, ArrayList<Movie> listMovies, boolean clearPrevious) {
        String table = (tableNum == 0)? movieSchema.TABLE_BOX_OFFICE:movieSchema.TABLE_UPCOMING;
        if (clearPrevious) {
            deleteMovies(tableNum);
        }

        for(int i =0; i<listMovies.size(); i++) {
            Movie movie = listMovies.get(i);

            ContentValues contentValues = new ContentValues();
            contentValues.put(movieSchema.COLUMN_TITLE, movie.getmTitle());
            contentValues.put(movieSchema.COLUMN_RELEASE_DATE, movie.getmDate() == null ? -1 : movie.getmDate().getTime());
            contentValues.put(movieSchema.COLUMN_AUDIENCE_SCORE, movie.getmAudienceScore());
            contentValues.put(movieSchema.COLUMN_SYNOPSIS, movie.getmSynopsis());
            contentValues.put(movieSchema.COLUMN_URL_THUMBNAIL, movie.getmUrlThumbnail());
            contentValues.put(movieSchema.COLUMN_URL_SELF, movie.getmUrlSelf());
            contentValues.put(movieSchema.COLUMN_URL_CAST, movie.getmUrlCast());
            contentValues.put(movieSchema.COLUMN_URL_REVIEWS, movie.getmUrlReviews());
            contentValues.put(movieSchema.COLUMN_URL_SIMILAR, movie.getmUrlSimiliar());

            if(tableNum != 2) {
                try {
                    mDatabase.insert(table, null, contentValues);
                    //MyApplication.display("list_inserted" + listMovies.size());
                } catch (SQLException e) {

                }
            }

        }

    }

    public ArrayList<Movie> readMovies(int TABLE) {
        String table = (TABLE == 0) ? movieSchema.TABLE_BOX_OFFICE:movieSchema.TABLE_UPCOMING;
        ArrayList<Movie> listMovies = new ArrayList<>();

        //get a list of columns to be retrieved, we need all of them
        String[] columns = {movieSchema.COLUMN_UID,
                movieSchema.COLUMN_TITLE,
                movieSchema.COLUMN_RELEASE_DATE,
                movieSchema.COLUMN_AUDIENCE_SCORE,
                movieSchema.COLUMN_SYNOPSIS,
                movieSchema.COLUMN_URL_THUMBNAIL,
                movieSchema.COLUMN_URL_SELF,
                movieSchema.COLUMN_URL_CAST,
                movieSchema.COLUMN_URL_REVIEWS,
                movieSchema.COLUMN_URL_SIMILAR
        };
        
        Cursor cursor = mDatabase.query(table , columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setmTitle(cursor.getString(cursor.getColumnIndex(movieSchema.COLUMN_TITLE)));
                long releaseDateMilliseconds = cursor.getLong(cursor.getColumnIndex(movieSchema.COLUMN_RELEASE_DATE));
                movie.setmDate(releaseDateMilliseconds != -1 ? new Date(releaseDateMilliseconds) : null);
                movie.setmAudienceScore(cursor.getInt(cursor.getColumnIndex(movieSchema.COLUMN_AUDIENCE_SCORE)));
                movie.setmSynopsis(cursor.getString(cursor.getColumnIndex(movieSchema.COLUMN_SYNOPSIS)));
                movie.setmUrlThumbnail(cursor.getString(cursor.getColumnIndex(movieSchema.COLUMN_URL_THUMBNAIL)));
                movie.setmUrlSelf(cursor.getString(cursor.getColumnIndex(movieSchema.COLUMN_URL_SELF)));
                movie.setmUrlCast(cursor.getString(cursor.getColumnIndex(movieSchema.COLUMN_URL_CAST)));
                movie.setmUrlReviews(cursor.getString(cursor.getColumnIndex(movieSchema.COLUMN_URL_REVIEWS)));
                movie.setmUrlSimiliar(cursor.getString(cursor.getColumnIndex(movieSchema.COLUMN_URL_SIMILAR)));
                
                listMovies.add(movie);
            }
            while (cursor.moveToNext());
        }
        return listMovies;
    }

    public void deleteMovies(int table) {
        String Table = (table == 0) ? movieSchema.TABLE_BOX_OFFICE:movieSchema.TABLE_UPCOMING;
        mDatabase.delete(Table, null, null);
    }
}
