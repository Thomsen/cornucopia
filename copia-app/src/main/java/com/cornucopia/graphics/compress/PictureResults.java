package com.cornucopia.graphics.compress;

import android.graphics.Bitmap;

public class PictureResults {

    String filePath;
    int height;
    int width;

    Bitmap color;
    Bitmap results;

    public PictureResults(String fp) {
        this.filePath = fp;

    }
    public void setBitmap(Bitmap bm){
        color= bm;
    }
    public Bitmap getBitmap(){
        return color;
    }
    public void  setHeight(int h){
        height = h;
    }

    public int getHeight(){
        return height;
    }
    public void  setWidth(int w){
        width = w;
    }
    public int getWidth(){
        return width;
    }

}
