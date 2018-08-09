package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = "CheatActivity";
    public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"; // The activity that will receive the extra
    public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";

    private static final String KEY_IS_CHEATER = "mIsCheater";

    //declare
    private Button mShowAnswer;

    private boolean mIsCheater; // Has the user cheated?
    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;

    /**
     * When Show Answer button is clicked, the CheatActivity retrieves the result and
     * the intent in the call to setResult(int, Intent) - refer to figure Figure 5.12.
     */
    private void setAnswerShownResult(boolean isAnswerShown) {

        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        Log.d(TAG, "setAnswerShownResult: isAnswerShown: " + isAnswerShown);
        setResult(RESULT_OK, data);
        mIsCheater = isAnswerShown;
        Log.d(TAG, "setAnswerShownResult: mIsCheater=isAnswerShown: " + mIsCheater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        setAnswerShownResult(false);

        // Link Show Answer Button and listen for click
        mAnswerTextView = findViewById(R.id.answerTextView);
        mShowAnswer = findViewById(R.id.showAnswerButton);

        mShowAnswer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mAnswerIsTrue) {

                    mAnswerTextView.setText(R.string.true_button);
                } else {

                    mAnswerTextView.setText(R.string.false_button);
                }

                setAnswerShownResult(true);
            }
        });

        // retrieve index state
        if (savedInstanceState != null) { //bundle is null when the activity is first created.

            setAnswerShownResult(savedInstanceState.getBoolean(KEY_IS_CHEATER, false));

            if (mAnswerIsTrue) {

                mAnswerTextView.setText(R.string.true_button);

            } else {
                mAnswerTextView.setText(R.string.false_button);
            }
        }
    }

    /**
     * saves data to the Bundle before the activity is destroyed.
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean(KEY_IS_CHEATER, mIsCheater);// save the cheat status of the user onSaveInstanceState

        Log.i(TAG, "onSaveInstanceState: Is answer shown: " + mAnswerIsTrue);
        Log.i(TAG, "onSaveInstanceState: Is cheater: " + mIsCheater);
    }
}
