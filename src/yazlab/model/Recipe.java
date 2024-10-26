/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.model;

/**
 *
 * @author kaan
 */
public class Recipe extends BaseEntity {

    private String name;
    private Category category;
    private Integer preperationTimeInMinute;
    private String instructions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getPreperationTimeInMinute() {
        return preperationTimeInMinute;
    }

    public void setPreperationTimeInMinute(Integer preperationTimeInMinute) {
        this.preperationTimeInMinute = preperationTimeInMinute;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString () {
        return name ;
    }
    
}
