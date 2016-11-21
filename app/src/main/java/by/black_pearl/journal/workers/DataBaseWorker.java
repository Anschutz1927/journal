package by.black_pearl.journal.workers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BLACK_Pearl.
 */

public class DataBaseWorker extends SQLiteOpenHelper{
    private static final String DB_NAME = "journal";
    private static final int VERSION = 1;

    private static final String TABLE_MAGAZIEN = "Magazine";
    public static final String COLUMN_MAGAZINE_ID = "MagazineId";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_IS_DOWNLOADED = "IsDownloaded";
    public static final String COLUMN_POSTER_IMAGE = "PosterImage";
    public static final String COLUMN_HTML_PATH = "HtmlPath";

    private static final String TABLE_TEST = "Test";
    public static final String COLUMN_TEST_ID = "TestId";
    public static final String COLUMN_LAST_SCORE = "LastScore";

    private static final String TABLE_QUESTION = "Question";
    public static final String COLUMN_QUESTION_ID = "QuestionId";
    public static final String COLUMN_TITLE = "Title";

    private static final String TABLE_ANSWER = "Answer";
    public static final String COLUMN_ANSWER_ID = "AnswerId";
    public static final String COLUMN_ANSWER = "Answer";
    public static final String COLUMN_IS_CORRECT = "IsCorrect";

    public DataBaseWorker(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public Cursor getTestsPreviews() {
        return getReadableDatabase().query(TABLE_TEST, null, null, null, null, null, COLUMN_TEST_ID);
    }

    public Cursor getTest(int journalNumber) {
        String sql = "SELECT " +
                TABLE_TEST + "." + COLUMN_MAGAZINE_ID + ", " +
                TABLE_TEST + "." + COLUMN_TEST_ID + ", " +
                TABLE_TEST + "." + COLUMN_NAME + ", " +
                TABLE_QUESTION + "." + COLUMN_QUESTION_ID + ", " +
                TABLE_QUESTION + "." + COLUMN_TITLE + ", " +
                TABLE_ANSWER + "." + COLUMN_ANSWER_ID + ", " +
                TABLE_ANSWER + "." + COLUMN_ANSWER + ", " +
                TABLE_ANSWER + "." + COLUMN_IS_CORRECT + " " +
                "FROM " +
                TABLE_TEST + ", " +
                TABLE_QUESTION + ", " +
                TABLE_ANSWER + " " +
                "WHERE " +
                TABLE_TEST + "." + COLUMN_MAGAZINE_ID + " = " + String.valueOf(journalNumber) + " " + "AND " +
                TABLE_TEST + "." + COLUMN_TEST_ID + " = " + TABLE_QUESTION + "." + COLUMN_TEST_ID + " " + "AND " +
                TABLE_ANSWER + "." + COLUMN_QUESTION_ID + " = " + TABLE_QUESTION + "." + COLUMN_QUESTION_ID + " ";
        return getReadableDatabase().rawQuery(sql, null);
    }

    public Cursor getTestByTestId(int testId) {
        String sql = "SELECT " +
                TABLE_TEST + "." + COLUMN_MAGAZINE_ID + ", " +
                TABLE_TEST + "." + COLUMN_TEST_ID + ", " +
                TABLE_TEST + "." + COLUMN_NAME + ", " +
                TABLE_QUESTION + "." + COLUMN_QUESTION_ID + ", " +
                TABLE_QUESTION + "." + COLUMN_TITLE + ", " +
                TABLE_ANSWER + "." + COLUMN_ANSWER_ID + ", " +
                TABLE_ANSWER + "." + COLUMN_ANSWER + ", " +
                TABLE_ANSWER + "." + COLUMN_IS_CORRECT + " " +
                "FROM " +
                TABLE_TEST + ", " +
                TABLE_QUESTION + ", " +
                TABLE_ANSWER + " " +
                "WHERE " +
                TABLE_TEST + "." + COLUMN_TEST_ID + " = " + String.valueOf(testId) + " " + "AND " +
                TABLE_TEST + "." + COLUMN_TEST_ID + " = " + TABLE_QUESTION + "." + COLUMN_TEST_ID + " " + "AND " +
                TABLE_ANSWER + "." + COLUMN_QUESTION_ID + " = " + TABLE_QUESTION + "." + COLUMN_QUESTION_ID + " ";
        return getReadableDatabase().rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "CREATE TABLE " + TABLE_MAGAZIEN + " (" +
                COLUMN_MAGAZINE_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                COLUMN_NAME + " TEXT NOT NULL," +
                COLUMN_IS_DOWNLOADED + " BLOB NOT NULL DEFAULT 0," +
                COLUMN_POSTER_IMAGE +" TEXT," +
                COLUMN_HTML_PATH + " TEXT" +
        ");";
        db.execSQL(sql);
        sql = "CREATE TABLE " + TABLE_TEST + " (" +
                COLUMN_TEST_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                COLUMN_NAME + " TEXT NOT NULL," +
                COLUMN_MAGAZINE_ID + " INTEGER NOT NULL," +
                COLUMN_LAST_SCORE + " TEXT," +
                COLUMN_POSTER_IMAGE + "TEXT" +
                ");";
        db.execSQL(sql);
        sql = "CREATE TABLE " + TABLE_QUESTION + " (" +
                COLUMN_QUESTION_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                COLUMN_TITLE + " TEXT NOT NULL," +
                COLUMN_TEST_ID + " INTEGER NOT NULL" +
                ");";
        db.execSQL(sql);
        sql = "CREATE TABLE " + TABLE_ANSWER + " (" +
                COLUMN_ANSWER_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                COLUMN_ANSWER + " TEXT NOT NULL," +
                COLUMN_QUESTION_ID + " INTEGER NOT NULL," +
                COLUMN_IS_CORRECT + " BLOB NOT NULL DEFAULT false" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
