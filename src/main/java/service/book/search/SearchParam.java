package service.book.search;

import java.util.List;

public abstract class SearchParam {
    private RelationalOperation operation;
    private String key;
    private List<String> values;

    SearchParam(String key, RelationalOperation relationalOperation) {
        this.key = key;
        this.operation = relationalOperation;
    }

    public RelationalOperation getOperation() {
        return operation;
    }

    public void setOperation(RelationalOperation operation) {
        this.operation = operation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchParam that = (SearchParam) o;

        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    /**.
     * @return the evaluation value to run by the DBMS
     */
    public abstract String getEvaluationValue();
}
