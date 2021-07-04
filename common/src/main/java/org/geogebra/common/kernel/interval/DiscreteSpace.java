package org.geogebra.common.kernel.interval;

import java.util.stream.Stream;

public interface DiscreteSpace {
	void update(Interval interval, int count);

	LinearSpace extendMax(double max);

	LinearSpace extendMin(double min);

	int shrinkMax(double max);

	int shrinkMin(double min);

	Stream<Interval> values();

}
