package edu.wheaton.simulator.datastructures.expression;

public final class BinaryOperator {
	public static final BinaryOperator EQUAL = new BinaryOperator("==");
	public static final BinaryOperator NOT_EQUAL = new BinaryOperator("!=");
	public static final BinaryOperator GREATER_THAN = new BinaryOperator(">");
	public static final BinaryOperator LESS_THAN = new BinaryOperator("<");
	public static final BinaryOperator GREATER_THAN_OR_EQUAL = new BinaryOperator(">=");
	public static final BinaryOperator LESS_THAN_OR_EQUAL = new BinaryOperator("<=");
	
	public static final BinaryOperator AND = new BinaryOperator("&&");
	public static final BinaryOperator OR = new BinaryOperator("||");
	
	public static final BinaryOperator ADDITION = new BinaryOperator("+");
	public static final BinaryOperator SUBTRACTION = new BinaryOperator("-");
	public static final BinaryOperator MULTIPLICATION = new BinaryOperator("*");
	public static final BinaryOperator DIVISION = new BinaryOperator("/");
	public static final BinaryOperator MODULUS = new BinaryOperator("%");
	
	private BinaryOperator(String str){
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
