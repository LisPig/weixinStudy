/*----------javabean---------
 * @功能说明：解析dom文档树
 * @**方法列表**
 * 最后修改日期：2013-3-14:22:55
 */
package com.ego.core.file.xml;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 解析dom文档树.用法说明
 * <p>
 *
 * 此类的构造器是私有的，所以不能直接用new创建对象，必须通过静态方法getInstance();
 * <p>
 *
 *
 * 解析dom文档树.用法说明：
 * <p>
 *
 * 第一步：获得XmlDomParse对象 <br>
 *
 * -----XmlDomParser dom=XmlDomParser.getInstance(); <br>
 *
 * 第二步：如果是解析已有xml文档则读入； <br>
 *
 * -----File f=new File(ToolKit.getClassPath( XmlDomParser.class)
 * +"system\\admin.xml"); <br>
 * -----dom.read(f); <br>
 *
 * 第三步：使用 XmlDomParse对象的方法操作 <br>
 *
 *
 * 第四步：如果文档有改动，或需要另存为，调用save（）或saveas（）方法 <br>
 *
 * 第五步：关闭
 */
public class XmlDomParser {

    private DocumentBuilderFactory domfact = null;//定义Dom解析工厂
    private DocumentBuilder dombuilder;//定义dom解析器
    private Document document = null;
    private int XmlOpenModel = 0;//打开xml文件的方式
    private final int XmlOpenModel_STRING = 0;
    private final int XmlOpenModel_FILE = 1;
    private final int XmlOpenModel_INPUTSTREAM = 2;
    private File savePath = null;

    /**
     * 定义私有构造器，只允许单一内部创建对象
     */
    private XmlDomParser() throws XmlFileParseException {
        try {
            domfact = DocumentBuilderFactory.newInstance(); //得到Dom解析工厂
            dombuilder = domfact.newDocumentBuilder(); // 取得dom解析器
        } catch (ParserConfigurationException ex) {
            throw new XmlFileParseException("构造文档解析器错误", ex);
        }
    }

    /**
     * 静态方法获得XmlDomParse对象
     *
     * @return
     * <p>
     * @throws com.ego.core.file.xml.XmlFileParseException
     */
    public static XmlDomParser getInstance() throws XmlFileParseException {
        return new XmlDomParser();
    }

    /**
     * 重载方法,根据已有xml文档创建document对象。XmlDomParse对象方法，读取xml文件，通过xml文件地址
     *
     * @param xmlpath xml文件的地址
     * <p>
     * @return Document类型
     * <p>
     * @throws Exception 没有创建XmlDomParse对象实例或xml文件地址为空
     */
    public Document read(String xmlpath) throws XmlFileParseException {
        try {
            if (dombuilder == null) {
                throw new XmlFileParseException("must get the Instance()");
            }
            if (xmlpath == null) {
                throw new XmlFileParseException("the path must not be null");
            }
            XmlOpenModel = 0;
            this.savePath = new File(xmlpath);
            return this.document = dombuilder.parse(xmlpath);
        } catch (Exception ex) {
            throw new XmlFileParseException(MessageFormat.format("解析文档{0}出现错误", xmlpath));
        }
    }

    /**
     * 重载方法。XmlDomParse对象方法，读取xml文件，通过InputStream
     *
     * @param xmlpath
     * <p>
     * @return Document 类型
     * <p>
     * @since @throws XmlFileParseException 没有创建XmlDomParse对象实例或xml文件地址为空
     */
    public Document read(InputStream xmlpath) throws XmlFileParseException {
        if (dombuilder == null) {
            throw new XmlFileParseException("must get the Instance()");
        }
        if (xmlpath == null) {
            throw new XmlFileParseException("the path must not be null");
        }
        XmlOpenModel = 2;
        try {
            return this.document = dombuilder.parse(xmlpath);
        } catch (Exception ex) {
            throw new XmlFileParseException(MessageFormat.format("解析文档{0}出现错误", xmlpath));
        }
    }

    /**
     * 重载方法。XmlDomParse对象方法，读取xml文件，通过File
     *
     * @param xmlpath
     * <p>
     * @return Document类型
     * <p>
     * @since @throws XmlFileParseException 没有创建XmlDomParse对象实例或xml文件地址为空
     */
    public Document read(File xmlpath) throws XmlFileParseException {
        if (dombuilder == null) {
            throw new XmlFileParseException("must get the Instance()");
        }
        if (xmlpath == null) {
            throw new XmlFileParseException("the path must not be null");
        }
        XmlOpenModel = 1;
        this.savePath = xmlpath;
        try {
            return this.document = dombuilder.parse(xmlpath);
        } catch (Exception ex) {
            throw new XmlFileParseException(MessageFormat.format("解析文档{0}出现错误", xmlpath), ex);
        }
    }

