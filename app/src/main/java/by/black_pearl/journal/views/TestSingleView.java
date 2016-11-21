package by.black_pearl.journal.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import by.black_pearl.journal.R;

/**
 * Created by BLACK_Pearl.
 */

public class TestSingleView extends ScrollView {
    private ImageView mPosterView;
    private TextView mTestNameView;
    private TextView mTestDescriptionView;
    private TextView mLastResultView;
    private Button mStartButton;
    private int mJournalId;
    private int mTestIdAtJournal;

    public TestSingleView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.test_single_view, this, true);
        initialize();
    }

    public TestSingleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.test_single_view, this, true);
        initialize();
    }

    public TestSingleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.test_single_view, this, true);
        initialize();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TestSingleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.test_single_view, this, true);
        initialize();
    }

    private void initialize() {
        this.mPosterView = (ImageView) findViewById(R.id.testImageView);
        this.mTestNameView = (TextView) findViewById(R.id.testName);
        this.mTestDescriptionView = (TextView) findViewById(R.id.testDescription);
        this.mLastResultView = (TextView) findViewById(R.id.testResult);
        this.mStartButton = (Button) findViewById(R.id.testStartButton);
    }

    public void setStartButonOnClickListener(OnClickListener listener) {
        this.mStartButton.setOnClickListener(listener);
    }

    /**
     * Call to set up image on poster of test.
     * @param resId
     */
    public void setPosterView(int resId) {
        this.mPosterView.setImageResource(resId);
    }

    /**
     * Call to set up test name of test.
     * @param testName
     */
    public void setTestNameView(String testName) {
        this.mTestNameView.setText(testName);
    }

    /**
     * Call to set up description of test.
     * @param description
     */
    public void setTestDescriptionView(String description) {
        this.mTestDescriptionView.setText(description);
    }

    /**
     * Call to set up last result of test.
     * @param lastResult
     */
    public void setLastResultView(String lastResult) {
        this.mLastResultView.setText(lastResult);
    }

    public int getJournalId() {
        return mJournalId;
    }
}
