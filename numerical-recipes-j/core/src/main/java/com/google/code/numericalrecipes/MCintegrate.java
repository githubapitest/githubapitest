package com.google.code.numericalrecipes;
public class MCintegrate
{
	public Int ndim = new Int();
	public Int nfun = new Int();
	public Int n = new Int();
	public VecDoub ff = new VecDoub();
	public VecDoub fferr = new VecDoub();
	public VecDoub xlo = new VecDoub();
	public VecDoub xhi = new VecDoub();
	public VecDoub x = new VecDoub();
	public VecDoub xx = new VecDoub();
	public VecDoub fn = new VecDoub();
	public VecDoub sf = new VecDoub();
	public VecDoub sferr = new VecDoub();
	public Doub vol = new Doub();

//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	VecDoub (*funcsp)(const VecDoub &);
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	VecDoub (*xmapp)(const VecDoub &);
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	Bool (*inregionp)(const VecDoub &);
	public Ran ran = new Ran();

	public MCintegrate(VecDoub xlow, VecDoub xhigh, VecDoub funcs(VecDoub ), Bool inregion(VecDoub ), VecDoub xmap(VecDoub ), Int ranseed)
	{
		ndim = xlow.size();
		n = 0;
		xlo = xlow;
		xhi = xhigh;
		x = ndim;
		xx = ndim;
		funcsp = funcs;
		xmapp = xmap;
		inregionp = inregion;
		vol = 1.;
		ran = ranseed;
		if (xmapp)
			nfun = funcs(xmapp(xlo)).size();
		else
			nfun = funcs(xlo).size();
		ff.resize(nfun);
		fferr.resize(nfun);
		fn.resize(nfun);
		sf.assign(nfun,0.);
		sferr.assign(nfun,0.);
		for (Int j = 0;j<ndim;j++)
			vol *= Math.abs(xhi[j]-xlo[j]);
	}

	public final void step(Int nstep)
	{
		Int i = new Int();
		Int j = new Int();
		for (i = 0;i<nstep;i++)
		{
			for (j = 0;j<ndim;j++)
				x[j] = xlo[j]+(xhi[j]-xlo[j])*ran.doub();
			if (xmapp)
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//				xx = (*xmapp)(x);
			else
				xx = x;
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//			if ((*inregionp)(xx))
			{
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//				fn = (*funcsp)(xx);
				for (j = 0;j<nfun;j++)
				{
					sf[j] += fn[j];
					sferr[j] += SQR(fn[j]);
				}
			}
		}
		n += nstep;
	}

	public final void calcanswers()
	{
		for (Int j = 0;j<nfun;j++)
		{
			ff[j] = vol *sf[j]/n;
			fferr[j] = vol *Math.sqrt((sferr[j]/n-SQR(sf[j]/n))/n);
		}
	}
}