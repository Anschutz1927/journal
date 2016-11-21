package by.black_pearl.journal.workers;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by BLACK_Pearl.
 */

public class JournalWorker {
    private DataBaseWorker mDataBaseWorker;

    /**
     * JournalWorker contains information about all journals.
     * It provide access to some journal. Some journals have some test...
     * @param context
     */
    public JournalWorker(Context context) {
        this.mDataBaseWorker = new DataBaseWorker(context);

    }

    /**
     * Return Cursor with all data in table "Test".
     * Use it for prepare preview about tests in test menu.
     * @return Cursor.
     * It contains:
     * TestId, TestName and MagazineId.
     */
    public Cursor getTestsPreview() {
        return mDataBaseWorker.getTestsPreviews();
    }

    public Cursor getTest(int journalNumber) {
        return this.mDataBaseWorker.getTest(journalNumber);
    }

    public JournalTest getTestByTestId(int testId) {
        Cursor c = mDataBaseWorker.getTestByTestId(testId);

        ArrayList<String> questions = new ArrayList<>();
        int questIndex = c.getColumnIndex(DataBaseWorker.COLUMN_TITLE);
        int answIndex = c.getColumnIndex(DataBaseWorker.COLUMN_ANSWER);
        if(c.moveToFirst()) {
            String lastQuest = "";
            do {
                if(!c.getString(questIndex).equals(lastQuest)) {
                    questions.add(c.getString(questIndex));
                    lastQuest = c.getString(questIndex);
                }
            }
            while (c.moveToNext());
        }
        ArrayList<String[]> answers = new ArrayList<>();
        if(c.moveToFirst()) {
            ArrayList<String> tmpAnswers = new ArrayList<>();
            String lastQuest = "";
            do {
                if(!lastQuest.equals(c.getString(questIndex))) {
                    if(tmpAnswers.size() != 0) {
                        answers.add(tmpAnswers.toArray(new String[tmpAnswers.size()]));
                    }
                    lastQuest = c.getString(questIndex);
                    tmpAnswers = new ArrayList<>();
                    tmpAnswers.add(c.getString(answIndex));
                }
                else {
                    tmpAnswers.add(c.getString(answIndex));
                }
            }
            while (c.moveToNext());
            answers.add(tmpAnswers.toArray(new String[tmpAnswers.size()]));
        }
        int isCorrectIndex = c.getColumnIndex(DataBaseWorker.COLUMN_IS_CORRECT);
        ArrayList<String[]> trueAnswers = new ArrayList<>();
        if(c.moveToFirst()) {
            ArrayList<String> tmpCorrectAnswers = new ArrayList<>();
            String lastQuest = "";
            do {
                if (!lastQuest.equals(c.getString(questIndex))) {
                    if (tmpCorrectAnswers.size() != 0) {
                        trueAnswers.add(tmpCorrectAnswers.toArray(new String[tmpCorrectAnswers.size()]));
                    }
                    lastQuest = c.getString(questIndex);
                    tmpCorrectAnswers = new ArrayList<>();
                    if (c.getInt(isCorrectIndex) == 1) {
                        tmpCorrectAnswers.add(c.getString(answIndex));
                    }
                } else {
                    if (c.getInt(isCorrectIndex) == 1) {
                        tmpCorrectAnswers.add(c.getString(answIndex));
                    }
                }
            }
            while (c.moveToNext());
            trueAnswers.add(tmpCorrectAnswers.toArray(new String[tmpCorrectAnswers.size()]));
        }
        JournalTest jTest = new JournalTest();
        jTest.fillTest(questions, answers, trueAnswers, null);
        return jTest;
    }

