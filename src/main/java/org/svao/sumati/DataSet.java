package org.svao.sumati;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.svao.sumati.config.XlsxConfig;

/**
 * Created by asviridov on 22/03/16.
 */

public class DataSet {

    private static DataSet instance;
    private DataSet() {}
    private List <Object> dataSet = new ArrayList<Object>();
    private List<String> headers = new ArrayList<String>();
    private List<String> types = new ArrayList<String>();

    public static DataSet getInstance() {
        if (instance == null) {
            instance = new DataSet();
        }
        return instance;
    }

    public void addToHeaders(String fieldName) {
        headers.add(fieldName);
    }

    public void addToTypes(String type) {
        types.add(type);
    }

    public List getHeaders() {
        return headers;
    }

    public List getTypes() {
        return types;
    }

    public void createRow() {
        List <Element> row = new ArrayList<Element>();
        dataSet.add(row);
    }

    public Object getCurrentRow() {
        return dataSet.get(dataSet.size() - 1);
    }

    public void addToRow(String fieldName, Object value) {
        ArrayList row = (ArrayList) getCurrentRow();
        Element el = new Element(fieldName, value);
        row.add(el);
    }

    public void addToDataRow(Object value) {
        ArrayList row = (ArrayList) getCurrentRow();
        Element el = new Element(value);
        row.add(el);
    }

    public void addRowToDataSet() {
        ArrayList row = (ArrayList) getCurrentRow();
        dataSet.add(row);
    }

    public HashMap toMapOfList() {
        ArrayList <String> headers = (ArrayList) getHeaders();
        ArrayList <String> types = (ArrayList) getTypes();

        HashMap<String, ArrayList> map = new HashMap<String, ArrayList>();

        for (Object row: dataSet) {
            ArrayList <Object> arrayEl = (ArrayList) row;
            int pos = 0;
            for (Object obj: arrayEl) {
                Element el = (Element) obj;
                el.setFieldName(headers.get(pos));
                el.setFieldType(types.get(pos));
                pos++;
            }
            Element el = (Element) arrayEl.get(arrayEl.size() - 1);
            map.put((String) el.getValue(), arrayEl);
        }

        return map;
    }

}