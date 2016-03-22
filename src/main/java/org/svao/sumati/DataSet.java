package org.svao.sumati;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asviridov on 22/03/16.
 */

public class DataSet {

    private static DataSet instance;
    private DataSet() {}
    List <Object> dataSet = new ArrayList<Object>();

    public static DataSet getInstance() {
        if (instance == null) {
            instance = new DataSet();
        }
        return instance;
    }

    public class Element {
        public String fieldName;
        public Object value;

        public Element(String field, Object val) {
            fieldName = field;
            value = val;
        }

        public Element(Object val) {
            value = val;
        }
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

    public void addToRow(Object value) {
        ArrayList row = (ArrayList) getCurrentRow();
        Element el = new Element(value);
        row.add(el);
    }

    public void addRowToDataSet() {
        ArrayList row = (ArrayList) getCurrentRow();
        dataSet.add(row);
    }
}
