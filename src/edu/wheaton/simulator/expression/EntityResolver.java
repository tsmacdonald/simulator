package edu.wheaton.simulator.expression;

import java.util.Map;

import net.sourceforge.jeval.VariableResolver;
import edu.wheaton.simulator.entity.Entity;

public class EntityResolver implements VariableResolver {

	private Map<String,Entity> entities;

	public EntityResolver(Map<String,Entity> entities) {
		this.entities = entities;
	}

	@Override
	public String resolveVariable(String varStr) {
		String[] paramSegments = varStr.split(".");
		String entityName = paramSegments[0];
		Entity target = resolveTarget(entityName);
		String fieldName = paramSegments[1];
		return target.getField(fieldName).value().toString();
	}

	private Entity resolveTarget(String targetName) {
		return entities.get(targetName);
	}
}
