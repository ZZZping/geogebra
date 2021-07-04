package org.geogebra.common.kernel.interval;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class DiscreteSpaceImp implements DiscreteSpace {
	private Interval interval;
	private int count;
	private double step;

	public DiscreteSpaceImp(double low, double high, double step) {
		interval = new Interval(low, high);
		this.step = step;
		double length = interval.getLength() + 0.0;
		count = (int) Math.floor(length / step);
	}

	public DiscreteSpaceImp() {

	}

	@Override
	public void update(Interval interval, int count) {
		this.interval = interval;
		this.count = count;
		step = interval.getLength() / count;

	}

	@Override
	public DiscreteSpace extendMax(double max) {
		return new DiscreteSpaceImp(interval.getHigh() + step, max, step);
	}

	@Override
	public DiscreteSpace extendMin(double min) {
		return new DiscreteSpaceImp(min, interval.getLow() - step, step);
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
