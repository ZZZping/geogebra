package org.geogebra.common.gui.view.table.dialog;

public class StatisticRow {
	private final String heading;
	private final String value;
	private boolean isLaTeX;

	/**
	 * @param heading heading row
	 * @param value value row
	 */
	public StatisticRow(String heading, String value) {
		this.value = value;
		this.heading = heading;
	}

	/**
	 * @return heading row
	 */
	public String getHeading() {
		return heading;
	}

	/**
	 * @return value row
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param heading heading row
	 * @param formula LaTeX formula (value row)
	 * @return stats dialog entry
	 */
	public static StatisticRow withLaTeX(String heading, String formula) {
		StatisticRow row = new StatisticRow(heading, formula);
		row.isLaTeX = true;
		return row;
	}

	/**
	 * @return whether this needs LaTeX to render value
	 */
	public boolean isLaTeX() {
		return isLaTeX;
	}
}
