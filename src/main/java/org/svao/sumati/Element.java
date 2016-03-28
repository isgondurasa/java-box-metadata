package org.svao.sumati;

/**
 * Created by asviridov on 28/03/16.
 */
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
