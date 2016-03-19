package eu.qm.fiszki.activity;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.MenuItem;

import eu.qm.fiszki.AlarmReceiver;
import eu.qm.fiszki.Alert;
import eu.qm.fiszki.AppCompatPreferenceActivity;
import eu.qm.fiszki.R;
import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBStatus;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.FlashcardRepository;

public class SettingsActivity extends AppCompatPreferenceActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static String notificationPosition = "notification_time";
    public Preference cleanerDataBase;
    public Preference pref;
    public PendingIntent pendingIntent;
    public AlarmReceiver alarm;
    public AlarmManager manager;
    public Intent alarmIntent;
    public Context context;
    public DBAdapter myDb = new DBAdapter(this);
    public DBStatus openDataBase = new DBStatus();
    public int time = 15;
    public Alert alert;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private FlashcardRepository flashcardRepository;
    private CategoryRepository categoryRepository;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        addPreferencesFromResource(R.xml.pref_settings);
        sharedPreferences = getSharedPreferences("eu.qm.fiszki.activity", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        context = this;
        alert = new Alert();
        alarm = new AlarmReceiver();
        flashcardRepository = new FlashcardRepository(context);
        categoryRepository = new CategoryRepository(context);

        sync();
        clearDataBase();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sync();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        sync();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent home = new Intent(this, MainActivity.class);
            startActivity(home);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(this, MainActivity.class);
            startActivity(home);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        pref = findPreference(key);

        //FOR ListPreference
        if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;

            //FOR NEVER
            if (listPref.getValue().equals(getResources().getString(R.string.frequency_0))) {
                alarm.close(context);
                time = 0;
                pref.setSummary(listPref.getEntry());
                editor.clear();
                editor.putInt(notificationPosition, 0);
                editor.commit();
            } else
                //FOR 1 min
                if (listPref.getValue().equals(getResources().getString(R.string.frequency_1)) &&
                        flashcardRepository.countFlashcards() > 0) {
                    alarm.close(context);
                    time = 1;
                    pref.setSummary(listPref.getEntry());
                    editor.clear();
                    editor.putInt(notificationPosition, 1);
                    editor.commit();
                    alarm.start(context, time);
                } else
                    //FOR 5 min
                    if (listPref.getValue().equals(getResources().getString(R.string.frequency_5)) &&
                            flashcardRepository.countFlashcards() > 0) {
                        alarm.close(context);
                        time = 5;
                        pref.setSummary(listPref.getEntry());
                        editor.clear();
                        editor.putInt(notificationPosition, 2);
                        editor.commit();
                        alarm.start(context, time);
                    } else
                        //FOR 15min
                        if (listPref.getValue().equals(getResources().getString(R.string.frequency_15)) &&
                                flashcardRepository.countFlashcards() > 0) {
                            alarm.close(context);
                            time = 15;
                            pref.setSummary(listPref.getEntry());
                            editor.clear();
                            editor.putInt(notificationPosition, 3);
                            editor.commit();
                            alarm.start(context, time);
                        } else
                            //FOR 30
                            if (listPref.getValue().equals(getResources().getString(R.string.frequency_30)) &&
                                    flashcardRepository.countFlashcards() > 0) {
                                alarm.close(context);
                                time = 30;
                                pref.setSummary(listPref.getEntry());
                                editor.clear();
                                editor.putInt(notificationPosition, 4);
                                editor.commit();
                                alarm.start(context, time);
                            } else {
                                alert.buildAlert(
                                        context.getString(R.string.alert_notification_change_title),
                                        context.getString(R.string.alert_notification_change_message),
                                        context.getString(R.string.button_action_ok),
                                        SettingsActivity.this);
                                listPref.setValue(getResources().getString(R.string.frequency_0));
                                editor.clear();
                                editor.putInt(notificationPosition, 0);
                                editor.commit();
                                alarm.close(context);
                            }
        }
    }

    public void sync() {
        //Notification
        pref = findPreference(getResources().getString(R.string.settings_key_notification));
        ListPreference listPref = (ListPreference) pref;
        if (sharedPreferences.getInt(notificationPosition, 0) == 0) {
            listPref.setValueIndex(0);
            pref.setSummary(listPref.getEntry());
        } else if (sharedPreferences.getInt(notificationPosition, 0) == 1
                && flashcardRepository.countFlashcards() > 0) {
            listPref.setValueIndex(1);
            pref.setSummary(listPref.getEntry());
        } else if (sharedPreferences.getInt(notificationPosition, 0) == 2
                && flashcardRepository.countFlashcards() > 0) {
            listPref.setValueIndex(2);
            pref.setSummary(listPref.getEntry());
        } else if (sharedPreferences.getInt(notificationPosition, 0) == 3
                && flashcardRepository.countFlashcards() > 0) {
            listPref.setValueIndex(3);
            pref.setSummary(listPref.getEntry());
        } else if (sharedPreferences.getInt(notificationPosition, 0) == 4
                && flashcardRepository.countFlashcards() > 0) {
            listPref.setValueIndex(4);
            pref.setSummary(listPref.getEntry());
        }
        //Version
        pref = findPreference(getResources().getString(R.string.settings_key_version));
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;
        pref.setSummary(version);

        //Clear database
        cleanerDataBase = findPreference(getResources().getString(R.string.settings_key_data_base));
        if (flashcardRepository.countFlashcards() > 0 || categoryRepository.countCategory()>2) {
            cleanerDataBase.setEnabled(true);
        } else {
            cleanerDataBase.setEnabled(false);
        }
    }

    public void clearDataBase() {
        cleanerDataBase.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setMessage(R.string.alert_clear_database_settings)
                        .setPositiveButton(R.string.button_action_yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteDbRows();
                            }
                        })
                        .setNegativeButton(R.string.button_action_no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        }).show();
                return true;

            }
        });
    }

    private void deleteDbRows() {

        flashcardRepository.deleteFlashcards(flashcardRepository.getAllFlashcards());
        categoryRepository.deleteCategories(categoryRepository.getAllCategory());
        Intent refresh = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(refresh);
        finish();
        alarm.close(context);
        editor.clear();
        editor.putInt(notificationPosition, 0);
        editor.commit();
    }
}
