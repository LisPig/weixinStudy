package com.ego.ext.weixin.common.msg;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import com.ego.ext.weixin.common.model.CustomNews;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 图文消息（只能回复，不能从微信服务器接收）,
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=发送被动响应消息
 */
public class NewsMsg extends Msg {

    public static final int MAX_COUNT = 10;
    // 图文消息个数，限制为10条以内
    private String articleCount = "0";
    // 图文消息的数据
    private final List<CustomNews> items = new ArrayList<CustomNews>(3);

    /**
     * 默认构造
     *
     */
    public NewsMsg() {
        this.head = new MsgHead();
        this.head.setMsgType(Msg.MsgType.NEWS.getType());
    }

    @Override
    public void write(Document document) {
        Element root = document.createElement(WeiXinXmlNodeName.ROOT);
        head.write(root, document);
        Element articleCountElement = document.createElement(WeiXinXmlNodeName.ARTICLE_COUNT);
        articleCountElement.setTextContent(this.articleCount);

        Element articlesElement = document.createElement(WeiXinXmlNodeName.ARTICLES);
        int size = Integer.parseInt(this.articleCount);
        for (int i = 0; i < size; i++) {
            CustomNews currentItem = items.get(i);
            Element itemElement = document.createElement(WeiXinXmlNodeName.ITEM);

            Element titleElement = document.createElement(WeiXinXmlNodeName.TITLE);
            titleElement.setTextContent(currentItem.getTitle());

            Element descriptionElement = document.createElement(WeiXinXmlNodeName.DESCRITION);
            descriptionElement.setTextContent(currentItem.getDescription());

            Element picUrlElement = document.createElement(WeiXinXmlNodeName.PIC_URL);
            picUrlElement.setTextContent(currentItem.getPicurl());

            Element urlElement = document.createElement(WeiXinXmlNodeName.URL);
            urlElement.setTextContent(currentItem.getUrl());

            itemElement.appendChild(titleElement);
            itemElement.appendChild(descriptionElement);
            itemElement.appendChild(picUrlElement);
            itemElement.appendChild(urlElement);

            articlesElement.appendChild(itemElement);
        }

        root.appendChild(articleCountElement);
        root.appendChild(articlesElement);

        document.appendChild(root);
    }

    @Override
    public void read(Document document) {
        throw new RuntimeException(MessageFormat.format("此消息类型{0}图文消息（只能回复）", super.getMsgType()));

    }

    /**
     * 添加一条图文信息
     *
     * @param item
     * @return 成功添加返回true，小于等于最大值成功
     */
    public boolean addItem(CustomNews item) {
        if (Integer.parseInt(articleCount) <= MAX_COUNT) {
            this.items.add(item);
            this.reflushArticleCount();
            return true;
        }
        return false;
    }

    /**
     * 返回图文列表
     *
     * @return
     */
    public List<CustomNews> getItems() {
        return this.items;
    }

    public void removeItem(int index) {
        this.items.remove(index);
        this.reflushArticleCount();
    }

    /**
     * 刷新当前文章条数
     *
     */
    private void reflushArticleCount() {
        this.articleCount = "" + this.items.size();
    }

}
