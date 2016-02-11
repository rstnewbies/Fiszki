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
import android.widget.TextView;

import eu.qm.fiszki.AlarmReceiver;
import eu.qm.fiszki.Alert;
import eu.qm.fiszki.R;
import eu.qm.fiszki.Rules;
import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBStatus;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardManagement;


public class AddWordActivity extends AppCompatActivity {

    public Context context = this;
    public AlarmReceiver alarm;
    FlashcardManagement flashcardManagement;
    EditText inputWord, inputTranslation;
    DBStatus OpenDataBase = new DBStatus();
    SettingsActivity settings = new SettingsActivity();
    Alert alert = new Alert();
    private Rules rules = new Rules();
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public String notificationPosition = "notification_time";
    public String notificationStatus = "notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        inputWord = (EditText) findViewById(R.id.inputWord);
        inputWord.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        inputWord.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        inputTranslation = (EditText) findViewById(R.id.inputTranslation);
        inputTranslation.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        settings.context = this;
        flashcardManagement = new FlashcardManagement(this);
        sharedPreferences = getSharedPreferences("eu.qm.fiszki.activity", Context.MODE_PRIVATE );
        editor = sharedPreferences.edit();
        alarm = new AlarmReceiver();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        clickDone();
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
            if (rules.addNewWord(inputWord, inputTranslation, this)) {
                Flashcard flashcard = new Flashcard(inputWord.getText().toString(),
                        inputTranslation.getText().toString(), 1);
                flashcardManagement.addFlashcards(flashcard);
                if (flashcardManagement.isFirst()) {
                    alarm.start(context, 15);
                    editor.putInt(notificationPosition, 3);
                    editor.putInt(notificationStatus, 1);
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
                    if (rules.addNewWord(inputWord, inputTranslation, AddWordActivity.this)) {
                        Flashcard flashcard = new Flashcard(inputWord.getText().toString(),
                                inputTranslation.getText().toString(), 1);
                        flashcardManagement.addFlashcards(flashcard);
                        if (flashcardManagement.isFirst()) {
                            alarm.start(context, 15);
                            editor.putInt(notificationPosition, 3);
                            editor.putInt(notificationStatus, 1);
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