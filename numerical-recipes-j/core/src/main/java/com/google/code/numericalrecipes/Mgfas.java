package com.google.code.numericalrecipes;
public class Mgfas
{
	public Int n = new Int();
	public Int ng = new Int();
	public MatDoub uj;
	public MatDoub uj1;
	public NRvector<NRmatrix<Doub> > rho = new NRvector<NRmatrix<Doub> >();

	public Mgfas(RefObject<MatDoub_IO> u, Int maxcyc)
	{
		n = u.argvalue.nrows();
		ng = 0;
		Int nn = n;
		while (nn >>= 1)
			ng++;
		if ((n-1) != (1 << ng))
			throw("n-1 must be a power of 2 in mgfas.");
		nn = n;
		Int ngrid = ng-1;
		rho.resize(ng);
		rho[ngrid]=new MatDoub(nn,nn);
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
		slvsm2(tempRefObject, *rho[0]);
		uj = tempRefObject.argvalue;
		for (Int j = 1;j<ng;j++)
		{
			nn = 2 *nn-1;
			uj1 = uj;
			uj = new MatDoub(nn,nn);
			MatDoub temp = new MatDoub(nn,nn);
			RefObject<MatDoub_O> tempRefObject2 = new RefObject<MatDoub_O>(uj);
			RefObject<MatDoub_I> tempRefObject3 = new RefObject<MatDoub_I>(uj1);
			interp(tempRefObject2, tempRefObject3);
			uj = tempRefObject2.argvalue;
			uj1 = tempRefObject3.argvalue;
			uj1 = null;
			for (Int jcycle = 0;jcycle<maxcyc;jcycle++)
			{
				Doub trerr = 1.0;
				RefObject<MatDoub_IO> tempRefObject4 = new RefObject<MatDoub_IO>(uj);
				RefObject<MatDoub_I> tempRefObject5 = new RefObject<MatDoub_I>(temp);
				RefObject<NRvector<NRmatrix<Doub> >> tempRefObject6 = new RefObject<NRvector<NRmatrix<Doub> >>(rho);
				RefObject<Doub> tempRefObject7 = new RefObject<Doub>(trerr);
				mg(j, tempRefObject4, tempRefObject5, tempRefObject6, tempRefObject7);
				uj = tempRefObject4.argvalue;
				temp = tempRefObject5.argvalue;
				rho = tempRefObject6.argvalue;
				trerr = tempRefObject7.argvalue;
				RefObject<MatDoub_O> tempRefObject8 = new RefObject<MatDoub_O>(temp);
				RefObject<MatDoub_I> tempRefObject9 = new RefObject<MatDoub_I>(uj);
				lop(tempRefObject8, tempRefObject9);
				temp = tempRefObject8.argvalue;
				uj = tempRefObject9.argvalue;
				RefObject<MatDoub_I> tempRefObject10 = new RefObject<MatDoub_I>(temp);
				RefObject<MatDoub_O> tempRefObject11 = new RefObject<MatDoub_O>(temp);
				matsub(tempRefObject10, *rho[j], tempRefObject11);
				temp = tempRefObject10.argvalue;
				temp = tempRefObject11.argvalue;
				RefObject<MatDoub_I> tempRefObject12 = new RefObject<MatDoub_I>(temp);
				Doub res = anorm2(tempRefObject12);
				temp = tempRefObject12.argvalue;
				if (res < trerr)
					break;
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

	public final void matadd(RefObject<MatDoub_I> a, RefObject<MatDoub_I> b, RefObject<MatDoub_O> c)
	{
		Int n = a.argvalue.nrows();
		for (Int j = 0;j<n;j++)
			for (Int i = 0;i<n;i++)
				c.argvalue[i][j]=a.argvalue[i][j]+b.argvalue[i][j];
	}

	public final void matsub(RefObject<MatDoub_I> a, RefObject<MatDoub_I> b, RefObject<MatDoub_O> c)
	{
		Int n = a.argvalue.nrows();
		for (Int j = 0;j<n;j++)
			for (Int i = 0;i<n;i++)
				c.argvalue[i][j]=a.argvalue[i][j]-b.argvalue[i][j];
	}

	public final void slvsm2(RefObject<MatDoub_O> u, RefObject<MatDoub_I> rhs)
	{
		Doub h = 0.5;
		for (Int i = 0;i<3;i++)
			for (Int j = 0;j<3;j++)
				u.argvalue[i][j]=0.0;
		Doub fact = 2.0/(h *h);
		Doub disc = Math.sqrt(fact *fact+rhs.argvalue[1][1]);
		u.argvalue[1][1]= -rhs.argvalue[1][1]/(fact+disc);
	}

	public final void relax2(RefObject<MatDoub_IO> u, RefObject<MatDoub_I> rhs)
	{
		Int n = u.argvalue.nrows();
		Int jsw = 1;
		Doub h = 1.0/(n-1);
		Doub h2i = 1.0/(h *h);
		Doub foh2 = -4.0 *h2i;
		for (Int ipass = 0;ipass<2;ipass++,jsw = 3-jsw)
		{
			Int isw = jsw;
			for (Int j = 1;j<n-1;j++,isw = 3-isw)
			{
				for (Int i = isw;i<n-1;i+=2)
				{
					Doub res = h2i*(u.argvalue[i+1][j]+u.argvalue[i-1][j]+u.argvalue[i][j+1]+u.argvalue[i][j-1]- 4.0 *u.argvalue[i][j])+u.argvalue[i][j]*u.argvalue[i][j]-rhs.argvalue[i][j];
					u.argvalue[i][j] -= res/(foh2+2.0 *u.argvalue[i][j]);
				}
			}
		}
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

	public final void lop(RefObject<MatDoub_O> out, RefObject<MatDoub_I> u)
	{
		Int n = u.argvalue.nrows();
		Doub h = 1.0/(n-1);
		Doub h2i = 1.0/(h *h);
		for (Int j = 1;j<n-1;j++)
			for (Int i = 1;i<n-1;i++)
				out.argvalue[i][j]=h2i*(u.argvalue[i+1][j]+u.argvalue[i-1][j]+u.argvalue[i][j+1]+u.argvalue[i][j-1]- 4.0 *u.argvalue[i][j])+u.argvalue[i][j]*u.argvalue[i][j];
		for (Int i = 0;i<n;i++)
			out.argvalue[i][0]=out.argvalue[i][n-1]=out.argvalue[0][i]=out.argvalue[n-1][i]=0.0;
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

	public final Doub anorm2(RefObject<MatDoub_I> a)
	{
		Doub sum = 0.0;
		Int n = a.argvalue.nrows();
		for (Int j = 0;j<n;j++)
			for (Int i = 0;i<n;i++)
				sum += a.argvalue[i][j]*a.argvalue[i][j];
		return Math.sqrt(sum)/n;
	}

	public final void mg(Int j, RefObject<MatDoub_IO> u, RefObject<MatDoub_I> rhs, RefObject<NRvector<NRmatrix<Doub> >> rho, RefObject<Doub> trerr)
	{
		final Int NPRE = 1;
		final Int NPOST = 1;
		final Doub ALPHA = 0.33;
		Doub dum = -1.0;
		Int nf = u.argvalue.nrows();
		Int nc = (nf+1)/2;
		MatDoub temp = new MatDoub(nf,nf);
		if (j == 0)
		{
			RefObject<MatDoub_O> tempRefObject = new RefObject<MatDoub_O>(temp);
			matadd(rhs, *rho.argvalue[j], tempRefObject);
			temp = tempRefObject.argvalue;
			RefObject<MatDoub_I> tempRefObject2 = new RefObject<MatDoub_I>(temp);
			slvsm2(u, tempRefObject2);
			temp = tempRefObject2.argvalue;
		}
		else
		{
			MatDoub v = new MatDoub(nc,nc);
			MatDoub ut = new MatDoub(nc,nc);
			MatDoub tau = new MatDoub(nc,nc);
			MatDoub tempc = new MatDoub(nc,nc);
			for (Int jpre = 0;jpre<NPRE;jpre++)
			{
				if (trerr.argvalue < 0.0)
				{
					RefObject<MatDoub_O> tempRefObject3 = new RefObject<MatDoub_O>(temp);
					matadd(rhs, *rho.argvalue[j], tempRefObject3);
					temp = tempRefObject3.argvalue;
					RefObject<MatDoub_I> tempRefObject4 = new RefObject<MatDoub_I>(temp);
					relax2(u, tempRefObject4);
					temp = tempRefObject4.argvalue;
				}
				else
					relax2(u, *rho.argvalue[j]);
			}
			RefObject<MatDoub_O> tempRefObject5 = new RefObject<MatDoub_O>(ut);
			rstrct(tempRefObject5, u);
			ut = tempRefObject5.argvalue;
			v = ut;
			RefObject<MatDoub_O> tempRefObject6 = new RefObject<MatDoub_O>(tau);
			RefObject<MatDoub_I> tempRefObject7 = new RefObject<MatDoub_I>(ut);
			lop(tempRefObject6, tempRefObject7);
			tau = tempRefObject6.argvalue;
			ut = tempRefObject7.argvalue;
			RefObject<MatDoub_O> tempRefObject8 = new RefObject<MatDoub_O>(temp);
			lop(tempRefObject8, u);
			temp = tempRefObject8.argvalue;
			if (trerr.argvalue < 0.0)
			{
				RefObject<MatDoub_I> tempRefObject9 = new RefObject<MatDoub_I>(temp);
				RefObject<MatDoub_O> tempRefObject10 = new RefObject<MatDoub_O>(temp);
				matsub(tempRefObject9, rhs, tempRefObject10);
				temp = tempRefObject9.argvalue;
				temp = tempRefObject10.argvalue;
			}
			RefObject<MatDoub_O> tempRefObject11 = new RefObject<MatDoub_O>(tempc);
			RefObject<MatDoub_I> tempRefObject12 = new RefObject<MatDoub_I>(temp);
			rstrct(tempRefObject11, tempRefObject12);
			tempc = tempRefObject11.argvalue;
			temp = tempRefObject12.argvalue;
			RefObject<MatDoub_I> tempRefObject13 = new RefObject<MatDoub_I>(tau);
			RefObject<MatDoub_I> tempRefObject14 = new RefObject<MatDoub_I>(tempc);
			RefObject<MatDoub_O> tempRefObject15 = new RefObject<MatDoub_O>(tau);
			matsub(tempRefObject13, tempRefObject14, tempRefObject15);
			tau = tempRefObject13.argvalue;
			tempc = tempRefObject14.argvalue;
			tau = tempRefObject15.argvalue;
			if (trerr.argvalue > 0.0)
			{
				RefObject<MatDoub_I> tempRefObject16 = new RefObject<MatDoub_I>(tau);
				trerr.argvalue = ALPHA *anorm2(tempRefObject16);
				tau = tempRefObject16.argvalue;
			}
			RefObject<MatDoub_IO> tempRefObject17 = new RefObject<MatDoub_IO>(v);
			RefObject<MatDoub_I> tempRefObject18 = new RefObject<MatDoub_I>(tau);
			RefObject<Doub> tempRefObject19 = new RefObject<Doub>(dum);
			mg(j-1, tempRefObject17, tempRefObject18, rho, tempRefObject19);
			v = tempRefObject17.argvalue;
			tau = tempRefObject18.argvalue;
			dum = tempRefObject19.argvalue;
			RefObject<MatDoub_I> tempRefObject20 = new RefObject<MatDoub_I>(v);
			RefObject<MatDoub_I> tempRefObject21 = new RefObject<MatDoub_I>(ut);
			RefObject<MatDoub_O> tempRefObject22 = new RefObject<MatDoub_O>(tempc);
			matsub(tempRefObject20, tempRefObject21, tempRefObject22);
			v = tempRefObject20.argvalue;
			ut = tempRefObject21.argvalue;
			tempc = tempRefObject22.argvalue;
			RefObject<MatDoub_O> tempRefObject23 = new RefObject<MatDoub_O>(temp);
			RefObject<MatDoub_I> tempRefObject24 = new RefObject<MatDoub_I>(tempc);
			interp(tempRefObject23, tempRefObject24);
			temp = tempRefObject23.argvalue;
			tempc = tempRefObject24.argvalue;
			RefObject<MatDoub_I> tempRefObject25 = new RefObject<MatDoub_I>(temp);
			matadd(u, tempRefObject25, u);
			temp = tempRefObject25.argvalue;
			for (Int jpost = 0;jpost<NPOST;jpost++)
			{
				if (trerr.argvalue < 0.0)
				{
					RefObject<MatDoub_O> tempRefObject26 = new RefObject<MatDoub_O>(temp);
					matadd(rhs, *rho.argvalue[j], tempRefObject26);
					temp = tempRefObject26.argvalue;
					RefObject<MatDoub_I> tempRefObject27 = new RefObject<MatDoub_I>(temp);
					relax2(u, tempRefObject27);
					temp = tempRefObject27.argvalue;
				}
				else
					relax2(u, *rho.argvalue[j]);
			}
		}
	}
}