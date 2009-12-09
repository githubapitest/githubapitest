package com.google.code.numericalrecipes;
public class Bessel
{
	public static final Int NUSE1 = 7;
	public static final Int NUSE2 = 8;
	public static final Doub[] c1 = new Doub[NUSE1];
	public static final Doub[] c2 = new Doub[NUSE2];
	public Doub xo = new Doub();
	public Doub nuo = new Doub();
	public Doub jo = new Doub();
	public Doub yo = new Doub();
	public Doub jpo = new Doub();
	public Doub ypo = new Doub();
	public Doub io = new Doub();
	public Doub ko = new Doub();
	public Doub ipo = new Doub();
	public Doub kpo = new Doub();
	public Doub aio = new Doub();
	public Doub bio = new Doub();
	public Doub aipo = new Doub();
	public Doub bipo = new Doub();
	public Doub sphjo = new Doub();
	public Doub sphyo = new Doub();
	public Doub sphjpo = new Doub();
	public Doub sphypo = new Doub();
	public Int sphno = new Int();

	public Bessel()
	{
		xo = 9.99e99;
		nuo = 9.99e99;
		sphno = -9999;
	}

	public final void besseljy(Doub nu, Doub x)
	{
		final Int MAXIT = 10000;
		final Doub EPS = numeric_limits<Doub>.epsilon();
		final Doub FPMIN = numeric_limits<Doub>.min()/EPS;
		final Doub XMIN = 2.0;
		final Doub PI = 3.141592653589793;
		Doub a = new Doub();
		Doub b = new Doub();
		Doub br = new Doub();
		Doub bi = new Doub();
		Doub c = new Doub();
		Doub cr = new Doub();
		Doub ci = new Doub();
		Doub d = new Doub();
		Doub del = new Doub();
		Doub del1 = new Doub();
		Doub den = new Doub();
		Doub di = new Doub();
		Doub dlr = new Doub();
		Doub dli = new Doub();
		Doub dr = new Doub();
		Doub e = new Doub();
		Doub f = new Doub();
		Doub fact = new Doub();
		Doub fact2 = new Doub();
		Doub fact3 = new Doub();
		Doub ff = new Doub();
		Doub gam = new Doub();
		Doub gam1 = new Doub();
		Doub gam2 = new Doub();
		Doub gammi = new Doub();
		Doub gampl = new Doub();
		Doub h = new Doub();
		Doub p = new Doub();
		Doub pimu = new Doub();
		Doub pimu2 = new Doub();
		Doub q = new Doub();
		Doub r = new Doub();
		Doub rjl = new Doub();
		Doub rjl1 = new Doub();
		Doub rjmu = new Doub();
		Doub rjp1 = new Doub();
		Doub rjpl = new Doub();
		Doub rjtemp = new Doub();
		Doub ry1 = new Doub();
		Doub rymu = new Doub();
		Doub rymup = new Doub();
		Doub rytemp = new Doub();
		Doub sum = new Doub();
		Doub sum1 = new Doub();
		Doub temp = new Doub();
		Doub w = new Doub();
		Doub x2 = new Doub();
		Doub xi = new Doub();
		Doub xi2 = new Doub();
		Doub xmu = new Doub();
		Doub xmu2 = new Doub();
		Doub xx = new Doub();
		Int i = new Int();
		Int isign = new Int();
		Int l = new Int();
		Int nl = new Int();
	
		if (x <= 0.0 || nu < 0.0)
			throw("bad arguments in besseljy");
		nl = (x < XMIN != null ? (Int)(nu+0.5) : MAX(0,(Int)(nu-x+1.5)));
		xmu = nu-nl;
		xmu2 = xmu *xmu;
		xi = 1.0/x;
		xi2 = 2.0 *xi;
		w = xi2/PI;
		isign = 1;
		h = nu *xi;
		if (h < FPMIN)
			h = FPMIN;
		b = xi2 *nu;
		d = 0.0;
		c = h;
		for (i = 0;i<MAXIT;i++)
		{
			b += xi2;
			d = b-d;
			if (Math.abs(d) < FPMIN)
				d = FPMIN;
			c = b-1.0/c;
			if (Math.abs(c) < FPMIN)
				c = FPMIN;
			d = 1.0/d;
			del = c *d;
			h = del *h;
			if (d < 0.0)
				isign = -isign;
			if (Math.abs(del-1.0) <= EPS)
				break;
		}
		if (i >= MAXIT)
			throw("x too large in besseljy; try asymptotic expansion");
		rjl = isign *FPMIN;
		rjpl = h *rjl;
		rjl1 = rjl;
		rjp1 = rjpl;
		fact = nu *xi;
		for (l = nl-1;l>=0;l--)
		{
			rjtemp = fact *rjl+rjpl;
			fact -= xi;
			rjpl = fact *rjtemp-rjl;
			rjl = rjtemp;
		}
		if (rjl == 0.0)
			rjl = EPS;
		f = rjpl/rjl;
		if (x < XMIN)
		{
			x2 = 0.5 *x;
			pimu = PI *xmu;
			fact = (Math.abs(pimu) < EPS != null ? 1.0 : pimu/Math.sin(pimu));
			d = -Math.log(x2);
			e = xmu *d;
			fact2 = (Math.abs(e) < EPS != null ? 1.0 : Math.sinh(e)/e);
			xx = 8.0 *SQR(xmu)-1.0;
			gam1 = chebev(c1, NUSE1, xx);
			gam2 = chebev(c2, NUSE2, xx);
			gampl = gam2-xmu *gam1;
			gammi = gam2+xmu *gam1;
			ff = 2.0/PI *fact*(gam1 *Math.cosh(e)+gam2 *fact2 *d);
			e = Math.exp(e);
			p = e/(gampl *PI);
			q = 1.0/(e *PI *gammi);
			pimu2 = 0.5 *pimu;
			fact3 = (Math.abs(pimu2) < EPS != null ? 1.0 : Math.sin(pimu2)/pimu2);
			r = PI *pimu2 *fact3 *fact3;
			c = 1.0;
			d = -x2 *x2;
			sum = ff+r *q;
			sum1 = p;
			for (i = 1;i<=MAXIT;i++)
			{
				ff = (i *ff+p+q)/(i *i-xmu2);
				c *= (d/i);
				p /= (i-xmu);
				q /= (i+xmu);
				del = c*(ff+r *q);
				sum += del;
				del1 = c *p-i *del;
				sum1 += del1;
				if (Math.abs(del) < (1.0+Math.abs(sum))*EPS)
					break;
			}
			if (i > MAXIT)
				throw("bessy series failed to converge");
			rymu = -sum;
			ry1 = -sum1 *xi2;
			rymup = xmu *xi *rymu-ry1;
			rjmu = w/(rymup-f *rymu);
		}
		else
		{
			a = 0.25-xmu2;
			p = -0.5 *xi;
			q = 1.0;
			br = 2.0 *x;
			bi = 2.0;
			fact = a *xi/(p *p+q *q);
			cr = br+q *fact;
			ci = bi+p *fact;
			den = br *br+bi *bi;
			dr = br/den;
			di = -bi/den;
			dlr = cr *dr-ci *di;
			dli = cr *di+ci *dr;
			temp = p *dlr-q *dli;
			q = p *dli+q *dlr;
			p = temp;
			for (i = 1;i<MAXIT;i++)
			{
				a += 2 *i;
				bi += 2.0;
				dr = a *dr+br;
				di = a *di+bi;
				if (Math.abs(dr)+Math.abs(di) < FPMIN)
					dr = FPMIN;
				fact = a/(cr *cr+ci *ci);
				cr = br+cr *fact;
				ci = bi-ci *fact;
				if (Math.abs(cr)+Math.abs(ci) < FPMIN)
					cr = FPMIN;
				den = dr *dr+di *di;
				dr /= den;
				di /= -den;
				dlr = cr *dr-ci *di;
				dli = cr *di+ci *dr;
				temp = p *dlr-q *dli;
				q = p *dli+q *dlr;
				p = temp;
				if (Math.abs(dlr-1.0)+Math.abs(dli) <= EPS)
					break;
			}
			if (i >= MAXIT)
				throw("cf2 failed in besseljy");
			gam = (p-f)/q;
			rjmu = Math.sqrt(w/((p-f)*gam+q));
			rjmu = SIGN(rjmu,rjl);
			rymu = rjmu *gam;
			rymup = rymu*(p+q/gam);
			ry1 = xmu *xi *rymu-rymup;
		}
		fact = rjmu/rjl;
		jo = rjl1 *fact;
		jpo = rjp1 *fact;
		for (i = 1;i<=nl;i++)
		{
			rytemp = (xmu+i)*xi2 *ry1-rymu;
			rymu = ry1;
			ry1 = rytemp;
		}
		yo = rymu;
		ypo = nu *xi *rymu-ry1;
		xo = x;
		nuo = nu;
	}
	public final void besselik(Doub nu, Doub x)
	{
		final Int MAXIT = 10000;
		final Doub EPS = numeric_limits<Doub>.epsilon();
		final Doub FPMIN = numeric_limits<Doub>.min()/EPS;
		final Doub XMIN = 2.0;
		final Doub PI = 3.141592653589793;
		Doub a = new Doub();
		Doub a1 = new Doub();
		Doub b = new Doub();
		Doub c = new Doub();
		Doub d = new Doub();
		Doub del = new Doub();
		Doub del1 = new Doub();
		Doub delh = new Doub();
		Doub dels = new Doub();
		Doub e = new Doub();
		Doub f = new Doub();
		Doub fact = new Doub();
		Doub fact2 = new Doub();
		Doub ff = new Doub();
		Doub gam1 = new Doub();
		Doub gam2 = new Doub();
		Doub gammi = new Doub();
		Doub gampl = new Doub();
		Doub h = new Doub();
		Doub p = new Doub();
		Doub pimu = new Doub();
		Doub q = new Doub();
		Doub q1 = new Doub();
		Doub q2 = new Doub();
		Doub qnew = new Doub();
		Doub ril = new Doub();
		Doub ril1 = new Doub();
		Doub rimu = new Doub();
		Doub rip1 = new Doub();
		Doub ripl = new Doub();
		Doub ritemp = new Doub();
		Doub rk1 = new Doub();
		Doub rkmu = new Doub();
		Doub rkmup = new Doub();
		Doub rktemp = new Doub();
		Doub s = new Doub();
		Doub sum = new Doub();
		Doub sum1 = new Doub();
		Doub x2 = new Doub();
		Doub xi = new Doub();
		Doub xi2 = new Doub();
		Doub xmu = new Doub();
		Doub xmu2 = new Doub();
		Doub xx = new Doub();
		Int i = new Int();
		Int l = new Int();
		Int nl = new Int();
		if (x <= 0.0 || nu < 0.0)
			throw("bad arguments in besselik");
		nl = (Int)(nu+0.5);
		xmu = nu-nl;
		xmu2 = xmu *xmu;
		xi = 1.0/x;
		xi2 = 2.0 *xi;
		h = nu *xi;
		if (h < FPMIN)
			h = FPMIN;
		b = xi2 *nu;
		d = 0.0;
		c = h;
		for (i = 0;i<MAXIT;i++)
		{
			b += xi2;
			d = 1.0/(b+d);
			c = b+1.0/c;
			del = c *d;
			h = del *h;
			if (Math.abs(del-1.0) <= EPS)
				break;
		}
		if (i >= MAXIT)
			throw("x too large in besselik; try asymptotic expansion");
		ril = FPMIN;
		ripl = h *ril;
		ril1 = ril;
		rip1 = ripl;
		fact = nu *xi;
		for (l = nl-1;l >= 0;l--)
		{
			ritemp = fact *ril+ripl;
			fact -= xi;
			ripl = fact *ritemp+ril;
			ril = ritemp;
		}
		f = ripl/ril;
		if (x < XMIN)
		{
			x2 = 0.5 *x;
			pimu = PI *xmu;
			fact = (Math.abs(pimu) < EPS != null ? 1.0 : pimu/Math.sin(pimu));
			d = -Math.log(x2);
			e = xmu *d;
			fact2 = (Math.abs(e) < EPS != null ? 1.0 : Math.sinh(e)/e);
			xx = 8.0 *SQR(xmu)-1.0;
			gam1 = chebev(c1, NUSE1, xx);
			gam2 = chebev(c2, NUSE2, xx);
			gampl = gam2-xmu *gam1;
			gammi = gam2+xmu *gam1;
			ff = fact*(gam1 *Math.cosh(e)+gam2 *fact2 *d);
			sum = ff;
			e = Math.exp(e);
			p = 0.5 *e/gampl;
			q = 0.5/(e *gammi);
			c = 1.0;
			d = x2 *x2;
			sum1 = p;
			for (i = 1;i<=MAXIT;i++)
			{
				ff = (i *ff+p+q)/(i *i-xmu2);
				c *= (d/i);
				p /= (i-xmu);
				q /= (i+xmu);
				del = c *ff;
				sum += del;
				del1 = c*(p-i *ff);
				sum1 += del1;
				if (Math.abs(del) < Math.abs(sum)*EPS)
					break;
			}
			if (i > MAXIT)
				throw("bessk series failed to converge");
			rkmu = sum;
			rk1 = sum1 *xi2;
		}
		else
		{
			b = 2.0*(1.0+x);
			d = 1.0/b;
			h = delh = d;
			q1 = 0.0;
			q2 = 1.0;
			a1 = 0.25-xmu2;
			q = c = a1;
			a = -a1;
			s = 1.0+q *delh;
			for (i = 1;i<MAXIT;i++)
			{
				a -= 2 *i;
				c = -a *c/(i+1.0);
				qnew = (q1-b *q2)/a;
				q1 = q2;
				q2 = qnew;
				q += c *qnew;
				b += 2.0;
				d = 1.0/(b+a *d);
				delh = (b *d-1.0)*delh;
				h += delh;
				dels = q *delh;
				s += dels;
				if (Math.abs(dels/s) <= EPS)
					break;
			}
			if (i >= MAXIT)
				throw("besselik: failure to converge in cf2");
			h = a1 *h;
			rkmu = Math.sqrt(PI/(2.0 *x))*Math.exp(-x)/s;
			rk1 = rkmu*(xmu+x+0.5-h)*xi;
		}
		rkmup = xmu *xi *rkmu-rk1;
		rimu = xi/(f *rkmu-rkmup);
		io = (rimu *ril1)/ril;
		ipo = (rimu *rip1)/ril;
		for (i = 1;i <= nl;i++)
		{
			rktemp = (xmu+i)*xi2 *rk1+rkmu;
			rkmu = rk1;
			rk1 = rktemp;
		}
		ko = rkmu;
		kpo = nu *xi *rkmu-rk1;
		xo = x;
		nuo = nu;
	}

