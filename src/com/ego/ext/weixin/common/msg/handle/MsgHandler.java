package com.ego.ext.weixin.common.msg.handle;

import com.ego.core.file.xml.XmlDomParser;
import com.ego.core.file.xml.XmlFileParseException;
import com.ego.core.util.UtilStream;
import com.ego.core.util.UtilString;
import com.ego.core.util.UtilXML;
import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.model.WxConfig;
import com.ego.ext.weixin.common.msg.EventMsg;
import com.ego.ext.weixin.common.msg.ImageMsg;
import com.ego.ext.weixin.common.msg.LinkMsg;
import com.ego.ext.weixin.common.msg.LocationMsg;
import com.ego.ext.weixin.common.msg.Msg;
import com.ego.ext.weixin.common.msg.MsgHead;
import com.ego.ext.weixin.common.msg.TextMsg;
import com.ego.ext.weixin.common.msg.VideoMsg;
import com.ego.ext.weixin.common.msg.VoiceMsg;
import com.ego.ext.weixin.common.util.UtilEncryp;
import com.ego.ext.weixin.common.context.WxConfigHolder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;

/**
 * 抽象消息处理器 此消息处理器声明周期在一个请求响应内。 通过继承类实现各种消息的处理方法
 *
 * @author marker
 *
 */
public abstract class MsgHandler {

    //输入流
    private InputStream is;
    //输出流
    private OutputStream os;
    private final XmlDomParser parser;
    //  private final TransformerFactory tffactory;

    public MsgHandler() throws WxException {
        try {
            parser = XmlDomParser.getInstance();
            //格式化工厂对象
            //  tffactory = TransformerFactory.newInstance();
        } catch (XmlFileParseException ex) {
            throw new WxException("获得xml文档处理器时异常", ex);
        }

    }

    /**
     * 解析微信消息，并传递给对应的处理方法方法.此方法不支持加密/解密
     *
     * @param is 输入流 微信服务器输入的消息格式流
     * @param os 输出流
     *
     * @throws com.ego.ext.weixin.common.WxException
     *
     */
    public void process(InputStream is, OutputStream os) throws WxException {
        try {
            this.os = os;
            this.is = is;
            Document document = parser.read(is);
            MsgHead head = new MsgHead();
            head.read(document);
            String type = head.getMsgType();
            if (Msg.MsgType.TEXT.getType().equals(type)) {//文本消息
                TextMsg msg = new TextMsg(head);
                msg.read(document);
                this.onTextMsg(msg);
                return;
            } else if (Msg.MsgType.IMAGE.getType().equals(type)) {//图片消息
                ImageMsg msg = new ImageMsg(head);
                msg.read(document);
                this.onImageMsg(msg);
            } else if (Msg.MsgType.EVENT.getType().equals(type)) {//事件推送
                EventMsg msg = new EventMsg(head);
                msg.read(document);
                this.onEventMsg(msg);
            } else if (Msg.MsgType.LINK.getType().equals(type)) {//链接消息
                LinkMsg msg = new LinkMsg(head);
                msg.read(document);
                this.onLinkMsg(msg);
            } else if (Msg.MsgType.LOCATION.getType().equals(type)) {//地理位置消息
                LocationMsg msg = new LocationMsg(head);
                msg.read(document);
                this.onLocationMsg(msg);
            } else if (Msg.MsgType.VOICE.getType().equals(type)) {//语音消息
                VoiceMsg msg = new VoiceMsg(head);
                msg.read(document);
                this.onVoiceMsg(msg);
            } else if (Msg.MsgType.VIDEO.getType().equals(type)) {//视频消息
                VideoMsg msg = new VideoMsg(head);
                msg.read(document);
                this.onVideoMsg(msg);
            } else {
                TextMsg msg = new TextMsg(head);
                msg.setContent("无法识别的消息类型");
                this.onErrorMsg(-1, msg);//这里暂时这样处理的
            }
        } catch (XmlFileParseException ex) {
            throw new WxException("解析微信服务器发来的流为xml文档时出现异常", ex);
        }
    }

