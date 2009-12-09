package com.google.code.numericalrecipes;
public class Mglin
{
	public Int n = new Int();
	public Int ng = new Int();
	public MatDoub uj;
	public MatDoub uj1;
	public NRvector<NRmatrix<Doub> > rho = new NRvector<NRmatrix<Doub> >();

	public Mglin(RefObject<MatDoub_IO> u, Int ncycle)
	{
		n = u.argvalue.nrows();
		ng = 0;
		Int nn = n;
		while (nn >>= 1)
			ng++;
		if ((n-1) != (1 << ng))
			throw("n-1 must be a power of 2 in mglin.");
		nn = n;
		Int ngrid = ng-1;
		rho.resize(ng);
		rho[ngrid] = new MatDoub(nn,nn);
		*rho[ngrid]=u.argvalue;
		while (nn > 3)
		{
			nn = nn/2+1;
			rho[--ngrid]=new MatDoub(nn,nn);
			rstrct(*rho[ngrid], *rho[ngrid+1]);
		}
		nn = 3;
		uj = new MatDoub(nn,nn);
		RefObject<MatDoub_O> tempRefObject = new RefObject<MatDoub_O>(uj);
		slvsml(tempRefObject, *rho[0]);
		uj = tempRefObject.argvalue;
		for (Int j = 1;j<ng;j++)
		{
			nn = 2 *nn-1;
			uj1 = uj;
			uj = new MatDoub(nn,nn);
			RefObject<MatDoub_O> tempRefObject2 = new RefObject<MatDoub_O>(uj);
			RefObject<MatDoub_I> tempRefObject3 = new RefObject<MatDoub_I>(uj1);
			interp(tempRefObject2, tempRefObject3);
			uj = tempRefObject2.argvalue;
			uj1 = tempRefObject3.argvalue;
			uj1 = null;
			for (Int jcycle = 0;jcycle<ncycle;jcycle++)
			{
				RefObject<MatDoub_IO> tempRefObject4 = new RefObject<MatDoub_IO>(uj);
				mg(j, tempRefObject4, *rho[j]);
				uj = tempRefObject4.argvalue;
			}
		}
		u.argvalue = uj;
	}

	public void Dispose()
	{
		if (uj != null)
			uj = null;
		for (Int j = 0;j<ng;j++)
			if (rho[j] != null)
				rho[j] = null;
	}

	public final void interp(RefObject<MatDoub_O> uf, RefObject<MatDoub_I> uc)
	{
		Int nf = uf.argvalue.nrows();
		Int nc = nf/2+1;
		for (Int jc = 0;jc<nc;jc++)
			for (Int ic = 0;ic<nc;ic++)
				uf.argvalue[2 *ic][2 *jc]=uc.argvalue[ic][jc];
		for (Int jf = 0;jf<nf;jf+=2)
			for (Int iif = 1;iif<nf-1;iif+=2)
				uf.argvalue[iif][jf]=0.5*(uf.argvalue[iif+1][jf]+uf.argvalue[iif-1][jf]);
		for (Int jf = 1;jf<nf-1;jf+=2)
			for (Int iif = 0;iif<nf;iif++)
				uf.argvalue[iif][jf]=0.5*(uf.argvalue[iif][jf+1]+uf.argvalue[iif][jf-1]);
	}

	public final void addint(RefObject<MatDoub_O> uf, RefObject<MatDoub_I> uc, RefObject<MatDoub_O> res)
	{
		Int nf = uf.argvalue.nrows();
		interp(res, uc);
		for (Int j = 0;j<nf;j++)
			for (Int i = 0;i<nf;i++)
				uf.argvalue[i][j] += res.argvalue[i][j];
	}

	public final void slvsml(RefObject<MatDoub_O> u, RefObject<MatDoub_I> rhs)
	{
		Doub h = 0.5;
		for (Int i = 0;i<3;i++)
			for (Int j = 0;j<3;j++)
				u.argvalue[i][j]=0.0;
		u.argvalue[1][1] = -h *h *rhs.argvalue[1][1]/4.0;
	}

