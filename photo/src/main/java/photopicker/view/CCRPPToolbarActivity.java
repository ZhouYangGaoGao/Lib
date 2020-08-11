/**
 * Copyright 2016 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package photopicker.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ViewStubCompat;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.zhy.photo.R;


/**
 * @author Acheng
 * @Created on 2020/5/16.
 * @Email 345887272@qq.com
 * @Description 说明:状态栏
 */
public abstract class CCRPPToolbarActivity extends AppCompatActivity {
    protected String TAG;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();

        initView(savedInstanceState);
        setListener();
        processLogic(savedInstanceState);
    }

    /**
     * 初始化布局以及View控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 给View控件添加事件监听器
     */
    protected abstract void setListener();

    /**
     * 处理业务逻辑，状态恢复等操作
     *
     * @param savedInstanceState
     */
    protected abstract void processLogic(Bundle savedInstanceState);

    @SuppressLint("RestrictedApi")
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.bga_pp_toolbar_viewstub);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewStubCompat viewStub = findViewById(R.id.viewStub);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) viewStub.getLayoutParams();
        lp.addRule(RelativeLayout.BELOW, R.id.toolbar);

        viewStub.setLayoutResource(layoutResID);
        viewStub.inflate();
    }

    @SuppressLint("RestrictedApi")
    public void setNoLinearContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.bga_pp_toolbar_viewstub);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewStubCompat viewStub = findViewById(R.id.viewStub);
        viewStub.setLayoutResource(layoutResID);
        viewStub.inflate();
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}