package org.geogebra.web.full.gui;

import org.geogebra.web.html5.gui.util.AriaMenuBar;

import com.google.gwt.dom.client.Style;

public class FontSubMenu extends AriaMenuBar {

	public static final int VERTICAL_PADDING = 16;
	private final FontList fontList;

	FontSubMenu(int height, FontList fontList) {
		this.fontList = fontList;
		addStyleName("mowScrollableSubmenu");
		setMaxHeight(height);
		createItems();
	}

	private void createItems() {
		for (int i=0;i < fontList.size(); i++) {
			addItem(fontList.getFontName(i), false,null);
		}
	}

	private void setMaxHeight(int height) {
		getElement().getStyle().setProperty("maxHeight", height - VERTICAL_PADDING, Style.Unit.PX);
	}
}
