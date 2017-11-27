/**
 * 微信公众平台
 */
package com.ego.ext.weixin.mp.api;

/**
 * 用户分析数据接口
 *
 *
 */
public class DatacubeApi {

    public static final String user_get_summary_URI = "https://api.weixin.qq.com/datacube/getusersummary?access_token={0}";
    public static final String user_get_cumulate_URI = "https://api.weixin.qq.com/datacube/getusercumulate?access_token={0}";
    public static final String article_get_summary_URI = "https://api.weixin.qq.com/datacube/getarticlesummary?access_token={0}";
    public static final String article_get_total_URI = "https://api.weixin.qq.com/datacube/getarticletotal?access_token={0}";
    public static final String user_get_read_URI = "https://api.weixin.qq.com/datacube/getuserread?access_token={0}";
    public static final String user_get_read_hour_URI = "https://api.weixin.qq.com/datacube/getuserreadhour?access_token={0}";
    public static final String user_get_share_URI = "https://api.weixin.qq.com/datacube/getusershare?access_token={0}";
    public static final String user_get_share_hour_URI = "https://api.weixin.qq.com/datacube/getusersharehour?access_token={0}";
    public static final String up_stream_msg_get_URI = "https://api.weixin.qq.com/datacube/getupstreammsg?access_token={0}";
    public static final String up_stream_msg_get_hour_URI = "https://api.weixin.qq.com/datacube/getupstreammsghour?access_token={0}";
    public static final String up_stream_msg_get_week_URI = "https://api.weixin.qq.com/datacube/getupstreammsgweek?access_token={0}";
    public static final String up_stream_msg_get_month_URI = "https://api.weixin.qq.com/datacube/getupstreammsgmonth?access_token={0}";
    public static final String up_stream_msg_dist_get_URI = "https://api.weixin.qq.com/datacube/getupstreammsgdist?access_token={0}";
    public static final String up_stream_msg_dist_get_week_URI = "https://api.weixin.qq.com/datacube/getupstreammsgdistweek?access_token={0}";
    public static final String up_stream_msg_dist_get_month_URI = "https://api.weixin.qq.com/datacube/getupstreammsgdistmonth?access_token={0}";
    public static final String interface_get_summary_URI = "https://api.weixin.qq.com/datacube/getinterfacesummary?access_token={0}";
    public static final String interface_get_summary_hour_URI = "https://api.weixin.qq.com/datacube/getinterfacesummaryhour?access_token={0}";

}
