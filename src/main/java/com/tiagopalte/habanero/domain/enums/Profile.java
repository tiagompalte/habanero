package com.tiagopalte.habanero.domain.enums;

public enum Profile {
    ADMIN(1, "ROLE_ADMIN"),
    CLIENT(2, "ROLE_CLIENT");

    private int code;
    private String description;

    private Profile(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Profile toEnum(Integer code) {

        if(code == null) {
            return null;
        }

        for(Profile state : Profile.values()) {
            if(code.equals(state.getCode())) {
                return state;
            }
        }

        throw new IllegalArgumentException("Id inválido " + code);
    }
}
