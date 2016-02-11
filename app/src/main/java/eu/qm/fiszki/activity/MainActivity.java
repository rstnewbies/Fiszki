package eu.qm.fiszki.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;

import eu.qm.fiszki.AlarmReceiver;
import eu.qm.fiszki.Alert;
import eu.qm.fiszki.ItemAdapter;
import eu.qm.fiszki.ListViewManagement;
import eu.qm.fiszki.R;
import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBModel;
import eu.qm.fiszki.database.DBStatus;
import eu.qm.fiszki.database.DBTransform;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardManagement;


public class MainActivity extends AppCompatActivity {

    static public SettingsActivity settings;
    static public DBAdapter myDb;
    static public DBStatus openDataBase;
    static public Alert alert;
    static public ItemAdapter flashCardList;
    static public ImageView emptyDBImage;
    static public TextView emptyDBText;
    static public Context context;
    static public ListView listView;
    static public FloatingActionButton fab;
    static public View selectedView;
    static public int earlierPosition, selectPosition;
    static public ItemAdapter editedItem;
    static public Dialog dialog;
    static public EditText editOriginal;
    static public EditText editTranslate;
    static public Button dialogButton;
    public boolean[] clickedItem;
    public int rowId;
    public AlarmReceiver alarm;
    public Toolbar toolbar;
    DBTransform transform;
    private Flashcard deletedFlashcard;
    View pastView;
    Flashcard selectedFlashcard;
    private FlashcardManagement flashcardManagement;
    private ListViewManagement listViewManagement;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public String notificationPosition = "notification_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        alarm = new AlarmReceiver();
        alert = new Alert();
        openDataBase = new DBStatus();
        myDb = new DBAdapter(this);
        context = this;
        listView = (ListView) findViewById(R.id.listView);
        emptyDBImage = (ImageView) findViewById(R.id.emptyDBImage);
        emptyDBText = (TextView) findViewById(R.id.emptyDBText);
        emptyDBImage.setImageResource(R.drawable.emptydb);
        openDataBase.openDB(myDb);
        listViewManagement = new ListViewManagement(listView);
        flashcardManagement = new FlashcardManagement(context);


