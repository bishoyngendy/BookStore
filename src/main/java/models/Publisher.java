package models;

/**
 * Publisher
 * Created on: May 05, 2018
 * Author: marc
 */
public class Publisher implements HasKey {
    private long id;
    private String name;
    private String address;
    private String phone;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getKey() {
        return this.id;
    }

    public void setKey(long key) {
        this.id = key;
    }
}
