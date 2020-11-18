package com.example.greatinternetofthings.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.greatinternetofthings.adapter.ArticlesAdapter;
import com.example.greatinternetofthings.adapter.AutoTurnAdapter;
import com.example.greatinternetofthings.adapter.UsuallyItemAdapter;
import com.example.greatinternetofthings.constant.ConstantAssemble;
import com.example.greatinternetofthings.constant.NativeResource;
import com.example.greatinternetofthings.customview.DragGridView;
import com.example.greatinternetofthings.R;
import com.example.greatinternetofthings.adapter.DragGridAdapter;
import com.example.greatinternetofthings.database.SQLOperator;
import com.example.greatinternetofthings.datastructor.AgricultureTypeItem;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import com.example.greatinternetofthings.interfaces.OnRLItemClicklistener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    RelativeLayout rlViewpager;
    RecyclerView gvDrag;
    RecyclerView gvUsuallyUse;
    RecyclerView rvArticles;
    ViewPager vpImages;
    ImageView ivDotOne;
    ImageView ivDotTwo;
    ImageView ivDotThree;
    ImageView ivDotFour;
    List<AgricultureTypeItem> items;
    List<AgricultureTypeItem> usuallyItems;
    List<ImageView> dots;
    ExecutorService executorService;
    UsuallyItemAdapter mAdapter;
    int SQLOperatorType, currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        appBarLayout = findViewById(R.id.ly_appbar);
        rlViewpager = findViewById(R.id.rl_viewpager);
        gvDrag = findViewById(R.id.gv_drag);
        gvUsuallyUse = findViewById(R.id.gv_usually_use);
        rvArticles = findViewById(R.id.rv_articles);
        vpImages = findViewById(R.id.vp_aotu_switch);
        ivDotOne = findViewById(R.id.iv_dot_one);
        ivDotTwo = findViewById(R.id.iv_dot_two);
        ivDotThree = findViewById(R.id.iv_dot_three);
        ivDotFour = findViewById(R.id.iv_dot_four);

        dots = new ArrayList<>();
        dots.add(ivDotFour);
        dots.add(ivDotThree);
        dots.add(ivDotTwo);
        dots.add(ivDotOne);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        usuallyItems = new ArrayList<>();
        executorService = Executors.newCachedThreadPool();
        SQLOperatorType = ConstantAssemble.SQL_OPERATE_GET;
        executorService.execute(new SQLExcutor());
        GetPermissions();
        CreateMenuItem();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int toolbarHeight = toolbar.getHeight();
                int vpHeight = vpImages.getHeight();
                int distance = vpHeight - toolbarHeight;
                float alpha = -((float) verticalOffset / (float) distance);
                toolbar.setAlpha(alpha);
                rlViewpager.setAlpha(1f - alpha);
            }
        });

        mAdapter = new UsuallyItemAdapter(usuallyItems);
        mAdapter.setOnItemClickListener(new UsuallyItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, MonitorRecieverActivity.class);
                startActivity(intent);
            }
        });
        gvUsuallyUse.setAdapter(mAdapter);
        gvUsuallyUse.setLayoutManager(new GridLayoutManager(this,4));
        DragGridAdapter adapter = new DragGridAdapter(items);
        gvDrag.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRLItemClicklistener() {
            @Override
            public void OnClick(int position) {
                Intent intent = new Intent(MainActivity.this, MonitorRecieverActivity.class);
                startActivity(intent);
                AgricultureTypeItem storeItem = items.get(position);
                int count = storeItem.getCount();
                storeItem.setCount(++count);
                SQLOperatorType = ConstantAssemble.SQL_OPERATE_STORE;
                executorService.execute(new SQLExcutor(storeItem));
            }
        });
        gvDrag.setLayoutManager(new GridLayoutManager(this,4));
        vpImages.setAdapter(new AutoTurnAdapter(this));
        vpImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("TAG", "selected position ---  " + position);
                int lastPosition = currentPosition;
                currentPosition = position;
                if (position == 0) {
                    lastPosition = 1;
                    currentPosition = 4;
                } else if (position == 5) {
                    lastPosition = 4;
                    currentPosition = 1;
                }
                ChangeDotScale(lastPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpImages.setCurrentItem(1);
        ArticlesAdapter articlesAdapter=new ArticlesAdapter();
        rvArticles.setAdapter(articlesAdapter);
        rvArticles.setLayoutManager(new GridLayoutManager(this, 2));
        articlesAdapter.setOnItemClicklistener(new OnRLItemClicklistener() {
            @Override
            public void OnClick(int position) {
                Intent intent=new Intent(MainActivity.this,ArticlesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ChangeDotScale(int lastPosition) {
        int dotPosition = currentPosition - 1;

        ImageView scaledImageView, shrinkedImageView;
        scaledImageView = dots.get(dotPosition);
        if (lastPosition > 0) {
            shrinkedImageView = dots.get(lastPosition - 1);
            shrinkedImageView.setScaleX(1);
            shrinkedImageView.setScaleY(1);
        }
        scaledImageView.setScaleX(3);
        scaledImageView.setScaleY(3);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    SQLOperatorType = ConstantAssemble.SQL_OPERATE_GET;
                    executorService.execute(new SQLExcutor());
                    break;
                case 1:
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    class SQLExcutor implements Runnable {

        AgricultureTypeItem storeItem;

        public SQLExcutor() {
        }

        public SQLExcutor(AgricultureTypeItem storeItem) {
            this.storeItem = storeItem;
        }

        @Override
        public void run() {
            if (SQLOperatorType == ConstantAssemble.SQL_OPERATE_GET) {
                GetUsuallyItem();
            } else if (SQLOperatorType == ConstantAssemble.SQL_OPERATE_STORE) {
                StoreUsuallyItem(storeItem);
            }
        }
    }

    void CreateMenuItem() {
        items = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            AgricultureTypeItem singleItem = new AgricultureTypeItem();
            singleItem.setItemId(i);
            singleItem.setName(ConstantAssemble.itemNames[i]);
            singleItem.setImgResource(NativeResource.menuImage[i]);
            items.add(singleItem);
        }
    }

    void GetUsuallyItem() {
        SQLOperator operator = new SQLOperator(this);
        List<AgricultureTypeItem> usuallyUseItems = operator.GetUsuallyUseItem();
        if (usuallyUseItems.size() != 0) {
            usuallyItems.clear();
            for (AgricultureTypeItem currentItem : usuallyUseItems) {
                Log.d("TAG", "begin count compare");
                int currentCount = currentItem.getCount();
                if (currentCount == 0) {
                    continue;
                }
                int insertPosition = 0;
                boolean needInsert = false;
                if (usuallyItems.size() == 0) {
                    usuallyItems.add(currentItem);
                } else {
                    int size = usuallyItems.size();
                    for (int index = 0; index != size; index++) {
                        AgricultureTypeItem usuallyUseItem = usuallyItems.get(index);
                        int count = usuallyUseItem.getCount();
                        if (currentCount >= count) {
                            needInsert = true;
                            insertPosition = index;
                        }
                    }
                    if (needInsert) {
                        usuallyItems.add(insertPosition, currentItem);
                    } else {
                        usuallyItems.add(currentItem);
                    }
                }
                Log.d("TAG", "item size" + usuallyItems.size());
            }
            mHandler.sendEmptyMessage(1);
        } else {
            for (AgricultureTypeItem singleItem : items) {
                operator.StoreUsuallyUseItem(singleItem, true);
            }
        }
    }

    void StoreUsuallyItem(AgricultureTypeItem storeItem) {
        SQLOperator operator = new SQLOperator(this);
        operator.StoreUsuallyUseItem(storeItem, false);
        mHandler.sendEmptyMessage(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        Bundle bundle;
        if (id == R.id.nav_columnar_chart) {
            intent = new Intent(MainActivity.this, StatisticsChartActivity.class);
            bundle = new Bundle();
            bundle.putInt("charttype", ConstantAssemble.CHART_TYPE_COLUMNAR);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_linear_chart) {
            intent = new Intent(MainActivity.this, StatisticsChartActivity.class);
            bundle = new Bundle();
            bundle.putInt("charttype", ConstantAssemble.CHART_TYPE_LINE);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_pie_chart) {
            intent = new Intent(MainActivity.this, StatisticsChartActivity.class);
            bundle = new Bundle();
            bundle.putInt("charttype", ConstantAssemble.CHART_TYPE_PIE);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_communicate) {

        } else if (id == R.id.nav_check_update) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void GetPermissions() {
        RequestOverlayPermission();
//        ActivityCompat.requestPermissions(this,ConstantAssemble.permissions,0);
    }

    private void RequestOverlayPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        if (Settings.canDrawOverlays(this)) {
            return;
        }
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 0);
    }
}
