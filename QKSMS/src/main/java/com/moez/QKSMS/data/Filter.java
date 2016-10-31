package com.moez.QKSMS.data;

/**
 * Created by zhangqian on 2016/10/30.
 * a filter .
 */
public class Filter {
    private int id;
    private int filterType;
    private String name;

    public Filter() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
