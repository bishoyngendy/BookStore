package service.book.search;

/**
 * RelationalOperation
 * Created on: May 13, 2018
 * Author: marc
 */
public enum RelationalOperation {
    BIGGER_THAN_EQUAL, LESS_THAN_EQUAL, CONTAINS, START_WITH, EQUAL, NOT_EQUAL, NO_OPERATION;

    public String getValue() {
        switch(this) {
            case BIGGER_THAN_EQUAL : return ">=";
            case LESS_THAN_EQUAL: return "<=";
            case EQUAL: return "=";
            case NOT_EQUAL: return "!=";
        }
        return "";
    }
}
