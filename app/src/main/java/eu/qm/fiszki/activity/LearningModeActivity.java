package eu.qm.fiszki.activity;

import android.content.Context;
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
import android.widget.ListView;
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
import eu.qm.fiszki.database.SQL.DBAdapter;
import eu.qm.fiszki.database.SQL.DBStatus;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
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
    String randomString;
    Checker check;
    Alert message;
    Context context;
    Cursor c;
    int position = 0;
    Flashcard flashcard;
    Algorithm algorithm;
    Menu menu;
    CatcherFlashcardToAlgorithm catcherFlashcardToAlgorithm;
    private Checker checker;
    private CategoryRepository categoryRepository;
    private ArrayList<Category> chosenCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        context = this;
        categoryRepository = new CategoryRepository(context);
        flashcardRepository = new FlashcardRepository(context);
        algorithm = new Algorithm(context);
        rules = new Rules();
        message = new Alert();
        checker = new Checker();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        catcherFlashcardToAlgorithm = new CatcherFlashcardToAlgorithm(context);

        chooseCategory();
    }


    @Override
    public void onResume() {
        super.onResume();
        if(!chosenCategory.isEmpty()) {
            enteredWord.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_exam_mode, menu);
        menu.findItem(R.id.action_OK).setVisible(false);
        return true;
    }

    public void drawString() {
        int[] strs = {R.string.statistic_a1, R.string.statistic_a2, R.string.statistic_a3, R.string.statistic_a4, R.string.statistic_b1, R.string.statistic_b2, R.string.statistic_c3, R.string.statistic_c5};
        int randomIndex = new Random().nextInt(8);
        int resId = strs[randomIndex];
        randomString = getString(resId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_OK) {
            if (checker.check(expectedWord, enteredWord.getText().toString())) {
                newDraw();
            } else {
                enteredWord.setText("");
                drawString();
                message.fail(this, expectedWord, randomString, getString(R.string.alert_message_correctis), getString(R.string.alert_message_tryagain), getString(R.string.alert_title_fail), getString(R.string.button_action_ok));
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
                        drawString();
                        message.fail(LearningModeActivity.this,
                                expectedWord,
                                getString(R.string.alert_message_fail),
                                getString(R.string.alert_message_correctis),
                                getString(R.string.alert_message_tryagain),
                                getString(R.string.alert_title_fail),
                                getString(R.string.button_action_ok));
                    }

                    enteredWord.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager keyboard =
                                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
                InputMethodManager keyboard =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(enteredWord, 0);
            }
        }, 0);

        flashcard = algorithm.drawCardAlgorithm(catcherFlashcardToAlgorithm
                .getFlashcardsFromChosenCategory(chosenCategory));

        word.setText("");
        wordFromData = flashcard.getWord();
        expectedWord = flashcard.getTranslation();
        enteredWord.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        enteredWord.setText("");
        word.append(wordFromData);
        enteredWord.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void chooseCategory() {
        setContentView(R.layout.layout_learning_choose_category);
        chosenCategory = new ArrayList<Category>();
        ListView learningModeChooseCategoryListView =
                (ListView) findViewById(R.id.learningMode_choose_category_listView);
        Button learningModeStartButtonn = (Button) findViewById(R.id.learningMode_button);

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
                new ShowCategoryAdapter(context, R.layout.category_choose_adapter, categories);
        learningModeChooseCategoryListView.setAdapter(showCategoryAdapter);

        learningModeStartButtonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showCategoryAdapter.getChoosenCategory().isEmpty()) {
                    Toast.makeText(context, context.getResources()
                                    .getString(R.string.exam_mode_no_choose_category),
                            Toast.LENGTH_LONG).show();
                }else{
                    chosenCategory = showCategoryAdapter.getChoosenCategory();
                    setContentView(R.layout.activity_check);
                    menu.findItem(R.id.action_OK).setVisible(true);
                    enteredWord = (EditText) findViewById(R.id.EnteredWord);
                    word = (TextView) findViewById(R.id.textView3);
                    newDraw();
                    keyboardAction();
                }
            }
        });
    }
}