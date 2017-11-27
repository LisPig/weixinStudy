package com.ego.core.util;

import com.ego.core.file.xml.XmlDomParser;
import com.ego.core.file.xml.XmlFileParseException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class UtilXML {
    
    public static Element getChildByTagName(Node node, String tagName) {
        if (node.getNodeType() != 1) {
            throw new RuntimeException(tagName + "节点格式不正确,非Element类型。");
        }
        Element el = (Element) node;
        
        NodeList nodeList = el.getElementsByTagName(tagName);
        int length = nodeList.getLength();
        if ((nodeList == null) || (length == 0)) {
            return null;
        }
        return (Element) nodeList.item(0);
    }
    
    public static Element getChildByTagName(Document doc, String tagName) {
        NodeList nodeList = doc.getElementsByTagName(tagName);
        int length = nodeList.getLength();
        if ((nodeList == null) || (length == 0)) {
            return null;
        }
        return (Element) nodeList.item(0);
    }
    
    public static Element getChildByAttrName(Node node, String attrName, String attrValue) {
        NodeList nodeList = node.getChildNodes();
        int i = 0;
        for (int len = nodeList.getLength(); i < len; i++) {
            Node n = nodeList.item(i);
            if (n.getNodeType() == 1) {
                Element el = (Element) n;
                if (attrValue.equals(el.getAttribute(attrName))) {
                    return el;
                }
            }
        }
        return null;
    }
    
    public static String document2XML(Document object, String encode) throws TransformerConfigurationException, TransformerException {
        encode = UtilValidate.isEmpty(encode) ? "utf-8" : encode;//"GB2312"
        Document doc = object;
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transFormer = transFactory.newTransformer();
        transFormer.setOutputProperty(OutputKeys.ENCODING, encode);
        DOMSource domSource = new DOMSource(doc);
        StringWriter sw = new StringWriter();
        StreamResult xmlResult = new StreamResult(sw);
        transFormer.transform(domSource, xmlResult);
        return sw.toString();
        
    }
    
    public static void main(String[] a) {
        try {
            XmlDomParser parser;
            //  private final TransformerFactory tffactory;

            parser = XmlDomParser.getInstance();
            Document d = parser.newDocument();
            Element x = d.createElement("xml");
            Element p = d.createElement("p");
            p.setTextContent("444444");
            x.appendChild(p);
            d.appendChild(x);
            try {
                System.out.println(document2XML(d, null));
                //格式化工厂对象
                //  tffactory = TransformerFactory.newInstance();
            } catch (TransformerException ex) {
                Logger.getLogger(UtilXML.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (XmlFileParseException ex) {
            Logger.getLogger(UtilXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
