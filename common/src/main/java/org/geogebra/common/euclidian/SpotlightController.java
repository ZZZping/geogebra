package org.geogebra.common.euclidian;

import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.geos.GeoSpotlight;
import org.geogebra.common.main.App;
import org.geogebra.common.util.GTimer;

/**
 * Spotlight related operations
 *
 * @author Laszlo
 */
public class SpotlightController {

	public static final int BOX_DISAPPEAR_DELAY = 3600;
	private final Construction cons;
	private final GTimer disappearBoxTimer;
	private final App app;

	/**
	 * Constructor
	 * @param app the application
	 */
	public SpotlightController(App app) {
		cons = app.getKernel().getConstruction();
		this.app = app;
		disappearBoxTimer = app.newTimer(this::disappearBoundingBox, BOX_DISAPPEAR_DELAY);
	}

	/**
	 * Turns spotlight on.
	 */
	public void turnOn() {
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

	/**
	 * Turns spotlight off.
	 */
	public void turnOff() {
		GeoSpotlight spotlight = spotlight();
		if (spotlight != null && canBeRemoved()) {
			disappearBoxTimer.stop();
			spotlight.remove();
		}
	}

	/**
	 *
	 * @return spotlight geo
	 */
	GeoSpotlight spotlight() {
		return (GeoSpotlight) cons.getSpotlight();
	}

	private boolean canBeRemoved() {
		EuclidianView ev = app.getActiveEuclidianView();
		return !ev.getHits().contains(spotlight())
				&& ev.getHitHandler() == EuclidianBoundingBoxHandler.UNDEFINED;
	}

	/**
	 * Prevents bounding box disappearing after a given time.
	 */
	public void keepBox() {
		disappearBoxTimer.stop();
	}

	private void disappearBoundingBox() {
		app.getSelectionManager().clearSelectedGeos();
		app.getActiveEuclidianView().setBoundingBox(null);
	}

	/**
	 * clears spotlight
	 */
	public void clear() {
		cons.setSpotlight(null);
	}
}
