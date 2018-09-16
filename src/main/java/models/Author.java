package models;

/**
 * Author
 * Created on: May 05, 2018
 * Author: marc
 */
public class Author implements HasKey {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getKey() {
        return this.id;
    }

    public void setKey(long key) {
        this.id = key;
    }
}
