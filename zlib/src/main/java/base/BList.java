package base;

import java.util.List;

public class BList<T> {

    /**
     * total : 0
     * list : []
     */

    private int total;
    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ListModel{" +
                "total='" + total + '\'' +
                ", list=" + list +
                '}';
    }
}