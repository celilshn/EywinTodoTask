package com.cengcelil.eywintodotask.ui.model;

public class HeaderItem extends ListItem {
    private static final String ACTIVE = "ACTIVE";
    private static final String DONE = "DONE";
    private String headerText;

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(Boolean active) {
        if (active)
            this.headerText = ACTIVE;
        else
            this.headerText = DONE;

    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }

}
