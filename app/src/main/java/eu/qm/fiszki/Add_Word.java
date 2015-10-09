package eu.qm.fiszki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class Add_Word extends AppCompatActivity {

    EditText inputWord, inputTranslation;
    DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__word);

        inputWord = (EditText) findViewById(R.id.inputWord);
        inputTranslation = (EditText) findViewById(R.id.inputTranslation);
        openDB();
    }

    private void openDB()
    {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void closeDB()
    {
        myDb = new DBAdapter(this);
        myDb.close();
    }

    public void onClick_Add(View v)
    {
        if(!TextUtils.isEmpty(inputWord.getText()) || !TextUtils.isEmpty(inputTranslation.getText()))
        {
            myDb.insertRow(inputWord.getText().toString(), inputTranslation.getText().toString());
        }
        inputWord.setText(null);
        inputTranslation.setText(null);
        //populateListView();
        finish();
    }
}
