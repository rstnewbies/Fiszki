package eu.qm.fiszki.activity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import eu.qm.fiszki.Alert;
import eu.qm.fiszki.Algorithm;
import eu.qm.fiszki.Checker;
import eu.qm.fiszki.R;
import eu.qm.fiszki.Rules;
import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBStatus;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;

public class LearningModeActivity extends AppCompatActivity {

    TextView word;
    EditText enteredWord;
    DBAdapter myDb = new DBAdapter(this);
    DBStatus OpenDataBase = new DBStatus();
    FlashcardRepository flashcardRepository;
    String wordFromData;
    String expectedWord;
    Rules rules = new Rules();
    Alert message;
    Context context;
    Cursor c;
    int position = 0;
    Flashcard flashcard;
    Algorithm algorithm;
    private Checker checker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        context = this;
        flashcardRepository = new FlashcardRepository(context);
        algorithm = new Algorithm(context);
        rules = new Rules();
        message = new Alert();
        checker = new Checker();
        enteredWord = (EditText) findViewById(R.id.EnteredWord);
        word = (TextView) findViewById(R.id.textView3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        newDraw();
        keyboardAction();
    }


    @Override
    public void onResume() {
        super.onResume();
        enteredWord.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exam_mode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_OK) {
            if (checker.check(expectedWord, enteredWord.getText().toString())) {
                finish();
                startActivity(getIntent());
            } else {
                enteredWord.setText("");
                message.fail(this, expectedWord, getString(R.string.alert_message_fail), getString(R.string.alert_message_tryagain), getString(R.string.alert_title_fail), getString(R.string.button_action_ok));

            }
        } else if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void keyboardAction() {
        enteredWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (checker.check(expectedWord, enteredWord.getText().toString())) {
                        newDraw();
                    } else {
                        enteredWord.setText("");
                        message.fail(LearningModeActivity.this, expectedWord, getString(R.string.alert_message_fail), getString(R.string.alert_message_tryagain), getString(R.string.alert_title_fail), getString(R.string.button_action_ok));
                    }

                    enteredWord.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            keyboard.showSoftInput(enteredWord, 0);
                        }
                    }, 50);

                }

                return false;
            }
        });
    }

    public void newDraw() {

        enteredWord.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(enteredWord, 0);
            }
        }, 0);

        flashcard = algorithm.drawCardAlgorithm();

        word.setText("");
        wordFromData = flashcard.getWord();
        expectedWord = flashcard.getTranslation();
        enteredWord.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        enteredWord.setText("");
        word.append(wordFromData);
        enteredWord.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}