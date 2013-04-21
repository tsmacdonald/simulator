package edu.wheaton.simulator.behavior;

import edu.wheaton.simulator.datastructure.Grid;
import edu.wheaton.simulator.entity.Agent;
import edu.wheaton.simulator.entity.Prototype;
import edu.wheaton.simulator.expression.Expression;
import net.sourceforge.jeval.EvaluationException;

public class ClonePrototype extends AbstractBehavior{

		/**
		 * Main (only) constructor for ClonePrototype
		 * @param xExpr
		 *            The expression for the x-coordinate where the clone will be
		 *            placed
		 * @param yExpr
		 *            The expression for the y-coordinate where the clone will be
		 *            placed
		 * @param prototypeName
		 * 			  The name of the prototype from the list of prototypes that
		 * 			  will be cloned.
		 */
		@Override
		public String getName() {
			return "clonePrototype";
		}

		@Override
		public Integer numArgs() {
			return 3;
		}

		/**
		 * Clones a prototype based on it's name into the grid at the x, y position.
		 * cloneProrotype(x, y, protypeName)
		 * returns false if position is invalid or the prototype name is invalid.
		 */
		@Override
		public String execute(String[] args) throws EvaluationException {
			Agent target = resolveAgent("this");
			Grid grid = target.getGrid();
			
			Prototype proto = Prototype.getPrototype(args[2].replaceAll("'", ""));
			if(proto == null){
				return Expression.FALSE;
			}

			Integer x = Double.valueOf(args[0]).intValue();
			Integer y = Double.valueOf(args[0]).intValue();
					
			if(grid.isValidCoord(x, y) && grid.getAgent(x,y)==null){
				grid.addAgent(proto.createAgent(grid), x, y);
				return Expression.TRUE;
			}
			return Expression.FALSE;
		}
}
