package base;

public interface BBean<T> {
    void setOnNext(BSub<? extends BBean<T>,T> sub);
}
