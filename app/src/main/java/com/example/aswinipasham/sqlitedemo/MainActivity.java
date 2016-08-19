package com.example.aswinipasham.sqlitedemo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

     MyDBHelper dbHelper;

    EditText etid;
    EditText etFirstName;
    EditText etLastName;
    EditText etAddress;
    EditText etSalary;

    Button btninsert;
    Button btnupdate;
    Button btndelete;
    Button btnload;
    TextView tvData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDBHelper(MainActivity.this);
        init();

    }

    private void init() {

        etid = (EditText) findViewById(R.id.etId);
        etFirstName = (EditText)findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etSalary = (EditText) findViewById(R.id.etSalary);

        btninsert = (Button)findViewById(R.id.btnInsert);
        btnupdate = (Button)findViewById(R.id.btnUpdate);
        btndelete = (Button)findViewById(R.id.btnDelete);
        btnload = (Button)findViewById(R.id.btnLoad);

        btninsert.setOnClickListener(dbButtonsListener);
        btnupdate.setOnClickListener(dbButtonsListener);
        btndelete.setOnClickListener(dbButtonsListener);
        btnload.setOnClickListener(dbButtonsListener);


        tvData = (TextView)findViewById(R.id.tvFinalData);

    }

    private View.OnClickListener dbButtonsListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnInsert:
                    //
                   long resultInsert =  dbHelper.insert(Integer.parseInt(getValue(etid)), getValue(etFirstName), getValue(etLastName),
                            getValue(etAddress), Double.valueOf(getValue(etSalary)));
                    if(resultInsert == -1) {
                        Toast.makeText(MainActivity.this, "Some error occured while inserting", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Data inserted successfully, ID:" + resultInsert, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btnDelete:
                    long resultDelete =  dbHelper.delete(Integer.parseInt(getValue(etid)));
                    if(resultDelete == 0) {
                        Toast.makeText(MainActivity.this, "Some error occured while inserting", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Data inserted successfully, ID:" + resultDelete, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btnUpdate:
                    long resultUpdate =  dbHelper.update(Integer.parseInt(getValue(etid)), getValue(etFirstName), getValue(etLastName),
                            getValue(etAddress), Double.valueOf(getValue(etSalary)));
                    if(resultUpdate == 0) {
                        Toast.makeText(MainActivity.this, "Some error occured while inserting", Toast.LENGTH_SHORT).show();
                    }
                    else if(resultUpdate == 1){
                        Toast.makeText(MainActivity.this, "Data updated successfully, ID:" + resultUpdate, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Error occurred, multiple records inserted, ID:" + resultUpdate, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btnLoad:

                    StringBuffer finalData = new StringBuffer();
                    Cursor cursor = dbHelper.getDataBasedOnQuery("Select * from " + MyDBHelper.TABLE_NAME);

                    for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
                    {
                        finalData.append(cursor.getInt(cursor.getColumnIndex(MyDBHelper.ID)));
                        finalData.append("-");
                        finalData.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.FIRST_NAME)));
                        finalData.append("-");
                        finalData.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.LAST_NAME)));
                        finalData.append("-");
                        finalData.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.Address)));
                        finalData.append("-");
                        finalData.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.Salary)));
                        finalData.append("\n");
                    }
                    tvData.setText(finalData);
                    break;
            }
        }
    };

    private String getValue(EditText editText)
    {
        return editText.getText().toString().trim();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbHelper.openDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.closeDB();
    }
}
