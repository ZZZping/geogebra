package org.geogebra.common.cas.view;

import org.geogebra.common.euclidean.euclideanConstants;
import org.geogebra.common.kernel.ModeSetter;
import org.geogebra.common.main.App;

/**
 * Controller for CAS mouse and keyboard events
 */
public class CASTableCellController {
	/**
	 * Handles pressing of Enter key after user input. The behaviour depends on
	 * the currently selected mode in the toolbar (Evaluate, Keep Input,
	 * Numeric) and Ctrl, Alt keys.
	 * 
	 * @param control
	 *            whether contrrol is pressed
	 * @param alt
	 *            whether alt is pressed
	 * 
	 * @param app
	 *            application
	 * @param focus
	 *            whether this was triggered by enter rather than blur
	 */
	public synchronized void handleEnterKey(boolean control, boolean alt,
			App app, boolean focus) {
		// AppD app = view.getApp();
		int mode = app.getMode();
		ModeSetter ms = focus ? ModeSetter.TOOLBAR : ModeSetter.CAS_BLUR;
		// Ctrl + Enter toggles between the modes Evaluate and Numeric
		if (control) {
			if (mode == euclideanConstants.MODE_CAS_NUMERIC) {
				app.setMode(euclideanConstants.MODE_CAS_EVALUATE, ms);
			} else {
				app.setMode(euclideanConstants.MODE_CAS_NUMERIC, ms);
			}
			app.setMode(mode, ms);
			return;
		}

		// Alt + Enter toggles between the modes Evaluate and Keep Input
		if (alt) {
			if (mode == euclideanConstants.MODE_CAS_KEEP_INPUT) {
				app.setMode(euclideanConstants.MODE_CAS_EVALUATE, ms);
			} else {
				app.setMode(euclideanConstants.MODE_CAS_KEEP_INPUT, ms);
			}
			app.setMode(mode, ms);
			return;
		}

		// Enter depends on current mode
		switch (mode) {
		default:
			// switch back to Evaluate
			app.setMode(euclideanConstants.MODE_CAS_EVALUATE, ms);
			break;
		case euclideanConstants.MODE_CAS_EVALUATE:
		case euclideanConstants.MODE_CAS_NUMERIC:
		case euclideanConstants.MODE_CAS_KEEP_INPUT:
			// apply current tool again
			app.setMode(mode, ms);
			break;

		}
	}
}
