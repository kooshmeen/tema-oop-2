package app.EventMerch;

import lombok.Getter;

@Getter
public class Merch {
    private String name;
    private String description;
    private Integer price;
    public Merch(String name, String description, Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
