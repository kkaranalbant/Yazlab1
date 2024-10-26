/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.model;

/**
 *
 * @author kaan
 */
public class Ingredient extends BaseEntity {

    private String name;

    private Float amount;

    private Unit unit;

    private Float unitPrice;

    public Ingredient() {

    }

    public Ingredient(String name, Float amount, Unit unit, Float unitPrice) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
