/* 
GeoGebra - Dynamic Mathematics for Everyone
http://www.geogebra.org

This file is part of GeoGebra.

This program is free software; you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by 
the Free Software Foundation.

*/

//
 
package geogebra.common.kernel.algos;

import geogebra.common.kernel.Construction;
import geogebra.common.kernel.geos.GeoElement;
import geogebra.common.kernel.geos.GeoPolygon;
import geogebra.common.kernel.locusequ.EquationElement;
import geogebra.common.kernel.locusequ.EquationScope;


/**
 * Computes union of two polygons
 * @author George Sturr
 */
public class AlgoPolygonUnion extends AlgoPolygonOperation {
	
	/**
	 * @param cons construction
	 * @param labels labels for output
	 * @param inPoly0 first input polygon
	 * @param inPoly1 second input polygon
	 */
	public AlgoPolygonUnion(Construction cons, String[] labels,
			GeoPolygon inPoly0, GeoPolygon inPoly1) {
		
		super(cons, labels, inPoly0, inPoly1, PolyOperation.UNION);
	}
	
	@Override
	public Algos getClassName() {
		return Algos.AlgoPolygonUnion;
	}

	@Override
	public EquationElement buildEquationElementForGeo(GeoElement element,
			EquationScope scope) {
		return null;
	}

	@Override
	public boolean isLocusEquable() {
		// TODO Consider locusequability
		return false;
	}
	
}