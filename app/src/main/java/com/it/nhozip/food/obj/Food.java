package com.it.nhozip.food.obj;
import java.io.Serializable;

/**
 * Created by huyen on 9/13/2016.
 */
public class Food  implements Serializable {
    private  String name;

    private  String img;

    private  String nguyenlieu;

    private  String video;

    private  String huongdan;

    public Food() {
    }

    public Food(String name, String img, String nguyenlieu, String video, String huongdan) {
        this.name = name;
        this.img = img;
        this.nguyenlieu = nguyenlieu;
        this.video = video;
        this.huongdan = huongdan;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", nguyenlieu='" + nguyenlieu + '\'' +
                ", video='" + video + '\'' +
                ", huongdan='" + huongdan + '\'' +
                '}';
    }
    public String getName() {
        return name;
    }
    public String getImg() {
        return img;
    }
    public String getNguyenlieu() {
        return nguyenlieu;
    }
    public String getHuongdan() {
        return huongdan;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setNguyenlieu(String nguyenlieu) {
        this.nguyenlieu = nguyenlieu;
    }

    public void setHuongdan(String huongdan) {
        this.huongdan = huongdan;
    }
}
