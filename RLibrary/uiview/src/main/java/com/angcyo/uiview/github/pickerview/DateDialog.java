package com.angcyo.uiview.github.pickerview;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.angcyo.uiview.R;
import com.angcyo.uiview.base.UIIDialogImpl;
import com.angcyo.uiview.github.pickerview.view.WheelTime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：日期选择对话框
 * 创建人员：Robi
 * 创建时间：2017/02/21 18:19
 * 修改人员：Robi
 * 修改时间：2017/02/21 18:19
 * 修改备注：
 * Version: 1.0.0
 */
public class DateDialog extends UIIDialogImpl {

    WheelTime.Type mType = WheelTime.Type.YEAR_MONTH_DAY;
    DateConfig mDateConfig;
    private WheelTime wheelTime;

    public DateDialog(DateConfig dateConfig) {
        mDateConfig = dateConfig;
    }

    /**
     * 获取周岁(时间格式 必须是: yyyy-MM-dd)
     */
    public static int getBirthday(String date) {
        if (TextUtils.isEmpty(date)) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);//当前那一年

        try {
            calendar.setTime(WheelTime.Date_FORMAT.parse(date));
            int y = calendar.get(Calendar.YEAR);//当前那一年
            return year - y;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    protected View inflateDialogView(RelativeLayout dialogRootLayout, LayoutInflater inflater) {
        return inflate(com.angcyo.uiview.R.layout.pickerview_time);
    }

    @Override
    protected void initDialogContentView() {
        super.initDialogContentView();
        wheelTime = new WheelTime(mViewHolder.v(R.id.timepicker), mType);
        mViewHolder.v(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateConfig.onDateSelector(wheelTime);
                finishDialog();
            }
        });
        mViewHolder.v(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDialog();
            }
        });
        //默认选中当前时间
        if (TextUtils.isEmpty(mDateConfig.getMaxDate())) {
            if (TextUtils.isEmpty(mDateConfig.getCurrentDate())) {
                setTime(null, true);
            } else {
                try {
                    setTime(WheelTime.Date_FORMAT.parse(mDateConfig.getCurrentDate()), false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (TextUtils.isEmpty(mDateConfig.getCurrentDate())) {
                setTime(null, true);
            } else {
                try {
                    Date maxData = WheelTime.Date_FORMAT.parse(mDateConfig.getMaxDate());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(maxData);

                    wheelTime.setEndYear(calendar.get(Calendar.YEAR));
                    wheelTime.setMaxValue(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    setTime(WheelTime.Date_FORMAT.parse(mDateConfig.getCurrentDate()), false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置选中时间
     *
     * @param date 时间
     */
    public void setTime(Date date, boolean isMax) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (isMax) {
            wheelTime.setEndYear(year);
            wheelTime.setMaxValue(year, month, day);
        }
        wheelTime.setPicker(year, month, day, hours, minute);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }

    public interface DateConfig {
        void onDateSelector(WheelTime wheelTime);

        /**
         * 当前的日期, 如果空, 表示使用当前的日期
         * "yyyy-MM-dd"
         */
        String getCurrentDate();

        /**
         * 最大的日期
         * "yyyy-MM-dd"
         */
        String getMaxDate();
    }

    public static class SimpleDateConfig implements DateConfig {

        @Override
        public void onDateSelector(WheelTime wheelTime) {

        }

        @Override
        public String getCurrentDate() {
            return null;
        }

        @Override
        public String getMaxDate() {
            return WheelTime.Date_FORMAT.format(new Date(System.currentTimeMillis()));
        }
    }

}
