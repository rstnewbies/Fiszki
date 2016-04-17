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

import java.util.Random;

import eu.qm.fiszki.Alert;
import eu.qm.fiszki.algorithm.Algorithm;
import eu.qm.fiszki.Checker;
import eu.qm.fiszki.R;
import eu.qm.fiszki.algorithm.CatcherFlashcardToAlgorithm;
import eu.qm.fiszki.database.SQL.DBAdapter;
import eu.qm.fiszki.database.SQL.DBStatus;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;

public class CheckActivity extends AppCompatActivity {

    Algorithm algorithm;
    TextView word;
    EditText enteredWord;
    DBAdapter myDb = new DBAdapter(this);
    DBStatus OpenDataBase = new DBStatus();
    Alert alert;
    Context context;
    FlashcardRepository flashcardRepository;
    String wordFromData;
    String expectedWord;
    String randomPassString;
    String randomFailString;
    int rowId;
    int rowPriority;
    boolean firstTry = true;
    Flashcard flashcard;
    Checker checker;
    CatcherFlashcardToAlgorithm catcherFlashcardToAlgorithm;
    MenuItem mi;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        OpenDataBase.openDB(myDb);
        Cursor c = myDb.getAllRows();
        alert = new Alert();
        context = this;
        algorithm = new Algorithm(context);
        flashcardRepository = new FlashcardRepository(context);
        checker = new Checker();
        catcherFlashcardToAlgorithm = new CatcherFlashcardToAlgorithm(context);

        if (flashcardRepository.countFlashcards() < 1) {
            alert.emptyBase(context, getString(R.string.main_activity_empty_base_main_layout),
                    getString(R.string.alert_title_fail), getString(R.string.button_action_ok));

        } else {
            if (catcherFlashcardToAlgorithm.getFlashcardsFromChosenCategoryToNotification().isEmpty()) {
                flashcard = algorithm.drawCardAlgorithm(flashcardRepository.getAllFlashcards());
            } else {
                flashcard = algorithm.drawCardAlgorithm
                        (catcherFlashcardToAlgorithm.getFlashcardsFromChosenCategoryToNotification());
            }

            wordFromData = flashcard.getWord();
            expectedWord = flashcard.getTranslation();
            rowId = flashcard.getId();
            rowPriority = flashcard.getPriority();
            enteredWord = (EditText) findViewById(R.id.EnteredWord);
            enteredWord.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            enteredWord.setText("");
            word = (TextView) findViewById(R.id.textView3);
            word.append(wordFromData);
            enteredWord.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            keyboardAction();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (myDb.getAllRows().getCount() > 0) {
            enteredWord.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OpenDataBase.closeDB(myDb);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_check, menu);
        return true;
    }

    public void drawFailString() {
        int[] strs = {R.string.statistic_a1, R.string.statistic_a2, R.string.statistic_a3, R.string.statistic_a4, R.string.statistic_b1, R.string.statistic_b2, R.string.statistic_c3, R.string.statistic_c5};
        int randomIndex = new Random().nextInt(8);
        int resId = strs[randomIndex];
        randomFailString = getString(resId);
    }

    public void drawPassString() {
        int[] strs = {R.string.statistic_e1, R.string.statistic_e2, R.string.statistic_e3, R.string.statistic_e4, R.string.statistic_e1, R.string.statistic_d4, R.string.statistic_d3, R.string.statistic_d5};
        int randomIndex = new Random().nextInt(8);
        int resId = strs[randomIndex];
        randomPassString = getString(resId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mi = item;
        id = R.id.action_OK;
        Alert message = new Alert();
        Checker check = new Checker();
        if (id == R.id.action_OK) {
            if (check.check(expectedWord, enteredWord.getText().toString())) {
                drawPassString();
                message.pass(this, randomPassString, getString(R.string.alert_title_pass), getString(R.string.button_action_ok));

                if (rowPriority < 5 && firstTry) {
                    myDb.updateFlashcardPriority(rowId, rowPriority + 1);
                }
            } else {
                drawFailString();
                enteredWord.setText("");
                enteredWord.requestFocus();
                message.fail(this, expectedWord, randomFailString, getString(R.string.alert_message_correctis),
                        getString(R.string.alert_message_tryagain), getString(R.string.alert_title_fail), getString(R.string.button_action_ok));
                firstTry = false;

                myDb.updateFlashcardPriority(rowId, 1);
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
                    id = R.id.action_OK;
                    onOptionsItemSelected(mi);
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
}
