package org.geogebra.common.gui.view.table.column;

import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.geos.GeoList;
import org.geogebra.common.kernel.geos.GeoNumeric;
import org.geogebra.common.kernel.geos.GeoText;

public class TableValuesListColumn extends AbstractTableValuesColumn {

	private final GeoList list;

	/**
	 * Creates a list column.
	 * @param list list
	 * @param initialSize size of the cache
	 */
	public TableValuesListColumn(GeoList list, int initialSize) {
		super(list, initialSize);
		this.list = list;
	}

	@Override
	protected double calculateValue(int row) {
		GeoElement element = list.get(row);
		return element.evaluateDouble();
	}

	@Override
	protected String getHeaderName() {
		return list.getLabelSimple();
	}

	@Override
	protected String getInputValue(int row) {
		GeoElement element = list.get(row);
		if (element instanceof GeoText) {
			return ((GeoText) element).getTextString();
		}
		return super.getInputValue(row);
	}
}

