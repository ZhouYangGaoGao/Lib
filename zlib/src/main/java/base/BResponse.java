package base;

public abstract class BResponse<T> {
    /**
     *
     * @param sub
     * @return 是否已定义处理数据结果
     */
   protected boolean setOnNext(BSub<? extends BResponse<T>, T> sub) {
        return false;
    }
}
