package com.example.crytocurrency_ltdt;

public class Cryto {
    private String symbol;
    private String uuid;
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    private String name;
    private String color;

    public Cryto(String uuid,String symbol, String name, String color, Double price, String rank, double pricelastPrice, double newPrice) {
        this.uuid=uuid;
        this.symbol = symbol;
        this.name = name;
        this.color = color;
        this.price = price;
        this.rank = rank;
        this.lastPrice = pricelastPrice;
        this.newPrice = newPrice;
    }

    private Double price;
    private String rank;
    private  double lastPrice;
    private  double newPrice;
}
