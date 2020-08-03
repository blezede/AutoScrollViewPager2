package cn.blezede.meterial.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.blezede.meterial.autoscrollviewpager.AutoScrollViewPager2;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.auto_view_pager1)
    AutoScrollViewPager2 mAutoScrollViewPager1;
    @BindView(R.id.auto_view_pager2)
    AutoScrollViewPager2 mAutoScrollViewPager2;
    @BindView(R.id.auto_view_pager3)
    AutoScrollViewPager2 mAutoScrollViewPager3;
    @BindView(R.id.auto_view_pager4)
    AutoScrollViewPager2 mAutoScrollViewPager4;
    private Unbinder mUnBinder;
    private Handler mHandler = new Handler();
    private int[] mColors = new int[]{
            Color.BLUE, Color.GREEN, Color.RED, Color.CYAN, Color.MAGENTA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnBinder = ButterKnife.bind(this, this);
        mSwipeRefreshLayout.setProgressViewEndTarget(true, 500);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setSize(CircularProgressDrawable.DEFAULT);
        AutoScrollViewPager2.Builder builder1 = new AutoScrollViewPager2.Builder()
                .setAdapter(new AutoScrollViewPager2.AutoAdapter() {
                    @Override
                    public int getBindItemViewId(int viewType) {
                        return R.layout.item_layout;
                    }

                    @Override
                    public void onBindItemView(View v, int position) {
                        TextView textView = v.findViewById(R.id.content_tv);
                        textView.setText(String.valueOf(position));
                        v.setBackgroundColor(mColors[position]);
                    }

                    @Override
                    public int getItemCount() {
                        return mColors.length;
                    }
                }).setOnPageListener(new AutoScrollViewPager2.OnPageListener() {
                    @Override
                    public void onPageSelected(int position) {
                        Log.e(this.getClass().getSimpleName(), "onPageSelected --> " + position);
                        Toast.makeText(getApplicationContext(), "page = " + position, Toast.LENGTH_LONG).show();
                    }
                }).setOnPageListener(new AutoScrollViewPager2.OnPageListener() {
                    @Override
                    public void onPageSelected(int position) {
                        Log.e(this.getClass().getSimpleName(), "onPageSelected --> " + position);
                        Toast.makeText(getApplicationContext(), "page = " + position, Toast.LENGTH_LONG).show();
                    }
                }).setSwitchTime(5000)
                .setOrientation(AutoScrollViewPager2.ORIENTATION_LEFT_TO_RIGHT)
                .setOffsetMargins(32, 0, 32, 0);
        mAutoScrollViewPager1.build(builder1);
        AutoScrollViewPager2.Builder builder2 = new AutoScrollViewPager2.Builder()
                .setAdapter(new AutoScrollViewPager2.AutoAdapter() {
                    @Override
                    public int getBindItemViewId(int viewType) {
                        return R.layout.item_layout;
                    }

                    @Override
                    public void onBindItemView(View v, int position) {
                        TextView textView = v.findViewById(R.id.content_tv);
                        textView.setText(String.valueOf(position));
                        v.setBackgroundColor(mColors[position]);
                    }

                    @Override
                    public int getItemCount() {
                        return mColors.length;
                    }
                }).setOnPageListener(new AutoScrollViewPager2.OnPageListener() {
                    @Override
                    public void onPageSelected(int position) {
                        Log.e(this.getClass().getSimpleName(), "onPageSelected --> " + position);
                        //Toast.makeText(getApplicationContext(), "page = " + position, Toast.LENGTH_LONG).show();
                    }
                }).setSwitchTime(5000)
                .setOrientation(AutoScrollViewPager2.ORIENTATION_RIGHT_TO_LEFT)
                .setOffsetMargins(32, 0, 32, 0);
        mAutoScrollViewPager2.build(builder2);
        AutoScrollViewPager2.Builder builder3 = new AutoScrollViewPager2.Builder()
                .setAdapter(new AutoScrollViewPager2.AutoAdapter() {
                    @Override
                    public int getBindItemViewId(int viewType) {
                        return R.layout.item_layout;
                    }

                    @Override
                    public void onBindItemView(View v, int position) {
                        TextView textView = v.findViewById(R.id.content_tv);
                        textView.setText(String.valueOf(position));
                        v.setBackgroundColor(mColors[position]);
                    }

                    @Override
                    public int getItemCount() {
                        return mColors.length;
                    }
                }).setOnPageListener(new AutoScrollViewPager2.OnPageListener() {
                    @Override
                    public void onPageSelected(int position) {
                        Log.e(this.getClass().getSimpleName(), "onPageSelected --> " + position);
                        //Toast.makeText(getApplicationContext(), "page = " + position, Toast.LENGTH_LONG).show();
                    }
                }).setSwitchTime(5000)
                .setOrientation(AutoScrollViewPager2.ORIENTATION_TOP_TO_BOTTOM)
                .setOffsetMargins(0, 32, 0, 32);
        mAutoScrollViewPager3.build(builder3);
        AutoScrollViewPager2.Builder builder4 = new AutoScrollViewPager2.Builder()
                .setAdapter(new AutoScrollViewPager2.AutoAdapter() {
                    @Override
                    public int getBindItemViewId(int viewType) {
                        return R.layout.item_layout;
                    }

                    @Override
                    public void onBindItemView(View v, int position) {
                        TextView textView = v.findViewById(R.id.content_tv);
                        textView.setText(String.valueOf(position));
                        v.setBackgroundColor(mColors[position]);
                    }

                    @Override
                    public int getItemCount() {
                        return mColors.length;
                    }
                }).setOnPageListener(new AutoScrollViewPager2.OnPageListener() {
                    @Override
                    public void onPageSelected(int position) {
                        Log.e(this.getClass().getSimpleName(), "onPageSelected --> " + position);
                        //Toast.makeText(getApplicationContext(), "page = " + position, Toast.LENGTH_LONG).show();
                    }
                }).setSwitchTime(5000)
                .setOrientation(AutoScrollViewPager2.ORIENTATION_BOTTOM_TO_TOP)
                .setOffsetMargins(0, 32, 0, 32);
        mAutoScrollViewPager4.build(builder4);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null)
            mUnBinder.unbind();

    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAutoScrollViewPager1.start();
        mAutoScrollViewPager2.start();
        mAutoScrollViewPager3.start();
        mAutoScrollViewPager4.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAutoScrollViewPager1.pause();
        mAutoScrollViewPager2.pause();
        mAutoScrollViewPager3.pause();
        mAutoScrollViewPager4.pause();
    }
}
