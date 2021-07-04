package org.geogebra.common.kernel.interval;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class DiscreteSpaceImp implements DiscreteSpace {
	private Interval interval;
	private int count;
	private double step;

	@Override
	public void update(Interval interval, int count) {
		this.interval = interval;
		this.count = count;
		step = interval.getLength() / count;

	}

	@Override
	public LinearSpace extendMax(double max) {
		return null;
	}

	@Override
	public LinearSpace extendMin(double min) {
		return null;
	}

	@Override
	public int shrinkMax(double max) {
		return 0;
	}

	@Override
	public int shrinkMin(double min) {
		return 0;
	}

	@Override
	public Stream<Interval> values() {
		return DoubleStream.iterate(interval.getLow(), d -> d + step)
				.limit(count)
				.mapToObj(value -> new Interval(value, value + step));
	}
}
