package service.book.search;

/**
 * StartWithOperation
 * Created on: May 13, 2018
 * Author: marc
 */
public class StartWithOperation extends SearchParam {

    StartWithOperation(String key, RelationalOperation relationalOperation) {
        super(key, relationalOperation);
    }

    public String getEvaluationValue() {
        return getKey() + " like " + "\"" + getValues().get(0) + "%\"";
    }
}
