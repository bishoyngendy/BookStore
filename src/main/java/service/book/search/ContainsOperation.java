package service.book.search;

/**
 * ContainsOperation
 * Created on: May 13, 2018
 * Author: marc
 */
public class ContainsOperation extends SearchParam {
    ContainsOperation(String key, RelationalOperation relationalOperation) {
        super(key, relationalOperation);
    }

    public String getEvaluationValue() {
        return getKey() + " like " + "\"%" + getValues().get(0) +"%\"";
    }

    @Override
    public ContainsOperation clone() {
        return new ContainsOperation(this.getKey(), this.getOperation());
    }
}
