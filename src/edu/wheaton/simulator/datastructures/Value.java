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
	
	public Boolean toBool(){
		return Boolean.valueOf(toString());
	}
	
	public Double toDouble(){
		return Double.valueOf(toString());
	}
	
	public Integer toInt(){
		return Integer.valueOf(toString());
	}
	
	public Character toChar(){
		return Character.valueOf(toString().charAt(0));
	}

	@Override
	public boolean equals(Object other) {
		return toString().equals(other.toString());
	}
}
