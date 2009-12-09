package com.google.code.numericalrecipes;
public class Minspantree implements Delaunay
{
	public Int nspan = new Int();
	public VecInt minsega = new VecInt();
	public VecInt minsegb = new VecInt();
	public Minspantree(java.util.ArrayList< Point<2> > pvec)
	{
		RefObject<java.util.ArrayList<Point<2> >> tempRefObject = new RefObject<java.util.ArrayList<Point<2> >>(pvec);
		super(tempRefObject, 0);
		pvec = tempRefObject.argvalue;
		nspan = npts-1;
		minsega = nspan;
		minsegb = nspan;
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int jj = new Int();
		Int kk = new Int();
		Int m = new Int();
		Int tmp = new Int();
		Int nline = new Int();
		Int n = 0;
		Triel tt = new Triel();
		nline = ntri + npts -1;
		VecInt sega = new VecInt(nline);
		VecInt segb = new VecInt(nline);
		VecDoub segd = new VecDoub(nline);
		VecInt mo = new VecInt(npts);
		for (j = 0; j<ntree; j++)
		{
			if (thelist[j].stat == 0)
				continue;
			tt = thelist[j];
			for (i = 0,k = 1; i<3; i++,k++)
			{
				if (k == 3)
					k = 0;
				if (tt.p[i] > tt.p[k])
					continue;
				if (tt.p[i] >= npts || tt.p[k] >= npts)
					continue;
				sega[n] = tt.p[i];
				segb[n] = tt.p[k];
				segd[n] = dist(pts[sega[n]],pts[segb[n]]);
				n++;
			}
		}
		Indexx idx = new Indexx(segd);
		for (j = 0; j<npts; j++)
			mo[j] = j;
		n = -1;
		for (i = 0; i<nspan; i++)
		{
			 for (;;)
			 {
				jj = j = idx.el(sega,++n);
				kk = k = idx.el(segb,n);
				while (mo[jj] != jj)
					jj = mo[jj];
				while (mo[kk] != kk)
					kk = mo[kk];
				if (jj != kk)
				{
					minsega[i] = j;
					minsegb[i] = k;
					m = mo[jj] = kk;
					jj = j;
					while (mo[jj] != m)
					{
						tmp = mo[jj];
						mo[jj] = m;
						jj = tmp;
					}
					kk = k;
					while (mo[kk] != m)
					{
						tmp = mo[kk];
						mo[kk] = m;
						kk = tmp;
					}
					break;
				}
			}
		}
	}
}