	public final Doub jnu(Doub nu, Doub x)
	{
		if (nu != nuo || x != xo)
			besseljy(nu, x);
		return jo;
	}
	public final Doub ynu(Doub nu, Doub x)
	{
		if (nu != nuo || x != xo)
			besseljy(nu, x);
		return yo;
	}
	public final Doub inu(Doub nu, Doub x)
	{
		if (nu != nuo || x != xo)
			besselik(nu, x);
		return io;
	}
	public final Doub knu(Doub nu, Doub x)
	{
		if (nu != nuo || x != xo)
			besselik(nu, x);
		return ko;
	}

	public final void airy(Doub x)
	{
		final Doub PI = 3.141592653589793238;
		final Doub ONOVRT = 0.577350269189626;
		final Doub THR = 1./3.;
		final Doub TWOTHR = 2.*THR;
		Doub absx = new Doub();
		Doub rootx = new Doub();
		Doub z = new Doub();
		absx = Math.abs(x);
		rootx = Math.sqrt(absx);
		z = TWOTHR *absx *rootx;
		if (x > 0.0)
		{
			besselik(THR, z);
			aio = rootx *ONOVRT *ko/PI;
			bio = rootx*(ko/PI+2.0 *ONOVRT *io);
			besselik(TWOTHR, z);
			aipo = -x *ONOVRT *ko/PI;
			bipo = x*(ko/PI+2.0 *ONOVRT *io);
		}
		else if (x < 0.0)
		{
			besseljy(THR, z);
			aio = 0.5 *rootx*(jo-ONOVRT *yo);
			bio = -0.5 *rootx*(yo+ONOVRT *jo);
			besseljy(TWOTHR, z);
			aipo = 0.5 *absx*(ONOVRT *yo+jo);
			bipo = 0.5 *absx*(ONOVRT *jo-yo);
		}
		else
		{
			aio = 0.355028053887817;
			bio = aio/ONOVRT;
			aipo = -0.258819403792807;
			bipo = -aipo/ONOVRT;
		}
	}
	public final Doub airy_ai(Doub x)
	{
		if (x != xo)
			airy(x);
		return aio;
	}
	public final Doub airy_bi(Doub x)
	{
		if (x != xo)
			airy(x);
		return bio;
	}

