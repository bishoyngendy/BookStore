package models.dto;

/**
 * BookSearchRange
 * Created on: May 13, 2018
 * Author: marc
 */
public class BookSearchRange {
    private long yearMin;
    private long yearMax;
    private float priceMin;
    private float priceMax;

    public long getYearMin() {
        return yearMin;
    }

    public void setYearMin(long yearMin) {
        this.yearMin = yearMin;
    }

    public long getYearMax() {
        return yearMax;
    }

    public void setYearMax(long yearMax) {
        this.yearMax = yearMax;
    }

    public float getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(float priceMin) {
        this.priceMin = priceMin;
    }

    public float getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(float priceMax) {
        this.priceMax = priceMax;
    }
}
