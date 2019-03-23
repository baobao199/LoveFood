package com.example.lovefood;

public class FoodOrdered {
    public String link,name,price,quantum;


    public FoodOrdered() {
    }

    public FoodOrdered(String link, String name, String price, String quantum) {
        this.link = link;
        this.name = name;
        this.price = price;
        this.quantum = quantum;
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

    public String getQuantum() {
        return quantum;
    }

    public void setQuantum(String quantum) {
        this.quantum = quantum;
    }
}
