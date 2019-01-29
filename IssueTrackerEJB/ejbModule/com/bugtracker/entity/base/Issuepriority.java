package com.bugtracker.entity.base;

public enum Issuepriority {
  hight("hight"),medium("medium"),low("low"),critical("critical");
	
	private Issuepriority(String value){
		this.value=value;
	}
	
	private String value;
	
	public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
  
  public static Issuepriority getEnum(String value) {
	    for(Issuepriority v : values())
	        if(v.getValue().equalsIgnoreCase(value)) return v;
	    throw new IllegalArgumentException();
	}

  public String toString() {
      return this.value; //will return , or ' instead of COMMA or APOSTROPHE
  }

}
