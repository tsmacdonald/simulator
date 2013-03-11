package edu.wheaton.simulator.datastructures;

public class Value{
	private Object mX;
	
	public Value(Object x){
		mX = x;
	}
	
	@Override
	public String toString(){
		return mX.toString();
	}
	
	public Boolean getBoolean(){
		return Boolean.valueOf(toString());
	}
	
	public Double getDouble(){
		return Double.valueOf(toString());
	}
	
	public String getString(){
		return toString();
	}

	@Override
	public boolean equals(Object other) {
		return toString().equals(other.toString());
	}
}
