package com.mjj.databinding.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mjj.databinding.R;
import com.mjj.databinding.bean.Main;
import com.mjj.databinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(new Main());
        binding.setClickHandler(new ClickHandler());
    }

    public class ClickHandler
    {
        public void goToRecyclerView(View v)
        {
            startActivity(new Intent(MainActivity.this,RecyclerActivity.class));
        }

        public void goToListView(View v)
        {
            startActivity(new Intent(MainActivity.this,ListViewActivity.class));
        }
    }
}
