package com.google.code.numericalrecipes;
public class GlobalMembersQotree
{

	public static Qotree<elT,DIM>.Qotree(Int nh, Int nv, Int maxdep)
	{
		elhash = new Mhash(nh, nv);
		maxd = maxdep;
		pophash = new Hash(maxd *nh, maxd *nv);
		if (maxd > PMAX)
			throw("maxdep too large in Qotree");
		setouterbox(Point<DIM>(0.0,0.0,0.0),Point<DIM>(1.0,1.0,1.0));
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class elT, Int DIM>

//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: void Qotree<elT,DIM>::setouterbox(Point<DIM> lo, Point<DIM> hi)
	public <elT, Int DIM> void Qotree<elT,DIM>.setouterbox(Point<DIM> lo, Point<DIM> hi)
	{
		for (Int j = 0; j<DIM; j++)
		{
			blo[j] = lo.x[j];
			bscale[j] = hi.x[j] - lo.x[j];
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class elT, Int DIM>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Box<DIM> Qotree<elT,DIM>::qobox(Int k)
	public <elT, Int DIM> Box<DIM> Qotree<elT,DIM>.qobox(Int k)
	{
		Int j = new Int();
		Int kb = new Int();
		Point<DIM> plo = new Point<DIM>();
		Point<DIM> phi = new Point<DIM>();
		Doub[] offset = new Doub[DIM];
		Doub del = 1.0;
		for (j = 0; j<DIM; j++)
			offset[j] = 0.0;
		while (k > 1)
		{
			kb = (k + QL) % QO;
			for (j = 0; j<DIM; j++)
			{
				if (kb & (1 << j) != 0)
					offset[j] += del;
			}
			k = (k + QL) >> DIM;
			del *= 2.0;
		}
		for (j = 0; j<DIM; j++)
		{
			plo.x[j] = blo[j] + bscale[j]*offset[j]/del;
			phi.x[j] = blo[j] + bscale[j]*(offset[j]+1.0)/del;
		}
		return Box<DIM>(plo,phi);
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class elT, Int DIM>

//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int Qotree<elT,DIM>::qowhichbox(elT tobj)
	public <elT, Int DIM> Int Qotree<elT,DIM>.qowhichbox(elT tobj)
	{
		Int p = new Int();
		Int k = new Int();
		Int kl = new Int();
		Int kr = new Int();
		Int ks = 1;
		for (p = 2; p<=maxd; p++)
		{
			kl = QO * ks - QL;
			kr = kl + QO -1;
			for (k = kl; k<=kr; k++)
			{
				if (tobj.isinbox(qobox(k)))
				{
					ks = k;
					break;
				}
			}
			if (k > kr)
				break;
		}
		return ks;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class elT, Int DIM>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int Qotree<elT,DIM>::qostore(elT tobj)
	public <elT, Int DIM> Int Qotree<elT,DIM>.qostore(elT tobj)
	{
		Int k = new Int();
		Int ks = new Int();
		Int kks = new Int();
		Int km = new Int();
		ks = kks = qowhichbox(tobj);
		elhash.store(ks, tobj);
		pophash[ks] |= 1;
		while (ks > 1)
		{
			km = (ks + QL) >> DIM;
			k = ks - (QO *km - QL);
			ks = km;
			pophash[ks] |= (1 << (k+1));
		}
		return kks;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class elT, Int DIM>

//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int Qotree<elT,DIM>::qoerase(elT tobj)
	public <elT, Int DIM> Int Qotree<elT,DIM>.qoerase(elT tobj)
	{
		Int k = new Int();
		Int ks = new Int();
		Int kks = new Int();
		Int km = new Int();
		Int ppop;
		ks = kks = qowhichbox(tobj);
		if (elhash.erase(ks, tobj) == 0)
			return 0;
		if (elhash.count(ks))
			return kks;
		ppop = pophash[ks];
		ppop &= ~((Uint)1);
		while (ks > 1)
		{
			if (ppop != null)
				break;
			pophash.erase(ks);
			km = (ks + QL) >> DIM;
			k = ks - (QO *km - QL);
			ks = km;
			ppop = pophash[ks];
			ppop &= ~((Uint)(1 << (k+1)));
		}
		return kks;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class elT, Int DIM>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int Qotree<elT,DIM>::qoget(Int k, elT *list, Int nmax)
	public <elT, Int DIM> Int Qotree<elT,DIM>.qoget(Int k, elT[] list, Int nmax)
	{
		Int ks = new Int();
		Int pop = new Int();
		Int nlist = new Int();
		ks = k;
		nlist = 0;
		pophash.get(ks,pop);
		if ((pop & 1) && elhash.getinit(ks))
		{
			while (nlist < nmax && elhash.getnext(list[nlist]))
			{
				nlist++;
			}
		}
		return nlist;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class elT, Int DIM>

//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int Qotree<elT,DIM>::qodump(Int *klist, elT *list, Int nmax)
	public <elT, Int DIM> Int Qotree<elT,DIM>.qodump(Int[] klist, elT[] list, Int nmax)
	{
		Int nlist = new Int();
		Int ntask = new Int();
		Int ks = new Int();
		Int pop = new Int();
		Int k = new Int();
		Int[] tasklist = new Int[200];
		nlist = 0;
		ntask = 1;
		tasklist[1] = 1;
		while (ntask != null)
		{
			ks = tasklist[ntask--];
			if (pophash.get(ks,pop) == 0)
				continue;
			if ((pop & 1) && elhash.getinit(ks))
			{
				while (nlist < nmax && elhash.getnext(list[nlist]))
				{
					klist[nlist] = ks;
					nlist++;
				}
			}
			if (nlist == nmax)
				break;
			k = QO *ks - QL;
			while (pop >>= 1)
			{
				if (pop & 1 != 0)
					tasklist[++ntask] = k;
				k++;
			}
		}
		return nlist;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class elT, Int DIM>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int Qotree<elT,DIM>::qocontainspt(Point<DIM>pt, elT *list, Int nmax)
	public <elT, Int DIM> Int Qotree<elT,DIM>.qocontainspt(Point<DIM>pt, elT[] list, Int nmax)
	{
		Int j = new Int();
		Int k = new Int();
		Int ks = new Int();
		Int pop = new Int();
		Int nlist = new Int();
		Doub[] bblo = new Doub[DIM];
		Doub[] bbscale = new Doub[DIM];
		for (j = 0; j<DIM; j++)
		{
			bblo[j] = blo[j];
			bbscale[j] = bscale[j];
		}
		nlist = 0;
		ks = 1;
		while (pophash.get(ks,pop))
		{
			if (pop & 1 != 0)
			{
				elhash.getinit(ks);
				while (nlist < nmax && elhash.getnext(list[nlist]))
				{
					if (list[nlist].contains(pt))
					{
						nlist++;
					}
				}
			}
			if ((pop >>= 1) == 0)
				break;
			for (k = 0, j = 0; j<DIM; j++)
			{
				bbscale[j] *= 0.5;
				if (pt.x[j] > bblo[j] + bbscale[j])
				{
					k += (1 << j);
					bblo[j] += bbscale[j];
				}
			}
			if (((pop >> k) & 1) == 0)
				break;
			ks = QO * ks - QL + k;
		}
		return nlist;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class elT, Int DIM>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int Qotree<elT,DIM>::qocollides(elT qt, elT *list, Int nmax)
	public <elT, Int DIM> Int Qotree<elT,DIM>.qocollides(elT qt, elT[] list, Int nmax)
	{
		Int k = new Int();
		Int ks = new Int();
		Int kks = new Int();
		Int pop = new Int();
		Int nlist = new Int();
		Int ntask = new Int();
		Int[] tasklist = new Int[200];
		nlist = 0;
		kks = ks = qowhichbox(qt);
		ntask = 0;
		while (ks > 0)
		{
			tasklist[++ntask] = ks;
			ks = (ks + QL) >> DIM;
		}
		while (ntask != null)
		{
			ks = tasklist[ntask--];
			if (pophash.get(ks,pop) == 0)
				continue;
			if (pop & 1 != 0)
			{
				elhash.getinit(ks);
				while (nlist < nmax && elhash.getnext(list[nlist]))
				{
					if (list[nlist].collides(qt))
					{
						nlist++;
					}
				}
			}
			if (ks >= kks)
			{
				k = QO *ks - QL;
				while (pop >>= 1)
				{
					if (pop & 1 != 0)
						tasklist[++ntask] = k;
					k++;
				}
			}
		}
		return nlist;
	}
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template <int DIM>
}