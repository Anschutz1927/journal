package by.black_pearl.journal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import by.black_pearl.journal.workers.JournalWorker;

public class TestActivity extends AppCompatActivity {

    public static final String JOURNAL_NUMBER = "journalNumber";
    public static final String JOURNAL_TEST = "journalTest";
    private static final String STATE_CURRENT_QUESTION = "currentQuestion";
    private static final String STATE_CURRENT_RESULT = "currentResult";

    private JournalWorker.JournalTest mTest;
    private int mCurrentQuestion;
    private ImageView mQuestionImageView;
    private TextView mQuestionTextView;
    private RadioGroup mAnswersRadioGroup;
    private long mSummPart;
    private long mResult = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestActivity.this.onClick(view);
            }
        });

        int journalTest = getIntent().getIntExtra(JOURNAL_TEST, 1);
        JournalWorker jWorker = new JournalWorker(this);
        this.mTest = jWorker.getTestByTestId(journalTest);
        this.mCurrentQuestion = 0;

        this.mQuestionImageView = (ImageView) findViewById(R.id.questionImageView);
        this.mQuestionTextView = (TextView) findViewById(R.id.questionTextView);
        this.mAnswersRadioGroup = (RadioGroup) findViewById(R.id.answersRadioGroup);

        ArrayList<String[]> trueAnsws = this.mTest.getTrueAnswers();
        int trueAnswCount = 0;
        for(int i = 0; i < trueAnsws.size(); i++) {
            trueAnswCount += trueAnsws.get(i).length;
        }

        this.mSummPart = 100 / (long)trueAnswCount;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_CURRENT_QUESTION, this.mCurrentQuestion);
        outState.putLong(STATE_CURRENT_RESULT, this.mResult);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onClick(null);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.mCurrentQuestion = savedInstanceState.getInt(STATE_CURRENT_QUESTION, 0);
        this.mResult = savedInstanceState.getLong(STATE_CURRENT_RESULT, 0);
    }

    private void onClick(@Nullable View view) {
        if(view != null){
            String[] trueAnsws = this.mTest.getTrueAnswersAt(this.mCurrentQuestion);
            if(trueAnsws.length > 1) {
                int chckBoxCount = this.mAnswersRadioGroup.getChildCount();
                for(int i = 0; i < chckBoxCount; i++) {
                    if(((CheckBox)this.mAnswersRadioGroup.getChildAt(i)).isChecked()) {
                        for(int j = 0; j < trueAnsws.length; j++) {
                            if(((CheckBox)this.mAnswersRadioGroup.getChildAt(i)).getText().toString().equals(trueAnsws[j])) {
                                this.mResult += this.mSummPart;
                                break;
                            }
                        }
                    }
                }
            }
            else {
                int checkedId = this.mAnswersRadioGroup.getCheckedRadioButtonId();
                if(checkedId == -1){
                    Snackbar.make(view, "Oups, check input!", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    return;
                }
                String answer = ((RadioButton)this.mAnswersRadioGroup.findViewById(checkedId))
                        .getText().toString();
                if(trueAnsws[0].equals(answer)){
                    this.mResult += this.mSummPart;
                }
            }
            this.mCurrentQuestion++;
            if(this.mTest.getQuestions().size() <= this.mCurrentQuestion) {
                this.mCurrentQuestion--;
                this.mQuestionImageView.setImageResource(R.drawable.test_poster);
                this.mQuestionTextView.setText("Your result: " + this.mResult + " points! \nCongratulations!!!");
                TestActivity.this.findViewById(R.id.fab).setVisibility(View.GONE);
                this.mAnswersRadioGroup.setVisibility(View.GONE);
                return;
            }
        }
        if(mTest.getImagePathAt(mCurrentQuestion) != null) {
            //TODO:
            //set image
        }
        this.mQuestionTextView.setText(this.mTest.getQuestionAt(this.mCurrentQuestion));
        this.mAnswersRadioGroup.removeAllViews();
        String[] answers = this.mTest.getAnswersAt(this.mCurrentQuestion);
        String[] trueAnsws = this.mTest.getTrueAnswersAt(this.mCurrentQuestion);
        if(trueAnsws.length > 1) {
            int length = answers.length;
            CheckBox chckBoxs[] = new CheckBox[length];
            for (int i = 0; i < length; i++) {
                chckBoxs[i] = new CheckBox(this);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                chckBoxs[i].setLayoutParams(params);
                chckBoxs[i].setText(answers[i]);
                this.mAnswersRadioGroup.addView(chckBoxs[i]);
            }
        }
        else {
            int length = answers.length;
            RadioButton rdioBtns[] = new RadioButton[length];
            for (int i = 0; i < length; i++) {
                rdioBtns[i] = new RadioButton(this);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                rdioBtns[i].setLayoutParams(params);
                rdioBtns[i].setText(answers[i]);
                this.mAnswersRadioGroup.addView(rdioBtns[i]);
                this.mAnswersRadioGroup.clearCheck();
                //((RadioButton)this.mAnswersRadioGroup.getChildAt(0)).setChecked(true);
            }
        }
    }
}
