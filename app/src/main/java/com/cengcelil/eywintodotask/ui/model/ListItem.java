package com.cengcelil.eywintodotask.ui.model;

public abstract class ListItem {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_BODY_ACTIVE = 1;
    public static final int TYPE_BODY_DONE = 2;
    abstract public int getType();
}