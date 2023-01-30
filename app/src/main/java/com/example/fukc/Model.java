package com.example.fukc;

public class Model {
    private int imageview1;
    private int stats;
    private int edit;
    private int delete;
    private String habitname;
    private String habitquestion;
    private String show_type;
    private String show_frequency;
    private String divider;

    public Model(int imageview1, int stats, int edit, int delete, String habitname, String habitquestion, String show_type, String show_frequency, String divider) {

        this.imageview1 = imageview1;
        this.stats = stats;
        this.edit = edit;
        this.delete = delete;
        this.habitname = habitname;
        this.habitquestion = habitquestion;
        this.show_type = show_type;
        this.show_frequency = show_frequency;
        this.divider = divider;
    }

    public int getImageview1() {
        return imageview1;
    }



    public int getImageview2() {
        return stats;
    }


    public int getImageview3() {
        return edit;
    }


    public int getImageview4() {
        return delete;
    }


    public String getTextview1() {
        return habitname;
    }



    public String getTextview2() {
        return habitquestion;
    }



    public String getTextview3() {
        return show_type;
    }



    public String getTextview4() {
        return show_frequency;
    }



    public String getDivider() {
        return divider;
    }


}
