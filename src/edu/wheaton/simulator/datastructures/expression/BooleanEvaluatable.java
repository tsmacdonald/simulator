package edu.wheaton.simulator.datastructures.expression;

import net.sourceforge.jeval.EvaluationException;
import edu.wheaton.simulator.gridentities.GridEntity;
import edu.wheaton.simulator.simulation.Grid;

public interface BooleanEvaluatable {

	public Boolean evaluate(GridEntity me, GridEntity other, GridEntity local, Grid global) throws EvaluationException;
}
