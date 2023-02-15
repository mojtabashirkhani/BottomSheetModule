package com.slimshady.bottomsheetmodule.model;


public class BaseBottomSheetRecyclerModel {

    private String name;
    private String type;
    private boolean isSelected = false;

    public BaseBottomSheetRecyclerModel(String name, String type, boolean isSelected) {
        this.name = name;
        this.type = type;
        this.isSelected = isSelected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}