package at.ac.uibk.sepm.pixplorer.rest.util;

import at.ac.uibk.sepm.pixplorer.db.GPSData;

/**
 * Utility class containing gps related calculations
 * 
 * @author cbo
 */
public class GpsUtils {

	/**
	 * Calculates distance of two gps coordinates in meter
	 * @param p1 - point 1
	 * @param p2 - point 2
	 * @return distance in meter
	 */
	public static double calculateDistance(GPSData p1, GPSData p2) {
		double pk = 180.0d / Math.PI;

		double a1 = p1.getLatitude() / pk;
		double a2 = p1.getLongitude() / pk;
		double b1 = p2.getLatitude() / pk;
		double b2 = p2.getLongitude() / pk;

		double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
		double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
		double t3 = Math.sin(a1) * Math.sin(b1);
		double tt = Math.acos(t1 + t2 + t3);

		return Math.abs(6366000 * tt);
	}

	private GpsUtils() {
		// do not instantiate
	}
}