        transform = new DBTransform(myDb, context);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddWordActivity.class);
                startActivity(myIntent);
            }
        });

        toolbarMainActivity();
        listViewSelect();

    }

    @Override
    public void onResume() {
        super.onResume();
        listViewPopulate();
        toolbarMainActivity();
        if (flashcardManagement.getAllFlashcards().size() > 0) {
            emptyDBImage.setVisibility(View.INVISIBLE);
            emptyDBText.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_selected_mainactivity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            pastView.setBackgroundColor(getResources().getColor(R.color.default_color));
            fab.show();
            toolbarMainActivity();
            pastView = null;
        }
        return true;
    }

    public void listViewPopulate() {
        if (flashcardManagement.getAllFlashcards().size() > 0) {
            listViewManagement.populate(context, flashcardManagement.getAllFlashcards());
        }
    }

    public void toolbarMainActivity() {
        toolbar.getMenu().clear();
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setBackgroundResource(R.color.ColorPrimary);
        toolbar.setNavigationIcon(null);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.settings) {
                            Intent goSettings = new Intent(MainActivity.this, SettingsActivity.class);
                            startActivity(goSettings);
                            finish();
                        } else if (id == R.id.examMode) {
                            if (flashcardManagement.getAllFlashcards().size() > 0) {
                                Intent goLearningMode = new Intent(MainActivity.this, ExamModeActivity.class);
                                startActivity(goLearningMode);
                            } else {
                                alert.buildAlert(getString(R.string.alert_title_fail), getString(R.string.alert_learningmode_emptybase), getString(R.string.button_action_ok), MainActivity.this);
                            }
                        } else if (id == R.id.learningMode) {
                            if (flashcardManagement.getAllFlashcards().size() > 0) {
                                Intent goLearningMode = new Intent(MainActivity.this, LearningModeActivity.class);
                                startActivity(goLearningMode);
                            } else {
                                alert.buildAlert(getString(R.string.alert_title_fail), getString(R.string.alert_learningmode_emptybase), getString(R.string.button_action_ok), MainActivity.this);
                            }
                        }
                        return true;
                    }
                });
        toolbar.dismissPopupMenus();
    }

    public void toolbarSelected() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.getMenu().clear();
        toolbar.setTitle(getString(R.string.main_activity_title_seleced_record));
        toolbar.setBackgroundResource(R.color.seleced_Adapter);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.editRecord) {
                            listViewEdit();
                        } else if (id == R.id.deleteRecord) {
                            listViewDelete();
                        } else if (id == android.R.id.home) {
                            selectedView.setBackgroundColor(getResources().getColor(R.color.default_color));
                            fab.show();
                            toolbarMainActivity();
                        }
                        return true;
                    }
                });
        toolbar.dismissPopupMenus();
    }

    public void listViewSelect() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_edit);
        dialog.setTitle(R.string.main_activity_dialog_edit_item);

        editOriginal = (EditText) dialog.findViewById(R.id.editOrginal);
        editTranslate = (EditText) dialog.findViewById(R.id.editTranslate);
        dialogButton = (Button) dialog.findViewById(R.id.editButton);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                selectedView = view;
                selectedFlashcard = (Flashcard) parent.getAdapter().getItem(position);
                editOriginal.setText(selectedFlashcard.getWord());
                editTranslate.setText(selectedFlashcard.getTranslation());

                if (pastView == view) {
                    toolbarMainActivity();
                    fab.show();
                    selectedView.setBackgroundColor(context.getResources().getColor(R.color.default_color));
                    pastView = null;

                } else {
                    if (pastView != null) {
                        pastView.setBackgroundColor(context.getResources().getColor(R.color.default_color));
                    }
                    selectedFlashcard = (Flashcard) parent.getAdapter().getItem(position);
                    selectedView.setBackgroundColor(context.getResources().getColor(R.color.pressed_color));
                    pastView = selectedView;
                    selectedView = null;
                    toolbarSelected();
                    fab.hide();
                }
                return true;

            }
        });
    }

    public void listViewEdit() {
        editOriginal.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(editOriginal, 0);
            }
        }, 50);
        editOriginal.setSelection(editOriginal.getText().length());

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editOriginal.getText().toString().isEmpty() || editTranslate.getText().toString().isEmpty()) {
                    alert.buildAlert(getString(R.string.alert_title), getString(R.string.alert_message_onEmptyFields), getString(R.string.button_action_ok), MainActivity.this);
                } else {
                    if (flashcardManagement.getFlashcardById(selectedFlashcard.getId()).getWord().equals(editOriginal.getText().toString())) {
                        selectedFlashcard.setWord(editOriginal.getText().toString());
                        selectedFlashcard.setTranslation(editTranslate.getText().toString());
                        flashcardManagement.updateFlashcard(selectedFlashcard);
                        pastView.setBackgroundColor(getResources().getColor(R.color.default_color));
                        fab.show();
                        listViewPopulate();
                        dialog.dismiss();

                    } else {
                        if (flashcardManagement.existence(selectedFlashcard.getWord())) {
                            alert.buildAlert(getString(R.string.alert_title), getString(R.string.alert_message_onRecordExist), getString(R.string.button_action_ok), MainActivity.this);
                            editOriginal.requestFocus();
                        } else {
                            selectedFlashcard.setWord(editOriginal.getText().toString());
                            selectedFlashcard.setTranslation(editTranslate.getText().toString());
                            flashcardManagement.updateFlashcard(selectedFlashcard);
                            fab.show();
                            listViewPopulate();
                            dialog.dismiss();

                        }
                    }
                }
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    public void listViewDelete() {
        final AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(getString(R.string.alert_title));
        alertDialog.setCancelable(false);
        alertDialog.setMessage(Html.fromHtml(getString(R.string.alert_delete_record)));
        alertDialog.setButton(getString(R.string.button_action_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deletedFlashcard = selectedFlashcard;
                flashcardManagement.deleteFlashcard(selectedFlashcard);

                if (flashcardManagement.getAllFlashcards().size() > 0) {
                    listViewPopulate();
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), getString(R.string.snackbar_returnword_message), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.snackbar_returnword_button), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    flashcardManagement.addFlashcards(deletedFlashcard);
                                    listViewPopulate();
                                }
                            });
                    snackbar.show();
                    fab.show();
                    toolbarMainActivity();
                } else {
                    emptyDBImage.setVisibility(View.VISIBLE);
                    emptyDBText.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                    fab.show();

                    editor.putInt(notificationPosition, 0);
                    editor.commit();

                    alarm.close(context);
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), getString(R.string.snackbar_returnword_message), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.snackbar_returnword_button), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    flashcardManagement.addFlashcards(deletedFlashcard);
                                    emptyDBImage.setVisibility(View.INVISIBLE);
                                    emptyDBText.setVisibility(View.INVISIBLE);
                                    listView.setVisibility(View.VISIBLE);
                                    listViewPopulate();
                                }
                            });
                    snackbar.show();
                    fab.show();
                }
            }
        });
        alertDialog.setButton2(getString(R.string.button_action_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

}

