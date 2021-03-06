package si.fri.core.primer_enums;

public enum OrderStatus {
    WANTED("wanted"),
    ORDERED("ordered"),
    NONE("No value"),
    RECEIVED("received");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static OrderStatus fromString(String name) {
        if (name == null || name.equalsIgnoreCase(""))
            return NONE;
        for (OrderStatus o : OrderStatus.values()) {
            if (o.name.equalsIgnoreCase(name)) {
                return o;
            }
        }
        return null;
    }
}