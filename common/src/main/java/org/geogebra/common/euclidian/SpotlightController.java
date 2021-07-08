package org.geogebra.common.euclidian;

import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.geos.GeoSpotlight;
import org.geogebra.common.main.App;

public class SpotlightController {

	private final Construction cons;
	private App app;

	public SpotlightController(App app) {
		cons = app.getKernel().getConstruction();
		this.app = app;
	}

	public void turnOff() {
		GeoSpotlight spotlight = spotlight();
		if (spotlight != null && spotlight.canBeRemoved()) {
			spotlight.remove();
		}
	}

	private GeoSpotlight spotlight() {
		return (GeoSpotlight) cons.getSpotlight();
	}

	public void turnOn() {
		app.getActiveEuclidianView().setSpotlight(true);
		app.setMode(EuclidianConstants.MODE_SELECT_MOW);
		GeoSpotlight spotlight = new GeoSpotlight(cons);
		app.getSelectionManager().addSelectedGeo(spotlight);
		spotlight.updateRepaint();

	}
}
