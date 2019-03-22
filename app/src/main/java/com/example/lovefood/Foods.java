package com.example.lovefood;

public class Foods {
    public String link, name, price;

    public Foods() {

    }

    public Foods(String link, String name, String price) {
        this.link = link;
        this.name = name;
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
