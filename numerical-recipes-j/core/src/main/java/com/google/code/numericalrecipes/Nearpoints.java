package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class elT, Int DIM>
public class Nearpoints<Integer DIM>
{
	public Int npts = new Int();
	public Qotree<Sphcirc<DIM>,DIM> thetree = new Qotree<Sphcirc<DIM>,DIM>();
	public Sphcirc<DIM> sphlist;
	public Nearpoints(java.util.ArrayList< Point<DIM> > pvec)
	{
		npts = pvec.size();
		thetree = new Qotree(npts,npts,32/DIM);
		Int j = new Int();
		Int k = new Int();
		sphlist = new Sphcirc<DIM>[npts];
		Point<DIM> lo = pvec.get(0);
		Point<DIM> hi = pvec.get(0);
		for (j = 1; j<npts; j++)
			for (k = 0; k<DIM; k++)
			{
			if (pvec.get(j).x[k] < lo.x[k])
				lo.x[k] = pvec.get(j).x[k];
			if (pvec.get(j).x[k] > hi.x[k])
				hi.x[k] = pvec.get(j).x[k];
		}
		for (k = 0; k<DIM; k++)
		{
			lo.x[k] -= 0.1*(hi.x[k]-lo.x[k]);
			hi.x[k] += 0.1*(hi.x[k]-lo.x[k]);
		}
		thetree.setouterbox(lo, hi);
		for (j = 0; j<npts; j++)
			thetree.qostore(Sphcirc<DIM>(pvec.get(j),0.0));
	}
	public void Dispose()
	{
		sphlist = null;
	}
	public final Int locatenear(Point<DIM> pt, Doub r, Point<DIM>[] list, Int nmax)
	{
		Int j = new Int();
		Int n = new Int();
		RefObject<elT> tempRefObject = new RefObject<elT>(sphlist);
		n = thetree.qocollides(Sphcirc<DIM>(pt,r), tempRefObject, nmax);
		sphlist = tempRefObject.argvalue;
		for (j = 0; j<n; j++)
			list[j] = sphlist[j].center;
		return n;
	}
}