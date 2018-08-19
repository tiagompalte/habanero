package com.tiagopalte.habanero.domain.enums;

public enum PaymentState {
	
	PENDING(1, "Pendente"),
	SETTLED(2, "Quitado"),
	CANCELED(3, "Cancelado");
	
	private int code;
	private String description;
	
	private PaymentState(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static PaymentState toEnum(Integer code) {
		
		if(code == null) {
			return null;
		}
		
		for(PaymentState state : PaymentState.values()) {
			if(code.equals(state.getCode())) {
				return state;
			}
		}
		
		throw new IllegalArgumentException("Id inválido " + code);
	}

}
