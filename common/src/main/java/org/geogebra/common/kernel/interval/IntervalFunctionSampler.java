package org.geogebra.common.kernel.interval;

import org.geogebra.common.euclidian.EuclidianView;
import org.geogebra.common.kernel.geos.GeoFunction;

/**
 * Class to provide samples of the given function as a
 * list of (x, y) pairs, where both x and y are intervals.
 *
 * @author Laszlo
 */
public class IntervalFunctionSampler {

	private final IntervalFunction function;
	private EuclidianView view;
	private int numberOfSamples;
	private final DiscreteSpace space;
	private int pointIndex;
	private boolean addEmpty;

	/**
	 * @param geoFunction function to get sampled
	 * @param range (x, y) range.
	 * @param numberOfSamples the sample rate.
	 */
	public IntervalFunctionSampler(GeoFunction geoFunction, IntervalTuple range,
			int numberOfSamples) {
		this(geoFunction);
		this.numberOfSamples = numberOfSamples;
		update(range);
	}

	/**
	 * @param geoFunction function to get sampled
	 * @param range (x, y) range.
	 * @param view {@link EuclidianView}
	 */
	public IntervalFunctionSampler(GeoFunction geoFunction, IntervalTuple range,
			EuclidianView view) {
		this(geoFunction);
		this.view = view;
		update(range);
	}

	private IntervalFunctionSampler(GeoFunction geoFunction) {
		this.function = new IntervalFunction(geoFunction);
		space = new DiscreteSpaceImp();
	}

	/**
	 * Gets the samples with the predefined range and sample rate
	 *
	 * @return the sample list
	 */
	public IntervalTupleList result() {
		try {
			return evaluateOnSpace(space);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new IntervalTupleList();
	}

	private IntervalTupleList evaluateOnSpace(DiscreteSpace space) throws Exception {
		IntervalTupleList samples = new IntervalTupleList();
		addEmpty = true;
		pointIndex = 0;
		space.values().forEach(x -> {
					try {
						Interval y = function.evaluate(x);
						if (!y.isEmpty() || addEmpty) {
							IntervalTuple tuple = new IntervalTuple(x, y);
							samples.add(tuple);
							pointIndex++;
						}

						addEmpty = !y.isEmpty();

					} catch (Exception e) {
						e.printStackTrace();
					}
				});

		IntervalAsymptotes asymtotes = new IntervalAsymptotes(samples);
		asymtotes.process();
		return samples;
	}

	/**
	 * Updates the range on which sampler has to run.
	 *
	 * @param range the new (x, y) range
	 */
	public void update(IntervalTuple range) {
		space.update(range.x(), calculateNumberOfSamples());
	}

	private int calculateNumberOfSamples() {
		return numberOfSamples > 0 ? numberOfSamples : view.getWidth();
	}

	public IntervalTupleList extendMax(double max) {
		return evaluateAtDomain(space.extendMax(max));
	}

	private IntervalTupleList evaluateAtDomain(DiscreteSpace domain) {
		try {
			return evaluateOnSpace(domain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new IntervalTupleList();
	}

	public IntervalTupleList extendMin(double min) {
		return evaluateAtDomain(space.extendMin(min));
	}

	public int shrinkMax(double max) {
		return space.shrinkMax(max);
	}

	public int shrinkMin(double min) {
		return space.shrinkMin(min);
	}
}
