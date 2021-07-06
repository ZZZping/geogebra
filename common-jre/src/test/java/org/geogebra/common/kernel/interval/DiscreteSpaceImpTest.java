package org.geogebra.common.kernel.interval;


import static org.geogebra.common.kernel.interval.IntervalTest.interval;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DiscreteSpaceImpTest {

	@Test
	public void testValues() {
		DiscreteSpaceImp space = new DiscreteSpaceImp();
		space.update(new Interval(0, 5), 10);
		Assert.assertEquals(
				Arrays.asList(
						interval(0, 0.5),
						interval(0.5, 1.0),
						interval(1.0, 1.5),
						interval(1.5, 2.0),
						interval(2.0, 2.5),
						interval(2.5, 3.0),
						interval(3.0, 3.5),
						interval(3.5, 4.0),
						interval(4.0, 4.5),
						interval(4.5, 5.0)
				)
				, space.values().collect(Collectors.toList()));
	}

	@Test
	public void testExtendMax() {
		DiscreteSpaceImp space = new DiscreteSpaceImp();
		space.update(new Interval(0, 5), 10);
		DiscreteSpace subSpace = space.extendMax(7);
		Assert.assertEquals(
				Arrays.asList(
						interval(5.5, 6.0),
						interval(6.0, 6.5),
						interval(6.5, 7.0)
				)
				, subSpace.values().collect(Collectors.toList()));
	}

	@Test
	public void testExtendMin() {
		DiscreteSpaceImp space = new DiscreteSpaceImp();
		space.update(new Interval(0, 5), 10);
		DiscreteSpace subSpace = space.extendMin(-2.0);
		Assert.assertEquals(
				Arrays.asList(
						interval(-2.0, -1.5),
						interval(-1.5, -1.0),
						interval(-1.0, -0.5)
				)
				, subSpace.values().collect(Collectors.toList()));
	}

	@Test
	public void testExtendMaxInteger() {
		DiscreteSpaceImp space = new DiscreteSpaceImp();
		space.update(new Interval(0, 1), 1);
		DiscreteSpace subSpace = space.extendMax(3);
		Assert.assertEquals(
				Arrays.asList(
						interval(2, 3)
				)
				, subSpace.values().collect(Collectors.toList()));
	}

	@Test
	public void testExtendMinInteger() {
		DiscreteSpaceImp space = new DiscreteSpaceImp();
		space.update(new Interval(0, 1), 1);
		DiscreteSpace subSpace = space.extendMin(-3);
		Assert.assertEquals(
				Arrays.asList(
						interval(-3, -2),
						interval(-2, -1)
				)
				, subSpace.values().collect(Collectors.toList()));
	}
}