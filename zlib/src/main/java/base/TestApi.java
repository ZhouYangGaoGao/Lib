package base;

import retrofit2.http.GET;
import rx.Observable;

public interface TestApi {
    @GET(BConfig.TEST_URL)
    Observable<Object> test();
}