    /**
     * 创建新文档,这个新文档没有根节点，如果此时获取这个新文档的根节点将会出错，一个xml文档只能有一个根节点
     *
     * @return 新创建的文档
     */
    public Document newDocument() {
        return this.document = dombuilder.newDocument();
    }

    /**
     * 创建新文档,为新文档创建根节点
     *
     * @param root 根节点名称
     * <p>
     * @return 返回根节点的引用
     */
    public Node newDocument(Element root) {
        return dombuilder.newDocument().appendChild(root);
    }

    /**
     * 通过标签名（元素名）获取节点对象列表,父对象是document
     *
     * @param tagname 元素名
     * <p>
     * @return 节点列表NodeList
     *
     * @throws XmlFileParseException 文档对象document为空或不存在
     */
    public NodeList getElementsByTagName(String tagname) throws XmlFileParseException {
        if (document == null) {
            throw new XmlFileParseException("the  document must not be null");
        }
        return document.getElementsByTagName(tagname);
    }

    /**
     * 获取文档中所有tagname元素名的第index个节点
     *
     * @param tagname 元素名
     * @param index 下标,从1开始
     * <p>
     * @return 返回Node,或者null
     * <p>
     * @throws XmlFileParseException document对象不存在
     */
    public Node getElementsByTagName(String tagname, int index) throws XmlFileParseException {
        if (document == null) {
            throw new XmlFileParseException("the  document must not be null");
        }
        NodeList items = document.getElementsByTagName(tagname);
        if (items.getLength() > 0) {
            return items.item(index - 1);
        } else {
            return null;
        }

    }

    /**
     * 获取某个父元素节点下指定名称的节点列表
     *
     * @param parentElement 父元素名 tagname元素名，index下标
     * <p>
     * @return 返回NodeList
     * <p>
     * @throws XmlFileParseException document对象不存在
     */
    public NodeList getElementsByTagName(Element parentElement, String tagname) throws XmlFileParseException {
        if (document == null) {
            throw new XmlFileParseException("the  document must not be null in method 'getElementsByTagName");
        }
        if (parentElement == null) {
            throw new XmlFileParseException("the  parentElement must not be null in method 'getElementsByTagName");
        }
        return parentElement.getElementsByTagName(tagname);
    }

    /**
     * 获取某个父元素节点下指定名称的节点列表中指定下标的节点对象
     *
     * @param parentElement
     * @param tagname
     * @param index 下标,从1开始
     * <p>
     * @return 返回Node,或者null
     * <p>
     * @throws XmlFileParseException
     */
    public Node getElementsByTagName(Element parentElement, String tagname, int index) throws XmlFileParseException {
        if (document == null) {
            throw new XmlFileParseException("the  document must not be null in method 'getElementsByTagName");
        }
        NodeList items = parentElement.getElementsByTagName(tagname);
        if (items.getLength() > 0) {
            return items.item(index - 1);
        } else {
            return null;
        }

    }

    /**
     * 获取某个元素节点的文本内容
     *
     * @param tagname
     * @param index
     * <p>
     * @return
     * <p>
     * @throws XmlFileParseException
     */
    public String getTextOfElementByTagName(String tagname, int index) throws XmlFileParseException {
        if (document == null) {
            throw new XmlFileParseException("the  document must not be null in method 'etTextOfElementsByTagName(String tagname)'");
        }
        return document.getElementsByTagName(tagname).item(index).getTextContent();
    }

    /**
     * 获取某个父元素节点下指定节点的文本内容
     *
     * @param parentElement
     * @param tagname
     * @param index
     * <p>
     * @return
     * <p>
     * @throws XmlFileParseException
     */
    public String getTextOfElementByTagName(Element parentElement, String tagname, int index) throws XmlFileParseException {
        if (document == null) {
            throw new XmlFileParseException("the  document must not be null in method 'etTextOfElementsByTagName(String tagname)'");
        }

        return parentElement.getElementsByTagName(tagname).item(index).getTextContent();
    }

    /**
     * 通过标签名和属性名/值获取对象
     *
     * @param tagname
     * @param attributeName
     * @param attributeValue
     * <p>
     * @return
     * <p>
     * @throws XmlFileParseException
     */
    public Element[] getElementsByTagNameAndAttribute(String tagname, String attributeName, String attributeValue) throws XmlFileParseException {
        if (document == null) {
            throw new XmlFileParseException("the  document must not be null");
        }
        ArrayList<Element> elem = new ArrayList<Element>();
        NodeList nodelist = document.getElementsByTagName(tagname);
        for (int i = 0, length = nodelist.getLength(); i < length; i++) {
            Element node = (Element) nodelist.item(i);
            if (node.getAttribute(attributeName).equals(attributeValue)) {
                elem.add(node);
            }
        }
        elem.trimToSize();
        return (Element[]) elem.toArray(new Element[0]);
    }

