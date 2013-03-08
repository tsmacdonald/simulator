package edu.wheaton.simulator.datastructures.expression;

public final class UnaryOperator {
	public static final UnaryOperator NOT = new UnaryOperator("!");
	public static final UnaryOperator NEGATE = new UnaryOperator("-");
	
	private UnaryOperator(String str){
		if(str==null || str.isEmpty())
			throw new IllegalArgumentException();
		
		mStr = str;
	}
	
	private String mStr;
	
	@Override
	public String toString(){
		return mStr;
	}
}
