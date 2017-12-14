package com.ins.common.utils;

import com.ins.common.entity.BaseSelectBean;

import java.util.List;

/**
 * Created by liaoinstan on 2017/8/11.
 */

public class ListUtil {

    //调用list的get方法，处理越界和空问题，如果越界或者空返回null
    public static <T> T get(List<T> list, int position) {
        if (list == null) return null;
        if (list.size() > position) {
            return list.get(position);
        } else {
            return null;
        }
    }

    //取列表最后一个，如果越界或者空返回null
    public static <T> T getLast(List<T> list) {
        return get(list, list.size() - 1);
    }

    //取列表前n个，如果越界或者空返回null
    public static <T> List<T> getFirst(List<T> list, int n) {
        if (list == null) return null;
        if (list.size() <= 4) {
            return list;
        } else {
            return list.subList(0, n);
        }
    }
}