	public final void relax(RefObject<MatDoub_IO> u, RefObject<MatDoub_I> rhs)
	{
		Int n = u.argvalue.nrows();
		Doub h = 1.0/(n-1);
		Doub h2 = h *h;
		for (Int ipass = 0,jsw=1;ipass<2;ipass++,jsw=3-jsw)
		{
			for (Int j = 1,isw=jsw;j<n-1;j++,isw=3-isw)
				for (Int i = isw;i<n-1;i+=2)
					u.argvalue[i][j]=0.25*(u.argvalue[i+1][j]+u.argvalue[i-1][j]+u.argvalue[i][j+1] +u.argvalue[i][j-1]-h2 *rhs.argvalue[i][j]);
		}
	}

	public final void resid(RefObject<MatDoub_O> res, RefObject<MatDoub_I> u, RefObject<MatDoub_I> rhs)
	{
		Int n = u.argvalue.nrows();
		Doub h = 1.0/(n-1);
		Doub h2i = 1.0/(h *h);
		for (Int j = 1;j<n-1;j++)
			for (Int i = 1;i<n-1;i++)
				res.argvalue[i][j] = -h2i*(u.argvalue[i+1][j]+u.argvalue[i-1][j]+u.argvalue[i][j+1] +u.argvalue[i][j-1]-4.0 *u.argvalue[i][j])+rhs.argvalue[i][j];
		for (Int i = 0;i<n;i++)
			res.argvalue[i][0]=res.argvalue[i][n-1]=res.argvalue[0][i]=res.argvalue[n-1][i]=0.0;
	}

	public final void rstrct(RefObject<MatDoub_O> uc, RefObject<MatDoub_I> uf)
	{
		Int nc = uc.argvalue.nrows();
		Int ncc = 2 *nc-2;
		for (Int jf = 2,jc=1;jc<nc-1;jc++,jf+=2)
		{
			for (Int iif = 2,ic=1;ic<nc-1;ic++,iif+=2)
			{
				uc.argvalue[ic][jc]=0.5 *uf.argvalue[iif][jf]+0.125*(uf.argvalue[iif+1][jf]+uf.argvalue[iif-1][jf] +uf.argvalue[iif][jf+1]+uf.argvalue[iif][jf-1]);
			}
		}
		for (Int jc = 0,ic=0;ic<nc;ic++,jc+=2)
		{
			uc.argvalue[ic][0]=uf.argvalue[jc][0];
			uc.argvalue[ic][nc-1]=uf.argvalue[jc][ncc];
		}
		for (Int jc = 0,ic=0;ic<nc;ic++,jc+=2)
		{
			uc.argvalue[0][ic]=uf.argvalue[0][jc];
			uc.argvalue[nc-1][ic]=uf.argvalue[ncc][jc];
		}
	}

	public final void mg(Int j, RefObject<MatDoub_IO> u, RefObject<MatDoub_I> rhs)
	{
		final Int NPRE = 1;
		final Int NPOST = 1;
		Int nf = u.argvalue.nrows();
		Int nc = (nf+1)/2;
		if (j == 0)
			slvsml(u, rhs);
		else
		{
			MatDoub res = new MatDoub(nc,nc);
			MatDoub v = new MatDoub(nc,nc,0.0);
			MatDoub temp = new MatDoub(nf,nf);
			for (Int jpre = 0;jpre<NPRE;jpre++)
				relax(u, rhs);
			RefObject<MatDoub_O> tempRefObject = new RefObject<MatDoub_O>(temp);
			resid(tempRefObject, u, rhs);
			temp = tempRefObject.argvalue;
			RefObject<MatDoub_O> tempRefObject2 = new RefObject<MatDoub_O>(res);
			RefObject<MatDoub_I> tempRefObject3 = new RefObject<MatDoub_I>(temp);
			rstrct(tempRefObject2, tempRefObject3);
			res = tempRefObject2.argvalue;
			temp = tempRefObject3.argvalue;
			RefObject<MatDoub_IO> tempRefObject4 = new RefObject<MatDoub_IO>(v);
			RefObject<MatDoub_I> tempRefObject5 = new RefObject<MatDoub_I>(res);
			mg(j-1, tempRefObject4, tempRefObject5);
			v = tempRefObject4.argvalue;
			res = tempRefObject5.argvalue;
			RefObject<MatDoub_I> tempRefObject6 = new RefObject<MatDoub_I>(v);
			RefObject<MatDoub_O> tempRefObject7 = new RefObject<MatDoub_O>(temp);
			addint(u, tempRefObject6, tempRefObject7);
			v = tempRefObject6.argvalue;
			temp = tempRefObject7.argvalue;
			for (Int jpost = 0;jpost<NPOST;jpost++)
				relax(u, rhs);
		}
	}
}