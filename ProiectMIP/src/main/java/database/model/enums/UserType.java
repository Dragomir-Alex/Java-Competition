package database.model.enums;

public enum UserType {
    USER("USER"), ADMIN("ADMIN");

    private String code;
    private UserType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}