package events.dtu;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.android.maps.GeoPoint;

public class InitialData {
	
	public static ArrayList<Instance> initialize() {
		ArrayList<Instance> instances = new ArrayList<Instance>();
		instances.add(hegnet());
		instances.add(sHuset());
		instances.add(ida());
		instances.add(diamanten());
		instances.add(diagonalen());
		instances.add(etherrummet());
		instances.add(testBar());
		return instances;
	}
	
	private static Bar hegnet() {
		OpeningTimes openingTimes = new OpeningTimes();
		openingTimes.add(	0,
							12,
							Calendar.FRIDAY,
							0,
							22,
							Calendar.FRIDAY);

		Bar hegnet = new Bar(	0,
								27992501059l,
								"Hegnet",
								"http://profile.ak.fbcdn.net/hprofile-ak-snc4/hs223.ash2/50254_27992501059_5168_s.jpg",
								true,
								new GeoPoint(55782329,12517054),
								"H.C. Ørsteds plads, 2800 Kongens Lyngby, Denmark",
								openingTimes,
								R.drawable.hegnet,
								R.drawable.hegnetevent);
		return hegnet;
	}
	
	private static Bar sHuset() {
		OpeningTimes openingTimes = new OpeningTimes();
		openingTimes.add(	30,7,Calendar.MONDAY,
							0,19,Calendar.MONDAY);
		openingTimes.add(	30,7,Calendar.TUESDAY,
							0,19,Calendar.TUESDAY);
		openingTimes.add(	0,19,Calendar.TUESDAY,
							0,1,Calendar.WEDNESDAY);
		openingTimes.add(	30,7,Calendar.WEDNESDAY,
							0,19,Calendar.WEDNESDAY);
		openingTimes.add(	0,19,Calendar.WEDNESDAY,
							0,1,Calendar.THURSDAY);
		openingTimes.add(	30,7,Calendar.THURSDAY,
							0,19,Calendar.THURSDAY);
		openingTimes.add(	0,19,Calendar.THURSDAY,
							0,5,Calendar.FRIDAY);
		openingTimes.add(	30,7,Calendar.FRIDAY,
							0,19,Calendar.FRIDAY);
		openingTimes.add(	0,19,Calendar.FRIDAY,
							0,5,Calendar.SATURDAY);
		

		Bar sHuset = new Bar(	1,
								27128934931l,
								"S-Huset",
								"http://profile.ak.fbcdn.net/hprofile-ak-snc4/hs322.snc4/41573_27128934931_9741_s.jpg",
								true,
								new GeoPoint(55786459, 12525696),
								"DTU byg. 101 indgang E, 2800 Kongens Lyngby, Denmark",
								openingTimes,
								R.drawable.shuset,
								R.drawable.shusetevent);
		return sHuset;
	}
	
	private static Group ida() {
		Group ida = new Group(	2,
								121974734518461l,
								"IDA",
								"",
								false,
								R.drawable.ida,
								R.drawable.idaevent);
		return ida;
	}
	
	private static Bar diamanten() {
		OpeningTimes openingTimes = new OpeningTimes();
		openingTimes.add(	0,
							12,
							Calendar.FRIDAY,
							0,
							22,
							Calendar.FRIDAY);

		Bar diamanten = new Bar(3,
								4637146354l,
								"Diamanten",
								"",
								false,
								new GeoPoint(55782643,12521174),
								"",
								openingTimes,
								R.drawable.diamanten,
								R.drawable.diamantenevent);
		return diamanten;
	}
	
	private static Bar diagonalen() {
		OpeningTimes openingTimes = new OpeningTimes();
		openingTimes.add(	0,
							12,
							Calendar.FRIDAY,
							0,
							22,
							Calendar.FRIDAY);

		Bar diagonalen = new Bar(4,
								6848184628l,
								"Diagonalen",
								"",
								false,
								new GeoPoint(55789240,12525648),
								"",
								openingTimes,
								R.drawable.diagonalen,
								R.drawable.diagonalenevent);
		return diagonalen;
	}
	
	private static Bar etherrummet() {
		OpeningTimes openingTimes = new OpeningTimes();
		openingTimes.add(	0,
							12,
							Calendar.FRIDAY,
							0,
							22,
							Calendar.FRIDAY);

		Bar etherrummet = new Bar(5,
								65278201619l,
								"Etherrummet",
								"",
								false,
								new GeoPoint(55787523,12518406),
								"",
								openingTimes,
								R.drawable.etherrummet,
								R.drawable.etherrummetevent);
		return etherrummet;
	}
	
	private static Bar testBar() {
		OpeningTimes openingTimes = new OpeningTimes();
		openingTimes.add(	30,7,Calendar.MONDAY,
							0,19,Calendar.MONDAY);
		openingTimes.add(	30,7,Calendar.TUESDAY,
							0,19,Calendar.TUESDAY);
		openingTimes.add(	0,19,Calendar.TUESDAY,
							0,1,Calendar.WEDNESDAY);
		openingTimes.add(	30,7,Calendar.WEDNESDAY,
							0,19,Calendar.WEDNESDAY);
		openingTimes.add(	0,19,Calendar.WEDNESDAY,
							0,1,Calendar.THURSDAY);
		openingTimes.add(	30,7,Calendar.THURSDAY,
							0,19,Calendar.THURSDAY);
		openingTimes.add(	0,19,Calendar.THURSDAY,
							0,5,Calendar.FRIDAY);
		openingTimes.add(	30,7,Calendar.FRIDAY,
							0,19,Calendar.FRIDAY);
		openingTimes.add(	0,19,Calendar.FRIDAY,
							0,5,Calendar.SATURDAY);

		Bar testBar = new Bar(6,
								188815817798467l,
								"Test Bar",
								"http://static.ak.fbcdn.net/rsrc.php/y0/r/XsEg9L6Ie5_.jpg",
								true,
								new GeoPoint(55787523,12518406),
								"Anker Engelundsvej 1, 2800 Kongens Lyngby, Denmark",
								openingTimes,
								R.drawable.etherrummet,
								R.drawable.etherrummetevent);
		return testBar;
	}
	
}
