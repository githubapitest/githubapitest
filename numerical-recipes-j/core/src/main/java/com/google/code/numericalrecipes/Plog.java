package com.google.code.numericalrecipes;
public class Plog
{
	public VecDoub dat;
	public Int ndat = new Int();
	public VecDoub stau = new VecDoub();
	public VecDoub slogtau = new VecDoub();

	public Plog(RefObject<VecDoub> data)
	{
		dat = data.argvalue;
		ndat = data.argvalue.size();
		stau = ndat;
		slogtau = ndat;
		Int i = new Int();
		stau[0] = slogtau[0] = 0.;
		for (i = 1;i<ndat;i++)
		{
			stau[i] = dat[i]-dat[0];
			slogtau[i] = slogtau[i-1] + Math.log(dat[i]-dat[i-1]);
		}
	}

//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(RefObject<State> s)
	{
		Int i = new Int();
		Int ilo = new Int();
		Int ihi = new Int();
		Int n1 = new Int();
		Int n2 = new Int();
		Doub st1 = new Doub();
		Doub st2 = new Doub();
		Doub stl1 = new Doub();
		Doub stl2 = new Doub();
		Doub ans = new Doub();
		ilo = 0;
		ihi = ndat-1;
		while (ihi-ilo>1)
		{
			i = (ihi+ilo) >> 1;
			if (s.argvalue.tc > dat[i])
				ilo = i;
			else
				ihi = i;
		}
		n1 = ihi;
		n2 = ndat-1-ihi;
		st1 = stau[ihi];
		st2 = stau[ndat-1]-st1;
		stl1 = slogtau[ihi];
		stl2 = slogtau[ndat-1]-stl1;
		ans = n1*(s.argvalue.k1 *Math.log(s.argvalue.lam1)-factln(s.argvalue.k1-1))+(s.argvalue.k1-1)*stl1-s.argvalue.lam1 *st1;
		ans += n2*(s.argvalue.k2 *Math.log(s.argvalue.lam2)-factln(s.argvalue.k2-1))+(s.argvalue.k2-1)*stl2-s.argvalue.lam2 *st2;
		return (s.argvalue.plog = ans);
	}
}