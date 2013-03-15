package edu.wheaton.simulator.expression;

import java.security.InvalidParameterException;

import net.sourceforge.jeval.VariableResolver;
import edu.wheaton.simulator.entity.Entity;

public class ParameterResolver implements VariableResolver {

	private Entity xThis;
	private Entity xOther;
	private Entity xLocal;
	private Entity xGlobal;

	public ParameterResolver(Entity xThis, Entity xOther,
			Entity xLocal, Entity xGlobal) {
		this.xThis = xThis;
		this.xOther = xOther;
		this.xLocal = xLocal;
		this.xGlobal = xGlobal;
	}

	@Override
	public String resolveVariable(String varStr) {
		String[] paramSegments = varStr.split(".");
		String targetName = paramSegments[0];
		String fieldName = paramSegments[1];

		Entity target = resolveTarget(targetName);
		return target.getField(fieldName).value().toString();
	}

	private Entity resolveTarget(String targetName) {
		if (targetName.equals("this"))
			return xThis;
		else if (targetName.equals("other"))
			return xOther;
		else if (targetName.equals("local"))
			return xLocal;
		else if (targetName.equals("global"))
			return xGlobal;
		else
			throw new InvalidParameterException();
	}
}
