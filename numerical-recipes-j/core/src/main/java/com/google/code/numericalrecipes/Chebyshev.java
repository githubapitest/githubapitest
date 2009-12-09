package com.google.code.numericalrecipes;
public class Chebyshev
{
	public Int n = new Int();
	public Int m = new Int();
	public VecDoub c = new VecDoub();
	public Doub a = new Doub();
	public Doub b = new Doub();

	public Chebyshev(Doub func(Doub), Doub aa, Doub bb)
	{
		this(, aa, bb, 50);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Chebyshev(Doub func(Doub), Doub aa, Doub bb, Int nn=50) : n(nn), m(nn), c(n), a(aa), b(bb)
	public Chebyshev(Doub func(Doub), Doub aa, Doub bb, Int nn)
	{
		n = nn;
		m = nn;
		c = n;
		a = aa;
		b = bb;
		final Doub pi = 3.141592653589793;
		Int k = new Int();
		Int j = new Int();
		Doub fac = new Doub();
		Doub bpa = new Doub();
		Doub bma = new Doub();
		Doub y = new Doub();
		Doub sum = new Doub();
		VecDoub f = new VecDoub(n);
		bma = 0.5*(b-a);
		bpa = 0.5*(b+a);
		for (k = 0;k<n;k++)
		{
			y = Math.cos(pi*(k+0.5)/n);
			f[k]=func(y *bma+bpa);
		}
		fac = 2.0/n;
		for (j = 0;j<n;j++)
		{
			sum = 0.0;
			for (k = 0;k<n;k++)
				sum += f[k]*Math.cos(pi *j*(k+0.5)/n);
			c[j]=fac *sum;
		}
	}
	public Chebyshev(RefObject<VecDoub> cc, Doub aa, Doub bb)
	{
		n = cc.argvalue.size();
		m = n;
		c = cc.argvalue;
		a = aa;
		b = bb;
	}
	public final Int setm(Doub thresh)
	{
		while (m>1 && Math.abs(c[m-1])<thresh)
			m--;
			return m;
	}

	public final Doub eval(Doub x, Int m)
	{
		Doub d = 0.0;
		Doub dd = 0.0;
		Doub sv = new Doub();
		Doub y = new Doub();
		Doub y2 = new Doub();
		Int j = new Int();
		if ((x-a)*(x-b) > 0.0)
			throw("x not in range in Chebyshev::eval");
		y2 = 2.0*(y = (2.0 *x-a-b)/(b-a));
		for (j = m-1;j>0;j--)
		{
			sv = d;
			d = y2 *d-dd+c[j];
			dd = sv;
		}
		return y *d-dd+0.5 *c[0];
	}
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	Doub operator ()(Doub x)
	{
		return eval(x, m);
	}

	public final Chebyshev derivative()
	{
		Int j = new Int();
		Doub con = new Doub();
		VecDoub cder = new VecDoub(n);
		cder[n-1]=0.0;
		cder[n-2]=2*(n-1)*c[n-1];
		for (j = n-2;j>0;j--)
			cder[j-1]=cder[j+1]+2 *j *c[j];
		con = 2.0/(b-a);
		for (j = 0;j<n;j++)
			cder[j] *= con;
		RefObject<VecDoub> tempRefObject = new RefObject<VecDoub>(cder);
		return new Chebyshev(tempRefObject, a, b);
		cder = tempRefObject.argvalue;
	}
	public final Chebyshev integral()
	{
		Int j = new Int();
		Doub sum = 0.0;
		Doub fac = 1.0;
		Doub con = new Doub();
		VecDoub cint = new VecDoub(n);
		con = 0.25*(b-a);
		for (j = 1;j<n-1;j++)
		{
			cint[j]=con*(c[j-1]-c[j+1])/j;
			sum += fac *cint[j];
			fac = -fac;
		}
		cint[n-1]=con *c[n-2]/(n-1);
		sum += fac *cint[n-1];
		cint[0]=2.0 *sum;
		RefObject<VecDoub> tempRefObject = new RefObject<VecDoub>(cint);
		return new Chebyshev(tempRefObject, a, b);
		cint = tempRefObject.argvalue;
	}

	public final VecDoub polycofs(Int m)
	{
		Int k = new Int();
		Int j = new Int();
		Doub sv = new Doub();
		VecDoub d = new VecDoub(m);
		VecDoub dd = new VecDoub(m);
		for (j = 0;j<m;j++)
			d[j]=dd[j]=0.0;
		d[0]=c[m-1];
		for (j = m-2;j>0;j--)
		{
			for (k = m-j;k>0;k--)
			{
				sv = d[k];
				d[k]=2.0 *d[k-1]-dd[k];
				dd[k]=sv;
			}
			sv = d[0];
			d[0] = -dd[0]+c[j];
			dd[0]=sv;
		}
		for (j = m-1;j>0;j--)
			d[j]=d[j-1]-dd[j];
		d[0] = -dd[0]+0.5 *c[0];
		return d;
	}
	public final VecDoub polycofs()
	{
		return polycofs(m);
	}
	public Chebyshev(RefObject<VecDoub> d)
	{
		n = d.argvalue.size();
		m = n;
		c = n;
		a = -1.;
		b = 1.;
		c[n-1]=d.argvalue[n-1];
		c[n-2]=2.0 *d.argvalue[n-2];
		for (Int j = n-3;j>=0;j--)
		{
			c[j]=2.0 *d.argvalue[j]+c[j+2];
			for (Int i = j+1;i<n-2;i++)
			{
					c[i] = (c[i]+c[i+2])/2;
			}
			c[n-2] /= 2;
			c[n-1] /= 2;
		}
	}

}