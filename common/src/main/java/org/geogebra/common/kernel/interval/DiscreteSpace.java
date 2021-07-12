package org.geogebra.common.kernel.interval;

import java.util.stream.Stream;

public interface DiscreteSpace {
	void update(Interval interval, int count);

	DiscreteSpace diffMax(double max);

	DiscreteSpace diffMin(double min);

	Stream<Interval> values();

	void setInterval(double min, double max);

	double getStep();
}
