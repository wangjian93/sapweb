package com.ivo.sapweb.sap.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ivo.sapweb.common.util.Dom4jUtils;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * 解析bapiMap的xml
 * @author wangjian
 * @date 2018/9/17
 */
public class BapiMapXmlParse {

    public static Map getBapiMap(String mapConfigFile) {
        List list = parseBapiMapFilesXml(mapConfigFile);
        Map map = parseBapiMapXml(list);
        return map;
    }

    /**
     * 从xml中获取所以有的bapiMAP的xml文件路径
     * @param mapConfigFile
     * @return
     */
    private static List<String> parseBapiMapFilesXml(String mapConfigFile) {
        Document document = Dom4jUtils.getXmlByResouorcePath(mapConfigFile);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements("bapiMap");
        List list = new ArrayList();

        // 遍历所有的bapiMap节点
        for(Element element : elementList) {
            list.add(element.attributeValue("resource"));
        }

        return list;
    }

    /**
     * 解析处理所有bapiMap xml文件
     * @param paths
     * @return
     */
    private static Map parseBapiMapXml(List<String> paths) {

        Map bapiMap = new HashMap();

        // 遍历path集合，解析每一个bapi的xml
        for(String path : paths) {

            Document document = Dom4jUtils.getXmlByResouorcePath(path);
            Element root = document.getRootElement();
            List<Element> bapiElements = root.elements("bapi");
            // 遍历所有的bapi节点
            for(Element bapiElement : bapiElements) {
                String bapiName = bapiElement.attributeValue("name");

                List tableList = new ArrayList();
                List<Element> tableElements = bapiElement.elements("table");

                // 遍历所有的table节点
                for(Element tableElement : tableElements) {
                    String tableName = tableElement.attributeValue("name");
                    String model = tableElement.attributeValue("model");

                    List columnList = new ArrayList();
                    List<Element> columnElements = tableElement.elements("column");

                    // 遍历所有的column节点
                    for(Element columnElement : columnElements) {
                        String columnName = columnElement.attributeValue("name");
                        String property = columnElement.attributeValue("property");
                        String type = columnElement.attributeValue("type");

                        Column column = new Column();
                        column.setName(columnName);
                        column.setProperty(property);
                        column.setType(type);

                        columnList.add(column);
                    }

                    Table table = new Table();
                    table.setName(tableName);
                    table.setModel(model);
                    table.setColumns(columnList);

                    tableList.add(table);
                }

                bapiMap.put(bapiName, tableList);
            }
        }

        return bapiMap;
    }
}
