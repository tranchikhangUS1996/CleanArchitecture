package com.example.lap60020_local.cleanarchitecture.presentation.view;

import android.content.Intent;
import android.view.View;

public class MyClickListener implements View.OnClickListener {

    private Integer id;
    public MyClickListener(Integer id) {
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        // open detail
        Intent intent = new Intent(v.getContext(),DetailActivity.class);
        intent.putExtra("ID",id);
        v.getContext().startActivity(intent);
    }
}
