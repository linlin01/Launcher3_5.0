package com.android.launcher3.preview;

import android.graphics.Bitmap;

public class RGKPreviewItemInfo {
    int index;
    boolean isHomePage;
    boolean isCurrentPage;
    boolean isEmptyPage;
    Bitmap image;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isHomePage() {
        return isHomePage;
    }

    public void setHomePage(boolean isHomePage) {
        this.isHomePage = isHomePage;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public boolean isCurrentPage() {
        return isCurrentPage;
    }

    public void setCurrentPage(boolean isCurrentPage) {
        this.isCurrentPage = isCurrentPage;
    }

    public boolean isEmptyPage() {
        return isEmptyPage;
    }

    public void setEmptyPage(boolean isEmptyPage) {
        this.isEmptyPage = isEmptyPage;
    }

}
