package app.postservice.utils.enums;

import java.util.ArrayList;
import java.util.List;

public class Item<T extends BaseEnum> {
    private final List<T> items;

    public Item(List<T> items) {
        this.items = new ArrayList<>(items);
    }

    public List<String> getCodes() {
        return items.stream()
                .map(T::getCode)
                .toList();
    }

    public List<String> getNames() {
        return items.stream()
                .map(T::getValue)
                .toList();
    }
}
