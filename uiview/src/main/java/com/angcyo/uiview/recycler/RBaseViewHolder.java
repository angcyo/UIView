package com.angcyo.uiview.recycler;

/**
 * Created by angcyo on 2016-01-30.
 */

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.angcyo.library.facebook.DraweeViewUtil;
import com.angcyo.library.utils.L;
import com.angcyo.uiview.R;
import com.angcyo.uiview.RApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 通用ViewHolder
 */
public class RBaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> sparseArray;
    private int viewType = -1;

    public RBaseViewHolder(View itemView) {
        super(itemView);
        sparseArray = new SparseArray();
    }

    public RBaseViewHolder(View itemView, int viewType) {
        super(itemView);
        sparseArray = new SparseArray();
        this.viewType = viewType;
    }

    /**
     * 填充两个字段相同的数据对象
     */
    public static void fill(Object from, Object to) {
        Field[] fromFields = from.getClass().getDeclaredFields();
        Field[] toFields = to.getClass().getDeclaredFields();
        for (Field f : fromFields) {
            String name = f.getName();
            for (Field t : toFields) {
                String tName = t.getName();
                if (name.equalsIgnoreCase(tName)) {
                    try {
                        f.setAccessible(true);
                        t.setAccessible(true);
                        t.set(to, f.get(from));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

        }
    }

    /**
     * 从object对象中, 获取字段name的get方法
     */
    public static Method getMethod(Object object, String name) {
        Method result = null;
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            String methodName = method.getName();
//            L.e("方法名:" + methodName);
            if (methodName.equalsIgnoreCase("realmGet$" + name)) {
                //优先从realm中获取方法名
//                L.e("方法名匹配到:" + methodName);
                result = method;
                break;
            } else if (methodName.equalsIgnoreCase("get" + name)) {
//                L.e("方法名匹配到:" + methodName);
                result = method;
                break;
            }
        }
        return result;
    }

    public int getViewType() {
        return viewType == -1 ? super.getItemViewType() : viewType;
    }

    public <T extends View> T v(@IdRes int resId) {
        View view = sparseArray.get(resId);
        if (view == null) {
            view = itemView.findViewById(resId);
            sparseArray.put(resId, view);
        }
        return (T) view;
    }

    public <T extends View> T tag(String tag) {
        View view = itemView.findViewWithTag(tag);
        return (T) view;
    }

    public <T extends View> T v(String idName) {
        return (T) viewByName(idName);
    }

    public View view(@IdRes int resId) {
        return v(resId);
    }

    public RRecyclerView reV(@IdRes int resId) {
        return (RRecyclerView) v(resId);
    }

    public RRecyclerView reV(String idName) {
        return (RRecyclerView) viewByName(idName);
    }

    /**
     * 返回 TextView
     */
    public TextView tV(@IdRes int resId) {
        return (TextView) v(resId);
    }

    public TextView tv(@IdRes int resId) {
        return (TextView) v(resId);
    }

    public TextView tV(String idName) {
        return (TextView) v(idName);
    }

    public TextView textView(@IdRes int resId) {
        return tV(resId);
    }

    /**
     * 返回 CompoundButton
     */
    public CompoundButton cV(@IdRes int resId) {
        return (CompoundButton) v(resId);
    }

    public CompoundButton cV(String idName) {
        return (CompoundButton) v(idName);
    }

    /**
     * 返回 EditText
     */
    public EditText eV(@IdRes int resId) {
        return (EditText) v(resId);
    }

    public EditText editView(@IdRes int resId) {
        return eV(resId);
    }

    /**
     * 返回 ImageView
     */
    public ImageView imgV(@IdRes int resId) {
        return (ImageView) v(resId);
    }

    public ImageView imageView(@IdRes int resId) {
        return imgV(resId);
    }

    /**
     * 返回 ViewGroup
     */
    public ViewGroup groupV(@IdRes int resId) {
        return (ViewGroup) v(resId);
    }

    public ViewGroup viewGroup(@IdRes int resId) {
        return groupV(resId);
    }

    public RecyclerView r(@IdRes int resId) {
        return (RecyclerView) v(resId);
    }

    public View viewByName(String name) {
        View view = v(getIdByName(name, "id"));
        return view;
    }

    /**
     * 根据name, 在主题中 寻找资源id
     */
    private int getIdByName(String name, String type) {
        Context context = itemView.getContext();
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }

    public void fillView(Object bean) {
        fillView(bean, false);
    }

    public void fillView(Object bean, boolean hideForEmpty) {
        fillView(bean, hideForEmpty, false);
    }

    public void fillView(Object bean, boolean hideForEmpty, boolean withGetMethod) {
        fillView(null, bean, hideForEmpty, withGetMethod);
    }

    /**
     * @param clz           为了效率, 并不会遍历父类的字段, 所以可以指定类
     * @param hideForEmpty  如果数据为空时, 是否隐藏View
     * @param withGetMethod 是否通过get方法获取对象字段的值
     */
    public void fillView(Class<?> clz, Object bean, boolean hideForEmpty, boolean withGetMethod) {
        if (bean == null) {
            return;
        }
        Field[] fields;
        if (clz == null) {
            fields = bean.getClass().getDeclaredFields();
        } else {
            fields = clz.getDeclaredFields();
        }
        for (Field f : fields) {
            f.setAccessible(true);
            String name = f.getName();
            try {
                View view = viewByName(name);
                if (view == null) {
                    view = viewByName(name + "_view");
                }

                if (view != null) {
                    String value = null;
                    if (withGetMethod) {
                        value = String.valueOf(getMethod(bean, name).invoke(bean));
                    } else {
                        try {
                            value = f.get(bean).toString();
                        } catch (Exception e) {
                            L.w("the clz=" + clz + " bean=" + bean.getClass().getSimpleName() + " field=" + name + " is null");
                        }
                    }
                    if (view instanceof TextView) {
                        if (TextUtils.isEmpty(value) && hideForEmpty) {
                            view.setVisibility(View.GONE);
                        } else {
                            view.setVisibility(View.VISIBLE);
                        }
                        ((TextView) view).setText(value);
                    } else if (view instanceof SimpleDraweeView) {
                        DraweeViewUtil.resize(((SimpleDraweeView) view), value,
                                view.getMeasuredWidth(), view.getMeasuredHeight());
                    } else if (view instanceof ImageView) {
                        Glide.with(RApplication.getApp())
                                .load(value)
                                .placeholder(R.drawable.default_image)
                                .error(R.drawable.default_image)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                                .into(((ImageView) view));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void post(Runnable runnable) {
        itemView.post(runnable);
    }

    public void postDelay(Runnable runnable, long delayMillis) {
        itemView.postDelayed(runnable, delayMillis);
    }
}
