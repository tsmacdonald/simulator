package edu.wheaton.simulator.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import net.sourceforge.jeval.VariableResolver;
import net.sourceforge.jeval.function.FunctionException;
import edu.wheaton.simulator.entity.Entity;

public class EntityFieldResolver implements VariableResolver {

	private Map<String, Entity> entityMap;

	/*
	 * default constructor
	 */
	protected EntityFieldResolver() {
		entityMap = new HashMap<String, Entity>();
	}

	/*
	 * copy constructor
	 */
	protected EntityFieldResolver(EntityFieldResolver resolver) {
		entityMap.putAll(resolver.entityMap);
	}

	@Override
	public String resolveVariable(String variableName)
			throws FunctionException {

		String[] args = variableName.split("\\x2e");

		if (args.length != 2) {
			return null;
		}

		String targetName = args[0];
		String fieldName = args[1];

		Entity target = entityMap.get(targetName);
		if (target == null) {
			System.err.println("##Target entity not found##");
			return null;
		}
		try {
			String toReturn = target.getField(fieldName).getValue().toString();
			return toReturn;
		} catch (NoSuchElementException e) {
			System.err.println("##NoSuchElementException thrown##");
			return null;
		}
	}

	protected void addEntity(String name, Entity entity) {
		entityMap.put(name, entity);
	}

	public Entity getEntity(String name) {
		return entityMap.get(name);
	}
}
