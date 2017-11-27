/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ego.ext.weixin.mp.api;

/**
 * 微信小店接口
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=微信小店接口
 * @author Administrator
 */
public class MerchantApi {

    /**
     * 1.1增加商品
     */
    public static final String MERCHANT_CREATE_URI = "https://api.weixin.qq.com/merchant/create?access_token={0}";
    public static final String MERCHANT_DEL_URI = "https://api.weixin.qq.com/merchant/del?access_token={0}";
    public static final String MERCHANT_UPDATE_URI = "https://api.weixin.qq.com/merchant/update?access_token={0}";
    public static final String MERCHANT_GET_URI = "https://api.weixin.qq.com/merchant/get?access_token={0}";
    public static final String MERCHANT_GETBYSTATUS_URI = "https://api.weixin.qq.com/merchant/getbystatus?access_token={0}";
    public static final String MERCHANT_MODPRODUCTSTATUS_URI = "https://api.weixin.qq.com/merchant/modproductstatus?access_token={0}";
    public static final String MERCHANT_CATEGORY_GETSUB_URI = "https://api.weixin.qq.com/merchant/category/getsub?access_token={0}";
    public static final String MERCHANT_CATEGORY_GETSKU_URI = "https://api.weixin.qq.com/merchant/category/getsku?access_token={0}";
    public static final String MERCHANT_CATEGORY_GETPROPERTY_URI = "https://api.weixin.qq.com/merchant/category/getproperty?access_token={0}";
    public static final String MERCHANT_STOCK_ADD_URI = "https://api.weixin.qq.com/merchant/stock/add?access_token={0}";
    public static final String MERCHANT_STOCK_REDUCE_URI = "https://api.weixin.qq.com/merchant/stock/reduce?access_token={0}";
    public static final String MERCHANT_EXPRESS_ADD_URI = "https://api.weixin.qq.com/merchant/express/add?access_token={0}";
    public static final String MERCHANT_EXPRESS_DEL_URI = "https://api.weixin.qq.com/merchant/express/del?access_token={0}";
    public static final String MERCHANT_EXPRESS_UPDATE_URI = "https://api.weixin.qq.com/merchant/express/update?access_token={0}";
    public static final String MERCHANT_EXPRESS_GETBYID_URI = "https://api.weixin.qq.com/merchant/express/getbyid?access_token={0}";
    public static final String MERCHANT_EXPRESS_GETALL_URI = "https://api.weixin.qq.com/merchant/express/getall?access_token={0}";
    public static final String MERCHANT_GROUP_ADD_URI = "https://api.weixin.qq.com/merchant/group/add?access_token={0}";
    public static final String MERCHANT_GROUP_DEL_URI = "https://api.weixin.qq.com/merchant/group/del?access_token={0}";
    public static final String MERCHANT_GROUP_PROPERTYMOD_URI = "https://api.weixin.qq.com/merchant/group/propertymod?access_token={0}";
    public static final String MERCHANT_GROUP_PRODUCTMOD_URI = "https://api.weixin.qq.com/merchant/group/productmod?access_token={0}";
    public static final String MERCHANT_GROUP_GETALL_URI = "https://api.weixin.qq.com/merchant/group/getall?access_token={0}";
    public static final String MERCHANT_GROUP_GETBYID_URI = "https://api.weixin.qq.com/merchant/group/getbyid?access_token={0}";
    public static final String MERCHANT_SHELF_ADD_URI = "https://api.weixin.qq.com/merchant/shelf/add?access_token={0}";
    public static final String MERCHANT_SHELF_DEL_URI = "https://api.weixin.qq.com/merchant/shelf/del?access_token={0}";
    public static final String MERCHANT_SHELF_MOD_URI = "https://api.weixin.qq.com/merchant/shelf/mod?access_token={0}";
    public static final String MERCHANT_SHELF_GETALL_URI = "https://api.weixin.qq.com/merchant/shelf/getall?access_token={0}";
    public static final String MERCHANT_SHELF_GETBYID_URI = "https://api.weixin.qq.com/merchant/shelf/getbyid?access_token={0}";
    public static final String MERCHANT_ORDER_GETBYID_URI = "https://api.weixin.qq.com/merchant/order/getbyid?access_token={0}";
    public static final String MERCHANT_ORDER_GETBYFILTER_URI = "https://api.weixin.qq.com/merchant/order/getbyfilter?access_token={0}";
    public static final String MERCHANT_ORDER_SETDELIVERY_URI = "https://api.weixin.qq.com/merchant/order/setdelivery?access_token={0}";
   //  public static final String MERCHANT_ORDER_SETDELIVERY_URI = "https://api.weixin.qq.com/merchant/order/setdelivery?access_token={0}";
}
