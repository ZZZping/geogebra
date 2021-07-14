package org.geogebra.common.gui.view.table.dialog;

import java.util.ArrayList;
import java.util.List;

import org.geogebra.common.kernel.Kernel;
import org.geogebra.common.kernel.StringTemplate;
import org.geogebra.common.kernel.arithmetic.Command;
import org.geogebra.common.kernel.arithmetic.MyVecNode;
import org.geogebra.common.kernel.commands.AlgebraProcessor;
import org.geogebra.common.kernel.kernelND.GeoElementND;
import org.geogebra.common.kernel.kernelND.GeoEvaluatable;
import org.geogebra.common.kernel.statistics.FitAlgo;
import org.geogebra.common.kernel.statistics.Regression;
import org.geogebra.common.kernel.statistics.Stat;
import org.geogebra.common.util.debug.Log;

public class RegressionBuilder {
	private final GeoEvaluatable xVal;
	private final GeoEvaluatable yVal;
	private final Kernel kernel;

	/**
	 * @param xVal list of x-values
	 * @param yVal list of y-values
	 */
	public RegressionBuilder(GeoEvaluatable xVal, GeoEvaluatable yVal) {
		this.xVal = xVal;
		this.yVal = yVal;
		this.kernel = xVal.getKernel();
	}

	/**
	 * @param regression regression type
	 * @param degree polynomial degree (used only for polynomial regression)
	 * @return regression parameters + coeffficient of determination
	 */
	public List<StatisticRow> getRegression(Regression regression, int degree) {
		MyVecNode points = new MyVecNode(kernel, xVal, yVal);
		Command cmd = regression.buildCommand(kernel, degree, points);
		List<StatisticRow> stats = new ArrayList<>();
		try {
			AlgebraProcessor algebraProcessor = kernel.getAlgebraProcessor();
			GeoElementND geo = algebraProcessor.processValidExpressionSilent(cmd)[0];
			FitAlgo fitAlgo = (FitAlgo) geo.getParentAlgorithm();
			double[] coeffs = fitAlgo.getCoeffs();
			char coeffName = 'a';
			stats.add(StatisticRow.withLaTeX("Formula",
					regression.getFormula(degree)));
			for (double coeff: coeffs) {
				stats.add(new StatisticRow(coeffName == 'a' ? "Parameters" : null,
						coeffName + " = "
						+ kernel.format(coeff, StringTemplate.defaultTemplate)));
				coeffName++;
			}
			Command residualCmd = new Command(kernel, Stat.RSQUARE.getCommandName(), false);
			residualCmd.addArgument(points.wrap());
			residualCmd.addArgument(geo.wrap());
			GeoElementND residual = algebraProcessor.processValidExpressionSilent(residualCmd)[0];
			String lhs = Stat.RSQUARE.getLHS(kernel.getLocalization(), "");
			stats.add(new StatisticRow("CoefficientOfDetermination", lhs + " = "
					+ kernel.format(residual.evaluateDouble(), StringTemplate.defaultTemplate)));
		} catch (Exception e) {
			Log.error(e);
		}
		return stats;
	}
}
