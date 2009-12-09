package com.google.code.numericalrecipes;
public class SVD
{
	public Int m = new Int();
	public Int n = new Int();
	public MatDoub u = new MatDoub();
	public MatDoub v = new MatDoub();
	public VecDoub w = new VecDoub();
	public Doub eps = new Doub();
	public Doub tsh = new Doub();
	public SVD(RefObject<MatDoub_I> a)
	{
		m = a.argvalue.nrows();
		n = a.argvalue.ncols();
		u = a.argvalue;
		v = new MatDoub(n,n);
		w = n;
		eps = numeric_limits<Doub>.epsilon();
		decompose();
		reorder();
		tsh = 0.5 *Math.sqrt(m+n+1.)*w[0]*eps;
	}

	public final void solve(RefObject<VecDoub_I> b, RefObject<VecDoub_O> x)
	{
		solve(b, x, -1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void solve(VecDoub_I &b, VecDoub_O &x, Doub thresh = -1.)
	public final void solve(RefObject<VecDoub_I> b, RefObject<VecDoub_O> x, Doub thresh)
	{
		Int i = new Int();
		Int j = new Int();
		Int jj = new Int();
		Doub s = new Doub();
		if (b.argvalue.size() != m || x.argvalue.size() != n)
			throw("SVD::solve bad sizes");
		VecDoub tmp = new VecDoub(n);
		tsh = (thresh >= 0.? thresh : 0.5 *Math.sqrt(m+n+1.)*w[0]*eps);
		for (j = 0;j<n;j++)
		{
			s = 0.0;
			if (w[j] > tsh)
			{
				for (i = 0;i<m;i++)
					s += u[i][j]*b.argvalue[i];
				s /= w[j];
			}
			tmp[j]=s;
		}
		for (j = 0;j<n;j++)
		{
			s = 0.0;
			for (jj = 0;jj<n;jj++)
				s += v[j][jj]*tmp[jj];
			x.argvalue[j]=s;
		}
	}
	public final void solve(RefObject<MatDoub_I> b, RefObject<MatDoub_O> x)
	{
		solve(b, x, -1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void solve(MatDoub_I &b, MatDoub_O &x, Doub thresh = -1.)
	public final void solve(RefObject<MatDoub_I> b, RefObject<MatDoub_O> x, Doub thresh)
	{
		int i;
		int j;
		int m = b.argvalue.ncols();
		if (b.argvalue.nrows() != n || x.argvalue.nrows() != n || b.argvalue.ncols() != x.argvalue.ncols())
			throw("SVD::solve bad sizes");
		VecDoub xx = new VecDoub(n);
		for (j = 0;j<m;j++)
		{
			for (i = 0;i<n;i++)
				xx[i] = b.argvalue[i][j];
			RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(xx);
			RefObject<VecDoub_O> tempRefObject2 = new RefObject<VecDoub_O>(xx);
			solve(tempRefObject, tempRefObject2, thresh);
			xx = tempRefObject.argvalue;
			xx = tempRefObject2.argvalue;
			for (i = 0;i<n;i++)
				x.argvalue[i][j] = xx[i];
		}
	}

	public final Int rank()
	{
		return rank(-1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Int rank(Doub thresh = -1.)
	public final Int rank(Doub thresh)
	{
		Int j = new Int();
		Int nr = 0;
		tsh = (thresh >= 0.? thresh : 0.5 *Math.sqrt(m+n+1.)*w[0]*eps);
		for (j = 0;j<n;j++)
			if (w[j] > tsh)
				nr++;
		return nr;
	}
	public final Int nullity()
	{
		return nullity(-1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Int nullity(Doub thresh = -1.)
	public final Int nullity(Doub thresh)
	{
		Int j = new Int();
		Int nn = 0;
		tsh = (thresh >= 0.? thresh : 0.5 *Math.sqrt(m+n+1.)*w[0]*eps);
		for (j = 0;j<n;j++)
			if (w[j] <= tsh)
				nn++;
		return nn;
	}
	public final MatDoub range()
	{
		return range(-1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: MatDoub range(Doub thresh = -1.)
	public final MatDoub range(Doub thresh)
	{
		Int i = new Int();
		Int j = new Int();
		Int nr = 0;
		MatDoub rnge = new MatDoub(m,rank(thresh));
		for (j = 0;j<n;j++)
		{
			if (w[j] > tsh)
			{
				for (i = 0;i<m;i++)
					rnge[i][nr] = u[i][j];
				nr++;
			}
		}
		return rnge;
	}
	public final MatDoub nullspace()
	{
		return nullspace(-1.);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: MatDoub nullspace(Doub thresh = -1.)
	public final MatDoub nullspace(Doub thresh)
	{
		Int j = new Int();
		Int jj = new Int();
		Int nn = 0;
		MatDoub nullsp = new MatDoub(n,nullity(thresh));
		for (j = 0;j<n;j++)
		{
			if (w[j] <= tsh)
			{
				for (jj = 0;jj<n;jj++)
					nullsp[jj][nn] = v[jj][j];
				nn++;
			}
		}
		return nullsp;
	}

	public final Doub inv_condition()
	{
		return (w[0] <= 0.|| w[n-1] <= 0.) ? 0.: w[n-1]/w[0];
	}

	public final void decompose()
	{
		boolean flag;
		Int i = new Int();
		Int its = new Int();
		Int j = new Int();
		Int jj = new Int();
		Int k = new Int();
		Int l = new Int();
		Int nm = new Int();
		Doub anorm = new Doub();
		Doub c = new Doub();
		Doub f = new Doub();
		Doub g = new Doub();
		Doub h = new Doub();
		Doub s = new Doub();
		Doub scale = new Doub();
		Doub x = new Doub();
		Doub y = new Doub();
		Doub z = new Doub();
		VecDoub rv1 = new VecDoub(n);
		g = scale = anorm = 0.0;
		for (i = 0;i<n;i++)
		{
			l = i+2;
			rv1[i]=scale *g;
			g = s = scale = 0.0;
			if (i < m)
			{
				for (k = i;k<m;k++)
					scale += Math.abs(u[k][i]);
				if (scale != 0.0)
				{
					for (k = i;k<m;k++)
					{
						u[k][i] /= scale;
						s += u[k][i]*u[k][i];
					}
					f = u[i][i];
					g = -SIGN(Math.sqrt(s),f);
					h = f *g-s;
					u[i][i]=f-g;
					for (j = l-1;j<n;j++)
					{
						for (s = 0.0,k = i;k<m;k++)
							s += u[k][i]*u[k][j];
						f = s/h;
						for (k = i;k<m;k++)
							u[k][j] += f *u[k][i];
					}
					for (k = i;k<m;k++)
						u[k][i] *= scale;
				}
			}
			w[i]=scale *g;
			g = s = scale = 0.0;
			if (i+1 <= m && i+1 != n)
			{
				for (k = l-1;k<n;k++)
					scale += Math.abs(u[i][k]);
				if (scale != 0.0)
				{
					for (k = l-1;k<n;k++)
					{
						u[i][k] /= scale;
						s += u[i][k]*u[i][k];
					}
					f = u[i][l-1];
					g = -SIGN(Math.sqrt(s),f);
					h = f *g-s;
					u[i][l-1]=f-g;
					for (k = l-1;k<n;k++)
						rv1[k]=u[i][k]/h;
					for (j = l-1;j<m;j++)
					{
						for (s = 0.0,k = l-1;k<n;k++)
							s += u[j][k]*u[i][k];
						for (k = l-1;k<n;k++)
							u[j][k] += s *rv1[k];
					}
					for (k = l-1;k<n;k++)
						u[i][k] *= scale;
				}
			}
			anorm = MAX(anorm,(Math.abs(w[i])+Math.abs(rv1[i])));
		}
		for (i = n-1;i>=0;i--)
		{
			if (i < n-1)
			{
				if (g != 0.0)
				{
					for (j = l;j<n;j++)
						v[j][i]=(u[i][j]/u[i][l])/g;
					for (j = l;j<n;j++)
					{
						for (s = 0.0,k = l;k<n;k++)
							s += u[i][k]*v[k][j];
						for (k = l;k<n;k++)
							v[k][j] += s *v[k][i];
					}
				}
				for (j = l;j<n;j++)
					v[i][j]=v[j][i]=0.0;
			}
			v[i][i]=1.0;
			g = rv1[i];
			l = i;
		}
		for (i = MIN(m,n)-1;i>=0;i--)
		{
			l = i+1;
			g = w[i];
			for (j = l;j<n;j++)
				u[i][j]=0.0;
			if (g != 0.0)
			{
				g = 1.0/g;
				for (j = l;j<n;j++)
				{
					for (s = 0.0,k = l;k<m;k++)
						s += u[k][i]*u[k][j];
					f = (s/u[i][i])*g;
					for (k = i;k<m;k++)
						u[k][j] += f *u[k][i];
				}
				for (j = i;j<m;j++)
					u[j][i] *= g;
			}
			else
				for (j = i;j<m;j++)
					u[j][i]=0.0;
			++u[i][i];
		}
		for (k = n-1;k>=0;k--)
		{
			for (its = 0;its<30;its++)
			{
				flag = true;
				for (l = k;l>=0;l--)
				{
					nm = l-1;
					if (l == 0 || Math.abs(rv1[l]) <= eps *anorm)
					{
						flag = false;
						break;
					}
					if (Math.abs(w[nm]) <= eps *anorm)
						break;
				}
				if (flag)
				{
					c = 0.0;
					s = 1.0;
					for (i = l;i<k+1;i++)
					{
						f = s *rv1[i];
						rv1[i]=c *rv1[i];
						if (Math.abs(f) <= eps *anorm)
							break;
						g = w[i];
						h = pythag(f, g);
						w[i]=h;
						h = 1.0/h;
						c = g *h;
						s = -f *h;
						for (j = 0;j<m;j++)
						{
							y = u[j][nm];
							z = u[j][i];
							u[j][nm]=y *c+z *s;
							u[j][i]=z *c-y *s;
						}
					}
				}
				z = w[k];
				if (l == k)
				{
					if (z < 0.0)
					{
						w[k] = -z;
						for (j = 0;j<n;j++)
							v[j][k] = -v[j][k];
					}
					break;
				}
				if (its == 29)
					throw("no convergence in 30 svdcmp iterations");
				x = w[l];
				nm = k-1;
				y = w[nm];
				g = rv1[nm];
				h = rv1[k];
				f = ((y-z)*(y+z)+(g-h)*(g+h))/(2.0 *h *y);
				g = pythag(f, 1.0);
				f = ((x-z)*(x+z)+h*((y/(f+SIGN(g,f)))-h))/x;
				c = s = 1.0;
				for (j = l;j<=nm;j++)
				{
					i = j+1;
					g = rv1[i];
					y = w[i];
					h = s *g;
					g = c *g;
					z = pythag(f, h);
					rv1[j]=z;
					c = f/z;
					s = h/z;
					f = x *c+g *s;
					g = g *c-x *s;
					h = y *s;
					y *= c;
					for (jj = 0;jj<n;jj++)
					{
						x = v[jj][j];
						z = v[jj][i];
						v[jj][j]=x *c+z *s;
						v[jj][i]=z *c-x *s;
					}
					z = pythag(f, h);
					w[j]=z;
					if (z != null)
					{
						z = 1.0/z;
						c = f *z;
						s = h *z;
					}
					f = c *g+s *y;
					x = c *y-s *g;
					for (jj = 0;jj<m;jj++)
					{
						y = u[jj][j];
						z = u[jj][i];
						u[jj][j]=y *c+z *s;
						u[jj][i]=z *c-y *s;
					}
				}
				rv1[l]=0.0;
				rv1[k]=f;
				w[k]=x;
			}
		}
	}
	public final void reorder()
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int s = new Int();
		Int inc = 1;
		Doub sw = new Doub();
		VecDoub su = new VecDoub(m);
		VecDoub sv = new VecDoub(n);
		do
		{
			inc *= 3;
			inc++;
		} while (inc <= n);
		do
		{
			inc /= 3;
			for (i = inc;i<n;i++)
			{
				sw = w[i];
				for (k = 0;k<m;k++)
					su[k] = u[k][i];
				for (k = 0;k<n;k++)
					sv[k] = v[k][i];
				j = i;
				while (w[j-inc] < sw)
				{
					w[j] = w[j-inc];
					for (k = 0;k<m;k++)
						u[k][j] = u[k][j-inc];
					for (k = 0;k<n;k++)
						v[k][j] = v[k][j-inc];
					j -= inc;
					if (j < inc)
						break;
				}
				w[j] = sw;
				for (k = 0;k<m;k++)
					u[k][j] = su[k];
				for (k = 0;k<n;k++)
					v[k][j] = sv[k];
	
			}
		} while (inc > 1);
		for (k = 0;k<n;k++)
		{
			s = 0;
			for (i = 0;i<m;i++)
				if (u[i][k] < 0.)
					s++;
			for (j = 0;j<n;j++)
				if (v[j][k] < 0.)
					s++;
			if (s > (m+n)/2)
			{
				for (i = 0;i<m;i++)
					u[i][k] = -u[i][k];
				for (j = 0;j<n;j++)
					v[j][k] = -v[j][k];
			}
		}
	}
	public final Doub pythag(Doub a, Doub b)
	{
		Doub absa = Math.abs(a);
		Doub absb = Math.abs(b);
		return (absa > absb != null ? absa *Math.sqrt(1.0+SQR(absb/absa)) : (absb == 0.0 ? 0.0 : absb *Math.sqrt(1.0+SQR(absa/absb))));
	}
}