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
	public void setInterval(double min, double max) {
		interval.set(min, max);
		step = interval.getLength() / count;
	}

	@Override
	public DiscreteSpace extendMax(double max) {
		double diff = max - interval.getHigh();
		return new DiscreteSpaceImp(interval.getLow() + diff, max, step);
	}

	@Override
	public DiscreteSpace extendMin(double min) {
		double diff = interval.getLow() - min;
		return new DiscreteSpaceImp(min, interval.getHigh() - diff, step);
	}

	@Override
	public DiscreteSpace diffMax(double max) {
		return new DiscreteSpaceImp(interval.getHigh(), max, step);
	}

	@Override
	public DiscreteSpace diffMin(double min) {
		return new DiscreteSpaceImp(min, interval.getLow(), step);
	}

	@Override
	public int shrinkMax(double max) {
		interval.set(interval.getLow(), max);
		return 0;
	}

	@Override
	public int shrinkMin(double min) {
		interval.set(min, interval.getHigh());
		return 0;
	}

	@Override
	public Stream<Interval> values() {
		return DoubleStream.iterate(interval.getLow(), d -> d + step)
				.limit(count)
				.mapToObj(value -> new Interval(value, value + step));
	}

	@Override
	public double getStep() {
		return step;
	}
}
