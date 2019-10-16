package com.example.androidendpointtest.model;

public enum PushPriority {
	HIGH, NORMAL, LOW;

	public String getValue(){
		return name().toLowerCase();
	}
}