    /**
     * 获取指定父节点下指定元素名和属性名/值获取对象
     *
     * @param parentElement
     * @param tagname
     * @param attributeName
     * @param attributeValue
     * <p>
     * @return
     * <p>
     * @throws XmlFileParseException
     */
    public Element[] getElementsByTagNameAndAttribute(Element parentElement, String tagname, String attributeName, String attributeValue) throws XmlFileParseException {
        if (document == null) {
            throw new XmlFileParseException("the  document must not be null");
        }
        ArrayList<Element> elem = new ArrayList<Element>();
        NodeList nodelist = parentElement.getElementsByTagName(tagname);
        for (int i = 0, length = nodelist.getLength(); i < length; i++) {
            Element node = (Element) nodelist.item(i);
            if (!node.getAttribute(attributeName).equals("") && node.getAttribute(attributeName).equals(attributeValue)) {
                elem.add(node);
            }
        }
        elem.trimToSize();
        return (Element[]) elem.toArray(new Element[0]);
    }

    /**
     * 获取指定元素名且具有某个属性的对象元素
     *
     * @param tagname
     * @param attributeName
     * <p>
     * @return
     * <p>
     * @throws XmlFileParseException
     */
    public Element[] getElementsByTagNameAndHasAttribute(String tagname, String attributeName) throws XmlFileParseException {
        if (document == null) {
            throw new XmlFileParseException("the  document must not be null");
        }
        ArrayList<Element> elem = new ArrayList<Element>();
        NodeList nodelist = document.getElementsByTagName(tagname);
        for (int i = 0, length = nodelist.getLength(); i < length; i++) {
            Element node = (Element) nodelist.item(i);
            if (node.hasAttribute(attributeName)) {
                elem.add(node);
            }
        }
        elem.trimToSize();
        return (Element[]) elem.toArray(new Element[1]);
    }

    /**
     * 获取指定父节点下指定元素名且具有某个属性的对象元素
     *
     * @param parentElement
     * @param tagname
     * @param attributeName
     * <p>
     * @return
     * <p>
     * @throws XmlFileParseException
     */
    public Element[] getElementsByTagNameAndHasAttribute(Element parentElement, String tagname, String attributeName) throws XmlFileParseException {
        if (document == null) {
            throw new XmlFileParseException("the  document must not be null");
        }
        ArrayList<Element> elem = new ArrayList<Element>();
        NodeList nodelist = document.getElementsByTagName(tagname);
        for (int i = 0, length = nodelist.getLength(); i < length; i++) {
            Element node = (Element) nodelist.item(i);
            if (node.hasAttribute(attributeName)) {
                elem.add(node);
            }
        }
        elem.trimToSize();
        return (Element[]) elem.toArray(new Element[1]);
    }

    /**
     * 创建Node对象
     *
     * @param nodeName
     * <p>
     * @return 新建的节点
     */
    public Node createNode(String nodeName) {
        return document.createElement(nodeName);
    }

    /**
     * Return the value of the named attribute. Returns null if this node does
     * not exist, or the attribute does not exist.
     */
    public String getAttribute(Element element, String name) {
        return element == null || !element.hasAttribute(name) ? null : element.getAttribute(name);
    }

    /**
     * Return the value of the named attribute. Returns defaultValue if this
     * node does not exist, or the attribute does not exist.
     */
    public String getAttribute(Element element, String name, String defaultValue) {
        return valueIfNotNull(getAttribute(element, name), defaultValue);
    }

    /**
     * Return text content of this element. Returns null if this node does not
     * exist, or it does not contain text.
     *
     * @param element
     */
    public String getText(Element element) {
        return element == null ? null : element.getTextContent();
    }

    /**
     * Return text content of this element. Returns defaultValue if this node
     * does not exist, or it does not contain text.
     */
    public String getText(Element element, String defaultValue) {
        return valueIfNotNull(getText(element), defaultValue);
    }

