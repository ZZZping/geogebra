package org.geogebra.web.full.cas.view;

import org.geogebra.common.cas.view.CASInputHandler;
import org.geogebra.common.cas.view.MarbleRenderer;
import org.geogebra.common.kernel.geos.GeoCasCell;
import org.geogebra.web.full.gui.images.AppResources;
import org.geogebra.web.html5.main.AppW;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * Row header with marble
 */
public class RowHeaderWidget extends FlowPanel implements MarbleRenderer {
	private Image marble;
	private boolean oldValue;
	private RowHeaderHandler handler;

	/**
	 * @param casTableW
	 *            table
	 * @param n
	 *            row number
	 * @param cell
	 *            cell
	 * @param app
	 *            application
	 */
	public RowHeaderWidget(CASTableW casTableW, int n, GeoCasCell cell, AppW app) {
		Label label = new Label();
		label.setText(n + "");
		marble = new Image(AppResources.INSTANCE.hidden());
		oldValue = false;
		add(label);
		add(marble);
		if (cell != null) {
			CASInputHandler.handleMarble(cell, this);
		}
		addStyleName("casRowHeader");
		marble.addClickHandler(new MarbleClickHandler(cell, this));

		// instead of here, from now on the whole of header areas should
		// handle this event, so this is moved to CASTableCellControllerW
		// but still, create the RowHeaderHandler for quick implementation
		// addDomHandler(
		handler = new RowHeaderHandler(app, casTableW, this);
		// , MouseUpEvent.getType());
	}

	/**
	 * @return handler
	 */
	public RowHeaderHandler getHandler() {
		return handler;
	}

	/**
	 * @param number
	 *            row number
	 */
	public void setLabel(int number) {
		((Label) (getWidget(0))).setText(number + "");
	}

	/**
	 * @return index in view (starts with 0)
	 */
	public int getIndex() {
		return Integer.parseInt(((Label) (getWidget(0))).getText()) - 1;
	}

	@Override
	public void setMarbleValue(boolean value) {
		if (value == oldValue) {
			return;
		}
		marble.setUrl(value ? AppResources.INSTANCE.shown().getSafeUri()
				: AppResources.INSTANCE.hidden().getSafeUri());
		oldValue = value;

	}

	@Override
	public void setMarbleVisible(boolean visible) {
		marble.setVisible(visible);
	}

	/**
	 * Handler for marble
	 */
	protected static class MarbleClickHandler implements ClickHandler {
		private GeoCasCell cell;
		private RowHeaderWidget rowHeaderWidget;

		/**
		 * @param cell
		 *            cas cell
		 * @param rowHeaderWidget
		 *            row header
		 */
		protected MarbleClickHandler(GeoCasCell cell,
				RowHeaderWidget rowHeaderWidget) {
			this.cell = cell;
			this.rowHeaderWidget = rowHeaderWidget;
		}

		@Override
		public void onClick(ClickEvent event) {
			cell.toggleTwinGeoeuclideanVisible();
			CASInputHandler.handleMarble(cell, rowHeaderWidget);
			event.stopPropagation();
		}
	}

}
