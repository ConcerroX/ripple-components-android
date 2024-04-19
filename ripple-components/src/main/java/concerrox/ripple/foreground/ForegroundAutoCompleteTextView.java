/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package concerrox.ripple.foreground;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

public class ForegroundAutoCompleteTextView extends AppCompatAutoCompleteTextView implements ForegroundCompatView {
    private final ForegroundHelper foregroundHelper = new ForegroundHelper(this);

    public ForegroundAutoCompleteTextView(@NonNull Context context) {
        super(context);
        foregroundHelper.init(context, null, 0, 0);
    }

    public ForegroundAutoCompleteTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        foregroundHelper.init(context, attrs, 0, 0);
    }

    public ForegroundAutoCompleteTextView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        foregroundHelper.init(context, attrs, defStyleAttr, 0);
    }

    @Override
    @RequiresApi(Build.VERSION_CODES.N)
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);
        foregroundHelper.onVisibilityAggregated(isVisible);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        foregroundHelper.draw(canvas);
    }

    @Override
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        foregroundHelper.onRtlPropertiesChanged(layoutDirection);
    }

    @Override
    public boolean verifyDrawable(@NonNull Drawable who) {
        return super.verifyDrawable(who)
                // mForegroundHelper may be null during super constructor invocation.
                || (foregroundHelper != null && foregroundHelper.verifyDrawable(who));
    }

    @Override
    public void drawableStateChanged() {
        super.drawableStateChanged();
        foregroundHelper.drawableStateChanged();
    }

    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        foregroundHelper.drawableHotspotChanged(x, y);
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        foregroundHelper.jumpDrawablesToCurrentState();
    }

    @Nullable
    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public Drawable getSupportForeground() {
        return foregroundHelper.getSupportForeground();
    }

    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void setSupportForeground(@Nullable Drawable foreground) {
        foregroundHelper.setSupportForeground(foreground);
    }

    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public int getSupportForegroundGravity() {
        return foregroundHelper.getSupportForegroundGravity();
    }

    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void setSupportForegroundGravity(int gravity) {
        foregroundHelper.setSupportForegroundGravity(gravity);
    }

    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void setSupportForegroundTintList(@Nullable ColorStateList tint) {
        foregroundHelper.setSupportForegroundTintList(tint);
    }

    @Nullable
    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public ColorStateList getSupportForegroundTintList() {
        return foregroundHelper.getSupportForegroundTintList();
    }

    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void setSupportForegroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        foregroundHelper.setSupportForegroundTintMode(tintMode);
    }

    @Nullable
    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public PorterDuff.Mode getSupportForegroundTintMode() {
        return foregroundHelper.getSupportForegroundTintMode();
    }
}
