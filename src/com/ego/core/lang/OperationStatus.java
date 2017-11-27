/*----------枚举---------
 * @功能说明：操作状态枚举常量
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-3-31:09:23
 */
package com.ego.core.lang;

/**
 * 操作状态枚举常量
 *
 * @author Administrator
 */
public enum OperationStatus {

    /**
     * 正确
     */
    TRUE("true"),
    /**
     * 错误
     */
    FALSE("false"),
    /**
     * 成功
     */
    SCUCCESS("success"),
    /**
     * 失败
     */
    FAILURE("failure");
    private String status;

    OperationStatus(String status) {
        this.status = status;
    }

    /**
     * 返回常量名的小写形式
     *
     * @return
     */
    @Override
    public String toString() {
        return this.status;
    }
}
