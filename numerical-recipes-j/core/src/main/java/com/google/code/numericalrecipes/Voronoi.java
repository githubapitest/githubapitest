package com.google.code.numericalrecipes;
public class Voronoi implements Delaunay
{
	public Int nseg = new Int();
	public VecInt trindx = new VecInt();
	public java.util.ArrayList<Voredge> segs = new java.util.ArrayList<Voredge>();
	public Voronoi(java.util.ArrayList< Point<2> > pvec)
	{
		super(pvec,1);
		nseg = 0;
		trindx = npts;
		segs = 6 *npts+12;
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int p = new Int();
		Int jfirst = new Int();
		Ullong key = new Ullong();
		Triel tt = new Triel();
		Point<2> cc = new Point<2>();
		Point<2> ccp = new Point<2>();
		for (j = 0; j<ntree; j++)
		{
			if (thelist[j].stat <= 0)
				continue;
			tt = thelist[j];
			for (k = 0; k<3; k++)
				trindx[tt.p[k]] = j;
		}
		for (p = 0; p<npts; p++)
		{
			tt = thelist[trindx[p]];
			if (tt.p[0] == p)
			{
				i = tt.p[1];
				j = tt.p[2];
			}
			else if (tt.p[1] == p)
			{
				i = tt.p[2];
				j = tt.p[0];
			}
			else if (tt.p[2] == p)
			{
				i = tt.p[0];
				j = tt.p[1];
			}
			else
				throw("triangle should contain p");
			jfirst = j;
			ccp = circumcircle(pts[p],pts[i],pts[j]).center;
			while (1)
			{
				key = hashfn.int64(i) - hashfn.int64(p);
				if (! linehash.get(key,k))
					throw("Delaunay is incomplete");
				cc = circumcircle(pts[p],pts[k],pts[i]).center;
				segs.set(nseg++, new Voredge(ccp,cc,p));
				if (k == jfirst)
					break;
				ccp = cc;
				j = i;
				i = k;
			}
		}
	}
}