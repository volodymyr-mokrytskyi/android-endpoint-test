package com.example.androidendpointtest.model;

public enum CarrierConfigType {
	KEY_CARRIER_VOLTE_AVAILABLE_BOOL("carrier_volte_available_bool"),
	KEY_ALLOW_ADDING_APNS_BOOL("allow_adding_apns_bool");

	private String configType;

	CarrierConfigType(String configType) {
		this.configType = configType;
	}

	public String getConfigType() {
		return this.configType;
	}

}
