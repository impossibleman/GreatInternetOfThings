package com.example.greatinternetofthings.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.greatinternetofthings.R;

public class ArticlesActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvTitle;
    TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_columnar_chart);
        toolbar=findViewById(R.id.toolbar);
        tvTitle=findViewById(R.id.tv_title);
        tvContent=findViewById(R.id.tv_content);
    }
}
