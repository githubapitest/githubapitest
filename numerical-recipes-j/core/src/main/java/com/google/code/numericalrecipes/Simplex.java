package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER WARNING: The declaration of the following method implementation was not found:
//ORIGINAL LINE: void NRlusol::load_col(Int col, VecInt &row_ind, VecDoub &val)
public class Simplex
{
	public Int m = new Int();
	public Int n = new Int();
	public Int ierr = new Int();
	public VecInt initvars = new VecInt();
	public VecInt ord = new VecInt();
	public VecInt ad = new VecInt();
	public VecDoub b = new VecDoub();
	public VecDoub obj = new VecDoub();
	public VecDoub u = new VecDoub();
	public VecDoub x = new VecDoub();
	public VecDoub xb = new VecDoub();
	public VecDoub v = new VecDoub();
	public VecDoub w = new VecDoub();
	public VecDoub scale = new VecDoub();
	public NRsparseMat sa;
	public Int NMAXFAC = new Int();
	public Int NREFAC = new Int();
	public Doub EPS = new Doub();
	public Doub EPSSMALL = new Doub();
	public Doub EPSARTIF = new Doub();
	public Doub EPSFEAS = new Doub();
	public Doub EPSOPT = new Doub();
	public Doub EPSINFEAS = new Doub();
	public Doub EPSROW1 = new Doub();
	public Doub EPSROW2 = new Doub();
	public Doub EPSROW3 = new Doub();
	public Int nm1 = new Int();
	public Int nmax = new Int();
	public Int nsteps = new Int();
	public Bool verbose = new Bool();
	public NRvector<NRsparseCol > a = new NRvector<NRsparseCol >();
	public NRlusol lu;

