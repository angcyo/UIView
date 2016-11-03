package com.angcyo.demo;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStructure;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.widget.TextView;

import com.angcyo.library.utils.L;

/**
 * Created by angcyo on 2016-11-03.
 */

public class TestView extends TextView {
    public TestView(Context context) {
        super(context);
        L.i("TestView: ");
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        L.i("TestView: ");
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        L.i("onConfigurationChanged: ");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        L.i("onSaveInstanceState: ");
        return super.onSaveInstanceState();
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        L.i("onRestoreInstanceState: ");
        super.onRestoreInstanceState(state);
    }

    @Override
    public void onEditorAction(int actionCode) {
        L.i("onEditorAction: ");
        super.onEditorAction(actionCode);
    }

    @Override
    public boolean onPreDraw() {
        L.i("onPreDraw: ");
        return super.onPreDraw();
    }

    @Override
    protected void onAttachedToWindow() {
        L.i("onAttachedToWindow: ");
        super.onAttachedToWindow();
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        L.i("onScreenStateChanged: ");
        super.onScreenStateChanged(screenState);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        L.i("onCreateDrawableState: ");
        return super.onCreateDrawableState(extraSpace);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        L.i("onDraw: ");
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        L.i("onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        L.i("onLayout: ");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void onBeginBatchEdit() {
        L.i("onBeginBatchEdit: ");
        super.onBeginBatchEdit();
    }

    @Override
    public void onEndBatchEdit() {
        L.i("onEndBatchEdit: ");
        super.onEndBatchEdit();
    }

    @Override
    public boolean onPrivateIMECommand(String action, Bundle data) {
        L.i("onPrivateIMECommand: ");
        return super.onPrivateIMECommand(action, data);
    }

    @Override
    public void onCommitCompletion(CompletionInfo text) {
        L.i("onCommitCompletion: ");
        super.onCommitCompletion(text);
    }

    @Override
    public void onCommitCorrection(CorrectionInfo info) {
        L.i("onCommitCorrection: ");
        super.onCommitCorrection(info);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        L.i("onKeyUp: ");
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.i("onKeyDown: ");
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        L.i("onTextChanged: ");
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        L.i("onSelectionChanged: ");
        super.onSelectionChanged(selStart, selEnd);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        L.i("onFocusChanged: ");
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        L.i("onWindowFocusChanged: ");
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        L.i("onVisibilityChanged: ");
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.i("onTouchEvent: ");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        L.i("onGenericMotionEvent: ");
        return super.onGenericMotionEvent(event);
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        L.i("onCreateContextMenu: ");
        super.onCreateContextMenu(menu);
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        L.i("onTrackballEvent: ");
        return super.onTrackballEvent(event);
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        L.i("onKeyShortcut: ");
        return super.onKeyShortcut(keyCode, event);
    }

    @Override
    public void onProvideStructure(ViewStructure structure) {
        L.i("onProvideStructure: ");
        super.onProvideStructure(structure);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        L.i("onTextContextMenuItem: ");
        return super.onTextContextMenuItem(id);
    }

    @Override
    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        L.i("onScrollChanged: ");
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        L.i("onDragEvent: ");
        return super.onDragEvent(event);
    }

    @Override
    protected void onDisplayHint(int hint) {
        L.i("onDisplayHint: ");
        super.onDisplayHint(hint);
    }

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        L.i("onVisibilityAggregated: ");
        super.onVisibilityAggregated(isVisible);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        L.i("onWindowVisibilityChanged: ");
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        L.i("onHoverEvent: ");
        return super.onHoverEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        L.i("onSizeChanged: ");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDetachedFromWindow() {
        L.i("onDetachedFromWindow: ");
        super.onDetachedFromWindow();
    }

    @Override
    public void onCancelPendingInputEvents() {
        L.i("onCancelPendingInputEvents: ");
        super.onCancelPendingInputEvents();
    }

    @Override
    protected void onFinishInflate() {
        L.i("onFinishInflate: ");
        super.onFinishInflate();
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        L.i("onDrawForeground: ");
        super.onDrawForeground(canvas);
    }

    @Override
    protected void onAnimationStart() {
        L.i("onAnimationStart: ");
        super.onAnimationStart();
    }

    @Override
    protected void onAnimationEnd() {
        L.i("onAnimationEnd: ");
        super.onAnimationEnd();
    }
}
