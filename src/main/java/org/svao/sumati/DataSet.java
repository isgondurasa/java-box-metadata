package org.svao.sumati;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by asviridov on 22/03/16.
 */
public class DataSet {
    List <Object> dataSet = new ArrayList<Object>();

    public class Element {
        public String fieldName;
        public Object value;

        public Element(String field, Object val) {
            fieldName = field;
            value = val;
        }
    }

    public List createRow() {
        List <Element> row = new ArrayList<Element>();
        return row;
    }

    public Object getCurrentRow() {
        return dataSet.get(dataSet.size() - 1);
    }

    public void addToRow(String fieldName, String value) {
        ArrayList row = (ArrayList) getCurrentRow();
        Element el = new Element(fieldName, value);
        row.add(el);
    }

    public void addToDataSet(List row) {
        dataSet.add(row);
    }
}
