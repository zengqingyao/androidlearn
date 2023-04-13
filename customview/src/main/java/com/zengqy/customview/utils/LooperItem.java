package com.zengqy.customview.utils;

/**
 * @包名: com.zengqy.customview.utils
 * @USER: zengqy
 * @DATE: 2022/4/25 16:52
 * @描述:
 */
public class LooperItem {

    private String Title;

    private int ImgRsId;

    public LooperItem(String title, int imgRsId) {
        Title = title;
        ImgRsId = imgRsId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getImgRsId() {
        return ImgRsId;
    }

    public void setImgRsId(int imgRsId) {
        ImgRsId = imgRsId;
    }
}
