package events.dtu.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.android.maps.GeoPoint;

public class Constants {

	public final static int UPPER_BOUND_INT = 55791655;
	public final static int LOWER_BOUND_INT = 55780073;
	public final static int LEFT_BOUND_INT = 12513642;
	public final static int RIGHT_BOUND_INT = 12525229;
	
	public final static double UPPER_BOUND_DOUBLE = 55.791655;
	public final static double LOWER_BOUND_DOUBLE = 55.780073;
	public final static double LEFT_BOUND_DOUBLE = 12.513642;
	public final static double RIGHT_BOUND_DOUBLE = 12.525229;
	
	@SuppressWarnings("serial")
	public static final Map<String,GeoPoint> BUILDINGS = Collections.unmodifiableMap(new HashMap<String,GeoPoint>(86, 1.0f)
	{
		{
			put("101",new GeoPoint(55786385,12524393));
			put("101a", new GeoPoint(55786070,12523368));
			put("101b", new GeoPoint(55786634,12522735));
			put("101e", new GeoPoint(55786518,12525750));
			put("101d", new GeoPoint(55786926,12523658));
			put("101f", new GeoPoint(55785804,12524854));
			put("113",new GeoPoint(55788338,12526710));
			put("114",new GeoPoint(55788445,12523835));
			put("115",new GeoPoint(55788773,12525980));
			put("116",new GeoPoint(55789532,12525144));
			put("117",new GeoPoint(55789135,12526710));
			put("118",new GeoPoint(55790268,12524929));
			put("119",new GeoPoint(55790619,12525058));
			put("121a",new GeoPoint(55791161,12525702));
			put("121b",new GeoPoint(55791168,12526152));
			put("122",new GeoPoint(55791523,12528062));
			put("201",new GeoPoint(55786400,12519522));
			put("204",new GeoPoint(55787434,12520037));
			put("205",new GeoPoint(55787674,12520723));
			put("206",new GeoPoint(55786774,12517290));
			put("207",new GeoPoint(55787113,12517505));
			put("208",new GeoPoint(55787663,12517816));
			put("210",new GeoPoint(55787766,12518502));
			put("221",new GeoPoint(55788078,12520477));
			put("222",new GeoPoint(55788361,12521453));
			put("223",new GeoPoint(55788689,12521732));
			put("224",new GeoPoint(55789085,12521882));
			put("227",new GeoPoint(55788651,12519286));
			put("228",new GeoPoint(55789013,12519629));
			put("229",new GeoPoint(55789436,12519779));
			put("230",new GeoPoint(55790817,12523309));
			put("233",new GeoPoint(55791683,12522064));
			put("237",new GeoPoint(55791840,12522762));
			put("240",new GeoPoint(55789150,12518932));
			put("301",new GeoPoint(55785858,12519243));
			put("302",new GeoPoint(55785423,12519334));
			put("303",new GeoPoint(55785294,12519913));
			put("304",new GeoPoint(55784355,12519227));
			put("307",new GeoPoint(55784828,12517065));
			put("306",new GeoPoint(55785450,12518808));
			put("308",new GeoPoint(55784184,12517907));
			put("309",new GeoPoint(55785927,12516968));
			put("312",new GeoPoint(55784550,12516518));
			put("321",new GeoPoint(55783772,12518921));
			put("322",new GeoPoint(55783432,12518803));
			put("325",new GeoPoint(55783775,12517118));
			put("326",new GeoPoint(55783451,12516947));
			put("327",new GeoPoint(55783138,12516711));
			put("329",new GeoPoint(55784042,12515756));
			put("341",new GeoPoint(55782722,12517194));
			put("342",new GeoPoint(55782509,12517011));
			put("343",new GeoPoint(55782215,12518041));
			put("344",new GeoPoint(55781876,12517837));
			put("345v",new GeoPoint(55781639,12517676));
			put("345ø",new GeoPoint(55781563,12518384));
			put("346",new GeoPoint(55781261,12517623));
			put("347",new GeoPoint(55780796,12517934));
			put("348",new GeoPoint(55782120,12516378));
			put("349",new GeoPoint(55781811,12516185));
			put("352",new GeoPoint(55781147,12515906));
			put("353",new GeoPoint(55782047,12515820));
			put("354",new GeoPoint(55781689,12515295));
			put("355",new GeoPoint(55781265,12515123));
			put("356",new GeoPoint(55781509,12514608));
			put("358",new GeoPoint(55780476,12516335));
			put("373",new GeoPoint(55782078,12513868));
			put("375",new GeoPoint(55783379,12512462));
			put("376",new GeoPoint(55783024,12512280));
			put("377",new GeoPoint(55782673,12512076));
			put("378",new GeoPoint(55782166,12512559));
			put("381",new GeoPoint(55782738,12512891));
			put("382",new GeoPoint(55781124,12511100));
			put("384",new GeoPoint(55780907,12511379));
			put("402",new GeoPoint(55784374,12521485));
			put("403",new GeoPoint(55783649,12521077));
			put("404",new GeoPoint(55782848,12520670));
			put("411",new GeoPoint(55784874,12522998));
			put("412",new GeoPoint(55784252,12522172));
			put("413",new GeoPoint(55783619,12521968));
			put("414",new GeoPoint(55782654,12521592));
			put("415",new GeoPoint(55784775,12523781));
			put("416",new GeoPoint(55784382,12523556));
			put("417",new GeoPoint(55783981,12523389));
			put("418",new GeoPoint(55783325,12523003));
			put("421",new GeoPoint(55782162,12520519));
			put("423",new GeoPoint(55781490,12519994));
			put("424",new GeoPoint(55781158,12519811));
			put("425",new GeoPoint(55780846,12519597));
			put("427",new GeoPoint(55781384,12521571));
			put("450",new GeoPoint(55779652,12520605));
			put("451",new GeoPoint(55780018,12519039));
        }
    });
	
	@SuppressWarnings("serial")
	public static final Map<String,GeoPoint> SPECIAL_PLACES = Collections.unmodifiableMap(new HashMap<String,GeoPoint>(5, 1.0f)
	{
		{
			put("glassalen", new GeoPoint(55786516,12524634));
			put("glassal", new GeoPoint(55786516,12524634));
			put("biblioteket", new GeoPoint(55786962,12523191));
			put("bibliotek", new GeoPoint(55786962,12523191));
			put("kantinen", new GeoPoint(55786757,12524355));
			put("kantine", new GeoPoint(55786757,12524355));
			put("hegnet",new GeoPoint(55782509,12517011));
			put("diamanten",new GeoPoint(55782654,12521592));
			put("diagonalen",new GeoPoint(55789240,12525648));
			put("etherrummet",new GeoPoint(55787529,12518481));
			put("s-huset",new GeoPoint(55786518,12525750));
			put("s huset",new GeoPoint(55786518,12525750));
			put("maskinen",new GeoPoint(55780476,12516335));
			put("oticonsalen",new GeoPoint(55786926,12525916));
			put("oticon salen",new GeoPoint(55786926,12525916));
			put("william demant kollegiet",new GeoPoint(55781975,12511743));
			put("villum kann rasmussen kollegiet",new GeoPoint(55784382,12524983));
			put("kampsax kollegiet",new GeoPoint(55782536,12524157));
			put("kampsax",new GeoPoint(55782536,12524157));
			put("saxen",new GeoPoint(55783076,12523792));
		}
	});
}
