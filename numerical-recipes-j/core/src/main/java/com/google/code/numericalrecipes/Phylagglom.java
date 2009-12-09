package com.google.code.numericalrecipes;
public abstract class Phylagglom
{
	public Int n = new Int();
	public Int root = new Int();
	public Int fsroot = new Int();
	public Doub seqmax = new Doub();
	public Doub depmax = new Doub();
	public java.util.ArrayList<Phylagglomnode> t = new java.util.ArrayList<Phylagglomnode>();
	public abstract void premin(RefObject<MatDoub> d, RefObject<VecInt> nextp);
	public abstract Doub dminfn(RefObject<MatDoub> d, Int i, Int j);
	public abstract Doub dbranchfn(RefObject<MatDoub> d, Int i, Int j);
	public abstract Doub dnewfn(RefObject<MatDoub> d, Int k, Int i, Int j, Int ni, Int nj);
	public abstract void drootbranchfn(RefObject<MatDoub> d, Int i, Int j, Int ni, Int nj, RefObject<Doub> bi, RefObject<Doub> bj);
	public final Int comancestor(Int leafa, Int leafb)
	{
		Int i = new Int();
		Int j = new Int();
		for (i = leafa; i != root; i = t.get(i).mo)
		{
			for (j = leafb; j != root; j = t.get(j).mo)
				if (i == j)
					break;
			if (i == j)
				break;
		}
		return i;
	}
	public Phylagglom(MatDoub dist)
	{
		this(dist, -1);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Phylagglom(const MatDoub &dist, Int fsr = -1) : n(dist.nrows()), fsroot(fsr), t(2 *n-1)
	public Phylagglom(MatDoub dist, Int fsr)
	{
		n = GlobalMembersPhylo.dist.nrows();
		fsroot = fsr;
		t = 2 *n-1;
	}

	public final void makethetree(MatDoub dist)
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int imin = new Int();
		Int jmin = new Int();
		Int ncurr = new Int();
		Int node = new Int();
		Int ntask = new Int();
		Doub dd = new Doub();
		Doub dmin = new Doub();
		MatDoub d = new MatDoub(GlobalMembersPhylo.dist);
		VecInt tp = new VecInt(n);
		VecInt nextp = new VecInt(n);
		VecInt prevp = new VecInt(n);
		VecInt tasklist = new VecInt(2 *n+1);
		VecDoub tmp = new VecDoub(n);
		for (i = 0;i<n;i++)
		{
			nextp[i] = i+1;
			prevp[i] = i-1;
			tp[i] = i;
			t.get(i).ldau = t.get(i).rdau = -1;
			t.get(i).nel = 1;
		}
		prevp[0] = nextp[n-1] = -1;
		ncurr = n;
		for (node = n; node < 2 *n-2; node++)
		{
			RefObject<MatDoub> tempRefObject = new RefObject<MatDoub>(d);
			RefObject<VecInt> tempRefObject2 = new RefObject<VecInt>(nextp);
			premin(tempRefObject, tempRefObject2);
			d = tempRefObject.argvalue;
			nextp = tempRefObject2.argvalue;
			dmin = 9.99e99;
			for (i = 0; i>=0; i = nextp[i])
			{
				if (tp[i] == fsroot)
					continue;
				for (j = nextp[i]; j>=0; j = nextp[j])
				{
					if (tp[j] == fsroot)
						continue;
					if ((dd = dminfn(d, i, j)) < dmin)
					{
						dmin = dd;
						imin = i;
						jmin = j;
					}
				}
			}
			i = imin;
			j = jmin;
			t.get(tp[i]).mo = t.get(tp[j]).mo = node;
			RefObject<MatDoub> tempRefObject3 = new RefObject<MatDoub>(d);
			t.get(tp[i]).modist = dbranchfn(tempRefObject3, i, j);
			d = tempRefObject3.argvalue;
			RefObject<MatDoub> tempRefObject4 = new RefObject<MatDoub>(d);
			t.get(tp[j]).modist = dbranchfn(tempRefObject4, j, i);
			d = tempRefObject4.argvalue;
			t.get(node).ldau = tp[i];
			t.get(node).rdau = tp[j];
			t.get(node).nel = t.get(tp[i]).nel + t.get(tp[j]).nel;
			for (k = 0; k>=0; k = nextp[k])
			{
				RefObject<MatDoub> tempRefObject5 = new RefObject<MatDoub>(d);
				tmp[k] = dnewfn(tempRefObject5, k, i, j, t.get(tp[i]).nel, t.get(tp[j]).nel);
				d = tempRefObject5.argvalue;
			}
			for (k = 0; k>=0; k = nextp[k])
				d[i][k] = d[k][i] = tmp[k];
			tp[i] = node;
			if (prevp[j] >= 0)
				nextp[prevp[j]] = nextp[j];
			if (nextp[j] >= 0)
				prevp[nextp[j]] = prevp[j];
			ncurr--;
		}
		i = 0;
		j = nextp[0];
		root = node;
		t.get(tp[i]).mo = t.get(tp[j]).mo = t.get(root).mo = root;
		RefObject<MatDoub> tempRefObject6 = new RefObject<MatDoub>(d);
		drootbranchfn(tempRefObject6, i, j, t.get(tp[i]).nel, t.get(tp[j]).nel, t.get(tp[i]).modist, t.get(tp[j]).modist);
		d = tempRefObject6.argvalue;
		t.get(root).ldau = tp[i];
		t.get(root).rdau = tp[j];
		t.get(root).modist = t.get(root).dep = 0.;
		t.get(root).nel = t.get(tp[i]).nel + t.get(tp[j]).nel;
		ntask = 0;
		seqmax = depmax = 0.;
		tasklist[ntask++] = root;
		while (ntask > 0)
		{
			i = tasklist[--ntask];
			if (i >= 0)
			{
				t.get(i).dep = t.get(t.get(i).mo).dep + t.get(i).modist;
				if (t.get(i).dep > depmax)
					depmax = t.get(i).dep;
				if (t.get(i).ldau < 0)
				{
					t.get(i).seq = seqmax++;
				}
				else
				{
					tasklist[ntask++] = -i-1;
					tasklist[ntask++] = t.get(i).ldau;
					tasklist[ntask++] = t.get(i).rdau;
				}
			}
			else
			{
				i = -i-1;
				t.get(i).seq = 0.5*(t.get(t.get(i).ldau).seq + t.get(t.get(i).rdau).seq);
			}
		}
	}
}