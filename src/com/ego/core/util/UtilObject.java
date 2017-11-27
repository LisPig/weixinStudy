
/*----------javabean工具类---------
 * @功能说明：操作对象的便捷工具
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-3-31:09:23
 */
package com.ego.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 操作对象的便捷工具。参考org.ofbiz.base.util;
 *
 */
public final class UtilObject {

    private UtilObject() {
    }
    public static final String module = UtilObject.class.getName();

    /**
     *
     *
     * @param is
     * @return
     */
    /**
     * 输入流转换为字节数组
     *
     * @param is
     * @return 输入流转换后的字节数组
     * @throws IOException 字节数组输出流或输入流关闭异常
     */
    public static byte[] getBytes(InputStream is) throws IOException {
        byte[] buffer = new byte[4 * 1024];
        byte[] data = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int numBytesRead;
        while ((numBytesRead = is.read(buffer)) != -1) {
            bos.write(buffer, 0, numBytesRead);
        }
        data = bos.toByteArray();
        bos.close();
        if (is != null) {
            is.close();
        }
        return data;
    }

    /**
     * 序列化对象到一个字节数组，将对象写到字节数组
     *
     * @param obj
     * @return
     * @throws IOException 字节数组输出流或输入流关闭异常
     */
    public static byte[] getBytes(Object obj) throws IOException {
        byte[] data = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(obj);
        data = bos.toByteArray();

        oos.flush();
        oos.close();
        // I don't know how to force an error during flush or
        // close of ObjectOutputStream; since OOS is wrapping
        // BAOS, and BAOS does not throw IOException during
        // write, I don't think this can happen.

        bos.close();

        // How could this ever happen?  BAOS.close() is listed as
        // throwing the exception, but I don't understand why this
        // is.
        return data;
    }

    /**
     * 返回一个序列化对象的大小，非序列化对象将抛出异常。特别注意：返回值是对象被序列化后字节流的长度，
     *
     * 返回值不表示对象使用的内存数量。并没有精确的方法来计算对象在内存中的大小。
     *
     * <p>
     *
     * Returns the size of a serializable object. Non-serializable objects will
     * throw an <code>IOException</code>.<p>
     * It is important to note that the returned value is length of the byte
     * stream after the object has been serialized. The returned value does not
     * represent the amount of memory the object uses. There is no accurate way
     * to determine the size of an object in memory.</p>
     *
     * @param obj
     * @return the number of bytes in the serialized object
     * @throws IOException
     */
    public static long getByteCount(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        long size = bos.size();
        bos.close();
        return size;
    }

    /**
     * Deserialize a byte array back to an object
     */
    /**
     *
     * 反序列化一个字节数组为对象
     *
     * <p>
     *
     * Deserialize a byte array back to an object
     *
     * @param bytes
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object getObject(byte[] bytes) throws IOException, ClassNotFoundException {
        Object obj = null;

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        //ObjectInputStream ois = new ObjectInputStream(bis, Thread.currentThread().getContextClassLoader());
        obj = ois.readObject();
        ois.close();
        bis.close();
        // How could this ever happen?  BAIS.close() is listed as
        // throwing the exception, but I don't understand why this
        // is.

        return obj;
    }

    /**
     * 两个对象是否相等。相同引用返回true，为null返回false。否则调用o1.equals(o2)比较
     *
     * @param o1
     * @param o2
     * @return
     */
    public static boolean equals(Object o1, Object o2) {
        if (o1 == o2) {
            // handles same-reference, or null
            return true;
        } else if (o1 == null || o2 == null) {
            // either o1 or o2 is null, but not both
            return false;
        } else {
            return o1.equals(o2);
        }
    }

    /**
     * 比较两个实现Comparable接口的对象。两个对象相同引用返回0，o1为null返回-1；o2为null返回1，否则返回o1.compareTo(o2)
     *
     * @param <T>
     * @param o1
     * @param o2
     * @return
     */
    public static <T> int compareTo(Comparable<T> o1, T o2) {
        if (o1 == o2) {
            // handles same-reference, or null
            return 0;
        } else if (o1 == null) {
            return -1;
        } else if (o2 == null) {
            // either o1 or o2 is null, but not both
            return 1;
        } else {
            return o1.compareTo(o2);
        }
    }

    /**
     * 返回对象的hashcode.如果对象为null返回0.否则返回o1.hashCode()。如果对象为数组，
     *
     * 轮询数组中的每一个元素并加每一个元素的hashcode
     *
     * @param o1
     * @return
     */
    public static int hashCode(Object o1) {
        if (o1 == null) {
            return 0;
        }
        if (o1.getClass().isArray()) {
            int length = Array.getLength(o1);
            int result = 0;
            for (int i = 0; i < length; i++) {
                result += hashCode(Array.get(o1, i));
            }
            return result;
        }
        return o1.hashCode();
    }

    /**
     * 复制对象属性 
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copy(Object source, Object target) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class sourceClass = source.getClass();//得到对象的Class
        Class targetClass = target.getClass();//得到对象的Class

        Field[] sourceFields = sourceClass.getDeclaredFields();//得到Class对象的所有属性
        Field[] targetFields = targetClass.getDeclaredFields();//得到Class对象的所有属性

        for (Field sourceField : sourceFields) {
            String name = sourceField.getName();//属性名
            Class type = sourceField.getType();//属性类型
            String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);

            Method method = sourceClass.getMethod("get" + methodName);//得到属性对应get方法
            method = method == null ? sourceClass.getMethod("is" + methodName) : method;//得到属性对应is方法
            if (method != null) {
                Object value = method.invoke(source);//执行源对象的get方法得到属性值
                if (value != null) {
                    for (Field targetField : targetFields) {
                        String targetName = targetField.getName();//目标对象的属性名
                        if (targetName.equals(name)) {
                            Method setMethod = targetClass.getMethod("set" + methodName, type);//属性对应的set方法
                            setMethod = setMethod == null ? targetClass.getMethod("is" + methodName, type) : setMethod;//属性对应的is方法
                            if (setMethod != null) {
                                setMethod.invoke(target, value);//执行目标对象的set方法
                            }
                        }

                    }
                }

            }

        }
    }

    /**
     * 复制序列化对象
     *
     * @param <S> 实现Serializable接口的对象
     * @param s 要复制的对象
     * @return 复制对象
     */
    public static <S extends Serializable> Object cloneSerialObj(S s) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(s);
            out.close();
            ByteArrayInputStream bin = new ByteArrayInputStream(bout
                    .toByteArray());
            ObjectInputStream in = new ObjectInputStream(bin);
            Object ret = in.readObject();
            in.close();
            return ret;
        } catch (Exception e) {
            return null;
        }
    }
}
