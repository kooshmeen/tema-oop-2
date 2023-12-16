package app.EventMerch;

import lombok.Getter;

@Getter
public class Merch {
    private String name;
    private String description;
    private Integer price;
    public Merch(final String name, final String description, final Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
