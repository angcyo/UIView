package com.angcyo.uiview.dialog;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.uiview.R;
import com.angcyo.uiview.RApplication;
import com.angcyo.uiview.base.UIIDialogImpl;
import com.angcyo.uiview.skin.SkinHelper;

/**
 * 标准形式的对话框
 * <p>
 * Created by angcyo on 2016-11-16.
 */

public class UIDialog extends UIIDialogImpl {

    /**
     * 是否使用5.0以上的样式
     */
    boolean isX5Style = true;

    TextView mBaseDialogTitleView;
    TextView mBaseDialogContentView;
    TextView mBaseDialogCancelView;
    TextView mBaseDialogOkView;
    LinearLayout mBaseDialogRootLayout;
    View mLineLayout;

    /**
     * 对话框标题和内容,为空不显示
     */
    String dialogTitle, dialogContent;

    /**
     * 取消文本, 确定文本, 为空不显示
     */
    String cancelText, okText;

    /**
     * 2个监听事件
     */
    View.OnClickListener cancelListener, okListener;

    private UIDialog() {
        cancelText = RApplication.getApp().getString(R.string.base_cancel);
        okText = RApplication.getApp().getString(R.string.base_ok);
    }

    public static UIDialog build() {
        return new UIDialog();
    }

    @Override
    protected View inflateDialogView(RelativeLayout dialogRootLayout, LayoutInflater inflater) {
        setGravity(Gravity.CENTER_VERTICAL);
        if (isX5Style) {
            return inflater.inflate(R.layout.base_dialog_layout_5x, dialogRootLayout);
        } else {
            return inflater.inflate(R.layout.base_dialog_layout, dialogRootLayout);
        }
    }

    /**
     * 设置对话框的标题
     */
    public UIDialog setDialogTitle(String title) {
        this.dialogTitle = title;
        return this;
    }

    /**
     * 设置对话框显示的内容
     */
    public UIDialog setDialogContent(String content) {
        this.dialogContent = content;
        return this;
    }

    public UIDialog setOkText(String text) {
        this.okText = text;
        return this;
    }

    public UIDialog setCancelText(String text) {
        this.cancelText = text;
        return this;
    }

    public UIDialog setCancelListener(View.OnClickListener listener) {
        this.cancelListener = listener;
        return this;
    }

    public UIDialog setOkListener(View.OnClickListener listener) {
        this.okListener = listener;
        return this;
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);

        mBaseDialogTitleView = (TextView) rootView.findViewById(R.id.base_dialog_title_view);
        mBaseDialogContentView = (TextView) rootView.findViewById(R.id.base_dialog_content_view);
        mBaseDialogRootLayout = (LinearLayout) rootView.findViewById(R.id.base_dialog_root_layout);
        mBaseDialogOkView = (TextView) rootView.findViewById(R.id.base_dialog_ok_view);
        mBaseDialogCancelView = (TextView) rootView.findViewById(R.id.base_dialog_cancel_view);
        if (!isX5Style) {
            mLineLayout = rootView.findViewById(R.id.line_layout);
        }

        //默认文本设置
        //mBaseDialogOkView.setText(mActivity.getResources().getString(R.string.base_cancel));
        //mBaseDialogCancelView.setText(mActivity.getResources().getString(R.string.base_ok));

        mBaseDialogOkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okListener != null) {
                    okListener.onClick(v);
                }
                finishDialog();
            }
        });
        mBaseDialogCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelListener != null) {
                    cancelListener.onClick(v);
                }
                finishDialog();
            }
        });

        mBaseDialogTitleView.setVisibility(TextUtils.isEmpty(dialogTitle) ? View.GONE : View.VISIBLE);
        mBaseDialogContentView.setVisibility(TextUtils.isEmpty(dialogContent) ? View.GONE : View.VISIBLE);
        mBaseDialogCancelView.setVisibility(TextUtils.isEmpty(cancelText) ? View.GONE : View.VISIBLE);
        mBaseDialogOkView.setVisibility(TextUtils.isEmpty(okText) ? View.GONE : View.VISIBLE);

        mBaseDialogTitleView.setText(dialogTitle);
        mBaseDialogContentView.setText(dialogContent);
        mBaseDialogOkView.setText(okText);
        mBaseDialogCancelView.setText(cancelText);

        if (!isX5Style) {
            mLineLayout.setVisibility((TextUtils.isEmpty(dialogTitle) && TextUtils.isEmpty(dialogContent)) ? View.GONE : View.VISIBLE);

            if (TextUtils.isEmpty(okText)) {
                mBaseDialogCancelView.setBackgroundResource(R.drawable.base_bottom_round_bg_selector);
            } else if (TextUtils.isEmpty(cancelText)) {
                mBaseDialogOkView.setBackgroundResource(R.drawable.base_bottom_round_bg_selector);
            }
        }

        mDialogRootLayout.setGravity(gravity);

        mBaseDialogOkView.setTextColor(SkinHelper.getSkin().getThemeSubColor());
    }
}
