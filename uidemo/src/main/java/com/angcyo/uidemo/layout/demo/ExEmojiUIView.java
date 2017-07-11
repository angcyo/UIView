package com.angcyo.uidemo.layout.demo;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.angcyo.library.utils.L;
import com.angcyo.uidemo.R;
import com.angcyo.uiview.base.UIContentView;
import com.angcyo.uiview.container.ContentLayout;
import com.angcyo.uiview.widget.RSoftInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by angcyo on 2016-12-22.
 */

public class ExEmojiUIView extends UIContentView {

    Control mControl;

    @Override
    protected void inflateContentLayout(ContentLayout baseContentLayout, LayoutInflater inflater) {
        inflate(R.layout.view_ex_emoji_layout);
    }

    @Override
    protected void initOnShowContentLayout() {
        super.initOnShowContentLayout();
        mControl = new Control((RecyclerView) mViewHolder.v(R.id.recycler_view),
                (RSoftInputLayout) mViewHolder.v(R.id.soft_input_layout),
                mActivity);
        mViewHolder.v(R.id.padd100).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetPadding100Click(v);
            }
        });
        mViewHolder.v(R.id.padd400).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetPadding400Click(v);
            }
        });
        mViewHolder.v(R.id.show_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowClick(v);
            }
        });
        mViewHolder.v(R.id.hide_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHideClick(v);
            }
        });
        mViewHolder.v(R.id.onTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTest(v);
            }
        });
        mViewHolder.v(R.id.dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inDialog(v);
            }
        });
        mViewHolder.v(R.id.layout_full).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLayoutFullScreen(v);
            }
        });
        mControl.initContentLayout();
    }

    @Override
    public boolean onBackPressed() {
        return mControl.onBackPressed();
    }

    public void onSetPadding100Click(View view) {
        mControl.onSetPadding100Click();
    }

    public void onSetPadding400Click(View view) {
        mControl.onSetPadding400Click();
    }

    public void onShowClick(View view) {
        mControl.onShowClick();
    }

    public void onHideClick(View view) {
        mControl.onHideClick();
    }

    public void onTest(View view) {
        long timeMillis = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss:SSS");
        String format = simpleDateFormat.format(new Date(timeMillis / 10000));
        L.e(format);
        EditText editText = mViewHolder.v(R.id.edit_text);
        Editable text = editText.getText();
        for (int i = 0; i < text.length() / 2; i++) {
            L.e(Integer.toHexString((int) (text.charAt(i * 2))) +
                    " " +
                    Integer.toHexString((int) text.charAt(1 + i * 2)));
        }
        L.e("on test ----- end");
    }

    public void onLayoutFullScreen(View view) {
        mControl.onLayoutFullScreen();
    }

    public void inDialog(View view) {
        //new DialogDemo().show(getSupportFragmentManager(), "dialog");
    }

    private static class Control {
        ArrayList<String> datas = new ArrayList<>();
        RSoftInputLayout mSoftInputLayout;
        Activity mActivity;
        private RecyclerView mRecyclerView;

        public Control(RecyclerView recyclerView, RSoftInputLayout softInputLayout, Activity activity) {
            mRecyclerView = recyclerView;
            mSoftInputLayout = softInputLayout;
            mActivity = activity;
        }

        protected void initContentLayout() {
            mSoftInputLayout.addOnEmojiLayoutChangeListener(new RSoftInputLayout.OnEmojiLayoutChangeListener() {
                @Override
                public void onEmojiLayoutChange(boolean isEmojiShow, boolean isKeyboardShow, int height) {
                    Log.w("Robi", "表情显示:" + mSoftInputLayout.isEmojiShow() + " 键盘显示:" + mSoftInputLayout.isKeyboardShow()
                            + " 表情高度:" + mSoftInputLayout.getShowEmojiHeight() + " 键盘高度:" + mSoftInputLayout.getKeyboardHeight());
                    String log = "表情显示:" + isEmojiShow + " 键盘显示:" + isKeyboardShow + " 高度:" + height;
                    Log.e("Robi", log);
                    datas.add(log);
                    mRecyclerView.getAdapter().notifyItemInserted(datas.size());
                    mRecyclerView.smoothScrollToPosition(datas.size());
                }
            });
            datas.add("内容顶部");
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(new RecyclerView.Adapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    TextView textView = new TextView(mActivity);
                    return new RecyclerView.ViewHolder(textView) {
                    };
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    ((TextView) holder.itemView).setText(datas.get(position));
                }

                @Override
                public int getItemCount() {
                    return datas.size();
                }
            });
        }

        public boolean onBackPressed() {
            return mSoftInputLayout.requestBackPressed();
        }

        public void onSetPadding100Click() {
            mSoftInputLayout.showEmojiLayout(dpToPx(100));
        }

        public void onSetPadding400Click() {
            mSoftInputLayout.showEmojiLayout(dpToPx(400));
        }

        public void onShowClick() {
            mSoftInputLayout.showEmojiLayout();
        }

        public void onHideClick() {
            mSoftInputLayout.hideEmojiLayout();
        }

        private int dpToPx(int dp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mActivity.getResources().getDisplayMetrics());
        }

        public void onLayoutFullScreen() {
            mSoftInputLayout.requestLayout();
            mSoftInputLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSoftInputLayout.requestLayout();
                }
            });
        }
    }
}
