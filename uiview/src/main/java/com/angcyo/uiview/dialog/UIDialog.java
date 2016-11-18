package com.angcyo.uiview.dialog;

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

    @Override
    protected View inflateDialogView(RelativeLayout dialogRootLayout, LayoutInflater inflater) {
        return inflater.inflate(R.layout.base_dialog_layout, dialogRootLayout);
    }

    @Override
    public void loadContentView(View rootView) {
        super.loadContentView(rootView);
        mBaseDialogTitleView = (TextView) rootView.findViewById(R.id.base_dialog_title_view);
        mBaseDialogContentView = (TextView) rootView.findViewById(R.id.base_dialog_content_view);
        mBaseDialogRootLayout = (LinearLayout) rootView.findViewById(R.id.base_dialog_root_layout);
        mBaseDialogOkView = (TextView) rootView.findViewById(R.id.base_dialog_ok_view);
        mBaseDialogCancelView = (TextView) rootView.findViewById(R.id.base_dialog_cancel_view);

        mBaseDialogOkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDialog();
            }
        });
        mBaseDialogCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDialog();
            }
        });

        mBaseDialogTitleView.setText("对话框标题");
        mBaseDialogContentView.setText("对话框内容....!!!");

//        mDialogRootLayout.setForegroundGravity(Gravity.TOP);
//        mBaseDialogRootLayout.setForegroundGravity(Gravity.TOP);
//        mDialogRootLayout.setGravity(Gravity.TOP);
    }

    private void finishDialog() {
        mILayout.finishIView(getView());
    }
}
