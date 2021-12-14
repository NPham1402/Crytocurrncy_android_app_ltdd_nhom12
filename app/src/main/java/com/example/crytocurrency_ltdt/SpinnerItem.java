package com.example.crytocurrency_ltdt;

public class SpinnerItem {

    private final String text;
    private final boolean isHint;

    public SpinnerItem(String strItem, boolean flag) {
        this.isHint = flag;
        this.text = strItem;
    }

    public String getItemString() {
        return text;
    }

    public boolean isHint() {
        return isHint;
    }
}
