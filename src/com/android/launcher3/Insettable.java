/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.launcher3;

import android.graphics.Rect;

public interface Insettable {

    /**
     * 这个方法的作用是将view的高度去掉状态栏和导航栏
     * 把子类复写改方法给注释掉的话那么这个子类的view就全屏了包括状态栏导航栏的高度。
     * 这个方法的调用在DragLayer.java里重写的addView方法里调用的。
     * @param insets
     */
    void setInsets(Rect insets);
}