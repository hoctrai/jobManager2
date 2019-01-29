package com.tma.nht.model;

public enum JobState {
	All("All"),
	Planned("Planned"),
	WorkedPool("Worked Pool"),
	Execution("Execution");
	
	private final String states;
	
	JobState(String str){
		this.states = str;
	}
	
	public String getState(){
		return this.states;
	}
	
	JobState findEnum(String s){
		if(this.states.equals(s)){
			return this;
		}
		return null;
		
	}
}
