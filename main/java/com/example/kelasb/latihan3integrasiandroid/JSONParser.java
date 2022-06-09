package com.example.kelasb.latihan3integrasiandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JSONParser {
    String content;
    public JSONParser(InputStream is){
        content = convertStreamToString(is);
    }
    public ArrayList<Student> parseStudent(){
        ArrayList<Student> retValue = new ArrayList<Student>();
        try {
//Parse JSON
            JSONArray studentArray = new JSONArray(content);
            for(int i=0; i<studentArray.length(); i++){
                JSONObject jsonObject = studentArray.getJSONObject(i);
                int nim = jsonObject.getInt("nim");
                String nama = jsonObject.getString("nama");
                int angkatan = jsonObject.getInt("angkatan");
                Student student = new Student(nim, nama, angkatan);
                retValue.add(student);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retValue;
    }
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public int parseRetValue() {
        try {
            JSONObject obj= new JSONObject(content);
            return obj.getInt("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}