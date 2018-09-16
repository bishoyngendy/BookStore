package servlets.models;

import java.util.ArrayList;
import java.util.List;

public class DatatableModel {

    private long recordsTotal;
    private long recordsFiltered;
    private List<List<String>> data;

    public DatatableModel() {
        this.data = new ArrayList<List<String>>();
    }

    public List<List<String>> getData() {
        return data;
    }
    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }
    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }
    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }
}
