package com.swufe.sugar;

public class MovieItem {
    private int id;
    private String curName;
    private String curScore;

    public MovieItem() {
        super();
        curName = "";
        curScore = "";
    }
    public MovieItem(String curName, String curScore) {
        super();
        this.curName = curName;
        this.curScore = curScore;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCurName() {
        return curName;
    }
    public void setCurName(String curName) {
        this.curName = curName;
    }
    public String getCurRate() {
        return curScore;
    }
    public void setCurRate(String curScore) {
        this.curScore = curScore;
    }
}
