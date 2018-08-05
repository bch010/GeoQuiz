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

    public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";

    private static final String KEY_IS_CHEATER = "is_cheater";




    //declare buttons
    private Button mShowAnswer;

    private boolean mIsCheater;
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        // TextView link
        mAnswerTextView = findViewById(R.id.answerTextView);

        mIsCheater = false;

        // Check to see if we are actually just redrawing after a state change
        if (savedInstanceState != null) {
            mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER);
            setAnswerShownResult(true);
        } else {
            // Answer won't be shown until user clicks the button
            setAnswerShownResult(false);
        }

        onShowAnswer();
    }

    /**
     * When Show Answer button is clicked, the CheatActivity packages up the result code and
     * the intent in the call to setResult(int, Intent) - refer to figure Figure 5.12.
     */
    private void setAnswerShownResult(boolean isAnswerShown) {

        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    /**
     * Displays answer when button is clicked
     * Link Show Answer Button and listen for click
     */
    protected void onShowAnswer() {

        mShowAnswer = findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);// the answer was seen
//                mIsCheater = true;// the user cheated

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");

        savedInstanceState.putBoolean(KEY_IS_CHEATER, mIsCheater);
    }


}
