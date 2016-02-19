package eu.qm.fiszki.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.qm.fiszki.AlarmReceiver;
import eu.qm.fiszki.Alert;
import eu.qm.fiszki.CategorySpinnerManagement;
import eu.qm.fiszki.R;
import eu.qm.fiszki.Rules;
import eu.qm.fiszki.database.DBHelper;
import eu.qm.fiszki.database.DBStatus;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryManagement;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardManagement;


public class AddWordActivity extends AppCompatActivity {

    public Context context;
    public AlarmReceiver alarm;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    FlashcardManagement flashcardManagement;
    EditText inputWord, inputTranslation;
    DBStatus OpenDataBase = new DBStatus();
    SettingsActivity settings = new SettingsActivity();
    Alert alert = new Alert();
    private Rules rules = new Rules();
    private Spinner categorySpinner;
    private CategoryManagement categoryManagement;
    private CategorySpinnerManagement categorySpinnerManagement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        context = this;
        inputWord = (EditText) findViewById(R.id.inputWord);
        inputWord.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        inputWord.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        inputTranslation = (EditText) findViewById(R.id.inputTranslation);
        inputTranslation.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        settings.context = this;
        flashcardManagement = new FlashcardManagement(context);
        sharedPreferences = getSharedPreferences("eu.qm.fiszki.activity", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        alarm = new AlarmReceiver();
        categorySpinner = (Spinner) findViewById(R.id.CategorySpinner);
        categoryManagement = new CategoryManagement(context);

        categorySpinnerManagement = new CategorySpinnerManagement(categorySpinner);
        categorySpinnerManagement.selectedSpinner(context);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        clickDone();
        entriesSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addword, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_new_word) {
            if (rules.addNewWordRule(inputWord, inputTranslation, this)) {
                Flashcard flashcard = new Flashcard(inputWord.getText().toString(),
                        inputTranslation.getText().toString(), 1);
                flashcardManagement.addFlashcards(flashcard);
                if (flashcardManagement.isFirst()) {
                    alarm.start(context, 15);
                    editor.clear();
                    editor.putInt(SettingsActivity.notificationPosition, 3);
                    editor.commit();
                    alert.buildAlert(
                            this.getString(R.string.alert_title_pass),
                            this.getString(R.string.alert_add_first_word_message),
                            this.getString(R.string.button_action_ok),
                            this);
                }
                inputWord.setText(null);
                inputTranslation.setText(null);
                inputWord.requestFocus();
            }
        }
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickDone() {
        inputTranslation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (rules.addNewWordRule(inputWord, inputTranslation, AddWordActivity.this)) {
                        Flashcard flashcard = new Flashcard(inputWord.getText().toString(),
                                inputTranslation.getText().toString(), 1);
                        flashcardManagement.addFlashcards(flashcard);
                        if (flashcardManagement.isFirst()) {
                            alarm.start(context, 15);
                            editor.clear();
                            editor.putInt(SettingsActivity.notificationPosition, 3);
                            editor.commit();
                            alert.buildAlert(
                                    AddWordActivity.this.getString(R.string.alert_title_pass),
                                    AddWordActivity.this.getString(R.string.alert_add_first_word_message),
                                    AddWordActivity.this.getString(R.string.button_action_ok),
                                    AddWordActivity.this);
                        }
                        inputWord.setText(null);
                        inputTranslation.setText(null);
                        inputWord.requestFocus();
                    }
                }

                return true;
            }
        });
    }

    public void entriesSpinner() {
        ArrayList<Category> categories = categoryManagement.getAllCategory();
        List<String> list = new ArrayList<String>();
        int x = 0;
        do {
            if (categories.get(x).getCategory().equals(DBHelper.firstCategoryName)) {
                list.add(getString(R.string.add_new_word_no_category));
            } else if (categories.get(x).getCategory().equals(DBHelper.addCategoryName)) {
                list.add(getString(R.string.add_new_word_add_category));
            } else {
                list.add(categories.get(x).getCategory());
            }
            x++;
        } while (x != categories.size());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        categorySpinner.setAdapter(dataAdapter);
    }

}