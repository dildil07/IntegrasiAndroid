package com.example.kelasb.latihan3integrasiandroid;

import android.view.View;

public interface CustomAdapterListener {
    void editBtnViewOnClick(View v, int position);
    void deleteBtnViewOnClick(View v, int position);
}