    public ArrayList<Journal> getJournals() {
        Cursor c = mDataBaseWorker.getJournals();
        if(c.getCount() == 0) {
            return null;
        }
        ArrayList<Journal> listJournals = new ArrayList<>();
        int idIndex = c.getColumnIndex(DataBaseWorker.COLUMN_MAGAZINE_ID);
        int nameIndex = c.getColumnIndex(DataBaseWorker.COLUMN_NAME);
        int isDownloadedIndex = c.getColumnIndex(DataBaseWorker.COLUMN_IS_DOWNLOADED);
        int posterIndex = c.getColumnIndex(DataBaseWorker.COLUMN_POSTER_IMAGE);
        int htmlIndex = c.getColumnIndex(DataBaseWorker.COLUMN_HTML_PATH);
        c.moveToFirst();
        do {
            Journal journal = new Journal(c.getInt(idIndex), c.getString(nameIndex));
            if(c.getInt(isDownloadedIndex) == 1) {
                journal.setJournalDownloaded(c.getString(posterIndex), c.getString(htmlIndex));
            }
            listJournals.add(journal);
        }
        while(c.moveToNext());
        return listJournals;
    }

    /**
     * By black_pearl-net.
     */
    public class Journal {
        private int mMagazineId;
        private String mMagazineName;
        private boolean mIsDownloaded = false;
        private String mPosterImage = null;
        private String mHtmlPath = null;

        /**
         * Container for one journal.
         * @param id magazine (journal) id.
         * @param name name of magazine (journal).
         */
        public Journal(int id, String name) {
            this.mMagazineId = id;
            this.mMagazineName = name;

        }

        /**
         * Call this method to set "is_downloaded" paramether to true and
         * set paths to image and html page.
         * @param posterImagePath path of image (poster).
         * @param htmlPath path of html page.
         */
        public void setJournalDownloaded(String posterImagePath, String htmlPath) {
            this.mIsDownloaded = true;
            this.mPosterImage = posterImagePath;
            this.mHtmlPath = htmlPath;
        }
    }

    /**
     * By black_pearl-net.
     */
    public class JournalTest {
        private static final String DEFAUL_IMAGE_PATH = "default";
        private ArrayList<String> mQuestions;
        private ArrayList<String[]> mAnswers;
        private ArrayList<String[]> mTrueAnswers;
        private ArrayList<String> mImagePaths;

        public JournalTest() {
            this.mQuestions = new ArrayList<>();
            this.mAnswers = new ArrayList<>();
            this.mTrueAnswers = new ArrayList<>();
            this.mImagePaths = new ArrayList<>();
        }

        private void fillTest(ArrayList<String> questions, ArrayList<String[]> answers,
                              ArrayList<String[]> trueAnswers, @Nullable ArrayList<String> imagePaths) {
            this.mQuestions = questions;
            this.mAnswers = answers;
            this.mTrueAnswers = trueAnswers;
            if(imagePaths != null) {
                this.mImagePaths = imagePaths;
            }
            else {
                for(int i = 0; i < mQuestions.size(); i++) {
                    this.mImagePaths.add(DEFAUL_IMAGE_PATH);
                }
            }
        }

        private void addQuestion(String question, String[] answers, String[] trueAnswers, @Nullable String imagePath) {
            this.mQuestions.add(question);
            this.mAnswers.add(answers);
            this.mTrueAnswers.add(trueAnswers);
            if(imagePath != null) {
                this.mImagePaths.add(imagePath);
            }
            else {
                this.mImagePaths.add(DEFAUL_IMAGE_PATH);
            }
        }

        public ArrayList<String> getQuestions() {
            return this.mQuestions;
        }

        public ArrayList<String[]> getAnswers() {
            return this.mAnswers;
        }

        public ArrayList<String[]> getTrueAnswers() {
            return this.mTrueAnswers;
        }

        public ArrayList<String> getImagePaths() {
            return this.mImagePaths;
        }

        public String getImagePathAt(int index) {
            return this.mImagePaths.get(index);
        }

        public String getQuestionAt(int index) {
            return this.mQuestions.get(index);
        }

        public String[] getAnswersAt(int index) {
            return this.mAnswers.get(index);
        }

        public String[] getTrueAnswersAt(int index) {
            return this.mTrueAnswers.get(index);
        }
    }
}
