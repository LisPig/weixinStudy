package com.ego.core.lang;

import java.util.List;

/**
 * 可变参数与数组的转换
 *
 * @author ego4qin@163.com
 */
public class Array {

    Object[] array;

    public Array(Object... objects) {
        array = objects;
    }

    public Object[] getArray() {
        return array;
    }

    /**
     * 获取可变参数数组中的所有对象，包括参数数组中嵌套的参数数组的对象
     *
     * @param fromArray
     * @param tolist
     *
     */
    public static void getObjects(Array fromArray, List<Object> tolist) {
        for (Object o : fromArray.getArray()) {
            if (o instanceof Array) {
                getObjects((Array) o, tolist);
            } else {
                tolist.add(o);
            }
        }
    }

}
