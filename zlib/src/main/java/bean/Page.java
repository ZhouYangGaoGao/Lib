package bean;

public class Page {
    private int startPage = 1;//起始页码
    public int page = startPage;//页码
    public int pageSize = 10;//每页数量

    public void setStartPage(int startPage) {
        this.startPage = startPage;
        this.page = startPage;
    }

    public void resetPage() {
        this.page = startPage;
    }
}
