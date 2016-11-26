package com.angcyo.uiview.utils;

import java.lang.reflect.Field;

/**
 * Created by angcyo on 2016-11-26.
 */

public class Reflect {

    /**
     * 从一个对象中, 获取指定的成员对象
     */
    public static Object getMember(Object target, String member) {
        return getMember(target.getClass(), target, member);
    }

    public static Object getMember(Class<?> cls, Object target, String member) {
        Object result = null;
        try {
            Field memberField = cls.getDeclaredField(member);
            memberField.setAccessible(true);
            result = memberField.get(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void setMember(Class<?> cls, Object target, String member, Object value) {
        try {
            Field memberField = cls.getDeclaredField(member);
            memberField.setAccessible(true);
            memberField.set(target, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setMember(Object target, String member, Object value) {
        setMember(target.getClass(), target, member, value);
    }

    /**
     * 获取调用堆栈上一级的方法名称
     */
    public static String getMethodName() {
        final StackTraceElement[] stackTraceElements = new Exception().getStackTrace();
        return stackTraceElements[1].getMethodName();
    }
}
