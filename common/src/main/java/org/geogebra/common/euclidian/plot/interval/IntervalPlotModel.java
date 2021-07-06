package org.geogebra.common.euclidian.plot.interval;

import org.geogebra.common.awt.GPoint;
import org.geogebra.common.euclidian.EuclidianView;
import org.geogebra.common.kernel.interval.Interval;
import org.geogebra.common.kernel.interval.IntervalFunctionSampler;
import org.geogebra.common.kernel.interval.IntervalTuple;
import org.geogebra.common.kernel.interval.IntervalTupleList;
import org.geogebra.common.util.debug.Log;

/**
 * Model for Interval plotter.
 *
 * @author laszlo
 */
public class IntervalPlotModel {
	private final IntervalTuple range;
	private final IntervalFunctionSampler sampler;
	private IntervalTupleList points;
	private IntervalPath path;
	private final EuclidianView view;
	private Interval oldDomain;

	/**
	 * Constructor
	 * @param range to plot.
	 * @param sampler to retrieve function data from.
	 * @param view {@link EuclidianView}
	 */
	public IntervalPlotModel(IntervalTuple range,
			IntervalFunctionSampler sampler,
			EuclidianView view) {
		this.range = range;
		this.sampler = sampler;
		this.view = view;
	}

	public void setPath(IntervalPath path) {
		this.path = path;
	}

	/**
	 * Updates what's necessary.
	 */
	public void update() {
		updatePath();
	}

		/**
		 * Updates the entire model.
		 */
	public void updateAll() {
		updateRanges();
		updateSampler();
		updatePath();
	}

	private void updateRanges() {
		range.set(view.domain(), view.range());
		oldDomain = view.domain();
	}

	void updateSampler() {
		sampler.update(range);
		points = sampler.result();
	}

	public boolean isEmpty() {
		return points.isEmpty();
	}

	public IntervalTupleList getPoints() {
		return points;
	}

	public void updatePath() {
		path.update();
	}

	/**
	 * update function domain to plot due to the visible x range.
	 */
	public void updateDomain() {
		if (view.domain().equals(oldDomain)) {
			return;
		}
		double oldMin = oldDomain.getLow();
		double oldMax = oldDomain.getHigh();
		oldDomain = view.domain();
		double min = view.domain().getLow();
		double max = view.domain().getHigh();
		if (oldMax < max && oldMin > min) {
			points = sampler.extendDomain(min, max);
		} else if (oldMax > max && oldMin < min) {
			shrinkDomain();
		} else if (oldMax != max) {
			moveDomain(oldMax - max);
		}
		logPointsCount();
	}

	private void shrinkDomain() {
		if (isEmpty()) {
			return;
		}

		shrinkMin();
		shrinkMax();
		points = sampler.result();
	}

	private void extendDomain() {
		extendMin();
		extendMax();
	}

	private void extendMin() {
		points = sampler.extendMin(view.getXmin());
	}

	private void shrinkMin() {
		sampler.shrinkMin(view.getXmin());
	}

	private void shrinkMax() {
		sampler.shrinkMax(view.getXmax());
	}

	private void extendMax() {
		points = sampler.extendMax(view.getXmax());
	}

	private void moveDomain(double difference) {
		if (difference < 0) {
			extendMax();
		} else {
			extendMin();
		}
	}

	private void logPointsCount() {
		Log.debug("points: " + points.count());
	}

	/**
	 * Clears the entire model.
	 */
	public void clear() {
		points.clear();
		path.reset();
	}

	GPoint getLabelPoint() {
		return path.getLabelPoint();
	}

	public IntervalTuple pointAt(int index) {
		return points.get(index);
	}

	/**
	 *
	 * @param point to check around
	 * @return if the function is ascending from point to the right.
	 */
	public boolean isAscending(IntervalTuple point) {
		if (point.index() > points.count() - 1) {
			return false;
		}

		IntervalTuple next = pointAt(point.index() + 1);
		return next != null && next.y().isGreaterThan(point.y());
	}
}