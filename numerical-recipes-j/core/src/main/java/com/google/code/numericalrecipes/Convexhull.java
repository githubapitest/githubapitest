package com.google.code.numericalrecipes;
public class Convexhull implements Delaunay
{
	public Int nhull = new Int();
	public Int hullpts;
	public Convexhull(java.util.ArrayList< Point<2> > pvec)
	{
		RefObject<java.util.ArrayList<Point<2> >> tempRefObject = new RefObject<java.util.ArrayList<Point<2> >>(pvec);
		super(tempRefObject, 2);
		pvec = tempRefObject.argvalue;
		nhull = 0;
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int pstart = new Int();
		java.util.ArrayList<Int> nextpt = new java.util.ArrayList<Int>(npts);
		for (j = 0; j<ntree; j++)
		{
			if (thelist[j].stat != -1)
				continue;
			for (i = 0,k = 1; i<3; i++,k++)
			{
				if (k == 3)
					k = 0;
				if (thelist[j].p[i] < npts && thelist[j].p[k] < npts)
					break;
			}
			if (i == 3)
				continue;
			++nhull;
			nextpt.set(thelist[j].p[i])((pstart;
		}
		if (nhull == 0)
			throw("no hull segments found");
		hullpts = new Int[nhull];
		j = 0;
		i = hullpts[j++] = pstart;
		while ((i = nextpt.get(i)) != pstart)
			hullpts[j++] = i;
	}
}