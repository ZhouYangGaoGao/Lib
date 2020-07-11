package base;

public abstract class BResponse<T> {
//    T data;
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }

    /**
     *
     * @param sub
     * @return 是否已定义处理数据结果
     */
   protected boolean setOnNext(BSub<? extends BResponse<T>, T> sub) {
        return false;
    }
}
