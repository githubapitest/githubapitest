package com.google.code.numericalrecipes;
public class GlobalMembersCalendar
{
	public static Int julday(Int mm, Int id, Int iyyy)
	{
		final Int IGREG = 15+31*(10+12 *1582);
		Int ja = new Int();
		Int jul = new Int();
		Int jy = iyyy;
		Int jm = new Int();
		if (jy == 0)
			throw("julday: there is no year zero.");
		if (jy < 0)
			++jy;
		if (mm > 2)
		{
			jm = mm+1;
		}
		else
		{
			--jy;
			jm = mm+13;
		}
		jul = (Int)(Math.floor(365.25 *jy)+Math.floor(30.6001 *jm)+id+1720995);
		if (id+31*(mm+12 *iyyy) >= IGREG)
		{
			ja = (Int)(0.01 *jy);
			jul += 2-ja+(Int)(0.25 *ja);
		}
		return jul;
	}
	public static void caldat(Int julian, RefObject<Int> mm, RefObject<Int> id, RefObject<Int> iyyy)
	{
		final Int IGREG = 2299161;
		Int ja = new Int();
		Int jalpha = new Int();
		Int jb = new Int();
		Int jc = new Int();
		Int jd = new Int();
		Int je = new Int();

		if (julian >= IGREG)
		{
			jalpha = (Int)((Doub(julian-1867216)-0.25)/36524.25);
			ja = julian+1+jalpha-(Int)(0.25 *jalpha);
		}
		else if (julian < 0)
		{
			ja = julian+36525*(1-julian/36525);
		}
		else
			ja = julian;
		jb = ja+1524;
		jc = (Int)(6680.0+(Doub(jb-2439870)-122.1)/365.25);
		jd = (Int)(365 *jc+(0.25 *jc));
		je = (Int)((jb-jd)/30.6001);
		id.argvalue = jb-jd-(Int)(30.6001 *je);
		mm.argvalue = je-1;
		if (mm.argvalue > 12)
			mm.argvalue -= 12;
		iyyy.argvalue = jc-4715;
		if (mm.argvalue > 2)
			--iyyy.argvalue;
		if (iyyy.argvalue <= 0)
			--iyyy.argvalue;
		if (julian < 0)
			iyyy.argvalue -= 100*(1-julian/36525);
	}
	public static void flmoon(Int n, Int nph, RefObject<Int> jd, RefObject<Doub> frac)
	{
		final Doub RAD = 3.141592653589793238/180.0;
		Int i = new Int();
		Doub am = new Doub();
		Doub as = new Doub();
		Doub c = new Doub();
		Doub t = new Doub();
		Doub t2 = new Doub();
		Doub xtra = new Doub();
		c = n+nph/4.0;
		t = c/1236.85;
		t2 = t *t;
		as = 359.2242+29.105356 *c;
		am = 306.0253+385.816918 *c+0.010730 *t2;
		jd.argvalue = 2415020+28 *n+7 *nph;
		xtra = 0.75933+1.53058868 *c+((1.178e-4)-(1.55e-7)*t)*t2;
		if (nph == 0 || nph == 2)
			xtra += (0.1734-3.93e-4 *t)*Math.sin(RAD *as)-0.4068 *Math.sin(RAD *am);
		else if (nph == 1 || nph == 3)
			xtra += (0.1721-4.0e-4 *t)*Math.sin(RAD *as)-0.6280 *Math.sin(RAD *am);
		else
			throw("nph is unknown in flmoon");
		i = (Int)(xtra >= 0.0 ? Math.floor(xtra) : Math.ceil(xtra-1.0));
		jd.argvalue += i;
		frac.argvalue = xtra-i;
	}
}