package com.google.code.numericalrecipes;
public class Symmeig
{
	public Int n = new Int();
	public MatDoub z = new MatDoub();
	public VecDoub d = new VecDoub();
	public VecDoub e = new VecDoub();
	public Bool yesvecs = new Bool();

	public Symmeig(RefObject<MatDoub_I> a)
	{
		this(a, true);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Symmeig(MatDoub_I &a, Bool yesvec=true) : n(a.nrows()), z(a), d(n), e(n), yesvecs(yesvec)
	public Symmeig(RefObject<MatDoub_I> a, Bool yesvec)
	{
		n = a.argvalue.nrows();
		z = a.argvalue;
		d = n;
		e = n;
		yesvecs = yesvec;
		tred2();
		tqli();
		sort();
	}
	public Symmeig(RefObject<VecDoub_I> dd, RefObject<VecDoub_I> ee)
	{
		this(dd, ee, true);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Symmeig(VecDoub_I &dd, VecDoub_I &ee, Bool yesvec=true) : n(dd.size()), d(dd), e(ee), z(n,n,0.0), yesvecs(yesvec)
	public Symmeig(RefObject<VecDoub_I> dd, RefObject<VecDoub_I> ee, Bool yesvec)
	{
		n = dd.argvalue.size();
		d = dd.argvalue;
		e = ee.argvalue;
		z = new MatDoub(n,n,0.0);
		yesvecs = yesvec;
		for (Int i = 0;i<n;i++)
			z[i][i]=1.0;
		tqli();
		sort();
	}
	public final void sort()
	{
		if (yesvecs != null)
		{
			RefObject<VecDoub_IO> tempRefObject = new RefObject<VecDoub_IO>(d);
			RefObject<MatDoub_IO> tempRefObject2 = new RefObject<MatDoub_IO>(z);
			GlobalMembersEigen_sym.eigsrt(tempRefObject, tempRefObject2);
			d = tempRefObject.argvalue;
			z = tempRefObject2.argvalue;
		}
		else
			GlobalMembersEigen_sym.eigsrt(d);
	}
	public final void tred2()
	{
		Int l = new Int();
		Int k = new Int();
		Int j = new Int();
		Int i = new Int();
		Doub scale = new Doub();
		Doub hh = new Doub();
		Doub h = new Doub();
		Doub g = new Doub();
		Doub f = new Doub();
		for (i = n-1;i>0;i--)
		{
			l = i-1;
			h = scale = 0.0;
			if (l > 0)
			{
				for (k = 0;k<i;k++)
					scale += Math.abs(z[i][k]);
				if (scale == 0.0)
					e[i]=z[i][l];
				else
				{
					for (k = 0;k<i;k++)
					{
						z[i][k] /= scale;
						h += z[i][k]*z[i][k];
					}
					f = z[i][l];
					g = (f >= 0.0 ? -Math.sqrt(h) : Math.sqrt(h));
					e[i]=scale *g;
					h -= f *g;
					z[i][l]=f-g;
					f = 0.0;
					for (j = 0;j<i;j++)
					{
						if (yesvecs != null)
							z[j][i]=z[i][j]/h;
						g = 0.0;
						for (k = 0;k<j+1;k++)
							g += z[j][k]*z[i][k];
						for (k = j+1;k<i;k++)
							g += z[k][j]*z[i][k];
						e[j]=g/h;
						f += e[j]*z[i][j];
					}
					hh = f/(h+h);
					for (j = 0;j<i;j++)
					{
						f = z[i][j];
						e[j]=g = e[j]-hh *f;
						for (k = 0;k<j+1;k++)
							z[j][k] -= (f *e[k]+g *z[i][k]);
					}
				}
			}
			else
				e[i]=z[i][l];
			d[i]=h;
		}
		if (yesvecs != null)
			d[0]=0.0;
		e[0]=0.0;
		for (i = 0;i<n;i++)
		{
			if (yesvecs != null)
			{
				if (d[i] != 0.0)
				{
					for (j = 0;j<i;j++)
					{
						g = 0.0;
						for (k = 0;k<i;k++)
							g += z[i][k]*z[k][j];
						for (k = 0;k<i;k++)
							z[k][j] -= g *z[k][i];
					}
				}
				d[i]=z[i][i];
				z[i][i]=1.0;
				for (j = 0;j<i;j++)
					z[j][i]=z[i][j]=0.0;
			}
			else
			{
				d[i]=z[i][i];
			}
		}
	}
	public final void tqli()
	{
		Int m = new Int();
		Int l = new Int();
		Int iter = new Int();
		Int i = new Int();
		Int k = new Int();
		Doub s = new Doub();
		Doub r = new Doub();
		Doub p = new Doub();
		Doub g = new Doub();
		Doub f = new Doub();
		Doub dd = new Doub();
		Doub c = new Doub();
		Doub b = new Doub();
		final Doub EPS = numeric_limits<Doub>.epsilon();
		for (i = 1;i<n;i++)
			e[i-1]=e[i];
		e[n-1]=0.0;
		for (l = 0;l<n;l++)
		{
			iter = 0;
			do
			{
				for (m = l;m<n-1;m++)
				{
					dd = Math.abs(d[m])+Math.abs(d[m+1]);
					if (Math.abs(e[m]) <= EPS *dd)
						break;
				}
				if (m != l)
				{
					if (iter++== 30)
						throw("Too many iterations in tqli");
					g = (d[l+1]-d[l])/(2.0 *e[l]);
					r = pythag(g, 1.0);
					g = d[m]-d[l]+e[l]/(g+SIGN(r,g));
					s = c = 1.0;
					p = 0.0;
					for (i = m-1;i>=l;i--)
					{
						f = s *e[i];
						b = c *e[i];
						e[i+1]=(r = pythag(f, g));
						if (r == 0.0)
						{
							d[i+1] -= p;
							e[m]=0.0;
							break;
						}
						s = f/r;
						c = g/r;
						g = d[i+1]-p;
						r = (d[i]-g)*s+2.0 *c *b;
						d[i+1]=g+(p = s *r);
						g = c *r-b;
						if (yesvecs != null)
						{
							for (k = 0;k<n;k++)
							{
								f = z[k][i+1];
								z[k][i+1]=s *z[k][i]+c *f;
								z[k][i]=c *z[k][i]-s *f;
							}
						}
					}
					if (r == 0.0 && i >= l)
						continue;
					d[l] -= p;
					e[l]=g;
					e[m]=0.0;
				}
			} while (m != l);
		}
	}
	public final Doub pythag(Doub a, Doub b)
	{
		Doub absa = Math.abs(a);
		Doub absb = Math.abs(b);
		return (absa > absb != null ? absa *Math.sqrt(1.0+SQR(absb/absa)) : (absb == 0.0 ? 0.0 : absb *Math.sqrt(1.0+SQR(absa/absb))));
	}
}