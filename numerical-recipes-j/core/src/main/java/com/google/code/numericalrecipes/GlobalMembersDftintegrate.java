package com.google.code.numericalrecipes;
public class GlobalMembersDftintegrate
{
	public static void dftcor(Doub w, Doub delta, Doub a, Doub b, RefObject<VecDoub_I> endpts, RefObject<Doub> corre, RefObject<Doub> corim, RefObject<Doub> corfac)
	{
		Doub a0i = new Doub();
		Doub a0r = new Doub();
		Doub a1i = new Doub();
		Doub a1r = new Doub();
		Doub a2i = new Doub();
		Doub a2r = new Doub();
		Doub a3i = new Doub();
		Doub a3r = new Doub();
		Doub arg = new Doub();
		Doub c = new Doub();
		Doub cl = new Doub();
		Doub cr = new Doub();
		Doub s = new Doub();
		Doub sl = new Doub();
		Doub sr = new Doub();
		Doub t = new Doub();
		Doub t2 = new Doub();
		Doub t4 = new Doub();
		Doub t6 = new Doub();
		Doub cth = new Doub();
		Doub ctth = new Doub();
		Doub spth2 = new Doub();
		Doub sth = new Doub();
		Doub sth4i = new Doub();
		Doub stth = new Doub();
		Doub th = new Doub();
		Doub th2 = new Doub();
		Doub th4 = new Doub();
		Doub tmth2 = new Doub();
		Doub tth4i = new Doub();
		th = w *delta;
		if (a >= b || th < 0.0e0 || th > 3.1416e0)
			throw("bad arguments to dftcor");
		if (Math.abs(th) < 5.0e-2)
		{
			t = th;
			t2 = t *t;
			t4 = t2 *t2;
			t6 = t4 *t2;
			corfac.argvalue = 1.0-(11.0/720.0)*t4+(23.0/15120.0)*t6;
			a0r = (-2.0/3.0)+t2/45.0+(103.0/15120.0)*t4-(169.0/226800.0)*t6;
			a1r = (7.0/24.0)-(7.0/180.0)*t2+(5.0/3456.0)*t4-(7.0/259200.0)*t6;
			a2r = (-1.0/6.0)+t2/45.0-(5.0/6048.0)*t4+t6/64800.0;
			a3r = (1.0/24.0)-t2/180.0+(5.0/24192.0)*t4-t6/259200.0;
			a0i = t*(2.0/45.0+(2.0/105.0)*t2-(8.0/2835.0)*t4+(86.0/467775.0)*t6);
			a1i = t*(7.0/72.0-t2/168.0+(11.0/72576.0)*t4-(13.0/5987520.0)*t6);
			a2i = t*(-7.0/90.0+t2/210.0-(11.0/90720.0)*t4+(13.0/7484400.0)*t6);
			a3i = t*(7.0/360.0-t2/840.0+(11.0/362880.0)*t4-(13.0/29937600.0)*t6);
		}
		else
		{
			cth = Math.cos(th);
			sth = Math.sin(th);
			ctth = cth *cth-sth *sth;
			stth = 2.0e0 *sth *cth;
			th2 = th *th;
			th4 = th2 *th2;
			tmth2 = 3.0e0-th2;
			spth2 = 6.0e0+th2;
			sth4i = 1.0/(6.0e0 *th4);
			tth4i = 2.0e0 *sth4i;
			corfac.argvalue = tth4i *spth2*(3.0e0-4.0e0 *cth+ctth);
			a0r = sth4i*(-42.0e0+5.0e0 *th2+spth2*(8.0e0 *cth-ctth));
			a0i = sth4i*(th*(-12.0e0+6.0e0 *th2)+spth2 *stth);
			a1r = sth4i*(14.0e0 *tmth2-7.0e0 *spth2 *cth);
			a1i = sth4i*(30.0e0 *th-5.0e0 *spth2 *sth);
			a2r = tth4i*(-4.0e0 *tmth2+2.0e0 *spth2 *cth);
			a2i = tth4i*(-12.0e0 *th+2.0e0 *spth2 *sth);
			a3r = sth4i*(2.0e0 *tmth2-spth2 *cth);
			a3i = sth4i*(6.0e0 *th-spth2 *sth);
		}
		cl = a0r *endpts.argvalue[0]+a1r *endpts.argvalue[1]+a2r *endpts.argvalue[2]+a3r *endpts.argvalue[3];
		sl = a0i *endpts.argvalue[0]+a1i *endpts.argvalue[1]+a2i *endpts.argvalue[2]+a3i *endpts.argvalue[3];
		cr = a0r *endpts.argvalue[7]+a1r *endpts.argvalue[6]+a2r *endpts.argvalue[5]+a3r *endpts.argvalue[4];
		sr = -a0i *endpts.argvalue[7]-a1i *endpts.argvalue[6]-a2i *endpts.argvalue[5]-a3i *endpts.argvalue[4];
		arg = w*(b-a);
		c = Math.cos(arg);
		s = Math.sin(arg);
		corre.argvalue = cl+c *cr-s *sr;
		corim.argvalue = sl+s *cr+c *sr;
	}
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Int init = 0;
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Doub aold = -1.e30;
Doub bold = -1.e30;
Doub delta = new Doub();
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private VecDoub data = new VecDoub(NDFT);
VecDoub endpts = new VecDoub(8);
	public static void dftint(Doub func(Doub), Doub a, Doub b, Doub w, RefObject<Doub> cosint, RefObject<Doub> sinint)
	{
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Int init=0;
}
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	static Doub (*funcold)(const Doub);
//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
//	static Doub aold = -1.e30,bold = -1.e30,delta;
	final Int M = 64;
	final Int NDFT = 1024;
	final Int MPOL = 6;
	final Doub TWOPI = 6.283185307179586476;
	Int j = new Int();
	Int nn = new Int();
	Doub c = new Doub();
	Doub cdft = new Doub();
	Doub corfac = new Doub();
	Doub corim = new Doub();
	Doub corre = new Doub();
	Doub en = new Doub();
	Doub s = new Doub();
	Doub sdft = new Doub();
//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
//	static VecDoub data(NDFT),endpts(8);
	VecDoub cpol = new VecDoub(MPOL);
	VecDoub spol = new VecDoub(MPOL);
	VecDoub xpol = new VecDoub(MPOL);
	if (init != 1 || a != aold || b != bold || func != funcold)
	{
		init = 1;
		aold = a;
		bold = b;
		funcold=func;
		delta = (b-a)/M;
		for (j = 0;j<M+1;j++)
			data[j]=func(a+j *delta);
		for (j = M+1;j<NDFT;j++)
			data[j]=0.0;
		for (j = 0;j<4;j++)
		{
			endpts[j]=data[j];
			endpts[j+4]=data[M-3+j];
		}
		realft(data,1);
		data[1]=0.0;
	}
	en = w *delta *NDFT/TWOPI+1.0;
	nn = MIN(MAX((Int)(en-0.5 *MPOL+1.0),1),NDFT/2-MPOL+1);
	for (j = 0;j<MPOL;j++,nn++)
	{
		cpol[j]=data[2 *nn-2];
		spol[j]=data[2 *nn-1];
		xpol[j]=nn;
	}
	cdft = Poly_interp(xpol,cpol,MPOL).interp(en);
	sdft = Poly_interp(xpol,spol,MPOL).interp(en);
	RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(endpts);
	RefObject<Doub> tempRefObject2 = new RefObject<Doub>(corre);
	RefObject<Doub> tempRefObject3 = new RefObject<Doub>(corim);
	RefObject<Doub> tempRefObject4 = new RefObject<Doub>(corfac);
	GlobalMembersDftintegrate.dftcor(w, delta, a, b, tempRefObject, tempRefObject2, tempRefObject3, tempRefObject4);
	endpts = tempRefObject.argvalue;
	corre = tempRefObject2.argvalue;
	corim = tempRefObject3.argvalue;
	corfac = tempRefObject4.argvalue;
	cdft *= corfac;
	sdft *= corfac;
	cdft += corre;
	sdft += corim;
	c = delta *Math.cos(w *a);
	s = delta *Math.sin(w *a);
	cosint.argvalue = c *cdft-s *sdft;
	sinint.argvalue = s *cdft+c *sdft;
}