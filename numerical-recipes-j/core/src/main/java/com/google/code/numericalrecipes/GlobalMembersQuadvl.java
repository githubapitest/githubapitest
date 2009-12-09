package com.google.code.numericalrecipes;
public class GlobalMembersQuadvl
{
	public static void quadvl(Doub x, Doub y, RefObject<Doub> fa, RefObject<Doub> fb, RefObject<Doub> fc, RefObject<Doub> fd)
	{
		Doub qa = new Doub();
		Doub qb = new Doub();
		Doub qc = new Doub();
		Doub qd = new Doub();
		qa = MIN(2.0,MAX(0.0,1.0-x));
		qb = MIN(2.0,MAX(0.0,1.0-y));
		qc = MIN(2.0,MAX(0.0,x+1.0));
		qd = MIN(2.0,MAX(0.0,y+1.0));
		fa.argvalue = 0.25 *qa *qb;
		fb.argvalue = 0.25 *qb *qc;
		fc.argvalue = 0.25 *qc *qd;
		fd.argvalue = 0.25 *qd *qa;
	}
}