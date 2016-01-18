package org.isep.rottencave;

import java.text.DateFormat;

public class GlobalConfiguration {
	public static final String VERSION = "1"; 
	public static final String REST_URL_BASE = "http://localhost:8080/RottenCaveServices/";

	public static final DateFormat MEDIUM_DATE_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM);
	
	public static boolean musicOn = true;
	public static Long configuredSeed;
}
