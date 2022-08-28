package com.example.application.model;

/**
 * Enum se stavy pro událost
 * @author krems
 *
 */
public enum EventState {
	WAITING("Zařazena do fronty"),RUNNING("Běží"),STOPED("Zastavena"),ERROR("Chyba");
	
	
	private String text;
	EventState(String text){
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	
}
