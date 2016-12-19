package com.angcyo.uiview.recycler;

import android.content.Context;
import android.support.annotation.IntDef;
import android.widget.CompoundButton;

import com.angcyo.uiview.utils.Reflect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * 支持单选, 多选的Adapter
 * <p>
 * Created by angcyo on 2016-12-19.
 */

public abstract class RModelAdapter<T> extends RBaseAdapter<T> {
    /**
     * 正常
     */
    public static final int MODEL_NORMAL = 1;
    /**
     * 单选
     */
    public static final int MODEL_SINGLE = MODEL_NORMAL << 1;
    /**
     * 多选
     */
    public static final int MODEL_MULTI = MODEL_SINGLE << 1;
    private HashSet<Integer> mSelector = new HashSet<>();
    @Model
    private int mModel = MODEL_NORMAL;

    public RModelAdapter(Context context) {
        super(context);
    }

    public RModelAdapter(Context context, List<T> datas) {
        super(context, datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return 0;
    }

    @Override
    protected void onBindView(RBaseViewHolder holder, int position, T bean) {
        onBindCommonView(holder, position, bean);
        if (mModel == MODEL_NORMAL) {
            onBindNormalView(holder, position, bean);
        } else {
            onBindModelView(mModel, isPositionSelector(position), holder, position, bean);
        }
    }

    /**
     * 公共布局部分
     */
    protected abstract void onBindCommonView(RBaseViewHolder holder, int position, T bean);

    protected abstract void onBindModelView(int model, boolean isSelector, RBaseViewHolder holder, int position, T bean);

    protected abstract void onBindNormalView(RBaseViewHolder holder, int position, T bean);

    protected void onUnselectorPosition(List<Integer> list) {

    }

    /**
     * 互斥的操作
     */
    public void setSelectorPosition(int position) {
        setSelectorPosition(position, null);
    }

    public HashSet<Integer> getAllSelector() {
        return mSelector;
    }

    public List<Integer> getAllSelectorList() {
        List<Integer> integers = new ArrayList<>();
        final Iterator<Integer> iterator = mSelector.iterator();
        while (iterator.hasNext()) {
            integers.add(iterator.next());
        }
        return integers;
    }

    public void setSelectorPosition(int position, CompoundButton compoundButton) {
        final boolean selector = isPositionSelector(position);

        if (selector) {
            if (mModel == MODEL_SINGLE) {
                return;
            } else {
                mSelector.remove(position);
            }
        } else {
            if (mModel == MODEL_SINGLE) {
                onUnselectorPosition(getAllSelectorList());
                mSelector.clear();
            }
            mSelector.add(position);
        }

        if (selector) {
            checkButton(compoundButton, false);
        } else {
            checkButton(compoundButton, true);
        }
    }

    private void checkButton(CompoundButton compoundButton, boolean checked) {
        if (compoundButton == null) {
            return;
        }
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener =
                (CompoundButton.OnCheckedChangeListener) Reflect.getMember(CompoundButton.class,
                        compoundButton, "mOnCheckedChangeListener");
        compoundButton.setOnCheckedChangeListener(null);
        compoundButton.setChecked(checked);
        compoundButton.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public boolean isPositionSelector(int position) {
        return mSelector.contains(position);
    }

    public int getModel() {
        return mModel;
    }

    /**
     * 设置模式,
     *
     * @param model 单选,多选, 正常
     */
    public void setModel(@Model int model) {
        if (mModel != model) {
            mModel = model;
            if (mModel != MODEL_NORMAL) {
                setEnableLoadMore(false);
                mSelector.clear();
            }
            notifyDataSetChanged();
        }
    }

    @IntDef({MODEL_NORMAL, MODEL_SINGLE, MODEL_MULTI})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Model {
    }
}
