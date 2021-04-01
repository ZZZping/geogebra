package org.geogebra.common.properties.impl.graphics;

import org.geogebra.common.geogebra3D.euclidean3D.euclideanView3D;
import org.geogebra.common.main.Localization;
import org.geogebra.common.properties.impl.AbstractEnumerableProperty;

public class RatioUnitProperty extends AbstractEnumerableProperty {

    private euclideanView3D view3D;

    /**
     * Constructs an ratio unit property.
     *
     * @param view3D      euclideanView3D
     * @param localization  localization
     */
    RatioUnitProperty(Localization localization, euclideanView3D view3D) {
        super(localization, "Settings.Unit");
        this.view3D = view3D;
        setValuesAndLocalize(new String[]{"Unit.cm", "Unit.inch"});

    }

    @Override
    protected void setValueSafe(String value, int index) {
        int lengthUnit;
        switch (index) {
            case 1:
                lengthUnit = euclideanView3D.RATIO_UNIT_INCHES;
                break;
            case 0:
            default:
                lengthUnit = euclideanView3D.RATIO_UNIT_METERS_CENTIMETERS_MILLIMETERS;
                break;
        }
        view3D.setARRatioMetricSystem(lengthUnit);
    }

    @Override
    public int getIndex() {
        switch (view3D.getARRatioMetricSystem()) {
            case euclideanView3D.RATIO_UNIT_INCHES:
                return 1;
            case euclideanView3D.RATIO_UNIT_METERS_CENTIMETERS_MILLIMETERS:
            default:
                return 0;
        }
    }

    @Override
    public boolean isEnabled() {
        return view3D.isARRatioShown();
    }
}
