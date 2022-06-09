package com.example.kelasb.latihan3integrasiandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button addBtn;
    CustomAdapter recviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//Ambil data dari database
        new DataDownload().execute();
// set up the RecyclerView
        recyclerView = findViewById(R.id.recviewName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recviewAdapter = new CustomAdapter(new CustomAdapterListener() {
            @Override
            public void editBtnViewOnClick(View v, int position) {
            }

            @Override
            public void deleteBtnViewOnClick(View v, int position) {
                Log.i("CLICK", "delete is click" + position);
                new DeleteTask().execute(recviewAdapter.getData().get(position).getNim());
                recviewAdapter.remove(position);
            }
        });
    }


    private class DataDownload extends AsyncTask<Void, Void, ArrayList<Student>> {
        @Override
        protected ArrayList<Student> doInBackground(Void... params) {
            ArrayList<Student> studentList = null;
            String input = "";
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/mobile/get_students.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JSONParser parser = new JSONParser(in);
                studentList = parser.parseStudent();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return studentList;
        }

        @Override
        protected void onPostExecute(ArrayList<Student> input) {
            super.onPostExecute(input);
            recviewAdapter.setAdapterData(input);
            recyclerView.setAdapter(recviewAdapter);
        }
    }


    private class DeleteTask extends AsyncTask<Integer, Void, Integer> {
        @Override
        protected Integer doInBackground(Integer... nim) {
            int success = 0;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://localhost/Tugas%203%20PAPB/delete_student.php");
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
                String message = "nim=" + nim[0];
                writer.write(message);
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
            if (input == 1) {
                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Tidak Berhasil", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


