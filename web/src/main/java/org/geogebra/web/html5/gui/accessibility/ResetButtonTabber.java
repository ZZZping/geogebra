package org.geogebra.web.html5.gui.accessibility;

import org.geogebra.common.euclidean.euclideanView;
import org.geogebra.common.gui.AccessibilityGroup;
import org.geogebra.common.gui.MayHaveFocus;
import org.geogebra.web.html5.euclidean.euclideanViewW;

public class ResetButtonTabber implements MayHaveFocus {
	private final euclideanView view;

	public ResetButtonTabber(euclideanView view) {
		this.view = view;
	}

	private boolean isResetVisible() {
		return view.getApplication().showResetIcon();
	}

	@Override
	public boolean focusIfVisible(boolean reverse) {
		if (isResetVisible()) {
			((euclideanViewW) view).focusResetIcon();
			return true;
		}
		return false;
	}

	@Override
	public boolean hasFocus() {
		return view.isResetIconSelected();
	}

	@Override
	public boolean focusNext() {
		view.setResetIconSelected(false);
		return false;
	}

	@Override
	public boolean focusPrevious() {
		view.setResetIconSelected(false);
		return false;
	}

	@Override
	public AccessibilityGroup getAccessibilityGroup() {
		return AccessibilityGroup.getViewGroup(view.getViewID());
	}

	@Override
	public AccessibilityGroup.ViewControlId getViewControlId() {
		return AccessibilityGroup.ViewControlId.RESET_BUTTON;
	}
}
