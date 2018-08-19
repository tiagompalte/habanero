package com.tiagopalte.habanero.domain.enums;

public enum ClientType {
	
	NATURAL_PERSON(1, "Pessoa Física"),
	LEGAL_PERSON(2, "Pessoa Jurídica");
	
	private int code;
	private String description;
	
	private ClientType(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static ClientType toEnum(Integer code) {
		
		if(code == null) {
			return null;
		}
		
		for(ClientType type : ClientType.values()) {
			if(code.equals(type.getCode())) {
				return type;
			}
		}
		
		throw new IllegalArgumentException("Id inválido " + code);
	}
}
