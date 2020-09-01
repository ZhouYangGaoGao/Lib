package com.zhy.wlthd.bean;

import com.orient.me.data.table.ICellItem;

public class TableCell implements ICellItem {

    private int type;
    private int index;
    private int row;
    private int col;
    private int widthSpan;
    private int heightSpan;

    public TableCell(  int index , int type, int row, int col, int rowSpan, int colSpan) {
        this.type = type;
        this.row = row;
        this.col = col;
        this.widthSpan = rowSpan;
        this.heightSpan = colSpan;
        this.index = index;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setWidthSpan(int widthSpan) {
        this.widthSpan = widthSpan;
    }

    public void setHeightSpan(int heightSpan) {
        this.heightSpan = heightSpan;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public int getWidthSpan() {
        return widthSpan;
    }

    @Override
    public int getHeightSpan() {
        return heightSpan;
    }
}
