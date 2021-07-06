package org.geogebra.common.kernel.interval;

import java.util.stream.Stream;

public interface DiscreteSpace {
	void update(Interval interval, int count);

	DiscreteSpace extendMax(double max);

	DiscreteSpace extendMin(double min);

	int shrinkMax(double max);

	int shrinkMin(double min);

	Stream<Interval> values();

	void setInterval(double min, double max);
}
