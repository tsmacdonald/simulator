package edu.wheaton.simulator.datastructures;

import java.security.InvalidParameterException;

import edu.wheaton.simulator.gridentities.GridEntity;
import net.sourceforge.jeval.VariableResolver;
import net.sourceforge.jeval.function.FunctionException;

public class ExpressionParameterResolver implements VariableResolver {

	private GridEntity xThis;
	private GridEntity xOther;
	private GridEntity xLocal;
	private GridEntity xGlobal;

	public ExpressionParameterResolver(GridEntity xThis, GridEntity xOther, GridEntity xLocal, GridEntity xGlobal){
		this.xThis = xThis;
		this.xOther = xOther;
		this.xLocal = xLocal;
		this.xGlobal = xGlobal;
	}
	
	@Override
	public String resolveVariable(String varStr) throws FunctionException {
		String[] paramSegments = varStr.split(".");
		String targetName = paramSegments[0];
		String fieldName = paramSegments[1];
		
		GridEntity target = resolveTarget(targetName);
		return target.getField(fieldName).getValue();
	}

	private GridEntity resolveTarget(String targetName) {
		if(targetName.equals("this"))
				return xThis;
		else if(targetName.equals("other"))
				return xOther;
		else if(targetName.equals("local"))
			return xLocal;
		else if(targetName.equals("global"))
			return xGlobal;
		else
			throw new InvalidParameterException();
	}
}