    /**
     * 解析微信消息，并传递给对应的处理方法方法.此方法支持加密/解密
     *
     * @param request
     * @param response
     * @throws WxException
     */
    public void process(HttpServletRequest request, HttpServletResponse response) throws WxException {
        try {
            InputStream in;
            WxConfig cfg = WxConfigHolder.getConfig();
            //http://mp.weixin.qq.com/wiki/0/61c3a8b9d50ac74f18bdf2e54ddfc4e0.html
            //在安全模式或兼容模式下，url上会新增两个参数encrypt_type和msg_signature。encrypt_type表示加密类型，
            //url上无encrypt_type参数或者其值为raw时表示为不加密；
            //encrypt_type为aes时，表示aes加密（暂时只有raw和aes两种值)。公众帐号开发者根据此参数来判断微信公众平台发送的消息是否加密。
            String encrypt_type = request.getParameter("encrypt_type");
            if (UtilString.trim(encrypt_type).equals("aes") && cfg.isEncrypt()) {
                //获取XML数据（含加密参数）
                //解密XML 数据
                in = UtilEncryp.decryptMsgToInputStream(UtilStream.copyToString(request.getInputStream(), null), request.getParameter("msg_signature"), request.getParameter("timestamp"), request.getParameter("nonce"));
            } else {
                in = request.getInputStream();
            }
            this.process(in, response.getOutputStream());
        } catch (IOException ex) {
            throw new WxException("解析微信服务器发来的流为xml文档时出现异常", ex);
        }
    }

    /**
     * 获得处理监听消息的回调对象。 回传消息给微信服务器 只能再接收到微信服务器消息后，才能调用此方法
     *
     *
     * @return
     */
    public Callback getCallback() {
        Callback callback = new Callback() {
            @Override
            public boolean invoke(Msg tomsg) {
                try {
                    Document document = parser.newDocument();
                    tomsg.write(document);
                    WxConfig cfg = WxConfigHolder.getConfig();
                    if (cfg.isEncrypt()) {
                        //加密document文档
                        UtilEncryp.encryptMsgToOutStream(UtilXML.document2XML(document, null), String.valueOf(System.currentTimeMillis()), UUID.randomUUID().toString(), os);
                        // UtilEncryp.encryptMsgToOutStream(UtilXML.document2XML(document, null), String.valueOf(System.currentTimeMillis()), UUID.randomUUID().toString());
                    } else {//不加密，直接写入document对象数据
                        XmlDomParser.dom2stream(document, os);
                    }
                    return true;
                    /*
                     try {
                     Transformer transformer = tffactory.newTransformer();
                     transformer.transform(new DOMSource(document), new StreamResult(new OutputStreamWriter(os, "utf-8")));
                     } catch (Exception e) {
                     e.printStackTrace();// 保存dom至目输出流
                     }
                     */
                } catch (Exception ex) {
                    //throw new WxException("回复消息至微信时文档转换为outputstream输出流时异常", ex);
                    return false;
                }

            }
        };
        return callback;

    }

    /**
     * 关闭
     *
     * @throws com.ego.ext.weixin.common.WxException
     */
    public void close() throws WxException {
        try {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.flush();
                os.close();
            }
            if (this.parser != null) {
                parser.close();
            }
        } catch (IOException ex) {
            throw new WxException("在关闭微信消息处理器时出现异常，可能原因输出流、输出流或xml文档解析处理器关闭异常", ex);
        }
    }

    /**
     * 收到文本消息
     *
     * @param msg
     */
    public abstract void onTextMsg(TextMsg msg);

    /**
     * 收到图片消息
     *
     * @param msg
     */
    public abstract void onImageMsg(ImageMsg msg);

    /**
     * 收到事件推送消息
     *
     * @param msg
     */
    public abstract void onEventMsg(EventMsg msg);

    /**
     * 收到链接消息
     *
     * @param msg
     */
    public abstract void onLinkMsg(LinkMsg msg);

    /**
     * 收到地理位置消息
     *
     * @param msg
     */
    public abstract void onLocationMsg(LocationMsg msg);

    /**
     * 收到语音识别消息
     *
     * @param msg
     */
    public abstract void onVoiceMsg(VoiceMsg msg);

    /**
     * 收到视频消息
     *
     * @param msg
     */
    public abstract void onVideoMsg(VideoMsg msg);

    /**
     * 错误消息
     *
     * @param errorCode
     * @param msg
     */
    public abstract void onErrorMsg(int errorCode, Msg msg);
}
