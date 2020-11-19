package org.geogebra.common.kernel.commands;

import org.geogebra.common.kernel.CircularDefinitionException;
import org.geogebra.common.kernel.Kernel;
import org.geogebra.common.kernel.advanced.AlgoSplit;
import org.geogebra.common.kernel.arithmetic.Command;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.geos.GeoList;
import org.geogebra.common.kernel.geos.GeoText;
import org.geogebra.common.main.MyError;
import org.geogebra.common.plugin.GeoClass;

public class CmdSplit extends CommandProcessor {
	/**
	 * Creates new command processor
	 * @param kernel kernel
	 */
	public CmdSplit(Kernel kernel) {
		super(kernel);
	}

	@Override
	public GeoElement[] process(Command c, EvalInfo info)
			throws MyError, CircularDefinitionException {
		int n = c.getArgumentNumber();
		GeoElement[] arg = resArgs(c);

		if (n == 2) {
			if (arg[0] instanceof GeoText && arg[1] instanceof GeoList
					&& ((GeoList) arg[1]).getElementType() == GeoClass.TEXT) {
				AlgoSplit algo = new AlgoSplit(cons, (GeoText) arg[0],
						(GeoList) arg[1]);
				algo.getOutput(0).setLabel(c.getLabel());
				return new GeoElement[]{algo.getOutput(0)};
			}
			throw argErr(c, arg[0]);
		}
		throw argNumErr(c);
	}
}
