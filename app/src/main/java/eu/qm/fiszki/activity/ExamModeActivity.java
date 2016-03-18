package eu.qm.fiszki.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;
import eu.qm.fiszki.Alert;
import eu.qm.fiszki.Algorithm;
import eu.qm.fiszki.Checker;
import eu.qm.fiszki.R;
import eu.qm.fiszki.Rules;
import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBStatus;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;

public class ExamModeActivity extends AppCompatActivity {

    FlashcardRepository flashcardRepository;
    Algorithm algorithm;
    TextView word;
    EditText enteredWord;
    TextView numberOfTrue;
    TextView numberOfFalse;
    TextView numberOfProcent;
    TextView numberOfTotal;
    TextView subtitle;
    int numberOfRepeat;
    int repeat = 0;
    int trueAnswer = 0;
    int falseAnswer = 0;
    String wordFromData;
    String expectedWord;
    Rules rules;
    Alert message;
    Context context;
    Button repeate;
    Menu menu;
    int[] strs;
    Flashcard flashcard;
    Checker checker;
    TextView counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        choosePacked();
        context = this;
        message = new Alert();
        rules = new Rules();
        algorithm = new Algorithm(context);
        flashcardRepository = new FlashcardRepository(context);
        checker = new Checker();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_exam_mode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_OK) {
            if (checker.check(expectedWord, enteredWord.getText().toString())) {
                trueAnswer++;
                repeat++;
                newDraw();
            } else {
                falseAnswer++;
                repeat++;
                newDraw();
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
                        trueAnswer++;
                        repeat++;
                        newDraw();
                    } else {
                        falseAnswer++;
                        repeat++;
                        newDraw();
                    }
                }
                return false;
            }
        });
    }

    public void newDraw() {
        if (repeat != numberOfRepeat) {
            enteredWord.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.showSoftInput(enteredWord, 0);
                }
            }, 0);

            counter.setText((repeat+1) +" / "+ numberOfRepeat);
            enteredWord.setText("");
            enteredWord.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            word.setText("");

            flashcard = algorithm.drawCardAlgorithm();

            wordFromData = flashcard.getWord();
            word.append(wordFromData);
            expectedWord = flashcard.getTranslation();
            enteredWord.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        } else {
            enteredWord.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.hideSoftInputFromWindow(enteredWord.getWindowToken(), 0);
                }
            }, 0);
            setContentView(R.layout.learning_mode_statistic_layout);
            menu.findItem(R.id.action_OK).setVisible(false);
            numberOfFalse = (TextView) findViewById(R.id.numberOfFalse);
            numberOfTrue = (TextView) findViewById(R.id.numberOfTrue);
            numberOfProcent = (TextView) findViewById(R.id.numberOfProcent);
            numberOfTotal = (TextView) findViewById(R.id.numberOfTotal);
            numberOfFalse.setText(Integer.toString(falseAnswer));
            numberOfTrue.setText(Integer.toString(trueAnswer));
            int percent = (int) ((trueAnswer * 100.0f) / numberOfRepeat);
            numberOfProcent.setText(Integer.toString(percent) + "%");
            numberOfTotal.setText(Integer.toString(numberOfRepeat));
            repeate = (Button) findViewById(R.id.repeate);
            repeate.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                    Intent myIntent = new Intent(ExamModeActivity.this, ExamModeActivity.class);
                    startActivity(myIntent);
                }
            });
            subtitle = (TextView) findViewById(R.id.statistic_subtitle);
            if (percent <= 100 && percent >= 95) {
                strs = new int[]{R.string.statistic_e1, R.string.statistic_e2, R.string.statistic_e3, R.string.statistic_e4, R.string.statistic_e5};
                randomStringDraw();
            }
            if (percent <= 94 && percent >= 80) {
                strs = new int[]{R.string.statistic_d1, R.string.statistic_d2, R.string.statistic_d3, R.string.statistic_d4, R.string.statistic_d5};
                randomStringDraw();
            }
            if (percent <= 79 && percent >= 50) {
                strs = new int[]{R.string.statistic_c1, R.string.statistic_c2, R.string.statistic_c3, R.string.statistic_c4, R.string.statistic_c5};
                randomStringDraw();
            }
            if (percent <= 49 && percent >= 30) {
                strs = new int[]{R.string.statistic_b1, R.string.statistic_b2, R.string.statistic_b3, R.string.statistic_b4, R.string.statistic_b5, R.string.statistic_b6};
                randomStringDraw();
            }
            if (percent <= 30 && percent >= 0) {
                strs = new int[]{R.string.statistic_a1, R.string.statistic_a2, R.string.statistic_a3, R.string.statistic_a4, R.string.statistic_a5};
                randomStringDraw();
            }
        }
    }

    public void choosePacked() {
        CharSequence[] items = {"10", "20", "50"};
        new AlertDialog.Builder(ExamModeActivity.this)
                .setCancelable(false)
                .setSingleChoiceItems(items, 0, null)
                .setTitle(R.string.exam_mode_repeat_number)
                .setPositiveButton(R.string.button_action_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selected = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        if (selected == 0) { //10
                            numberOfRepeat = 10;
                        }
                        if (selected == 1) { //20
                            numberOfRepeat = 20;
                        }
                        if (selected == 2) { //50
                            numberOfRepeat = 50;
                        }
                        setContentView(R.layout.activity_exam_mode);
                        enteredWord = (EditText) findViewById(R.id.EnteredWord);
                        word = (TextView) findViewById(R.id.textView3);
                        counter = (TextView) findViewById(R.id.counter);
                        newDraw();
                        keyboardAction();
                    }
                })
                .setNegativeButton(R.string.button_action_back, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .show();
    }

    public void randomStringDraw(){
        int randomIndex = new Random().nextInt(6);
        int resId = strs[randomIndex];
        String randomString = getString(resId);
        subtitle.setText(randomString);
    }
}
