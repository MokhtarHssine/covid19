package com.projet.dsi32g5_covid19.center;

public class RowItem {

    private String title;
    private String local;

    public RowItem(String title, String local) {
        this.title = title;
        this.local = local;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

}
