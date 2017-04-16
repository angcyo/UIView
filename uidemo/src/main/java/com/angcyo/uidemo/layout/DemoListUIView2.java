package com.angcyo.uidemo.layout;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.angcyo.uidemo.NavUIView;
import com.angcyo.uidemo.R;
import com.angcyo.uidemo.layout.demo.AnimatorDemoUIView;
import com.angcyo.uidemo.layout.demo.BehaviorStickDemoUIView;
import com.angcyo.uidemo.layout.demo.BehaviorStickDemoUIView2;
import com.angcyo.uidemo.layout.demo.CenterRadioButtonUIView;
import com.angcyo.uidemo.layout.demo.EmojiUIView;
import com.angcyo.uidemo.layout.demo.ExEmojiUIView;
import com.angcyo.uidemo.layout.demo.GameCircleLayoutUIView;
import com.angcyo.uidemo.layout.demo.GithubDemoUIView;
import com.angcyo.uidemo.layout.demo.LoopRecyclerViewUIView;
import com.angcyo.uidemo.layout.demo.MentionDemoUIView;
import com.angcyo.uidemo.layout.demo.RTLUIView;
import com.angcyo.uidemo.layout.demo.RippleDrawableDemoUIView;
import com.angcyo.uidemo.layout.demo.StickLayoutDemo2UIView;
import com.angcyo.uidemo.layout.demo.StickLayoutDemoUIView;
import com.angcyo.uidemo.layout.demo.StickLayoutManagerUIView;
import com.angcyo.uidemo.layout.demo.SwipeRecyclerViewUIView;
import com.angcyo.uidemo.layout.demo.TouchEventDemoUIView;
import com.angcyo.uidemo.refresh.RefreshLayoutDemo;
import com.angcyo.uidemo.uiview.ScrollerIView;
import com.angcyo.uidemo.uiview.TestDemo;
import com.angcyo.uiview.base.Item;
import com.angcyo.uiview.base.SingleItem;
import com.angcyo.uiview.base.UIItemUIView;
import com.angcyo.uiview.model.TitleBarPattern;
import com.angcyo.uiview.recycler.RBaseViewHolder;
import com.angcyo.uiview.widget.ItemInfoLayout;

import java.util.List;

/**
 * Created by angcyo on 2017-03-13.
 */
public class DemoListUIView2 extends UIItemUIView<SingleItem> {

    @Override
    protected TitleBarPattern getTitleBar() {
        return super.getTitleBar().setShowBackImageView(false);
    }

    @Override
    public void onViewShowFirst(Bundle bundle) {
        super.onViewShowFirst(bundle);
        //mRootView.setEnabled(false);
        //showLoadView();
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_demo_list_layout;
    }

    @Override
    protected void createItems(List<SingleItem> items) {
        items.add(new SingleItem(SingleItem.Type.TOP) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Scroller IView", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new ScrollerIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Refresh Layout Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new RefreshLayoutDemo());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Test Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new TestDemo());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".RTL Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new RTLUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Emoji Layout Demo (Android5.+)", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new EmojiUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".ExEmoji Layout Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new ExEmojiUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Swipe RecyclerUIView Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new SwipeRecyclerViewUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Link Url Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new NavUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Center Button Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new CenterRadioButtonUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Animator Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new AnimatorDemoUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Loop RecyclerUIView Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new LoopRecyclerViewUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".@ Mention Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new MentionDemoUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Stick Layout Demo (Beta)", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new StickLayoutDemoUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Stick Layout Demo2 (Deprecated)", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new StickLayoutDemo2UIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Behavior Stick Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new BehaviorStickDemoUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Behavior Stick Demo2", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new BehaviorStickDemoUIView2());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Stick LayoutManager Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new StickLayoutManagerUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".GitHub Project Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new GithubDemoUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Game CircleLayout Demo (Beta)", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new GameCircleLayoutUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {
            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Touch Event Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new TouchEventDemoUIView());
                    }
                });
            }
        });
        items.add(new SingleItem(SingleItem.Type.LINE) {

            @Override
            public void setItemOffsets(Rect rect) {
                super.setItemOffsets(rect);
                rect.bottom = getDimensionPixelOffset(R.dimen.base_xhdpi);
            }

            @Override
            public void onBindView(RBaseViewHolder holder, int posInData, Item dataBean) {
                initItem(holder, posInData + 1 + ".Ripple Drawable Demo", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startIView(new RippleDrawableDemoUIView());
                    }
                });
            }
        });
    }

    void initItem(RBaseViewHolder holder, String itemText, View.OnClickListener onClickListener) {
        ItemInfoLayout infoLayout = holder.v(R.id.item_info_layout);
        infoLayout.setItemText(itemText);
        infoLayout.setOnClickListener(onClickListener);
    }

}
