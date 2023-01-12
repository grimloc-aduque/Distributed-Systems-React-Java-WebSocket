package com.hw4.commerce;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Item implements Serializable {
    private String id;
    private String numUnitsToPurchase;
    private String totalItemPrice;
    private String serialNumber;
    private String productName;
    private String pricePerUnit;
    private String numUnitsInStock;
    public void setId(String id) {
        this.id = id;
    }
    public void setNumUnitsToPurchase(String numUnitsToPurchase) {
        this.numUnitsToPurchase = numUnitsToPurchase;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPricePerUnit(String pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public void setNumUnitsInStock(String numUnitsInStock) {
        this.numUnitsInStock = numUnitsInStock;
    }

    public String getId() {
        return id;
    }
    public String getNumUnitsToPurchase() {
        return numUnitsToPurchase;
    }
    public String getSerialNumber() {
        return serialNumber;
    }
    public String getProductName() {
        return productName;
    }
    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public String getPricePerUnit() {
        return pricePerUnit;
    }

    public String getNumUnitsInStock() {
       return numUnitsInStock;
    }

    public String[] toCSVRow(){
        String[] row = new String[7];
        row[0] = this.getId();
        row[1] = this.getNumUnitsToPurchase();
        row[2] = this.getTotalItemPrice();
        row[3] = this.getSerialNumber();
        row[4] = this.getProductName();
        row[5] = this.getPricePerUnit();
        row[6] = this.getNumUnitsInStock();
        return row;
    }

    public static Item fromCSVRow(String[] row){
        Item item = new Item();
        item.setId(row[0]);
        item.setNumUnitsToPurchase(row[1]);
        item.setTotalItemPrice(row[2]);
        item.setSerialNumber(row[3]);
        item.setProductName(row[4]);
        item.setPricePerUnit(row[5]);
        item.setNumUnitsInStock(row[6]);
        return item;
    }
}
