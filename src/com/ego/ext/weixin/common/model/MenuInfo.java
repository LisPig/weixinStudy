package com.ego.ext.weixin.common.model;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @deprecated @author Administrator
 */
public class MenuInfo {

    private Long id;
    private String name;
    private String key;
    private String type;
    private String url;
    private ArrayList<MenuInfo> childs = new ArrayList<MenuInfo>();

    public MenuInfo() {
    }

    public MenuInfo(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<MenuInfo> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<MenuInfo> childs) {
        this.childs = childs;
    }

    public void addChild(MenuInfo child) {
        this.childs.add(child);
    }

    /**
     * 解析查询微信菜单返回的微信菜单信息字符串
     *
     * @param menuinfoStr
     * @return
     */
    public static List<MenuInfo> parseMenus(String menuinfoStr) {
        List<MenuInfo> menus = new ArrayList<MenuInfo>();
        Map<String, Map> map = JSON.parseObject(menuinfoStr, Map.class);
        Map has = map.get("menu");
        if (has != null) {
            String btnsStr = has.get("button").toString();
            MenuInfo.parseSubMenu(menus, btnsStr);
        }
        return menus;
    }

    private static void parseSubMenu(List<MenuInfo> menus, String menuinfoArrStr) {
        List<Map> btns = JSON.parseArray(menuinfoArrStr, Map.class);
        for (int i = 0, len = btns.size(); i < len; i++) {
            Map btn = btns.get(i);
            String n = (String) btn.get("name");
            Object sub = btn.get("sub_button");
            MenuInfo menuinfo = new MenuInfo(n);
            menuinfo.setId(Long.valueOf(i));
            menuinfo.setKey((String) btn.get("key"));
            menuinfo.setType((String) btn.get("type"));
            menuinfo.setUrl((String) btn.get("url"));
            menus.add(menuinfo);
            if (sub != null) {
                parseSubMenu(menuinfo.getChilds(), sub.toString());
            }
        }
    }

    public static void main(String[] args) {
        //  String menu = "{\"menu\":{\"button\":[{\"type\":\"click\",\"name\":\"歌手简介\",\"key\":\"V1001_TODAY_SINGER\",\"sub_button\":[]},{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\":\"视频\",\"url\":\"http:\/\/v.qq.com\/\",\"sub_button\":[]},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\",\"sub_button\":[]}]}]}}";
        String menu = "{\"menu\":{\"button\":[{\"type\":\"click\",\"name\":\"歌手简介\",\"key\":\"V1001_TODAY_SINGER\"},"
                + "{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\":\"视频\",\"url\":\"http://v.qq.com/\"},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\"}]}"
                + "] }}";
        /*
         Map<String, Map> map = JSON.parseObject(menu, Map.class);
         Map has = map.get("menu");
         Map h = has;
         List<Map> m = JSON.parseArray(h.get("button").toString(), Map.class);
         List<MenuInfo> menus = new ArrayList<MenuInfo>();
         for (Map b : m) {
         String n = (String) b.get("name");
         MenuInfo menuinfo = new MenuInfo(n);
         menus.add(menuinfo);
         Object sub = b.get("sub_button");
         if (sub != null) {
         System.out.println(sub);
         }
         }
         */
        //   Object b = h.get("button");
        //  List<MenuInfo> m = MenuInfo.parseMenus(h.get("button").toString());
        // System.out.print(h.get("button"));
        List<MenuInfo> m = parseMenus(menu);
        for (MenuInfo b : m) {
            System.out.println("|" + b.getName());
            for (MenuInfo sb : b.getChilds()) {
                System.out.println("   |" + sb.getName());
            }

        }

    }
}
