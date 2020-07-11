package base;

import java.util.List;

public class MList<T> extends BList<T> {
    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    private List<T> datas;

    @Override
    public List<T> getList() {
        return getDatas();
    }
}
