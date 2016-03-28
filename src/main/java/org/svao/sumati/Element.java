package org.svao.sumati;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by asviridov on 28/03/16.
 */
public class Element {

    private String fieldName;
    private String fieldType;
    private Object value;


    public Element(String field, Object val) {
        fieldName = field;
        value = val;
    }

    public Element(Object val) {
        value = val;
    }

    public Element(String field, String _fieldType, Object val) {
        fieldName = field;
        fieldType = _fieldType;
        value = val;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public Object getValue() {
        return value;
    }

    public void setFieldName(String _fieldName) {

        String [] s = _fieldName.split(" ");
        _fieldName = s[0].toLowerCase();
        for (int i = 0; i < s.length; i++) {
            if (i > 0) {
                s[i] = s[i].substring(0,1).toUpperCase() + s[i].substring(1);
                _fieldName += s[i];
            }
        }
        fieldName = _fieldName.replace("-", "");
    }

    public void setFieldType(String _fieldType) {
        fieldType = _fieldType;
    }

    public void setValue(Object val) {
        value = val;
    }



}
