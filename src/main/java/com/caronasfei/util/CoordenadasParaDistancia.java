package com.caronasfei.util;

public class CoordenadasParaDistancia {
	
	public static double calcular(double lat1, double lon1, double lat2, double lon2) {

		// Haversine formula:

		double R = 6371; // Radius of the earth in km
		double dLat = grausParaRadianos(lat2 - lat1); // deg2rad below
		double dLon = grausParaRadianos(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(grausParaRadianos(lat1))
				* Math.cos(grausParaRadianos(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c; // Distance in km
		return d;

	}

	public static double grausParaRadianos(double grau) {
		return grau * (Math.PI / 180);
	}

}
