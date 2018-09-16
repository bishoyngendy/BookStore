package service.book.search;

/**
 * MultipleOperations
 * Created on: May 13, 2018
 * Author: marc
 */
public class MultipleOperations extends SearchParam {
    MultipleOperations(String key, RelationalOperation relationalOperation) {
        super(key, relationalOperation);
    }

    public String getEvaluationValue() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for (String eval : getValues()) {
            if (!first) {
                stringBuilder.append(" or ");
            }
            first = false;
            stringBuilder.append(getKey()).append("=").append(eval);
        }

        return stringBuilder.toString();
    }
}
