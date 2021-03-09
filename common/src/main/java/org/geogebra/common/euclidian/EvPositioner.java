package org.geogebra.common.euclidian;

import org.geogebra.common.main.settings.EuclidianSettings;
import org.geogebra.common.util.DoubleUtil;

/**
 * Recenters the Euclidian View after Clear all menu action and Algebra View size changes.
 */
public abstract class EvPositioner {

    private EuclidianView euclidianView;
    private EuclidianSettings settings;
    private int moveToX;
    private int moveToY;
    private int oldWidth = -1;
    private boolean isAnimationEnabled;

    protected EvPositioner(EuclidianView euclidianView) {
        this.euclidianView = euclidianView;
        settings = euclidianView.getSettings();
    }

    protected abstract int getAvWidth();

    protected abstract int getAvHeight();

    protected abstract boolean isPortrait();

    protected abstract int translateToDp(int pixels);

    /**
     * Initializes the xZero and yZero.
     *
     * @param width width
     * @param height height
     */
    public void initIfEvSizeNotSet(int width, int height) {
        oldWidth = settings.getRawWidth();
        if (oldWidth < 0) {
            initialize(width, height);
        }
    }

    protected void initialize(int width, int height) {
        oldWidth = width;
        settings.setVisibleFromX(0);
        settings.setVisibleUntilY(height);
        double xZero = width / 2.0;
        double yZero = height / 2.0;
        settings.setOriginNoUpdate(xZero, yZero);
        euclidianView.xZero = xZero;
        euclidianView.yZero = yZero;
    }

    /**
     * Centers the EV on app start or after orientation change
     */
    public void reCenter() {
        centerWithAvSize(getAvWidth(), getAvHeight());
    }

    public void centerWithAvSize(int overlappedWidth, int overlappedHeight) {
        boolean isPortrait = isPortrait();
        int newVisibleFromX = isPortrait ? 0 : translateToDp(overlappedWidth);
        int newVisibleUntilY =
                isPortrait
                        ? settings.getHeight() - translateToDp(overlappedHeight)
                        : settings.getHeight();

        euclidianView.xZero = getNewXZero(newVisibleFromX);
        euclidianView.yZero = getNewYZero(newVisibleUntilY);
        oldWidth = settings.getRawWidth();

        settings.setVisibleFromX(newVisibleFromX);
        settings.setVisibleUntilY(newVisibleUntilY);

        settings.setOriginNoUpdate(euclidianView.xZero, euclidianView.yZero);

        euclidianView.updateSizeChange();
    }

    private double getNewXZero(int newVisibleFromX) {
        double newSize = newVisibleFromX + settings.getWidth();
        double oldSize = settings.getVisibleFromX() + oldWidth;
        double dx = (newSize - oldSize) / 2.0;
        return settings.getXZero() + dx;
    }

    private double getNewYZero(int newVisibleUntilY) {
        double dy = (newVisibleUntilY - settings.getVisibleUntilY()) / 2.0;
        return settings.getYZero() + dy;
    }

    /**
     * Pans the EV after AV (and keyboard) animations
     *
     * @param avWidth av width
     * @param avHeight av height
     */
    public void onAvSizeChanged(int avWidth, int avHeight) {
        int x, y;
        if (isPortrait()) {
            x = 0;
            y = euclidianView.getHeight() - translateToDp(avHeight);
        } else {
            x = translateToDp(avWidth);
            y = euclidianView.getHeight();
        }

        if (x != settings.getVisibleFromX() || y != settings.getVisibleUntilY()) {
            updateVisibleFromX(x);
            updateVisibleUntilY(y);
            updateVisibleEv();
        }
    }

    private void updateVisibleFromX(int x) {
        moveToX = (int) (settings.getXZero() + (x - settings.getVisibleFromX()) / 2.0);
        settings.setVisibleFromX(x);
    }

    private void updateVisibleUntilY(int y) {
        moveToY = (int) (settings.getYZero() + (y - settings.getVisibleUntilY()) / 2.0);
        settings.setVisibleUntilY(y);
    }

    private void updateVisibleEv() {
        if (DoubleUtil.isEqual(moveToX, euclidianView.xZero)
                && DoubleUtil.isEqual(moveToY, euclidianView.yZero)) {
            return;
        }

        if (isAnimationEnabled) {
            euclidianView.animateMove(moveToX, moveToY, false);
        }
        euclidianView.xZero = moveToX;
        euclidianView.yZero = moveToY;
        settings.setOriginNoUpdate(moveToX, moveToY);
        euclidianView.updateSizeChange();
    }

    protected void setOldWidth(int oldWidth) {
        this.oldWidth = oldWidth;
    }

    protected EuclidianView getEuclidianView() {
        return euclidianView;
    }
}
