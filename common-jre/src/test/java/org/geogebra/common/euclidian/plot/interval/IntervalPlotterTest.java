package org.geogebra.common.euclidean.plot.interval;

import static org.junit.Assert.assertEquals;

import org.geogebra.common.BaseUnitTest;
import org.geogebra.common.euclidean.euclideanView;
import org.geogebra.common.kernel.geos.GeoFunction;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class IntervalPlotterTest extends BaseUnitTest {

	public static final String EMPTY_PATH = "R";

	@Test
	public void testDiscontinuity() {
		euclideanView view = getApp().getActiveeuclideanView();
		view.setRealWorldCoordSystem(Math.PI - 0.0001, 2 * Math.PI + 0.0001, -2, 2);
		IntervalPathPlotterMock gp = new IntervalPathPlotterMock();
		IntervalPlotter plotter = new IntervalPlotter(view, gp, 10);
		GeoFunction function = add("sqrt(sin(x))");
		plotter.enableFor(function);
		assertEquals(EMPTY_PATH, gp.getLog());
	}

	@Test
	public void testDiscontinoutyOfArgument() {
		euclideanView view = getApp().getActiveeuclideanView();
		view.setRealWorldCoordSystem(-1, -1E-7, -9, 9);
		IntervalPathPlotterMock gp = new IntervalPathPlotterMock();
		IntervalPlotter plotter = new IntervalPlotter(view, gp, 10);
		GeoFunction function = add("-1sqrt(-1/x)");
		plotter.enableFor(function);
		assertEquals(EMPTY_PATH, gp.getLog());
	}

	@Test
	public void testSinLnx() {
		euclideanView view = getApp().getActiveeuclideanView();
		view.setRealWorldCoordSystem(-10, 0, -9, 9);
		IntervalPathPlotterMock gp = new IntervalPathPlotterMock();
		IntervalPlotter plotter = new IntervalPlotter(view, gp, 10);
		GeoFunction function = add("sin(ln(x))");
		plotter.enableFor(function);
		assertEquals(EMPTY_PATH, gp.getLog());
	}

	@Test
	public void testSqrtReciprocalX() {
		euclideanView view = getApp().getActiveeuclideanView();
		view.setRealWorldCoordSystem(-10, 0, -9, 9);
		IntervalPathPlotterMock gp = new IntervalPathPlotterMock();
		IntervalPlotter plotter = new IntervalPlotter(view, gp, 100);
		GeoFunction function = add("sqrt(1/x)");
		plotter.enableFor(function);
		assertEquals(EMPTY_PATH, gp.getLog());
	}

	@Test
	public void testSecLnXInverse() {
		euclideanView view = getApp().getActiveeuclideanView();
		view.setRealWorldCoordSystem(0, 1, -7, 7);
		IntervalPathPlotterMock gp = new IntervalPathPlotterMock();
		IntervalPlotter plotter = new IntervalPlotter(view, gp, 20);
		GeoFunction function = add("(-8)/sec(ln(x))");
		plotter.enableFor(function);
		assertEquals(EMPTY_PATH, gp.getLog());
	}
}
