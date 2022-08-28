package com.example.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Modelová třida pro IP adresu
 * @author krems
 *
 */
public class IPaddress {
	@JsonProperty("ip")
	private String ip = "";

	public IPaddress(String ip) {
		this.ip = ip;
	}

	public IPaddress() {
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}