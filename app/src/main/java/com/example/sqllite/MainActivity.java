package com.example.sqllite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name, phone, date_Of_Birth;
    Button insert, select;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.txtName);
        phone = findViewById(R.id.txtNumber);
        date_Of_Birth = findViewById(R.id.txtDate);
        insert = findViewById(R.id.btnInsert);
        select = findViewById(R.id.btnSelect);
        databaseHelper = new DatabaseHelper(this);

        insert.setOnClickListener(view -> {
                Boolean checkInsertData = databaseHelper.insert(name.getText().toString(),
                        phone.getText().toString(), date_Of_Birth.getText().toString());
                if (checkInsertData) {
                    Toast.makeText(getApplicationContext(),
                            "Данные успешно добавлены", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Произошла ошибка", Toast.LENGTH_LONG).show();
                }
            });

        select.setOnClickListener(view -> {
            Cursor res = databaseHelper.getdata();
            if (res.getCount() == 0) {
                Toast.makeText(MainActivity.this, "Нет данных", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()) {
                buffer.append("Имя: ").append(res.getString(0)).append("\n");
                buffer.append("Тел. номер: ").append(res.getString(1)).append("\n");
                buffer.append("Дата рождения: ").append(res.getString(2)).append("\n\n");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Данные пользователей");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}