package com.bugtracker.entity.base;

public enum State {
  New("New"),
  open("open"),
  closed("closed"),
  fixed("fixed"),
  reopen("reopen");
	private State(String value){
		this.value=value;
	}
	
	
private String value;
	
	public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
  
  public static State getEnum(String value) {
	    for(State v : values())
	        if(v.getValue().equalsIgnoreCase(value)) return v;
	    throw new IllegalArgumentException();
	}

  public String toString() {
      return this.value; //will return , or ' instead of COMMA or APOSTROPHE
  }
}
