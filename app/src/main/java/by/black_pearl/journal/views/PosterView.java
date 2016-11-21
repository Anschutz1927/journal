package by.black_pearl.journal.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import by.black_pearl.journal.R;

/**
 * Created by BLACK_Pearl.
 */

public class PosterView extends FrameLayout {
    private ImageView mPosterImageView;

    public PosterView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_poster, this, true);

        if(mPosterImageView == null) {
            this.mPosterImageView = new ImageView(context);
        }
    }

    public PosterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_poster, this, true);

        if(mPosterImageView == null) {
            this.mPosterImageView = new ImageView(context);
        }
    }

    public PosterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_poster, this, true);

        if(mPosterImageView == null) {
            this.mPosterImageView = new ImageView(context);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PosterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.view_poster, this, true);

        if(mPosterImageView == null) {
            this.mPosterImageView = new ImageView(context);
        }
    }

    public void setPoster(int res) {
        this.mPosterImageView.setImageResource(res);
    }
}
