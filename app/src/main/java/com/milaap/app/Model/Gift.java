package com.milaap.app.Model;

public class Gift {
    String img;
    String count;

    public Gift(String img, String count) {
        this.img = img;
        this.count = count;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Gift{" +
                "img=" + img +
                ", count='" + count + '\'' +
                '}';
    }
}
