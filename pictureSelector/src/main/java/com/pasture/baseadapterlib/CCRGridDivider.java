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

package com.pasture.baseadapterlib;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:17/1/9 下午11:12
 * 描述:
 */
public class CCRGridDivider extends RecyclerView.ItemDecoration {
    private int mSpace;

    private CCRGridDivider(int space) {
        mSpace = space;
    }

    /**
     * 设置间距资源 id
     *
     * @param resId
     * @return
     */
    public static CCRGridDivider newInstanceWithSpaceRes(@DimenRes int resId) {
        return new CCRGridDivider(CCRBaseAdapterUtil.getDimensionPixelOffset(resId));
    }

    /**
     * 设置间距
     *
     * @param spaceDp 单位为 dp
     * @return
     */
    public static CCRGridDivider newInstanceWithSpaceDp(int spaceDp) {
        return new CCRGridDivider(CCRBaseAdapterUtil.dp2px(spaceDp));
    }

    /**
     * 设置间距
     *
     * @param spacePx 单位为 px
     * @return
     */
    public static CCRGridDivider newInstanceWithSpacePx(int spacePx) {
        return new CCRGridDivider(spacePx);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.top = mSpace;
        outRect.bottom = mSpace;
    }
}