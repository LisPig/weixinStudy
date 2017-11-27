package com.ego.ext.weixin.mp.model.massmsg;

import com.ego.ext.weixin.mp.model.UploadedVideo;

/**
 * 仅适用于对 openid 发送接口
 *
 * @author LiYi
 *
 */
public class SendedVideoMassMsg extends SendedMassMsg {

    private UploadedVideo video;

    /**
     *
     * @param media_id MessageAPI mediaUploadvideo 返回的media_id
     */
    public SendedVideoMassMsg(UploadedVideo uploadvideo) {
        super();
        video = uploadvideo;
        super.msgtype = "video";
    }

    public UploadedVideo getVideo() {
        return video;
    }

    public void setVideo(UploadedVideo video) {
        this.video = video;
    }

}
