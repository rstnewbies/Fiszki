package eu.qm.fiszki.activity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import eu.qm.fiszki.Alert;
import eu.qm.fiszki.Checker;
import eu.qm.fiszki.R;
import eu.qm.fiszki.Rules;
import eu.qm.fiszki.ShowCategoryAdapter;
import eu.qm.fiszki.algorithm.Algorithm;
import eu.qm.fiszki.algorithm.CatcherFlashcardToAlgorithm;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;

public class ExamModeActivity extends AppCompatActivity {

    public ArrayList<Category> chosenCategory;
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
    CatcherFlashcardToAlgorithm catcherFlashcardToAlgorithm;
    CategoryRepository categoryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        categoryRepository = new CategoryRepository(context);
        catcherFlashcardToAlgorithm = new CatcherFlashcardToAlgorithm(context);
        context = this;
        message = new Alert();
        rules = new Rules();
        algorithm = new Algorithm(context);
        flashcardRepository = new FlashcardRepository(context);
        checker = new Checker();

        choosePacked();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_exam_mode, menu);
        menu.findItem(R.id.action_OK).setVisible(false);
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
            menu.findItem(R.id.action_OK).setVisible(true);

            enteredWord.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.showSoftInput(enteredWord, 0);
                }
            }, 0);

            counter.setText((repeat + 1) + " / " + numberOfRepeat);
            enteredWord.setText("");
            enteredWord.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            word.setText("");

            flashcard = algorithm.drawCardAlgorithm(catcherFlashcardToAlgorithm
                    .getFlashcardsFromChosenCategory(chosenCategory));

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
        setContentView(R.layout.layout_exam_mode_choosen);
        numberOfRepeat = 0;
        ListView chosenCategoryListview = (ListView) findViewById(R.id.exam_choose_category);
        Button start = (Button) findViewById(R.id.exam_start_button);
        final RadioButton numberOfRepeatOne = (RadioButton) findViewById(R.id.numberOfRepeatOne);
        final RadioButton numberOfRepeatTwo = (RadioButton) findViewById(R.id.numberOfRepeatTwo);
        final RadioButton numberOfRepeatThree = (RadioButton) findViewById(R.id.numberOfRepeatThree);
        numberOfRepeatOne.setText(Integer.toString(context.getResources().getInteger(R.integer.repeatOne)));
        numberOfRepeatTwo.setText(Integer.toString(context.getResources().getInteger(R.integer.repeatTwo)));
        numberOfRepeatThree.setText(Integer.toString(context.getResources().getInteger(R.integer.repeatThree)));

        //populate chosenCategoryListview
        final ArrayList<Category> categories = new ArrayList<Category>();
        if(!flashcardRepository.getFlashcardsByCategoryID(1).isEmpty()) {
            categories.add(categoryRepository.getCategoryByID(1));
        }
        ArrayList<Category> userCategory = categoryRepository.getUserCategory();
        for (Category category:userCategory) {
            if(!flashcardRepository.getFlashcardsByCategoryID(category.getId()).isEmpty()){
                categories.add(category);
            }
        }
        final ShowCategoryAdapter showCategoryAdapter =
                new ShowCategoryAdapter(context, R.layout.layout_choose_category_adapter, categories);
        chosenCategoryListview.setAdapter(showCategoryAdapter);

        //setSelected RadioButton
        numberOfRepeatOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfRepeatTwo.setChecked(false);
                numberOfRepeatThree.setChecked(false);
                numberOfRepeat = context.getResources().getInteger(R.integer.repeatOne);
            }
        });
        numberOfRepeatTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfRepeatOne.setChecked(false);
                numberOfRepeatThree.setChecked(false);
                numberOfRepeat = context.getResources().getInteger(R.integer.repeatTwo);
            }
        });
        numberOfRepeatThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfRepeatOne.setChecked(false);
                numberOfRepeatTwo.setChecked(false);
                numberOfRepeat = context.getResources().getInteger(R.integer.repeatThree);
            }
        });

        //setStart Button
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showCategoryAdapter.getChoosenCategory().isEmpty()) {
                    Toast.makeText(context, context.getResources()
                                    .getString(R.string.exam_mode_no_choose_category),
                            Toast.LENGTH_LONG).show();
                }else if(numberOfRepeat == 0) {
                    Toast.makeText(context, context.getResources()
                                    .getString(R.string.exam_mode_no_choose_repeat),
                            Toast.LENGTH_LONG).show();
                } else {
                    chosenCategory = showCategoryAdapter.getChoosenCategory();
                    setContentView(R.layout.activity_exam_mode);
                    enteredWord = (EditText) findViewById(R.id.EnteredWord);
                    word = (TextView) findViewById(R.id.textView3);
                    counter = (TextView) findViewById(R.id.counter);
                    newDraw();
                    keyboardAction();
                }
            }
        });

    }

    public void randomStringDraw() {
        int randomIndex = new Random().nextInt(5);
        int resId = strs[randomIndex];
        String randomString = getString(resId);
        subtitle.setText(randomString);
    }
}
