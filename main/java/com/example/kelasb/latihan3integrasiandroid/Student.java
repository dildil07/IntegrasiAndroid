package com.example.kelasb.latihan3integrasiandroid;

public class Student {
    private int nim;
    private String name;
    private int year;
    public Student() {
        nim = 0;
        name = "";
        year = 0;
    }
    public Student(int inputNim, String inputName, int inputYear){
        nim = inputNim;
        name = inputName;
        year = inputYear;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getNim() {
        return nim;
    }
    public void setNim(int nim) {
        this.nim = nim;
    }
    public String toString(){
        String result = "nim=" + nim + "&nama=" + name + "&angkatan=" + year;
        return result;
    }
}