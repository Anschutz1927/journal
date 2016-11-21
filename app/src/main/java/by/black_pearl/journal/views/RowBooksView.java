package by.black_pearl.journal.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import by.black_pearl.journal.R;

/**
 * Created by BLACK_Pearl.
 */

public class RowBooksView extends LinearLayout {
    public static final int NUMBER_COLUMNS_IN_ROW = 3;
    public static final int ROW_FIRST = 0;
    public static final int ROW_SECOND = 1;
    public static final int ROW_THIRD = 2;

    public RowBooksView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_row_books, this, true);
    }

    public RowBooksView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_row_books, this, true);
    }

    public RowBooksView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_row_books, this, true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RowBooksView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.view_row_books, this, true);
    }

    /**
     * Run to set posters image to some column.
     * @param posterAt ROW_FIRST, ROW_SECOND or ROW_THIRD to set poster at column.
     * @param resId resource id of poster.
     */
    public void setPosterAt(int posterAt, int resId) {
        switch (posterAt) {
            case ROW_FIRST:
                ((ImageView)findViewById(R.id.rowImage1)).setImageResource(resId);
                break;
            case ROW_SECOND:
                ((ImageView)findViewById(R.id.rowImage2)).setImageResource(resId);
                break;
            case ROW_THIRD:
                ((ImageView)findViewById(R.id.rowImage3)).setImageResource(resId);
                break;
        }
    }

    /**
     * Call to set onClickListener to poster.
     * @param posterAt ROW_FIRST, ROW_SECOND or ROW_THIRD to set poster at column.
     * @param listener listener for poster.
     */
    public void setOnClickListenerAt(int posterAt, OnClickListener listener) {
        switch (posterAt) {
            case ROW_FIRST:
                findViewById(R.id.rowImage1).setOnClickListener(listener);
                break;
            case ROW_SECOND:
                findViewById(R.id.rowImage2).setOnClickListener(listener);
                break;
            case ROW_THIRD:
                findViewById(R.id.rowImage3).setOnClickListener(listener);
                break;
        }
    }
}
