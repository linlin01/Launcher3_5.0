package com.android.launcher3.preview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

public class RGKDragGridView extends GridView {
    
    private GridPageListener pageListener;
    private GridItemChangeListener itemListener;
    protected ViewGroup itemView;
    
    public RGKDragGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RGKDragGridView(Context context) {
        super(context);
    }

    public void setGridPageListener(GridPageListener pageListener) {
        this.pageListener = pageListener;
    }
    
    public GridPageListener getGridPageListener() {
        return pageListener;
    }
    
    public interface GridPageListener {
        void page(int page);
    }

    public void setOnGridItemChangeListener(GridItemChangeListener itemListener) {
        this.itemListener = itemListener;
    }
    
    public GridItemChangeListener getOnGridItemChangeListener() {
        return itemListener;
    }

    public interface GridItemChangeListener {
        void change(int from, int to, int count);
    }
}
