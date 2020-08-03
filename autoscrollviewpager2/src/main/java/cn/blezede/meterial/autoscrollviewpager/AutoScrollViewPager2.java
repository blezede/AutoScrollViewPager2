package cn.blezede.meterial.autoscrollviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Author: GaoQiang
 * Time: 2020/8/3 14:34
 * Description:ViewPager2 自动滚屏
 */
public class AutoScrollViewPager2 extends FrameLayout {

    @Retention(SOURCE)
    @IntDef({ORIENTATION_LEFT_TO_RIGHT, ORIENTATION_RIGHT_TO_LEFT, ORIENTATION_BOTTOM_TO_TOP, ORIENTATION_TOP_TO_BOTTOM})
    public @interface Orientation {
    }

    public static final int ORIENTATION_LEFT_TO_RIGHT = 0x001;

    public static final int ORIENTATION_RIGHT_TO_LEFT = 0x002;

    public static final int ORIENTATION_BOTTOM_TO_TOP = 0x003;

    public static final int ORIENTATION_TOP_TO_BOTTOM = 0x004;

    private static final int sMAX_ITEM_COUNT = Integer.MAX_VALUE;

    private static final long sSWITCH_TIME = 3000L;

    private static final int sSWITCH_FLAG = 0x001;

    private Context mContext;

    private ViewPager2 mViewPager2;

    private Handler mHandler = new SwitchHandler(this);

    private AutoAdapter mAutoAdapter;

    private long mSwitchTime = sSWITCH_TIME;

    private int mCurrentOrientation = ORIENTATION_LEFT_TO_RIGHT;

    private ViewPager2.OnPageChangeCallback mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            if (mOnPageListener != null && mAutoAdapter != null) {
                int realPosition = position % mAutoAdapter.getItemCount();
                mOnPageListener.onPageSelected(realPosition);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };

    private OnPageListener mOnPageListener;

    public AutoScrollViewPager2(@NonNull Context context) {
        this(context, null);
    }

    public AutoScrollViewPager2(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollViewPager2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bindView(context);
    }

