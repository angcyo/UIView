package com.angcyo.uiview.dialog;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angcyo.uiview.R;
import com.angcyo.uiview.base.UIBaseDialog;

/**
 * Created by angcyo on 2016-11-16.
 */

public class UIDialog extends UIBaseDialog {
    TextView mBaseDialogTitleView;
    TextView mBaseDialogContentView;
    TextView mBaseDialogCancelView;
    TextView mBaseDialogOkView;
    LinearLayout mBaseDialogRootLayout;
    View mLineLayout;

    String dialogTitle, dialogContent;

    View.OnClickListener cancelListener, okListener;

    int gravity = Gravity.BOTTOM;

    @Override
    protected View inflateDialogView(RelativeLayout dialogRootLayout, LayoutInflater inflater) {
        return inflater.inflate(R.layout.base_dialog_layout, dialogRootLayout);
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

    public UIDialog setCancelListener(View.OnClickListener listener) {
        this.cancelListener = listener;
        return this;
    }

    public UIDialog setOkListener(View.OnClickListener listener) {
        this.okListener = listener;
        return this;
    }

    public UIDialog setGravity(int gravity) {
        this.gravity = gravity;
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
        mLineLayout = rootView.findViewById(R.id.line_layout);

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
        mLineLayout.setVisibility((TextUtils.isEmpty(dialogTitle) && TextUtils.isEmpty(dialogContent)) ? View.GONE : View.VISIBLE);

        mBaseDialogTitleView.setText(dialogTitle);
        mBaseDialogContentView.setText(dialogContent);

        mDialogRootLayout.setGravity(gravity);
    }

    private void finishDialog() {
        mILayout.finishIView(getView());
    }
}
