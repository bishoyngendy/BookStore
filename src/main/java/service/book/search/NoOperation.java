package service.book.search;

/**
 * NoOperation
 * Created on: May 13, 2018
 * Author: marc
 */
public class NoOperation extends SearchParam{

    NoOperation(String key, RelationalOperation relationalOperation) {
        super(key, relationalOperation);
    }

    public String getEvaluationValue() {
        return getKey();
    }
}