	public Simplex(Int mm, Int nn, RefObject<VecInt_I> initv, RefObject<VecDoub_I> bb, RefObject<VecDoub_I> objj, RefObject<NRsparseMat> ssa, Bool verb)
	{
		m = mm;
		n = nn;
		initvars = initv.argvalue;
		b = bb.argvalue;
		obj = objj.argvalue;
		sa = ssa.argvalue;
		verbose = verb;
		ord = m+1;
		ad = m+n+1;
		u = m+n+1;
		x = m+1;
		xb = m+1;
		v = m+1;
		w = m+1;
		scale = n+m+1;
		a = n+1;
		NMAXFAC = 40;
		NREFAC = 50;
		EPS = numeric_limits<Doub>.epsilon();
		EPSSMALL = 1.0e5 *EPS;
		EPSARTIF = 1.0e5 *EPS;
		EPSFEAS = 1.0e8 *EPS;
		EPSOPT = 1.0e8 *EPS;
		EPSINFEAS = 1.0e4 *EPS;
		EPSROW1 = 1.0e-5;
		EPSROW2 = EPS;
		EPSROW3 = EPS;
	}
	public final void solve()
	{
		initialize();
		scaleit();
		phase0();
		if (verbose != null)
		{
			System.out.print("    at end of phase0,iter= ");
			System.out.print(nsteps);
			System.out.print("\n");
		}
		if (ierr != 0)
		{
			return;
		}
		phase1();
		if (verbose != null)
		{
			System.out.print("    at end of phase1,iter= ");
			System.out.print(nsteps);
			System.out.print("\n");
		}
		if (ierr != 0)
		{
			return;
		}
		phase2();
		prepare_output();
	}
	public final void initialize()
	{
		VecInt irow = new VecInt(2);
		VecDoub value = new VecDoub(2);
		nsteps = 0;
		ierr = 0;
		nm1 = n+m+1;
		nmax = NMAXFAC *MAX(m,n);
		for (Int i = 1;i<=n;i++)
			if (initvars[i] == 0)
				ad[i]=0;
			else
				ad[i]=1;
		for (Int i = n+1;i<=n+m;i++)
			ad[i]=-1;
		for (Int i = 1;i<=m;i++)
			if (initvars[n+i] >= 0)
				ord[i]=n+i;
			else
				ord[i]=-m+i-1;
		for (Int i = 0;i<n;i++)
		{
			Int nvals = sa.col_ptr[i+1]-sa.col_ptr[i];
			a[i+1]=new NRsparseCol(m+1,nvals+1);
			Int count = 1;
			for (Int j = sa.col_ptr[i]; j<sa.col_ptr[i+1]; j++)
			{
				Int k = sa.row_ind[j];
				a[i+1].row_ind[count]=k+1;
				a[i+1].val[count]=sa.val[j];
				count++;
			}
		}
		lu = new NRlusol(m,sa.col_ptr[n]);
		value[0]=0.0;
		value[1]=1.0;
		irow[0]=0;
		for (Int i = 1;i<=m;i++)
		{
			irow[1]=i;
			RefObject<VecInt> tempRefObject = new RefObject<VecInt>(irow);
			RefObject<VecDoub> tempRefObject2 = new RefObject<VecDoub>(value);
			lu.load_col(i, tempRefObject, tempRefObject2);
			irow = tempRefObject.argvalue;
			value = tempRefObject2.argvalue;
		}
		lu.factorize();
	}
	public final void scaleit()
	{
		for (Int i = 1;i<=m;i++)
			scale[n+i]=0.0;
		for (Int k = 1;k<=n;k++)
		{
			x = getcol(k);
			Doub h = 0.0;
			for (Int i = 1;i<=m;i++)
				if (Math.abs(x[i]) > h)
					h = Math.abs(x[i]);
			if (h == 0.0)
				scale[k]=0.0;
			else
				scale[k]=1.0/h;
			for (Int i = 1;i<=m;i++)
				scale[n+i]=MAX(scale[n+i],Math.abs(x[i])*scale[k]);
		}
		for (Int i = 1;i<=m;i++)
			if (scale[n+i] == 0.0)
				scale[n+i]=1.0;
	}
	public final void phase0()
	{
		Int ind = new Int();
		Int ip = new Int();
		Int kp = new Int();
		Doub piv = new Doub();
		for (kp = 1;kp<=n;kp++)
		{
			if (initvars[kp] < 0)
			{
				x = lx(kp);
				RefObject<VecDoub> tempRefObject = new RefObject<VecDoub>(x);
				w = lu.uinv(tempRefObject);
				x = tempRefObject.argvalue;
				RefObject<VecDoub_I> tempRefObject2 = new RefObject<VecDoub_I>(w);
				ip = rowpiv(tempRefObject2, 0, kp, 0.0);
				w = tempRefObject2.argvalue;
				ind = ord[ip];
				RefObject<VecDoub> tempRefObject3 = new RefObject<VecDoub>(x);
				transform(tempRefObject3, ip, kp);
				x = tempRefObject3.argvalue;
				ord[ip] -= nm1;
				if (initvars[ind] == 0)
					ad[ind]=0;
			}
		}
		for (ip = 1;ip<=m;ip++)
		{
			ind = ord[ip];
			if (ind < 0 || initvars[ind] != 0)
				continue;
			for (Int i = 1;i<=m;i++)
				v[i]=0.0;
			v[ip]=1.0;
			RefObject<VecDoub> tempRefObject4 = new RefObject<VecDoub>(v);
			RefObject<Doub> tempRefObject5 = new RefObject<Doub>(piv);
			kp = colpiv(tempRefObject4, 0, tempRefObject5);
			v = tempRefObject4.argvalue;
			piv = tempRefObject5.argvalue;
			if (Math.abs(piv) < EPSSMALL)
			{
				RefObject<VecDoub> tempRefObject6 = new RefObject<VecDoub>(b);
				xb = lu.solve(tempRefObject6);
				b = tempRefObject6.argvalue;
				if (Math.abs(xb[ip]) > EPSARTIF *maxnorm(xb))
				{
					ierr = 1;
					return;
				}
				else
				{
					if (verbose != null)
					{
						System.out.print("    artificial variable remains: ip ");
						System.out.print(ip);
						System.out.print("\n");
					}
					continue;
				}
			}
			x = lx(kp);
			RefObject<VecDoub> tempRefObject7 = new RefObject<VecDoub>(x);
			transform(tempRefObject7, ip, kp);
			x = tempRefObject7.argvalue;
			if (ad[ind] == 1)
				ad[ind]=0;
		}
	}
	public final void phase1()
	{
		Int ip = new Int();
		Int kp = new Int();
		Doub piv = new Doub();
		for (;;)
		{
			RefObject<VecDoub> tempRefObject = new RefObject<VecDoub>(b);
			xb = lu.solve(tempRefObject);
			b = tempRefObject.argvalue;
			RefObject<VecDoub_I> tempRefObject2 = new RefObject<VecDoub_I>(xb);
			Doub xbmax = maxnorm(tempRefObject2);
			xb = tempRefObject2.argvalue;
			Bool done = true;
			for (Int i = 1;i<=m;i++)
			{
				if (ord[i] > 0 && xb[i] < -EPSFEAS *xbmax)
				{
					v[i]=1.0/scale[ord[i]];
					done = false;
				}
				  else
					v[i]=0.0;
			}
			if (done != null)
				break;
			RefObject<VecDoub> tempRefObject3 = new RefObject<VecDoub>(v);
			RefObject<Doub> tempRefObject4 = new RefObject<Doub>(piv);
			kp = colpiv(tempRefObject3, 1, tempRefObject4);
			v = tempRefObject3.argvalue;
			piv = tempRefObject4.argvalue;
			if (ierr != 0)
				return;
			Bool first = true;
			for (;;)
			{
				x = lx(kp);
				RefObject<VecDoub> tempRefObject5 = new RefObject<VecDoub>(x);
				w = lu.uinv(tempRefObject5);
				x = tempRefObject5.argvalue;
				RefObject<VecDoub_I> tempRefObject6 = new RefObject<VecDoub_I>(w);
				ip = rowpiv(tempRefObject6, 1, kp, xbmax);
				w = tempRefObject6.argvalue;
				if (ierr == 0)
					break;
				if (first == null)
				{
					ierr = 6;
					return;
				}
				if (verbose != null)
				{
					System.out.print("    attempt to recover");
					System.out.print("\n");
				}
				ierr = 0;
				first = false;
				refactorize();
			}
			RefObject<VecDoub> tempRefObject7 = new RefObject<VecDoub>(x);
			transform(tempRefObject7, ip, kp);
			x = tempRefObject7.argvalue;
			if (nsteps >= nmax)
			{
				ierr = 3;
				return;
			}
		}
	}
	public final void phase2()
	{
		Int ip = new Int();
		Int kp = new Int();
		Doub piv = new Doub();
		for (;;)
		{
			for (Int i = 1;i<=m;i++)
			{
				if (ord[i] > 0)
					v[i]=-obj[ord[i]];
				  else
					v[i]=-obj[ord[i]+nm1];
			}
			RefObject<VecDoub> tempRefObject = new RefObject<VecDoub>(v);
			RefObject<Doub> tempRefObject2 = new RefObject<Doub>(piv);
			kp = colpiv(tempRefObject, 2, tempRefObject2);
			v = tempRefObject.argvalue;
			piv = tempRefObject2.argvalue;
			if (piv > -EPSOPT)
				break;
			Bool first = true;
			for (;;)
			{
				x = lx(kp);
				RefObject<VecDoub> tempRefObject3 = new RefObject<VecDoub>(x);
				w = lu.uinv(tempRefObject3);
				x = tempRefObject3.argvalue;
				RefObject<VecDoub> tempRefObject4 = new RefObject<VecDoub>(b);
				xb = lu.solve(tempRefObject4);
				b = tempRefObject4.argvalue;
				RefObject<VecDoub_I> tempRefObject5 = new RefObject<VecDoub_I>(xb);
				Doub xbmax = maxnorm(tempRefObject5);
				xb = tempRefObject5.argvalue;
				RefObject<VecDoub_I> tempRefObject6 = new RefObject<VecDoub_I>(w);
				ip = rowpiv(tempRefObject6, 2, kp, xbmax);
				w = tempRefObject6.argvalue;
				if (ierr == 0)
					break;
				if (first == null)
					return;
				if (verbose != null)
				{
					System.out.print("    attempt to recover");
					System.out.print("\n");
				}
				ierr = 0;
				first = false;
				refactorize();
			}
			RefObject<VecDoub> tempRefObject7 = new RefObject<VecDoub>(x);
			transform(tempRefObject7, ip, kp);
			x = tempRefObject7.argvalue;
			if (verbose != null)
			{
				prepare_output();
				System.out.print("    in phase2,iter,obj. fn. ");
				System.out.print(nsteps);
				System.out.print(" ");
				System.out.print(u[0]);
				System.out.print("\n");
			}
			if (nsteps >= nmax)
			{
				prepare_output();
				System.out.print("    in phase2,iter,obj. fn. ");
				System.out.print(nsteps);
				System.out.print(" ");
				System.out.print(u[0]);
				System.out.print("\n");
				ierr = 4;
				return;
			}
		}
	}
	public final Int colpiv(RefObject<VecDoub> v, Int phase, RefObject<Doub> piv)
	{
		Int kp = new Int();
		Doub h1 = new Doub();
		x = lu.solvet(v);
		piv.argvalue = 0.0;
		for (Int k = 1;k<=n+m;k++)
		{
			if (ad[k] > 0)
			{
				if (k > n)
					h1 = x[k-n];
				else
				{
					RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(x);
					h1 = xdotcol(tempRefObject, k);
					x = tempRefObject.argvalue;
				}
				if (phase == 2)
					h1 = h1+obj[k];
				h1 = h1 *scale[k];
				if ((phase == 0 && Math.abs(h1) > Math.abs(piv.argvalue)) || (phase > 0 && h1 < piv.argvalue))
				{
					piv.argvalue = h1;
					kp = k;
				}
			}
		}
		if (phase == 1)
		{
			h1 = 0.0;
			for (Int k = 1;k<=m;k++)
				h1 += Math.abs(x[k])*scale[n+k];
			if (piv.argvalue > -EPSINFEAS *h1)
				ierr = 2;
		}
		return kp;
	}
	public final Int rowpiv(RefObject<VecDoub_I> w, Int phase, Int kp, Doub xbmax)
	{
		Int j = 0;
		Int ip = 0;
		Doub h1 = new Doub();
		Doub h2 = new Doub();
		Doub min = 0.0;
		Doub piv = 0.0;
		for (Int i = 1;i<=m;i++)
		{
			Int ind = ord[i];
			if (ind > 0)
			{
				if (Math.abs(w.argvalue[i])*scale[kp] <= EPSROW1 *scale[ind])
					continue;
				if (phase == 0)
				{
					h1 = Math.abs(w.argvalue[i]);
					h2 = h1;
				}
				else
				{
					Doub hmin = EPSROW2 *xbmax *scale[ind]*m;
					if (Math.abs(xb[i]) < hmin)
						h2 = hmin;
					else
						h2 = xb[i];
					h1 = w.argvalue[i]/h2;
				}
				if (h1 > 0.0)
				{
					if (h2 > 0.0 && h1 > piv)
					{
						piv = h1;
						ip = i;
					}
					else if ((h2 *scale[kp] < -EPSROW3 *scale[ind]) && (j == 0 || h1 < min))
					{
							min = h1;
							j = i;
					}
				}
			}
		}
		if (min > piv)
		{
			piv = min;
			ip = j;
		}
		if (ip == 0)
			ierr = 5;
		return ip;
	}
	public final void transform(RefObject<VecDoub> x, Int ip, Int kp)
	{
		ad[ord[ip]]=1;
		ad[kp]=-1;
		Int oldord = ord[ip];
		ord[ip]=kp;
		nsteps++;
		if ((nsteps % NREFAC) != 0)
		{
			Int ok = new Int();
			RefObject<Int> tempRefObject = new RefObject<Int>(ok);
			lu.update(x, ip, tempRefObject);
			ok = tempRefObject.argvalue;
			if (ok != 0)
			{
				if (verbose != null)
				{
					System.out.print("    singular update, refactorize");
					System.out.print("\n");
				}
				ad[oldord]=-1;
				ad[kp]=1;
				ord[ip]=oldord;
				refactorize();
			}
		}
		else
			refactorize();
	}
	public final Doub maxnorm(RefObject<VecDoub_I> xb)
	{
		Int indv = new Int();
		Doub maxn = 0.0;
		for (Int i = 1;i<=m;i++)
		{
			if (ord[i] > 0)
				indv = ord[i];
			else
				indv = ord[i]+nm1;
			Doub test = Math.abs(xb.argvalue[i])/scale[indv];
			if (test > maxn)
				maxn = test;
		}
		if (maxn != 0.0)
			return maxn;
		else
			return 1.0;
	}
	public final VecDoub getcol(Int k)
	{
		VecDoub temp = new VecDoub(m+1,0.0);
		for (Int i = 1;i<a[k].nvals;i++)
		{
			temp[a[k].row_ind[i]]=a[k].val[i];
		}
		return temp;
	}
	public final VecDoub lx(Int kp)
	{
		if (kp <= n)
			x = getcol(kp);
		else
		{
			for (Int i = 1;i<=m;i++)
				x[i]=0.0;
			x[kp-n]=1.0;
		}
		RefObject<VecDoub> tempRefObject = new RefObject<VecDoub>(x);
		return lu.linv(tempRefObject);
		x = tempRefObject.argvalue;
	}
	public final Doub xdotcol(RefObject<VecDoub_I> x, Int k)
	{
		Doub sum = 0.0;
		for (Int i = 1;i<a[k].nvals;i++)
			sum += x.argvalue[a[k].row_ind[i]]*a[k].val[i];
		return sum;
	}
	public final void refactorize()
	{
		Int count = 0;
		Doub sum = 0.0;
		VecInt irow = new VecInt(2);
		VecDoub value = new VecDoub(2);
		value[0]=0.0;
		value[1]=1.0;
		irow[0]=0;
		lu.clear();
		for (Int i = 1;i<=m;i++)
		{
			Int ind = ord[i];
			if (ind < 0)
				ind += nm1;
			if (ind > n)
			{
				irow[1]=ind-n;
				RefObject<VecInt> tempRefObject = new RefObject<VecInt>(irow);
				RefObject<VecDoub> tempRefObject2 = new RefObject<VecDoub>(value);
				lu.load_col(i, tempRefObject, tempRefObject2);
				irow = tempRefObject.argvalue;
				value = tempRefObject2.argvalue;
				count++;
				sum += 1.0;
			}
			else
			{
				lu.load_col(i, a[ind].row_ind, a[ind].val);
				for (Int j = 1;j<a[ind].nvals;j++)
				{
					count++;
					sum += Math.abs(a[ind].val[j]);
				}
			}
		}
		Doub small = EPSSMALL *sum/count;
		lu.LUSOL.parmlu[LUSOL_RP_SMALLDIAG_U] = lu.LUSOL.parmlu[LUSOL_RP_EPSDIAG_U] = small;
		lu.factorize();
	}
	public final void prepare_output()
	{
		Int indv = new Int();
		Doub sum = obj[0];
		RefObject<VecDoub> tempRefObject = new RefObject<VecDoub>(b);
		xb = lu.solve(tempRefObject);
		b = tempRefObject.argvalue;
		for (Int i = 0;i<=m+n;i++)
			u[i]=0.0;
		for (Int i = 1;i<=m;i++)
		{
			if (ord[i] > 0)
				indv = ord[i];
			else
				indv = ord[i]+nm1;
			u[indv]=xb[i];
			sum += xb[i]*obj[indv];
		}
		u[0]=sum;
	}
}