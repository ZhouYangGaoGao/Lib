package listener;

import android.content.Intent;

public interface OnResultListener {
    void onResult(int requestCode, int resultCode, Intent data);
}