    private void bindView(Context context) {
        if (context == null) return;
        setClipChildren(false);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mContext = context;
        mViewPager2 = new ViewPager2(context);
        if (getChildCount() > 0) removeAllViews();
        addView(mViewPager2);
        mViewPager2.setClipChildren(false);
        mViewPager2.registerOnPageChangeCallback(mOnPageChangeCallback);
        mViewPager2.setOffscreenPageLimit(1);
        setOrientation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void setAdapter(AutoAdapter adapter) {
        if (adapter == null) return;
        if (adapter.getItemCount() <= 0) return;
        mAutoAdapter = adapter;
        mViewPager2.setAdapter(new ViewPager2Adapter());
        mViewPager2.setCurrentItem((sMAX_ITEM_COUNT / 2 - 1) - ((sMAX_ITEM_COUNT / 2 - 1) % mAutoAdapter.getItemCount()), false);
    }

    private void setOnPageListener(OnPageListener pageListener) {
        this.mOnPageListener = pageListener;
    }

    private void setOrientation() {
        switch (mCurrentOrientation) {
            case ORIENTATION_LEFT_TO_RIGHT:
            case ORIENTATION_RIGHT_TO_LEFT:
                mViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                break;
            case ORIENTATION_TOP_TO_BOTTOM:
            case ORIENTATION_BOTTOM_TO_TOP:
                mViewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                break;
        }
    }

    private void switchNextOne() {
        if (mAutoAdapter == null) return;
        if (mAutoAdapter.getItemCount() <= 0) return;
        switch (mCurrentOrientation) {
            case ORIENTATION_LEFT_TO_RIGHT:
            case ORIENTATION_TOP_TO_BOTTOM:
                if (mViewPager2.getCurrentItem() + 1 < sMAX_ITEM_COUNT)
                    mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() + 1);
                break;
            case ORIENTATION_RIGHT_TO_LEFT:
            case ORIENTATION_BOTTOM_TO_TOP:
                if (mViewPager2.getCurrentItem() - 1 > 0 && mViewPager2.getCurrentItem() - 1 < sMAX_ITEM_COUNT)
                    mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() - 1);
                break;
        }
    }

    private void setSwitchTime(long time) {
        if (time <= 0) return;
        mSwitchTime = time;
        mHandler.removeCallbacksAndMessages(null);
    }

    public void start() {
        if (mAutoAdapter == null) return;
        if (mHandler != null)
            mHandler.sendEmptyMessageDelayed(sSWITCH_FLAG, mSwitchTime);
    }

    public void pause() {
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
    }

    private class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2ViewHolder> {


        @Override
        public int getItemViewType(int position) {
            if (mAutoAdapter != null) {
                int realPosition = position % mAutoAdapter.getItemCount();
                return mAutoAdapter.getItemViewType(realPosition);
            } else {
                return super.getItemViewType(position);
            }
        }

        @NonNull
        @Override
        public ViewPager2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = null;
            if (mAutoAdapter != null) {
                int layoutId = mAutoAdapter.getBindItemViewId(viewType);
                view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
                return new ViewPager2ViewHolder(view);
            }
            return new ViewPager2ViewHolder(new View(mContext));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewPager2ViewHolder holder, int position) {
            if (mAutoAdapter != null) {
                int realPosition = position % mAutoAdapter.getItemCount();
                mAutoAdapter.onBindItemView(holder.itemView, realPosition);
            }
        }

        @Override
        public int getItemCount() {
            return sMAX_ITEM_COUNT;
        }
    }


    private class ViewPager2ViewHolder extends RecyclerView.ViewHolder {

        public ViewPager2ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static abstract class AutoAdapter {

        public int getItemViewType(int position) {
            return 0;
        }

        public abstract @LayoutRes
        int getBindItemViewId(int viewType);

        public abstract void onBindItemView(View v, int position);

        public abstract int getItemCount();
    }

    private static class SwitchHandler extends Handler {

        private AutoScrollViewPager2 scrollViewPager2;

        public SwitchHandler(AutoScrollViewPager2 scrollViewPager2) {
            this.scrollViewPager2 = scrollViewPager2;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (scrollViewPager2 == null) return;
            if (msg.what == sSWITCH_FLAG) {
                scrollViewPager2.switchNextOne();
                scrollViewPager2.mHandler.sendEmptyMessageDelayed(sSWITCH_FLAG, scrollViewPager2.mSwitchTime);
            }

        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pause();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                pause();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                start();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public AutoScrollViewPager2 build(Builder builder) {
        if (builder == null) return this;
        setAdapter(builder.autoAdapter);
        if (builder.orientation > 0) {
            mCurrentOrientation = builder.orientation;
            setOrientation();
        }
        setSwitchTime(builder.switchTime);
        if (builder.leftMargin > 0 || builder.topMargin > 0 || builder.rightMargin > 0 || builder.bottomMargin > 0) {
            MarginLayoutParams layoutParams = (MarginLayoutParams) mViewPager2.getLayoutParams();
            layoutParams.leftMargin = builder.leftMargin > 0 ? builder.leftMargin : 0;
            layoutParams.topMargin = builder.topMargin > 0 ? builder.topMargin : 0;
            layoutParams.rightMargin = builder.rightMargin > 0 ? builder.rightMargin : 0;
            layoutParams.bottomMargin = builder.bottomMargin > 0 ? builder.bottomMargin : 0;
            mViewPager2.setLayoutParams(layoutParams);
        }
        setOnPageListener(builder.onPageListener);
        return this;
    }

    public interface OnPageListener {
        void onPageSelected(int position);
    }

    public static class Builder implements Cloneable {

        /**
         * 页面元素适配器
         */
        private AutoAdapter autoAdapter;

        /**
         * 页面滚动监听器
         */
        private OnPageListener onPageListener;

        /**
         * 滚动方向 按position 012 左-右 012上-下
         */
        private int orientation;

        /**
         * 页面切换周期
         */
        private long switchTime;

        /**
         * 页面左边距
         */
        private int leftMargin;

        /**
         * 页面右边距
         */
        private int rightMargin;

        /**
         * 页面上边距
         */
        private int topMargin;

        /**
         * 页面下边距
         */
        private int bottomMargin;

        public Builder() {

        }

        public Builder setAdapter(AutoAdapter adapter) {
            if (adapter == null) return this;
            if (adapter.getItemCount() <= 0) return this;
            this.autoAdapter = adapter;
            return this;
        }

        public Builder setOnPageListener(OnPageListener pageListener) {
            this.onPageListener = pageListener;
            return this;
        }

        public Builder setOrientation(@Orientation int orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder setSwitchTime(long time) {
            if (time < 0) return this;
            this.switchTime = time;
            return this;
        }

        public Builder setOffsetMargins(int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
            this.leftMargin = leftMargin;
            this.rightMargin = rightMargin;
            this.topMargin = topMargin;
            this.bottomMargin = bottomMargin;
            return this;
        }

        @NonNull
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
