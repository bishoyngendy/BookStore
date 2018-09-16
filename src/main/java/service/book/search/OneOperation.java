package service.book.search;

/**
 * OneOperation
 * Created on: May 13, 2018
 * Author: marc
 */
public class OneOperation extends SearchParam {

    OneOperation(String key, RelationalOperation relationalOperation) {
        super(key, relationalOperation);
    }

    public String getEvaluationValue() {
        return getKey() + getOperation().getValue() + "\'" + getValues().get(0) + "\'";
    }
}
