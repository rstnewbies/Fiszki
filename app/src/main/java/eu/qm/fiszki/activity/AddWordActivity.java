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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import eu.qm.fiszki.AlarmReceiver;
import eu.qm.fiszki.Alert;
import eu.qm.fiszki.CategorySpinnerRepository;
import eu.qm.fiszki.R;
import eu.qm.fiszki.Rules;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;


public class AddWordActivity extends AppCompatActivity {

    public Context context;
    public AlarmReceiver alarm;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    FlashcardRepository flashcardRepository;
    EditText inputWord, inputTranslation;
    SettingsActivity settings = new SettingsActivity();
    Alert alert = new Alert();
    private Rules rules = new Rules();
    private Spinner categorySpinner;
    private CategoryRepository categoryRepository;
     CategorySpinnerRepository categorySpinnerRepository;


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
        flashcardRepository = new FlashcardRepository(context);
        sharedPreferences = getSharedPreferences("eu.qm.fiszki.activity", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        alarm = new AlarmReceiver();
        categorySpinner = (Spinner) findViewById(R.id.CategorySpinner);
        categoryRepository = new CategoryRepository(context);

        categorySpinnerRepository = new CategorySpinnerRepository(categorySpinner,context);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        clickDone();
        categoryRepository.addSystemCategory();
        categorySpinnerRepository.populate();
        categorySpinnerRepository.setSelectedListener(this);
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
                        inputTranslation.getText().toString(), 1,
                        categorySpinnerRepository.getSelectedCategoryID());
                flashcardRepository.addFlashcards(flashcard);
                if (flashcardRepository.isFirst()) {
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
                                inputTranslation.getText().toString(), 1,
                                categorySpinnerRepository.getSelectedCategoryID());
                        flashcardRepository.addFlashcards(flashcard);
                        if (flashcardRepository.isFirst()) {
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



}