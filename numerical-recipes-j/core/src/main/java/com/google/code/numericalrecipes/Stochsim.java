package com.google.code.numericalrecipes;
public class Stochsim
{
	public VecDoub s = new VecDoub();
	public VecDoub a = new VecDoub();
	public MatDoub instate = new MatDoub();
	public MatDoub outstate = new MatDoub();
	public NRvector<NRsparseCol> outchg = new NRvector<NRsparseCol>();
	public NRvector<NRsparseCol> depend = new NRvector<NRsparseCol>();
	public VecInt pr = new VecInt();
	public Doub t = new Doub();
	public Doub asum = new Doub();
	public Ran ran = new Ran();
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//	typedef Doub(Stochsim::*rateptr)();
	public rateptr dispatch;

	// begin user section
	public static final Int mm = 3;
	public static final Int nn = 4;
	public Doub k0 = new Doub();
	public Doub k1 = new Doub();
	public Doub k2 = new Doub();
	public final Doub rate0()
	{
		return k0 *s[0]*s[1];
	}
	public final Doub rate1()
	{
		return k1 *s[1]*s[2];
	}
	public final Doub rate2()
	{
		return k2 *s[2];
	}
	public final void describereactions ()
	{
		k0 = 0.01;
		k1 = .1;
		k2 = 1.;
		Doub[] indat = { 1.,0.,0., 1.,1.,0., 0.,1.,1., 0.,0.,0.};
		instate = MatDoub(nn,mm,indat);
		Doub[] outdat = { -1.,0.,0., 1.,-1.,0., 0.,1.,-1., 0.,0.,1.};
		outstate = MatDoub(nn,mm,outdat);
		dispatch[0] = this.rate0;
		dispatch[1] = this.rate1;
		dispatch[2] = this.rate2;
	}
	// end user section

	public Stochsim(RefObject<VecDoub> sinit)
	{
		this(sinit, 1);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Stochsim(VecDoub &sinit, Int seed=1) : s(sinit), a(mm,0.), outchg(mm), depend(mm), pr(mm), t(0.), asum(0.), ran(seed), dispatch(new rateptr[mm])
	public Stochsim(RefObject<VecDoub> sinit, Int seed)
	{
		s = sinit.argvalue;
		a = new VecDoub(mm,0.);
		outchg = mm;
		depend = mm;
		pr = mm;
		t = 0.;
		asum = 0.;
		ran = seed;
		dispatch = new rateptr[mm];
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int d = new Int();
		describereactions();
		RefObject<NRvector<NRsparseCol>> tempRefObject = new RefObject<NRvector<NRsparseCol>>(outchg);
		RefObject<MatDoub> tempRefObject2 = new RefObject<MatDoub>(outstate);
		GlobalMembersStochsim.sparmatfill(tempRefObject, tempRefObject2);
		outchg = tempRefObject.argvalue;
		outstate = tempRefObject2.argvalue;
		MatDoub dep = new MatDoub(mm,mm);
		for (i = 0;i<mm;i++)
			for (j = 0;j<mm;j++)
			{
			d = 0;
			for (k = 0;k<nn;k++)
				d = d || (instate[k][i] && outstate[k][j]);
			dep[i][j] = d;
		}
		RefObject<NRvector<NRsparseCol>> tempRefObject3 = new RefObject<NRvector<NRsparseCol>>(depend);
		RefObject<MatDoub> tempRefObject4 = new RefObject<MatDoub>(dep);
		GlobalMembersStochsim.sparmatfill(tempRefObject3, tempRefObject4);
		depend = tempRefObject3.argvalue;
		dep = tempRefObject4.argvalue;
		for (i = 0;i<mm;i++)
		{
			pr[i] = i;
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//			a[i] = (this.*dispatch[i])();
			asum += a[i];
		}
	}
	public void Dispose()
	{
		dispatch = null;
	}

	public final Doub step()
	{
		Int i = new Int();
		Int n = new Int();
		Int m = new Int();
		Int k = 0;
		Doub tau = new Doub();
		Doub atarg = new Doub();
		Doub sum = new Doub();
		Doub anew = new Doub();
		if (asum == 0.)
		{
			t *= 2.;
			return t;
		}
		tau = -Math.log(ran.doub())/asum;
		atarg = ran.doub()*asum;
		sum = a[pr[0]];
		while (sum < atarg)
			sum += a[pr[++k]];
		m = pr[k];
		if (k > 0)
			SWAP(pr[k],pr[k-1]);
		if (k == mm-1)
			asum = sum;
		n = outchg[m].nvals;
		for (i = 0;i<n;i++)
		{
			k = outchg[m].row_ind[i];
			s[k] += outchg[m].val[i];
		}
		n = depend[m].nvals;
		for (i = 0;i<n;i++)
		{
			k = depend[m].row_ind[i];
//C++ TO JAVA CONVERTER TODO TASK: There are no simple equivalents to function pointers in Java:
//			anew = (this.*dispatch[k])();
			asum += (anew - a[k]);
			a[k] = anew;
		}
		if (t *asum < 0.1)
			for (asum = 0.,i = 0;i<mm;i++)
				asum += a[i];
		return (t += tau);
	}
}