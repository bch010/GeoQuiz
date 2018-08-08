package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    //declare buttons
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;

    private boolean mIsCheater;
    private TextView mQuestionTextView;

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    // True/False array
    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0; // start quiz from question 1


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(TAG, "onCreate(Bundle) called"); // page 55

        // Retrieve a saved bundle in onCreate()
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0); // If has null (views have nothing to load), load KEY_INDEX, which might not exist, so 0 used as a default return value.
            // need something here to load ' mIsCheater'
        }


        // printing out various states, see output
        Log.d(TAG, "onCreate(): Current Index: " + mCurrentIndex);
        Log.i(TAG, "onCreate(): Current Index: " + mCurrentIndex);
        Log.i(TAG, "onCreate(): mIsCheater Status: " + mIsCheater);
        Log.d(TAG, "onCreate(): mIsCheater Status: " + mIsCheater);

        //controller part

        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {

            // When TextView is pressed, user sees next question
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + mQuestionBank.length - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        // Link question from question bank to view
        // Sets up event handlers for the button clicks
        onTrueButtonClick();
        onFalseButtonClick();
        onNextButtonClick();
        onPrevButtonClick();
        onCheatButtonClick();

        updateQuestion(); // first time called add 1 to set_text
    }

    // from page 55
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //page 66
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);


        //Send a INFO log message and log the exception.
        Log.i(TAG, "onSaveInstanceState: what is this output for?" + mCurrentIndex);

        //Send a DEBUG log message.
        Log.d(TAG, "onSaveInstanceState: mIsCheater Status: " + mIsCheater);

    }

    /**
     * When true button is pressed, returns correct answer
     */
    protected void onTrueButtonClick() {

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
    }

    /**
     * When false button is pressed, returns incorrect answer
     */
    protected void onFalseButtonClick() {
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
    }

    /**
     * When next button is pressed, returns next questions
     */
    protected void onNextButtonClick() {

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion(); // called as part of the onCreate method, sets a default value to Textview
            }
        });
    }

    /**
     * When previous button is pressed, returns next questions
     */
    protected void onPrevButtonClick() {

        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mCurrentIndex = (mCurrentIndex + mQuestionBank.length - 1) % mQuestionBank.length; // ' + mQuestionBank.length' so does not throw out of bounds.. just loops
                updateQuestion(); // called as part of the onCreate method, sets a default value to Textview
            }
        });

    }

    /**
     * When clicked, displays answer
     */
    protected void onCheatButtonClick() {

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue); // intent extra
                startActivityForResult(i, 0);
                updateQuestion();
            }
        });

    }

    /**
     * Updates questions
     */
    private void updateQuestion() {

        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    /**
     * Checks if selected answer is correct
     */
    private void checkAnswer(boolean userPressedTrue) {

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    // Check if cheated
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);



    }

}

