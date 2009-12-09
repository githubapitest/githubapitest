package com.google.code.numericalrecipes;
public class Delaunay
{
	public Int npts = new Int();
	public Int ntri = new Int();
	public Int ntree = new Int();
	public Int ntreemax = new Int();
	public Int opt = new Int();
	public Doub delx = new Doub();
	public Doub dely = new Doub();
	public java.util.ArrayList< Point<2> > pts = new java.util.ArrayList< Point<2> >();
	public java.util.ArrayList<Triel> thelist = new java.util.ArrayList<Triel>();
	public Hash<Ullong,Int,Nullhash> linehash;
	public Hash<Ullong,Int,Nullhash> trihash;
	public Int perm;
	public Delaunay(RefObject<java.util.ArrayList< Point<2> >> pvec)
	{
		this(pvec, 0);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Delaunay(java.util.ArrayList< Point<2> > &pvec, Int options = 0) : npts(pvec.size()), ntri(0), ntree(0), ntreemax(10 *npts+1000), opt(options), pts(npts+3), thelist(ntreemax)
	public Delaunay(RefObject<java.util.ArrayList< Point<2> >> pvec, Int options)
	{
		npts = pvec.argvalue.size();
		ntri = 0;
		ntree = 0;
		ntreemax = 10 *npts+1000;
		opt = options;
		pts = npts+3;
		thelist = ntreemax;
		Int j = new Int();
		Doub xl = new Doub();
		Doub xh = new Doub();
		Doub yl = new Doub();
		Doub yh = new Doub();
		linehash = new Hash<Ullong,Int,Nullhash>(6 *npts+12,6 *npts+12);
		trihash = new Hash<Ullong,Int,Nullhash>(2 *npts+6,2 *npts+6);
		perm = new Int[npts];
		xl = xh = pvec.argvalue.get(0).x[0];
		yl = yh = pvec.argvalue.get(0).x[1];
		for (j = 0; j<npts; j++)
		{
			pts.set(j, pvec.argvalue.get(j));
			perm[j] = j;
			if (pvec.argvalue.get(j).x[0] < xl)
				xl = pvec.argvalue.get(j).x[0];
			if (pvec.argvalue.get(j).x[0] > xh)
				xh = pvec.argvalue.get(j).x[0];
			if (pvec.argvalue.get(j).x[1] < yl)
				yl = pvec.argvalue.get(j).x[1];
			if (pvec.argvalue.get(j).x[1] > yh)
				yh = pvec.argvalue.get(j).x[1];
		}
		delx = xh - xl;
		dely = yh - yl;
		pts.set(npts, Point<2>(0.5*(xl + xh), yh + bigscale *dely));
		pts.set(npts+1, Point<2>(xl - 0.5 *bigscale *delx,yl - 0.5 *bigscale *dely));
		pts.set(npts+2, Point<2>(xh + 0.5 *bigscale *delx,yl - 0.5 *bigscale *dely));
		storetriangle(npts, npts+1, npts+2);
		for (j = npts; j>0; j--)
			SWAP(perm[j-1],perm[hashfn.int64(jran++) % j]);
		for (j = 0; j<npts; j++)
			insertapoint(perm[j]);
		for (j = 0; j<ntree; j++)
		{
		  if (thelist.get(j).stat > 0)
		  {
				if (thelist.get(j).p[0] >= npts || thelist.get(j).p[1] >= npts || thelist.get(j).p[2] >= npts)
				{
					thelist.get(j).stat = -1;
					ntri--;
				}
			}
		}
		if (!(opt & 1))
		{
			perm = null;
			trihash = null;
			linehash = null;
		}
	}
	public Ranhash hashfn = new Ranhash();
	public final Doub interpolate(Point<2> p, java.util.ArrayList<Doub> fnvals)
	{
		return interpolate(p, fnvals, 0.0);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Doub interpolate(const Point<2> &p, const java.util.ArrayList<Doub> &fnvals, Doub defaultval =0.0)
	public final Doub interpolate(Point<2> p, java.util.ArrayList<Doub> fnvals, Doub defaultval)
	{
		Int n = new Int();
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Doub[] wgts = new Doub[3];
		Int[] ipts = new Int[3];
		Doub sum = new Doub();
		Doub ans = 0.0;
		n = whichcontainspt(p);
		if (n < 0)
			return defaultval;
		 for (i = 0; i<3; i++)
			 ipts[i] = thelist.get(n).p[i];
		for (i = 0,j = 1,k = 2; i<3; i++,j++,k++)
		{
			if (j == 3)
				j = 0;
			if (k == 3)
				k = 0;
			wgts[k] =(pts.get(ipts[j]).x[0]-pts.get(ipts[i]).x[0])*(p.x[1]-pts.get(ipts[i]).x[1]) - (pts.get(ipts[j]).x[1]-pts.get(ipts[i]).x[1])*(p.x[0]-pts.get(ipts[i]).x[0]);
		}
		sum = wgts[0] + wgts[1] + wgts[2];
		if (sum == 0)
			throw("degenerate triangle");
		for (i = 0; i<3; i++)
			ans += wgts[i]*fnvals.get(ipts[i])/sum;
		return ans;
	}
	public final void insertapoint(Int r)
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int l = new Int();
		Int s = new Int();
		Int tno = new Int();
		Int ntask = new Int();
		Int d0 = new Int();
		Int d1 = new Int();
		Int d2 = new Int();
		Ullong key = new Ullong();
		Int[] tasks = new Int[50];
		Int[] taski = new Int[50];
		Int[] taskj = new Int[50];
		for (j = 0; j<3; j++)
		{
			tno = whichcontainspt(pts.get(r), 1);
			if (tno >= 0)
				break;
			pts.get(r).x[0] += fuzz * delx * (hashfn.doub(jran++)-0.5);
			pts.get(r).x[1] += fuzz * dely * (hashfn.doub(jran++)-0.5);
		}
		if (j == 3)
			throw("points degenerate even after fuzzing");
		ntask = 0;
		i = thelist.get(tno).p[0];
		j = thelist.get(tno).p[1];
		k = thelist.get(tno).p[2];
		if (opt & 2 && i < npts && j < npts && k < npts)
			return;
		d0 = storetriangle(r, i, j);
		tasks[++ntask] = r;
		taski[ntask] = i;
		taskj[ntask] = j;
		d1 = storetriangle(r, j, k);
		tasks[++ntask] = r;
		taski[ntask] = j;
		taskj[ntask] = k;
		d2 = storetriangle(r, k, i);
		tasks[++ntask] = r;
		taski[ntask] = k;
		taskj[ntask] = i;
		erasetriangle(i, j, k, d0, d1, d2);
		while (ntask != null)
		{
			s = tasks[ntask];
			i = taski[ntask];
			j = taskj[ntask--];
			key = hashfn.int64(j) - hashfn.int64(i);
			if (! linehash.get(key,l))
				continue;
			if (GlobalMembersDelaunay.incircle(pts.get(l), pts.get(j), pts.get(s), pts.get(i)) > 0.0)
			{
				d0 = storetriangle(s, l, j);
				d1 = storetriangle(s, i, l);
				erasetriangle(s, i, j, d0, d1, -1);
				erasetriangle(l, j, i, d0, d1, -1);
				key = hashfn.int64(i)-hashfn.int64(j);
				linehash.erase(key);
				key = 0 - key;
				linehash.erase(key);
				tasks[++ntask] = s;
				taski[ntask] = l;
				taskj[ntask] = j;
				tasks[++ntask] = s;
				taski[ntask] = i;
				taskj[ntask] = l;
			}
		}
	}
	public final Int whichcontainspt(Point<2> p)
	{
		return whichcontainspt(p, 0);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Int whichcontainspt(const Point<2> &p, Int strict = 0)
	public final Int whichcontainspt(Point<2> p, Int strict)
	{
		Int i = new Int();
		Int j = new Int();
		Int k = 0;
		while (thelist.get(k).stat <= 0)
		{
			for (i = 0; i<3; i++)
			{
				if ((j = thelist.get(k).d[i]) < 0)
					continue;
				if (strict != null)
				{
					if (thelist.get(j).contains(p) > 0)
						break;
				}
				else
				{
					if (thelist.get(j).contains(p) >= 0)
						break;
				}
			}
			if (i == 3)
				return -1;
			k = j;
		}
		return k;
	}
	public final Int storetriangle(Int a, Int b, Int c)
	{
		Ullong key = new Ullong();
		thelist.get(ntree).setme(a, b, c, pts.get(0));
		key = hashfn.int64(a) ^ hashfn.int64(b) ^ hashfn.int64(c);
		trihash.set(key,ntree);
		key = hashfn.int64(b)-hashfn.int64(c);
		linehash.set(key,a);
		key = hashfn.int64(c)-hashfn.int64(a);
		linehash.set(key,b);
		key = hashfn.int64(a)-hashfn.int64(b);
		linehash.set(key,c);
		if (++ntree == ntreemax)
			throw("thelist is sized too small");
		ntri++;
		return (ntree-1);
	}
	public final void erasetriangle(Int a, Int b, Int c, Int d0, Int d1, Int d2)
	{
		Ullong key = new Ullong();
		Int j = new Int();
		key = hashfn.int64(a) ^ hashfn.int64(b) ^ hashfn.int64(c);
		if (trihash.get(key,j) == 0)
			throw("nonexistent triangle");
		trihash.erase(key);
		thelist.get(j).d[0] = d0;
		thelist.get(j).d[1] = d1;
		thelist.get(j).d[2] = d2;
		thelist.get(j).stat = 0;
		ntri--;
	}
	public static Uint jran = 14921620;
	public final Doub fuzz = new Doub();
	public final Doub bigscale = new Doub();
}