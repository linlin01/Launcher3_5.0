package com.android.launcher3.preview;

import com.android.launcher3.Launcher;
import com.android.launcher3.Launcher.RGKPreviewAdapter;
import com.android.launcher3.LauncherAppState;
import com.android.launcher3.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Scroller;

public class RGKWorkspacePreviewScrollLayout extends ViewGroup {
    private static final String TAG = "RGKWorkspacePreviewScrollLayout";
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private Context mContext;
    private int mCurScreen;
    private int mDefaultScreen = 0;

    private static final int TOUCH_STATE_REST = 0;
    private static final int TOUCH_STATE_SCROLLING = 1;

    private static final int SNAP_VELOCITY = 600;
    public static final int MAX_SCROLLLAYOUT_COUNT = 4;

    private int mTouchState = TOUCH_STATE_REST;
    private int mTouchSlop;
    private float mLastMotionX;

    private PageListener pageListener;

    protected boolean isActive;
    protected int mLastX;
    protected int mLastY;
    protected int startPosition;
    protected int dragPosition;
    protected int dropPosition;
    protected ViewGroup itemView;
    protected boolean isCountXY;
    protected int halfItemWidth;
    protected int itemTotalCount;
    protected int nColumns;
    protected int Remainder;
    protected int nRows;
    protected int specialPosition;
    protected int leftBottomPosition;
    protected int specialItemY;
    protected int specialItemX;
    protected int leftBtmItemY;
    protected int leftBtmItemX;
    private WindowManager.LayoutParams windowParams;
    private WindowManager windowManager;
    private ImageView dragImageView;
    private boolean isMoving;
    private int movePageNum;
    private int holdPosition;
    private String LastAnimationID;
    private boolean isPrevious = false;
    public boolean b_mask = false;
    private int statusBarH = 0;
    private float mAlpha = 0.4f;
    private int mCurrentX = 0;
    private int mCurrentY = 0;

    public RGKWorkspacePreviewScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
        nColumns = RGKPreviewConfigure.WORKSPACE_PREVIEW_COLUMNS;
    }

    public RGKWorkspacePreviewScrollLayout(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;

        mScroller = new Scroller(context);

        mCurScreen = mDefaultScreen;
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        nColumns = RGKPreviewConfigure.WORKSPACE_PREVIEW_COLUMNS;
        statusBarH = 20;//context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.status_bar_height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                final int childWidth = childView.getMeasuredWidth();
                childView.layout(childLeft, 0, childLeft + childWidth,
                        childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException(
                    "ScrollLayout only canmCurScreen run at EXACTLY mode!");
        }

        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException(
                    "ScrollLayout only can run at EXACTLY mode!");
        }

        // The children are given the same width and height as the scrollLayout
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
        scrollTo(mCurScreen * width, 0);
    }

    /**
     * According to the position of current layout scroll to the destination
     * page.
     */
    public void snapToDestination() {
        final int screenWidth = getWidth();
        final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
        snapToScreen(destScreen);
    }

    public void snapToScreen(int whichScreen) {
        // get the valid layout page
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        if (getScrollX() != (whichScreen * getWidth())) {

            final int delta = whichScreen * getWidth() - getScrollX();
            mScroller.startScroll(getScrollX(), 0, delta, 0,
                    Math.abs(delta) * 2);
            mCurScreen = whichScreen;
            if (mCurScreen > RGKPreviewConfigure.curentPage) {
                RGKPreviewConfigure.curentPage = whichScreen;
                pageListener.page(RGKPreviewConfigure.curentPage);
            } else if (mCurScreen < RGKPreviewConfigure.curentPage) {
                RGKPreviewConfigure.curentPage = whichScreen;
                pageListener.page(RGKPreviewConfigure.curentPage);
            }
            invalidate(); // Redraw the layout
        }
    }

    public void setToScreen(int whichScreen) {
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        mCurScreen = whichScreen;
        scrollTo(whichScreen * getWidth(), 0);
    }

    public int getCurScreen() {
        return mCurScreen;
    }

    public int getPage() {
        return RGKPreviewConfigure.curentPage;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCurrentX = (int)event.getX();
        mCurrentY = (int)event.getY();
        if (dragImageView != null
                && dragPosition != AdapterView.INVALID_POSITION) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!isCountXY) {
                    isCountXY = true;
                }
                onDrag(x, y, event);
                if (!isMoving)
                    onMove(x, y);
                return true;
            case MotionEvent.ACTION_UP:
                isActive = false;
                stopDrag();
                onDrop(x, y);
                if (RGKPreviewConfigure.isHideButton) {
                    ((Launcher) mContext).showNewScreenAddButton(true);
                }
                RGKPreviewConfigure.isHideButton = false;
                return true;
            }
            return false;
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        final int action = event.getAction();
        final float x = event.getX();

        switch (action) {
        case MotionEvent.ACTION_DOWN:
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }
            mLastMotionX = x;
            break;
        case MotionEvent.ACTION_MOVE:
            int deltaX = (int) (mLastMotionX - x);
            mLastMotionX = x;
            scrollBy(deltaX, 0);
            break;
        case MotionEvent.ACTION_UP:
            final VelocityTracker velocityTracker = mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000);
            int velocityX = (int) velocityTracker.getXVelocity();

            if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {
                // Fling enough to move left
                snapToScreen(mCurScreen - 1);

            } else if (velocityX < -SNAP_VELOCITY
                    && mCurScreen < getChildCount() - 1) {
                // Fling enough to move right
                if (mCurScreen + 1 >= MAX_SCROLLLAYOUT_COUNT) {
                    snapToScreen(mCurScreen);
                } else {
                    snapToScreen(mCurScreen + 1);
                }
            } else {
                if (mCurScreen + 1 >= MAX_SCROLLLAYOUT_COUNT) {
                    snapToScreen(mCurScreen);
                } else {
                    snapToDestination();
                }
            }
            if (mVelocityTracker != null) {
                mVelocityTracker.recycle();
                mVelocityTracker = null;
            }
            mTouchState = TOUCH_STATE_REST;
            break;
        case MotionEvent.ACTION_CANCEL:
            mTouchState = TOUCH_STATE_REST;
            break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        final float x = ev.getX();

        if (isActive && action == MotionEvent.ACTION_UP) {
            return onTouchEvent(ev);
        } else if (isActive && action == MotionEvent.ACTION_MOVE) {
            return onTouchEvent(ev);
        }

        if ((action == MotionEvent.ACTION_MOVE)
                && (mTouchState != TOUCH_STATE_REST)) {
            return true;
        }


        switch (action) {
        case MotionEvent.ACTION_MOVE:
            final int xDiff = (int) Math.abs(mLastMotionX - x);
            if (xDiff > mTouchSlop) {
                mTouchState = TOUCH_STATE_SCROLLING;
            }
            break;
        case MotionEvent.ACTION_DOWN:
            mLastMotionX = x;
            mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
                    : TOUCH_STATE_SCROLLING;
            setOnItemLongClickListener(ev);
            break;

        case MotionEvent.ACTION_CANCEL:
        case MotionEvent.ACTION_UP:
            mTouchState = TOUCH_STATE_REST;
            break;
        }
        return mTouchState != TOUCH_STATE_REST;
    }

    public void setPageListener(PageListener pageListener) {
        this.pageListener = pageListener;
    }

    public interface PageListener {
        void page(int page);
    }

    public boolean setOnItemLongClickListener(final MotionEvent ev) {

        if (mContext instanceof Launcher) {
            if (((Launcher) mContext).getCurentDragPageView() == null)
                return false;
            ((Launcher) mContext).getCurentDragPageView()
                    .setOnItemLongClickListener(
                            new AdapterView.OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClick(
                                        AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                                    if (isActive) {
                                        return true;
                                    }
                                    isActive = true;
                                    RGKPreviewConfigure.isMove = true;

                                    RGKPreviewConfigure.isHideButton = true;
                                    ((Launcher) mContext)
                                            .hideNewScreenAddButton();

                                    int x = (int) ev.getX();
                                    int y = (int) ev.getY();
                                    mLastX = x;
                                    mLastY = y;
                                    startPosition = dragPosition = dropPosition = position;

                                    RGKPreviewConfigure.startDragItemIndex = startPosition
                                            + RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE
                                            * RGKPreviewConfigure.curentPage;
                                    if (dragPosition == AdapterView.INVALID_POSITION) {
                                        return false;
                                    }

                                    if (mContext instanceof Launcher) {
                                        itemView = (ViewGroup) ((Launcher) mContext)
                                                .getCurentDragPageView()
                                                .getChildAt(dragPosition);
                                        itemView.setVisibility(View.INVISIBLE);
                                        hideDropItem();
                                        ((RGKPreviewAdapter) ((Launcher) mContext)
                                                .getCurentDragPageView()
                                                .getAdapter()).setChange(true);
                                        ((RGKPreviewAdapter) ((Launcher) mContext)
                                                .getCurentDragPageView()
                                                .getAdapter())
                                                .setHoldPosition(RGKPreviewConfigure.startDragItemIndex);
                                        ((RGKPreviewAdapter) ((Launcher) mContext)
                                                .getCurentDragPageView()
                                                .getAdapter())
                                                .notifyDataSetChanged();
                                    }

                                    if (!isCountXY) {
                                        halfItemWidth = itemView.getWidth() / 2;
                                        int rows;
                                        itemTotalCount = ((Launcher) mContext)
                                                .getCurentDragPageView()
                                                .getCount();

                                        if (RGKPreviewConfigure.curentPage == RGKPreviewConfigure.countPages - 1) {
                                            itemTotalCount--;
                                        }

                                        rows = itemTotalCount / nColumns;
                                        Remainder = itemTotalCount % nColumns;
                                        nRows = Remainder == 0 ? rows
                                                : rows + 1;
                                        specialPosition = itemTotalCount - 1
                                                - Remainder;
                                        if (Remainder != 1)
                                            leftBottomPosition = nColumns
                                                    * (nRows - 1);
                                        if (Remainder == 0 || nRows == 1)
                                            specialPosition = -1;
                                        isCountXY = true;
                                    }

                                    if (specialPosition != dragPosition
                                            && dragPosition != -1
                                            && specialPosition != -1) {
                                        specialItemY = (nRows - 1)
                                                * itemView.getHeight();
                                        specialItemX = (nColumns - 1)
                                                * itemView.getHeight();
                                    } else {
                                        specialItemY = -1;
                                        specialItemX = -1;
                                    }

                                    if (leftBottomPosition != dragPosition
                                            && dragPosition != -1
                                            && leftBottomPosition != -1) {
                                        leftBtmItemY = (nRows - 1)
                                                * itemView.getHeight();
                                        leftBtmItemX = (nColumns - 1)
                                                * itemView.getHeight();
                                    } else {
                                        leftBtmItemY = -1;
                                        leftBtmItemX = -1;
                                    }

                                    itemView.destroyDrawingCache();
                                    itemView.setDrawingCacheEnabled(true);
                                    itemView.setDrawingCacheBackgroundColor(0x00ffffff);

                                    ImageView iv = (ImageView) itemView.findViewById(R.id.home_mark);
                                    Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache(true));
                                    Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight() - iv.getHeight());
                                    startDrag(getRoundedCornerBitmap(bitmap), x, y);

                                    isMoving = false;

                                    ((RGKPreviewAdapter) ((Launcher) mContext)
                                            .getDragPageView(
                                                    RGKPreviewConfigure.countPages - 1)
                                            .getAdapter())
                                            .notifyDataSetChanged();

                                    return true;
                                }

                            });
        }
        return false;
    }

    public Bitmap getRoundedCornerBitmap(Bitmap sourceBitmap) {
        try {
            Bitmap targetBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(),
                    sourceBitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(targetBitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            float roundPx = 5;
            float roundPy = 5;
            Rect rect = new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight());
            RectF rectF = new RectF(rect);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawRoundRect(rectF, roundPx, roundPy, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(sourceBitmap, rect, rect, paint);
            return targetBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return null;
    }

    private void hideDropItem() {
        RGKPreviewAdapter adapter = null;

        if (mContext instanceof Launcher) {
            adapter = (RGKPreviewAdapter) ((Launcher) mContext)
                    .getCurentDragPageView().getAdapter();
        }
        adapter.showDropItem(false);
    }

    private void startDrag(Bitmap bm, int x, int y) {
        stopDrag();
        windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.TOP | Gravity.LEFT;
        windowParams.x = itemView.getLeft();
        windowParams.y = itemView.getTop();
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.format = PixelFormat.RGBA_8888;
        windowParams.alpha = mAlpha;

        ImageView iv = new ImageView(getContext());
        iv.setImageBitmap(bm);
        windowManager = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        if (isActive) {
            windowManager.addView(iv, windowParams);
        }

        dragImageView = iv;

    }

    public void stopDrag() {
        if (dragImageView != null) {
            windowManager.removeView(dragImageView);
            dragImageView = null;
        }
    }

    private void onDrag(int x, int y, MotionEvent ev) {
        if (dragImageView != null) {
            windowParams.alpha = mAlpha;
            windowParams.x = (x - mLastX) + itemView.getLeft();
            windowParams.y = (y - mLastY) + itemView.getTop() + statusBarH;// Add status bar height
            windowManager.updateViewLayout(dragImageView, windowParams);
        }
        if ((x >= (RGKPreviewConfigure.screenWidth - RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_EXCHANG_DENSITY))
                && !RGKPreviewConfigure.isChangingPage
                && RGKPreviewConfigure.curentPage < RGKPreviewConfigure.countPages - 1
                && !b_mask) {
            RGKPreviewConfigure.isChangingPage = true;
            nextPageMoveAnimation();
            if (mContext instanceof Launcher) {
                ((Launcher) mContext).getCurentDragPageView()
                        .getGridPageListener()
                        .page(++RGKPreviewConfigure.curentPage);
            }
            movePageNum++;
            RGKPreviewConfigure.moveNum = movePageNum;
            isPrevious = false;
        } else if ((x <= RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_EXCHANG_DENSITY)
                && !RGKPreviewConfigure.isChangingPage
                && RGKPreviewConfigure.curentPage > 0 && !b_mask) {
            RGKPreviewConfigure.isChangingPage = true;
            previousPageMoveAnimation();
            if (mContext instanceof Launcher) {
                ((Launcher) mContext).getCurentDragPageView()
                        .getGridPageListener()
                        .page(--RGKPreviewConfigure.curentPage);
            }
            movePageNum--;
            RGKPreviewConfigure.moveNum = movePageNum;
            isPrevious = true;
        }
    }

    public void onMove(int x, int y) {
        int TempPosition = AdapterView.INVALID_POSITION;

        if (mContext instanceof Launcher) {
            TempPosition = ((Launcher) mContext).getCurentDragPageView()
                    .pointToPosition(x, y);
        }

        if (TempPosition != AdapterView.INVALID_POSITION
                && TempPosition != dragPosition) {
            dropPosition = TempPosition;
        }

        if (dragPosition != startPosition)
            dragPosition = startPosition;

        showCurentPageMoveAnimation(RGKPreviewConfigure.curentPage);
    }

    public void onDrop(int x, int y) {
        mCurrentX = 0;
        mCurrentY = 0;
        itemView.setDrawingCacheBackgroundColor(0);
        RGKPreviewConfigure.isMove = false;

        int tempPosition = AdapterView.INVALID_POSITION;

        if (mContext instanceof Launcher) {
            tempPosition = ((Launcher) mContext).getCurentDragPageView()
                    .pointToPosition(x, y);
        }

        if (tempPosition != AdapterView.INVALID_POSITION) {
            dropPosition = tempPosition;
        }

        if (movePageNum != 0) {
            if (mContext instanceof Launcher) {
                ((Launcher) mContext).getCurentDragPageView()
                        .getOnGridItemChangeListener()
                        .change(startPosition, dropPosition, movePageNum);
            }

            RGKPreviewConfigure.endDragItemIndex = dropPosition
                    + RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE
                    * RGKPreviewConfigure.curentPage;
            // ((LauncherApplication) mContext.getApplicationContext())
            // .getLauncherProvider().changeSceenPosition(
            // RGKPreviewConfigure.startDragItemIndex,
            // RGKPreviewConfigure.endDragItemIndex);
            mContext.sendBroadcast(new Intent("com.launcher.REFLUSH_WORKSPACE"));
            movePageNum = 0;
            RGKPreviewConfigure.moveNum = movePageNum;
            return;
        }

        movePageNum = 0;
        RGKPreviewConfigure.moveNum = movePageNum;
        RGKPreviewAdapter adapterTemp = null;
        if (mContext instanceof Launcher) {
            adapterTemp = (RGKPreviewAdapter) ((Launcher) mContext)
                    .getCurentDragPageView().getAdapter();
        }
        final RGKPreviewAdapter adapter = adapterTemp;

        adapter.exchange(startPosition, dropPosition);

        RGKPreviewConfigure.endDragItemIndex = dropPosition
                + RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE
                * RGKPreviewConfigure.curentPage;
        LauncherAppState.getLauncherProvider().changeScreenRank(RGKPreviewConfigure.startDragItemIndex, RGKPreviewConfigure.endDragItemIndex);
        LauncherAppState.getInstance().getModel().forceReload();
        // ((LauncherApplication) mContext.getApplicationContext())
        // .getLauncherProvider().changeSceenPosition(
        // RGKPreviewConfigure.startDragItemIndex,
        // RGKPreviewConfigure.endDragItemIndex);
        mContext.sendBroadcast(new Intent("com.launcher.REFLUSH_WORKSPACE"));
        startPosition = dropPosition;
        adapter.showDropItem(true);
        adapter.resetParameter();
        adapter.notifyDataSetChanged();

        ((RGKPreviewAdapter) ((Launcher) mContext).getDragPageView(
                RGKPreviewConfigure.countPages - 1).getAdapter())
                .resetParameter();

        mLastX = 0;
        mLastY = 0;
        holdPosition = 0;
        LastAnimationID = null;
        isCountXY = false;
    }

    public void cancelDrag() {
        isActive = false;
        stopDrag();
        onDrop(mCurrentX, mCurrentY);
        if (RGKPreviewConfigure.isHideButton) {
            ((Launcher) mContext).showNewScreenAddButton(true);
        }
        RGKPreviewConfigure.isHideButton = false;
    }

    public void resetMoveParameter() {
        if (isPrevious) {
            dropPosition = RGKPreviewConfigure.curentPage
                    * RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE
                    + RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE - 1;
            startPosition += (RGKPreviewConfigure.curentPage + 1)
                    * RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE;
        } else {
            dropPosition = RGKPreviewConfigure.curentPage
                    * RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE;
            startPosition += (RGKPreviewConfigure.curentPage - 1)
                    * RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE;
        }

        isActive = true;
        RGKPreviewConfigure.isMove = true;

        if (isPrevious) {
            startPosition = dropPosition = RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE - 1;
        } else {
            startPosition = dropPosition = 0;
        }

        int rows;
        itemTotalCount = ((Launcher) mContext).getCurentDragPageView()
                .getCount();

        rows = itemTotalCount / nColumns;
        Remainder = itemTotalCount % nColumns;
        nRows = Remainder == 0 ? rows : rows + 1;
        specialPosition = itemTotalCount - 1 - Remainder;
        if (Remainder != 1)
            leftBottomPosition = nColumns * (nRows - 1);
        if (Remainder == 0 || nRows == 1)
            specialPosition = -1;

        if (specialPosition != dragPosition && dragPosition != -1
                && specialPosition != -1) {
            specialItemY = (nRows - 1) * itemView.getHeight();
            specialItemX = (nColumns - 1) * itemView.getHeight();
        } else {
            specialItemY = -1;
            specialItemX = -1;
        }

        if (leftBottomPosition != dragPosition && dragPosition != -1
                && leftBottomPosition != -1) {
            leftBtmItemY = (nRows - 1) * itemView.getHeight();
            leftBtmItemX = (nColumns - 1) * itemView.getHeight();
        } else {
            leftBtmItemY = -1;
            leftBtmItemX = -1;
        }

        hideDropItem();

        if (isPrevious) {
            int position = RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE - 1;
            ((Launcher) mContext).getCurentDragPageView().getChildAt(position)
                    .setVisibility(View.INVISIBLE);
        } else {
            ((Launcher) mContext).getCurentDragPageView().getChildAt(0)
                    .setVisibility(View.INVISIBLE);
        }
        isMoving = false;
    }

    public void nextPageMoveAnimation() {
        int endIndex = (RGKPreviewConfigure.curentPage + 1)
                * RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE;
        int startIndex = dragPosition + RGKPreviewConfigure.curentPage
                * RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE;
        dropPosition = RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE - 1;
        ((Launcher) mContext).swapListData(startIndex, endIndex);
    }

    public void previousPageMoveAnimation() {
        int endIndex = RGKPreviewConfigure.curentPage
                * RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE - 1;
        int startIndex = dragPosition + RGKPreviewConfigure.curentPage
                * RGKPreviewConfigure.WORKSPACE_PREVIEW_PAGE_SIZE;
        dropPosition = 0;
        ((Launcher) mContext).swapListData(startIndex, endIndex);
    }

    public void showCurentPageMoveAnimation(int index) {
        int MoveNum = dropPosition - dragPosition;
        if (dragPosition != startPosition && dragPosition == dropPosition)
            MoveNum = 0;

        if (MoveNum != 0) {
            int itemMoveNum = Math.abs(MoveNum);
            float Xoffset, Yoffset;
            for (int i = 0; i < itemMoveNum; i++) {
                if (MoveNum > 0) {
                    holdPosition = dragPosition + 1;
                    Xoffset = (dragPosition / nColumns == holdPosition
                            / nColumns) ? (-1) : (nColumns - 1);
                    Yoffset = (dragPosition / nColumns == holdPosition
                            / nColumns) ? 0 : (-1);
                } else {
                    holdPosition = dragPosition - 1;
                    Xoffset = (dragPosition / nColumns == holdPosition
                            / nColumns) ? 1 : (-(nColumns - 1));
                    Yoffset = (dragPosition / nColumns == holdPosition
                            / nColumns) ? 0 : 1;
                }

                ViewGroup moveView = null;
                if (mContext instanceof Launcher) {
                    moveView = (ViewGroup) ((Launcher) mContext)
                            .getDragPageView(index).getChildAt(
                                    holdPosition
                                            - ((Launcher) mContext)
                                                    .getDragPageView(index)
                                                    .getFirstVisiblePosition());
                }

                if (moveView != null) {
                    Animation animation = getMoveAnimation(Xoffset, Yoffset);
                    moveView.startAnimation(animation);
                    b_mask = true;
                    dragPosition = holdPosition;

                    if (dragPosition == dropPosition)
                        LastAnimationID = animation.toString();

                    RGKPreviewAdapter adapterTemp = null;
                    if (mContext instanceof Launcher) {
                        adapterTemp = (RGKPreviewAdapter) ((Launcher) mContext)
                                .getDragPageView(index).getAdapter();
                    }

                    final RGKPreviewAdapter adapter = adapterTemp;

                    animation
                            .setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    isMoving = true;
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    String animaionID = animation.toString();
                                    if (animaionID.equalsIgnoreCase(LastAnimationID)) {
                                        adapter.exchange(startPosition, dropPosition);
                                        startPosition = dropPosition;
                                        isMoving = false;
                                    }
                                    b_mask = false;
                                }
                            });
                } else {
                    RGKPreviewAdapter adapterTemp = null;
                    if (mContext instanceof Launcher) {
                        adapterTemp = (RGKPreviewAdapter) ((Launcher) mContext)
                                .getDragPageView(index).getAdapter();
                    }

                    final RGKPreviewAdapter adapter = adapterTemp;
                    adapter.exchange(startPosition, dropPosition);
                    startPosition = dropPosition;
                    isMoving = false;
                    break;
                }

            }
        }
    }

    public Animation getMoveAnimation(float x, float y) {
        TranslateAnimation go = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, x,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, y);
        go.setFillAfter(true);
        go.setDuration(300);
        return go;
    }
}
