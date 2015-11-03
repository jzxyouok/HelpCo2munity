package com.lvyerose.helpcommunity.found;

/**
 * author: lvyeRose
 * objective:
 * mailbox: lvyerose@163.com
 * time: 15/11/3 14:27
 */
public class BeanFriendData {
    private int id;
    private String type;
    private String name;
    private String icon;
    private String dec;
    private String address;
    private String time;
    private String content;
    private boolean isCollect;
    private boolean isFavour;
    private int commnunity;
    private int favour;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }

    public boolean isFavour() {
        return isFavour;
    }

    public void setIsFavour(boolean isFavour) {
        this.isFavour = isFavour;
    }

    public int getCommnunity() {
        return commnunity;
    }

    public void setCommnunity(int commnunity) {
        this.commnunity = commnunity;
    }

    public int getFavour() {
        return favour;
    }

    public void setFavour(int favour) {
        this.favour = favour;
    }
}
