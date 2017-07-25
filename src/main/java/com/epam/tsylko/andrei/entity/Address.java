package com.epam.tsylko.andrei.entity;


import java.io.Serializable;

public class Address implements Serializable{

    private static final long serialVersionUID = -8946324271752867953L;
    private int id;
    private String country;
    private String city;
    private String street;
    private int house;
    private int room;

    public Address() {

    }

    public Address(int id) {
        this.id = id;
    }


    public Address(int id, String country, String city, String street, int house, int room) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (id != address.id) return false;
        if (house != address.house) return false;
        if (room != address.room) return false;
        if (country != null ? !country.equals(address.country) : address.country != null) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        return street != null ? street.equals(address.street) : address.street == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + house;
        result = 31 * result + room;
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Address{");
        sb.append("id=").append(id);
        sb.append(", country='").append(country).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", house=").append(house);
        sb.append(", room=").append(room);
        sb.append('}');
        return sb.toString();
    }
}
