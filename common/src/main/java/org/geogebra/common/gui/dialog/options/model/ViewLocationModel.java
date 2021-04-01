package org.geogebra.common.gui.dialog.options.model;

import org.geogebra.common.euclidean.euclideanView;
import org.geogebra.common.euclidean3D.euclideanView3DInterface;
import org.geogebra.common.gui.view.algebra.AlgebraView;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.main.App;

public class ViewLocationModel extends OptionsModel {
	private IGraphicsViewLocationListener listener;

	public interface IGraphicsViewLocationListener extends PropertyListener {
		public void selectView(int index, boolean isSelected);

		public void setCheckBox3DVisible(boolean flag);

		public void setCheckBoxForPlaneVisible(boolean flag);

		// public void setCheckBoxAlgebraVisible(boolean flag);
	}

	public ViewLocationModel(App app, IGraphicsViewLocationListener listener) {
		super(app);
		this.listener = listener;

	}

	@Override
	public void updateProperties() {
		boolean isInEV = false;
		boolean isInEV2 = false;
		boolean isInEV3D = false;
		boolean isInEVForPlane = false;
		boolean isInAV = false;

		for (int i = 0; i < getGeosLength(); i++) {
			GeoElement geo = getGeoAt(i);
			if (geo.isVisibleInView(App.VIEW_euclidean)) {
				isInEV = true;
			}

			if (geo.isVisibleInView(App.VIEW_euclidean2)) {
				isInEV2 = true;
			}

			if (geo.isVisibleInView3D()) {
				isInEV3D = true;
			}

			if (app.haseuclideanViewForPlane()) {
				if (geo.isVisibleInViewForPlane()) {
					isInEVForPlane = true;
				}
			}

			if (geo.isAlgebraVisible()) {
				isInAV = true;
			}

		}

		listener.selectView(0, isInEV);
		listener.selectView(1, isInEV2);
		listener.selectView(2, isInEV3D);
		listener.selectView(3, isInEVForPlane);

		listener.selectView(4, isInAV);

	}

	public void applyToeuclideanView1(boolean value) {
		for (int i = 0; i < getGeosLength(); i++) {
			GeoElement geo = getGeoAt(i);
			if (value) {
				app.addToeuclideanView(geo);
			} else {
				app.removeFromeuclideanView(geo);
			}
		}
	}

	public void applyToeuclideanView2(boolean value) {
		for (int i = 0; i < getGeosLength(); i++) {
			GeoElement geo = getGeoAt(i);
			euclideanView ev2 = app.geteuclideanView2(1);

			if (value) {
				geo.addView(App.VIEW_euclidean2);
				ev2.add(geo);
			} else {
				geo.removeView(App.VIEW_euclidean2);
				ev2.remove(geo);
			}

		}
		storeUndoInfo();
	}

	public void applyToeuclideanView3D(boolean value) {

		if (!app.iseuclideanView3Dinited()) {
			return;
		}

		for (int i = 0; i < getGeosLength(); i++) {
			GeoElement geo = getGeoAt(i);
			euclideanView3DInterface ev3D = app.geteuclideanView3D();

			if (value) {
				geo.addViews3D();
				ev3D.add(geo);
			} else {
				geo.removeViews3D();
				ev3D.remove(geo);
			}

		}
		storeUndoInfo();
	}

	public void applyToeuclideanViewForPlane(boolean value) {
		for (int i = 0; i < getGeosLength(); i++) {
			GeoElement geo = getGeoAt(i);

			if (value) {
				geo.setVisibleInViewForPlane(true);
				app.addToViewsForPlane(geo);
			} else {
				geo.setVisibleInViewForPlane(false);
				app.removeFromViewsForPlane(geo);
			}

		}
		storeUndoInfo();
	}

	public void applyToAlgebraView(Boolean value) {

		AlgebraView av = app.getAlgebraView();
		for (int i = 0; i < getGeosLength(); i++) {
			GeoElement geo = getGeoAt(i);
			geo.setAlgebraVisible(value);
			if (value) {
				av.add(geo);
			} else {
				av.remove(geo);
			}
			geo.updateRepaint();

		}
		storeUndoInfo();
	}

	@Override
	public boolean checkGeos() {

		if (listener == null) {
			return false;
		}

		listener.setCheckBox3DVisible(true);

		if (app.haseuclideanViewForPlane()) {
			listener.setCheckBoxForPlaneVisible(true);
		} else {
			listener.setCheckBoxForPlaneVisible(false);
		}

		boolean go = true;
		for (int i = 0; go && i < getGeosLength(); i++) {
			GeoElement geo = getGeoAt(i);
			if (!geo.hasDrawable3D()) {
				listener.setCheckBox3DVisible(false);
				listener.setCheckBoxForPlaneVisible(false);
				go = false;
			}
		}

		// if (app.has(Feature.AV_EXTENSIONS)) {
		//
		// }
		return true;
	}

	@Override
	protected boolean isValidAt(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PropertyListener getListener() {
		return listener;
	}

}
