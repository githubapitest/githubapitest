package com.google.code.numericalrecipes;
public class GlobalMembersKdtree
{
	public static Int selecti(Int k, Int[] indx, Int n, Doub[] arr)
	{
		Int i = new Int();
		Int ia = new Int();
		Int ir = new Int();
		Int j = new Int();
		Int l = new Int();
		Int mid = new Int();
		Doub a = new Doub();

		l = 0;
		ir = n-1;
		for (;;)
		{
			if (ir <= l+1)
			{
				if (ir == l+1 && arr[indx[ir]] < arr[indx[l]])
					SWAP(indx[l],indx[ir]);
				return indx[k];
			}
			else
			{
				mid = (l+ir) >> 1;
				SWAP(indx[mid],indx[l+1]);
				if (arr[indx[l]] > arr[indx[ir]])
					SWAP(indx[l],indx[ir]);
				if (arr[indx[l+1]] > arr[indx[ir]])
					SWAP(indx[l+1],indx[ir]);
				if (arr[indx[l]] > arr[indx[l+1]])
					SWAP(indx[l],indx[l+1]);
				i = l+1;
				j = ir;
				ia = indx[l+1];
				a = arr[ia];
				for (;;)
				{
					do
						i++;
						while (arr[indx[i]] < a);
					do
						j--;
						while (arr[indx[j]] > a);
					if (j < i)
						break;
					SWAP(indx[i],indx[j]);
				}
				indx[l+1]=indx[j];
				indx[j]=ia;
				if (j >= k)
					ir = j-1;
				if (j <= k)
					l = i;
			}
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
	public static KDtree<DIM>.KDtree(RefObject<java.util.ArrayList< Point<DIM> >> pts)
	{
		ptss = pts.argvalue;
		npts = pts.argvalue.size();
		ptindx = npts;
		rptindx = npts;
		Int ntmp = new Int();
		Int m = new Int();
		Int k = new Int();
		Int kk = new Int();
		Int j = new Int();
		Int nowtask = new Int();
		Int jbox = new Int();
		Int np = new Int();
		Int tmom = new Int();
		Int tdim = new Int();
		Int ptlo = new Int();
		Int pthi = new Int();
		Int hp;
		Doub cp;
		Int[] taskmom = new Int[50];
		Int[] taskdim = new Int[50];
		for (k = 0; k<npts; k++)
			ptindx[k] = k;
		m = 1;
		for (ntmp = npts; ntmp; ntmp >>= 1)
		{
			m <<= 1;
		}
		nboxes = 2 *npts - (m >> 1);
		if (m < nboxes)
			nboxes = m;
		nboxes--;
		boxes = new Boxnode<DIM>[nboxes];
		coords = new Doub[DIM *npts];
		for (j = 0, kk = 0; j<DIM; j++, kk += npts)
		{
			for (k = 0; k<npts; k++)
				coords[kk+k] = pts.argvalue.get(k).x[j];
		}
		Point<DIM> lo = new Point<DIM>(-BIG,-BIG,-BIG);
		Point<DIM> hi = new Point<DIM>(BIG,BIG,BIG);
		boxes[0] = Boxnode<DIM>(lo, hi, 0, 0, 0, 0, npts-1);
		jbox = 0;
		taskmom[1] = 0;
		taskdim[1] = 0;
		nowtask = 1;
		while (nowtask != null)
		{
			tmom = taskmom[nowtask];
			tdim = taskdim[nowtask--];
			ptlo = boxes[tmom].ptlo;
			pthi = boxes[tmom].pthi;
			hp = ptindx[ptlo];
			cp = coords[tdim *npts];
			np = pthi - ptlo + 1;
			kk = (np-1)/2;
			() selecti(kk, hp, np, cp);
			hi = boxes[tmom].hi;
			lo = boxes[tmom].lo;
			hi.x[tdim] = lo.x[tdim] = coords[tdim *npts + hp[kk]];
			boxes[++jbox] = Boxnode<DIM>(boxes[tmom].lo,hi,tmom,0,0,ptlo,ptlo+kk);
			boxes[++jbox] = Boxnode<DIM>(lo,boxes[tmom].hi,tmom,0,0,ptlo+kk+1,pthi);
			boxes[tmom].dau1 = jbox-1;
			boxes[tmom].dau2 = jbox;
			if (kk > 1)
			{
				taskmom[++nowtask] = jbox-1;
				taskdim[nowtask] = (tdim+1) % DIM;
			}
			if (np - kk > 3)
			{
				taskmom[++nowtask] = jbox;
				taskdim[nowtask] = (tdim+1) % DIM;
			}
		}
		for (j = 0; j<npts; j++)
			rptindx[ptindx[j]] = j;
		coords = null;
	}
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Doub KDtree<DIM>::disti(Int jpt, Int kpt)
	public <Int DIM> Doub KDtree<DIM>.disti(Int jpt, Int kpt)
	{
		if (jpt == kpt)
			return BIG;
		else
			return dist(ptss[jpt], ptss[kpt]);
	}
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int KDtree<DIM>::locate(Point<DIM> pt)
	public <Int DIM> Int KDtree<DIM>.locate(Point<DIM> pt)
	{
		Int nb = new Int();
		Int d1 = new Int();
		Int jdim = new Int();
		nb = jdim = 0;
		while (boxes[nb].dau1)
		{
			d1 = boxes[nb].dau1;
			if (pt.x[jdim] <= boxes[d1].hi.x[jdim])
				nb = d1;
			else
				nb = boxes[nb].dau2;
			jdim = ++jdim % DIM;
		}
		return nb;
	}
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int KDtree<DIM>::locate(Int jpt)
	public <Int DIM> Int KDtree<DIM>.locate(Int jpt)
	{
		Int nb = new Int();
		Int d1 = new Int();
		Int jh = new Int();
		jh = rptindx[jpt];
		nb = 0;
		while (boxes[nb].dau1)
		{
			d1 = boxes[nb].dau1;
			if (jh <= boxes[d1].pthi)
				nb = d1;
			else
				nb = boxes[nb].dau2;
		}
		return nb;
	}
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int KDtree<DIM>::nearest(Point<DIM> pt)
	public <Int DIM> Int KDtree<DIM>.nearest(Point<DIM> pt)
	{
		Int i = new Int();
		Int k = new Int();
		Int nrst = new Int();
		Int ntask = new Int();
		Int[] task = new Int[50];
		Doub dnrst = BIG;
		Doub d = new Doub();
		k = locate(pt);
		for (i = boxes[k].ptlo; i<=boxes[k].pthi; i++)
		{
			d = dist(ptss[ptindx[i]],pt);
			if (d < dnrst)
			{
				nrst = ptindx[i];
				dnrst = d;
			}
		}
		task[1] = 0;
		ntask = 1;
		while (ntask != null)
		{
			k = task[ntask--];
			if (dist(boxes[k],pt) < dnrst)
			{
				if (boxes[k].dau1)
				{
					task[++ntask] = boxes[k].dau1;
					task[++ntask] = boxes[k].dau2;
				}
				else
				{
					for (i = boxes[k].ptlo; i<=boxes[k].pthi; i++)
					{
						d = dist(ptss[ptindx[i]],pt);
						if (d < dnrst)
						{
							nrst = ptindx[i];
							dnrst = d;
						}
					}
				}
			}
		}
		return nrst;
	}
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: void KDtree<DIM>::nnearest(Int jpt, Int *nn, Doub *dn, Int n)
	public <Int DIM> void KDtree<DIM>.nnearest(Int jpt, Int[] nn, Doub[] dn, Int n)
	{
		Int i = new Int();
		Int k = new Int();
		Int ntask = new Int();
		Int kp = new Int();
		Int[] task = new Int[50];
		Doub d = new Doub();
		if (n > npts-1)
			throw("too many neighbors requested");
		for (i = 0; i<n; i++)
			dn[i] = BIG;
		kp = boxes[locate(jpt)].mom;
		while (boxes[kp].pthi - boxes[kp].ptlo < n)
			kp = boxes[kp].mom;
		for (i = boxes[kp].ptlo; i<=boxes[kp].pthi; i++)
		{
			if (jpt == ptindx[i])
				continue;
			d = disti(ptindx[i],jpt);
			if (d < dn[0])
			{
				dn[0] = d;
				nn[0] = ptindx[i];
				if (n>1)
					sift_down(dn,nn,n);
			}
		}
		task[1] = 0;
		ntask = 1;
		while (ntask != null)
		{
			k = task[ntask--];
			if (k == kp)
				continue;
			if (dist(boxes[k],ptss[jpt]) < dn[0])
			{
				if (boxes[k].dau1)
				{
					task[++ntask] = boxes[k].dau1;
					task[++ntask] = boxes[k].dau2;
				}
				else
				{
					for (i = boxes[k].ptlo; i<=boxes[k].pthi; i++)
					{
						d = disti(ptindx[i],jpt);
						if (d < dn[0])
						{
							dn[0] = d;
							nn[0] = ptindx[i];
							if (n>1)
								sift_down(dn,nn,n);
						}
					}
				}
			}
		}
		return;
	}
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: void KDtree<DIM>::sift_down(Doub *heap, Int *ndx, Int nn)
	public <Int DIM> void KDtree<DIM>.sift_down(Doub[] heap, Int[] ndx, Int nn)
	{
		Int n = nn - 1;
		Int j = new Int();
		Int jold = new Int();
		Int ia = new Int();
		Doub a = new Doub();
		a = heap[0];
		ia = ndx[0];
		jold = 0;
		j = 1;
		while (j <= n)
		{
			if (j < n && heap[j] < heap[j+1])
				j++;
			if (a >= heap[j])
				break;
			heap[jold] = heap[j];
			ndx[jold] = ndx[j];
			jold = j;
			j = 2 *j + 1;
		}
		heap[jold] = a;
		ndx[jold] = ia;
	}
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: Int KDtree<DIM>::locatenear(Point<DIM> pt, Doub r, Int *list, Int nmax)
	public <Int DIM> Int KDtree<DIM>.locatenear(Point<DIM> pt, Doub r, Int[] list, Int nmax)
	{
		Int k = new Int();
		Int i = new Int();
		Int nb = new Int();
		Int nbold = new Int();
		Int nret = new Int();
		Int ntask = new Int();
		Int jdim = new Int();
		Int d1 = new Int();
		Int d2 = new Int();
		Int[] task = new Int[50];
		nb = jdim = nret = 0;
		if (r < 0.0)
			throw("radius must be nonnegative");
		while (boxes[nb].dau1)
		{
			nbold = nb;
			d1 = boxes[nb].dau1;
			d2 = boxes[nb].dau2;
			if (pt.x[jdim] + r <= boxes[d1].hi.x[jdim])
				nb = d1;
			else if (pt.x[jdim] - r >= boxes[d2].lo.x[jdim])
				nb = d2;
			jdim = ++jdim % DIM;
			if (nb == nbold)
				break;
		}
		task[1] = nb;
		ntask = 1;
		while (ntask != null)
		{
			k = task[ntask--];
			if (dist(boxes[k],pt) > r)
				continue;
			if (boxes[k].dau1)
			{
				task[++ntask] = boxes[k].dau1;
				task[++ntask] = boxes[k].dau2;
			}
			else
			{
				for (i = boxes[k].ptlo; i<=boxes[k].pthi; i++)
				{
					if (dist(ptss[ptindx[i]],pt) <= r && nret < nmax)
						list[nret++] = ptindx[i];
					if (nret == nmax)
						return nmax;
				}
			}
		}
		return nret;
	}
}