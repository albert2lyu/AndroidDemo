package com.mjj.databinding.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.mjj.databinding.R;
import com.mjj.databinding.adapter.ListViewAdapter;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ListViewAdapter());
    }
}