    private String valueIfNotNull(String value, String defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * 保存xml文档。如果对原xml文档有修改就保存 .不支持流打开xml源保存
     *
     * @throws XmlFileParseException document对象不存在
     */
    public void save() throws XmlFileParseException {
        if (XmlOpenModel == this.XmlOpenModel_STRING || XmlOpenModel == this.XmlOpenModel_FILE) {
            dom2xml(this.document, this.savePath);
        }

    }

    /**
     * 保存xml文档。如果对原xml文档有修改就保存 .不支持流打开xml源保存
     *
     * @param 保存的xml文件地址名称
     * <p>
     * @throws XmlFileParseException document对象不存在
     */
    public void saveAs(File xmlpath) throws XmlFileParseException {
        dom2xml(this.document, xmlpath);
    }

    /**
     * 重载方法。保存xml文档。如果对原xml文档有修改就保存 .不支持流打开xml源保存
     *
     * @param xmlpath 保存的xml文件地址名称，参数为字符串型
     * <p>
     * @throws XmlFileParseException document对象不存在
     */
    public void saveAs(String xmlpath) throws XmlFileParseException {
        dom2xml(this.document, new File(xmlpath));
    }

    /**
     * 静态方法，将dom保存在指定的xml文件中。不支持流打开xml源保存 。输出的默认编码为utf-8
     *
     * @param dom 要保存的cument类型文档，xmlpath另存为的xml文件路径
     * @param xmlpath
     *
     *
     * @throws XmlFileParseException document对象不存在 转换失败
     */
    public static void dom2xml(Document dom, File xmlpath) throws XmlFileParseException {
        dom.normalize();
        //boolean flag = true;
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.setOutputProperty("indent", "yes");
            DOMSource source = new DOMSource(dom);
            StreamResult result = null;
            result = new StreamResult(xmlpath);
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            throw new XmlFileParseException(MessageFormat.format("文档{0}转换为文件{1}出现异常", dom, xmlpath), ex);
        }
        //return flag;
    }

    /**
     * 文档输出为输出流,输出编码为utf-8
     *
     * @param dom
     * @param outputStream
     * <p>
     * @throws XmlFileParseException
     */
    public static void dom2stream(Document dom, OutputStream outputStream) throws XmlFileParseException {
        dom.normalize();
        //boolean flag = true;
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.setOutputProperty("indent", "yes");
            DOMSource source = new DOMSource(dom);
            StreamResult result = null;
            result = new StreamResult(outputStream);
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            throw new XmlFileParseException(MessageFormat.format("文档{0}转换为输出流{1}出现异常", dom, outputStream), ex);
        }
        //return flag;
    }

    /**
     * 获得document对象
     *
     * @return
     */
    public Document getDocument() {
        return this.document;
    }

    /**
     * 关闭对象，释放资源对象
     *
     * @param
     * @return
     * <p>
     * @since @throws
     */
    public void close() {
        this.domfact = null;
        this.dombuilder = null;
        this.document = null;
    }

    /*
     public String getgCurrentPath() throws UnsupportedEncodingException{
     String className = this.getClass().getName();
     String packageName = this.getClass().getPackage().getName();
     String classFileName = className.substring((packageName.length()+1))+".class";
     String classFilePath = this.getClass().getResource(classFileName).toString();
     String filePath =classFilePath.substring(0,classFilePath.length()-className.length()-14);
     return new URLDecoder().decode(filePath, "UTF-8").substring(6).replace("%20", " ");
     }
     */
    public static void main(String[] args) {
        try {
            XmlDomParser d = XmlDomParser.getInstance();
            //     File f = new File(WebKit.getWebInfDirPath(XmlDomParser.class) + "ego\\admin.xml");
            // BufferedReader b=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            // d.read(f);
            //d.getDocument().getDocumentElement().appendChild((Node)d.createElement("d"));
            String s;
            //d.newDocument();

            d.saveAs("D:/grade.xml");
            d.save();
            System.out.print(d.savePath);
            d.saveAs(d.savePath);
            // d.save(f);
            //  System.out.print(b.readLine());
            // System.out.print(d.getElementsByTagAndAttributeName("database", "type","sqlserver2000")[0].getElementsByTagName("driver-name").item(0).getFirstChild().getNodeValue());
            // System.out.print(d.getElementsByTagName("admin-name").item(0).getTextContent());
//        System.out.print( d.getElementsByTagName((Element)d.getElementsByTagName("admin-list", 0),"admin-name").item(0).getTextContent());
            // Element catalog3 = d.getDocument().createElement("catalog3");
//catalog3.setTextContent("Music");

            //   d.getElementsByTagName("admin-list").item(0).appendChild(d.getDocument().createElement("e").appendChild(d.getDocument().createTextNode("ee66666666")));
            //  d.getElementsByTagName("admin-list").item(0).getTextContent();
            //   d.save(f);
            //   System.out.print(XmlDomParser.class.getName());
            //System.out.print(XmlDomParser.class.getName().substring(XmlDomParser.class.getName().lastIndexOf(".")+1));
            //   File f=new File(XmlDomParser.class.getName().substring(XmlDomParser.class.getName().lastIndexOf(".")+1)+".class");
            //  System.out.print(d.getElementsByTagNameAndAttribute("admin","type","owner").length);
        } catch (Exception ex) {
            Logger.getLogger(XmlDomParser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
