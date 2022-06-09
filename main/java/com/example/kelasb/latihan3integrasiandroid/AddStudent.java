package com.example.kelasb.latihan3integrasiandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddStudent extends AppCompatActivity {
    private TextView nimTxtVw, nameTxtVw, yearTxtVw;
    private Button addBtnStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        nimTxtVw = findViewById(R.id.txtViewNim);
        nameTxtVw = findViewById(R.id.txtViewNama);
        yearTxtVw = findViewById(R.id.txtViewAngkatan);
        addBtnStudent = findViewById(R.id.btnAddStudent);
        addBtnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nim = Integer.parseInt(nimTxtVw.getText().toString());
                String name = nameTxtVw.getText().toString();
                int year = Integer.parseInt(yearTxtVw.getText().toString());
                Student newStudent = new Student(nim, name, year);
                new AddTask().execute(newStudent);
//Get back to main
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private class AddTask extends AsyncTask<Student, Void, Integer> {
        @Override
        protected Integer doInBackground(Student... students) {
            int success = 0;
            HttpURLConnection urlConnection = null;
            try {
                Student student = null;
                if(students.length > 0){
                    student = students[0];
                }

                URL url = new URL("http://localhost/Tugas%203%20PAPB/insert_student.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setChunkedStreamingMode(0);
                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        out, "UTF-8"));
                writer.write(student.toString());
                writer.flush();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JSONParser parser = new JSONParser(in);
                success = parser.parseRetValue();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return success;
        }
        @Override
        protected void onPostExecute(Integer input) {
            super.onPostExecute(input);
            if(input == 1){
                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Tidak Berhasil", Toast.LENGTH_SHORT).show();
            }
        }
    }
}