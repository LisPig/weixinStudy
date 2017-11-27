/*----------javabean---------
 * @功能说明：运用流解析机制解析xml文档
 * @**方法列表**
 * 最后修改日期：2013-3-14:22:55
 */
package com.ego.core.file.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * <p > <b>运用流解析机制解析xml文档</b> <p >
 *
 *
 *
 *
 */
public class XmlStaxParser {

    private File xmlPath = null;
    private XMLInputFactory xmlInputFactory = null;
    private XMLStreamReader xmlStreamReader = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;

    /**
     * 传入要读取或写入的xml文件地址对象
     *
     * @param xmlPathxml文件地址
     * @throws XMLStreamException
     */
    private XmlStaxParser(File xmlPath) throws XMLStreamException {
        this.xmlPath = xmlPath;
    }

    /**
     * 获得刘解析器对象
     *
     * @param xmlPath 传入要读取或写入的xml文件地址对象
     * @return
     * @throws XMLStreamException
     */
    public static XmlStaxParser getInstance(File xmlPath) throws XMLStreamException {
        return new XmlStaxParser(xmlPath);
    }

    /**
     * 打开读取xml解析器
     *
     * @return
     * @throws XMLStreamException XMLStreamReader读取流
     * @throws FileNotFoundException
     */
    public XMLStreamReader openRead() throws XMLStreamException, FileNotFoundException {
        xmlInputFactory = XMLInputFactory.newInstance();//得到解析工厂
        this.inputStream = new FileInputStream(this.xmlPath);
        xmlStreamReader = xmlInputFactory.createXMLStreamReader(this.inputStream); // 取得dom解析器
        return this.xmlStreamReader;
    }

    /**
     * 关闭读取xml解析器。
     */
    public void closeRead() throws IOException, XMLStreamException {
        this.inputStream.close();
        this.xmlStreamReader.close();
        this.inputStream = null;
        this.xmlStreamReader = null;
        this.xmlInputFactory = null;
    }
}
