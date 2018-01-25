package com.gugu.biom.LikeListView;

import android.graphics.drawable.Drawable;

/**
 * Created by gugu on 2018-01-26.
 */

public class SnsListViewItem {

    private Drawable ivSnsPhoto;
    private String tvSnsContent;

    public Drawable getIvSnsPhoto() {
        return ivSnsPhoto;
    }

    public void setIvSnsPhoto(Drawable ivSnsPhoto) {
        this.ivSnsPhoto = ivSnsPhoto;
    }

    public String getTvSnsContent() {
        return tvSnsContent;
    }

    public void setTvSnsContent(String tvSnsContent) {
        this.tvSnsContent = tvSnsContent;
    }
}
