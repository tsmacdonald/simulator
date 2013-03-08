package edu.wheaton.simulator.datastructures;

import edu.wheaton.simulator.datastructures.expression.Primitive;
import edu.wheaton.simulator.datastructures.expression.PrimitiveEvaluatable;
import edu.wheaton.simulator.gridentities.GridEntity;
import edu.wheaton.simulator.simulation.Grid;

public class ReferenceNode implements PrimitiveEvaluatable {
	
	private enum Concerns {
		ME, OTHER, LOCAL, GLOBAL
	}
	
	private String fieldName;
	private Concerns concerns;
	
	public ReferenceNode(String fieldName, Concerns concerns) {
		this.fieldName = fieldName;
		this.concerns = concerns;
	}
	
	@Override
	public Primitive evaluate(GridEntity me, GridEntity other,
			GridEntity local, Grid global) {
		if(concerns == Concerns.ME) {
			return me.getField(fieldName);
		}
		else if(concerns == Concerns.OTHER) {
			return other.getField(fieldName);
		}
		else if(concerns == Concerns.LOCAL) {
			return local.getField(fieldName);
		}
		else { //global
			return global.getField(fieldName);
		}
	}

}
