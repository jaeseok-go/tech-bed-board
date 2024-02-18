package js.toy.member.domain;

public enum MemberRole {
    USER("USER"),
    ADMIN("ADMIN");

    private final static String PREFIX = "ROLE_";

    private String key;

    MemberRole(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public String getFullKey() {
        return PREFIX + this.key;
    }
}
