package models;

/**
 * Category
 * Created on: May 05, 2018
 * Author: marc
 */
public enum Category {
    SCIENCE, ART, RELIGION, HISTORY, GEOGRAPHY;

    String getValue(Category category) {
        String val = "";
        switch (category) {
            case SCIENCE:   val = "Science"; break;
            case ART: val = "Art"; break;
            case RELIGION: val = "Religion"; break;
            case HISTORY: val = "History"; break;
            case GEOGRAPHY: val = "Geography"; break;
        }

        return val;
    }
}
