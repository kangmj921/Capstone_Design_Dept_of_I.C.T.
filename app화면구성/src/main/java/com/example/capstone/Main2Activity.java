package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.example.capstone.fragments.PageFragment1;
import com.example.capstone.fragments.PageFragment2;
import com.example.capstone.fragments.PageFragment3;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main2);
        List<Fragment> list = new ArrayList<>();
        list.add(new PageFragment1());
        list.add(new PageFragment2());
        list.add(new PageFragment3());

        pager = findViewById((R.id.pager));
        pagerAdapter = new SlidePagerAdaper(getSupportFragmentManager(),list);

        pager.setAdapter(pagerAdapter);
    }

    public void page1(View view){

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);//액티비티 띄우기
    }
    public void page2(View view){

        Intent intent = new Intent(getApplicationContext(),PopupActivityPage2.class);
        startActivity(intent);//액티비티 띄우기
    }
    public void page3(View view){

        Intent intent = new Intent(getApplicationContext(),PopupActivityPage3.class);
        startActivity(intent);//액티비티 띄우기
    }
}