	public final void sphbes(Int n, Doub x)
	{
		final Doub RTPIO2 = 1.253314137315500251;
		Doub factor = new Doub();
		Doub order = new Doub();
		if (n < 0 || x <= 0.0)
			throw("bad arguments in sphbes");
		order = n+0.5;
		besseljy(order, x);
		factor = RTPIO2/Math.sqrt(x);
		sphjo = factor *jo;
		sphyo = factor *yo;
		sphjpo = factor *jpo-sphjo/(2.*x);
		sphypo = factor *ypo-sphyo/(2.*x);
		sphno = n;
	}
	public final Doub sphbesj(Int n, Doub x)
	{
		if (n != sphno || x != xo)
			sphbes(n, x);
		return sphjo;
	}
	public final Doub sphbesy(Int n, Doub x)
	{
		if (n != sphno || x != xo)
			sphbes(n, x);
		return sphyo;
	}

	public final Doub chebev(Doub[] c, Int m, Doub x)
	{
		Doub d = 0.0;
		Doub dd = 0.0;
		Doub sv = new Doub();
		Int j = new Int();
		for (j = m-1;j>0;j--)
		{
			sv = d;
			d = 2.*x *d-dd+c[j];
			dd = sv;
		}
		return x *d-dd+0.5 *c[0];
	}
}