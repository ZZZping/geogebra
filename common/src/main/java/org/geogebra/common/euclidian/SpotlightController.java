package org.geogebra.common.euclidian;

import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.geos.GeoSpotlight;
import org.geogebra.common.main.App;
import org.geogebra.common.util.GTimer;

public class SpotlightController {

	public static final int BOX_DISAPPEAR_DELAY = 3600;
	private final Construction cons;
	private final GTimer disappearBoxTimer;
	private final App app;

	public SpotlightController(App app) {
		cons = app.getKernel().getConstruction();
		this.app = app;
		disappearBoxTimer = app.newTimer(this::disappearBoundingBox, BOX_DISAPPEAR_DELAY);
	}

	public void turnOff() {
		GeoSpotlight spotlight = spotlight();
		if (spotlight != null && spotlight.canBeRemoved()) {
			disappearBoxTimer.stop();
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
		disappearBox();
		spotlight.updateRepaint();

	}

	public void disappearBox() {
		if (app.getSelectionManager().containsSelectedGeo(spotlight())) {
			disappearBoxTimer.start();
		}
	}

	public void keepBox() {
		disappearBoxTimer.stop();
	}

	private void disappearBoundingBox() {
		app.getSelectionManager().clearSelectedGeos();
		app.getActiveEuclidianView().setBoundingBox(null);
	}
}
