package com.google.code.numericalrecipes;
public class Jacobi
{
	public final Int n = new Int();
	public MatDoub a = new MatDoub();
	public MatDoub v = new MatDoub();
	public VecDoub d = new VecDoub();
	public Int nrot = new Int();
	public final Doub EPS = new Doub();

	public Jacobi(RefObject<MatDoub_I> aa)
	{
		n = aa.argvalue.nrows();
		a = aa.argvalue;
		v = new MatDoub(n,n);
		d = n;
		nrot = 0;
		EPS = numeric_limits<Doub>.epsilon();
		Int i = new Int();
		Int j = new Int();
		Int ip = new Int();
		Int iq = new Int();
		Doub tresh = new Doub();
		Doub theta = new Doub();
		Doub tau = new Doub();
		Doub t = new Doub();
		Doub sm = new Doub();
		Doub s = new Doub();
		Doub h = new Doub();
		Doub g = new Doub();
		Doub c = new Doub();
		VecDoub b = new VecDoub(n);
		VecDoub z = new VecDoub(n);
		for (ip = 0;ip<n;ip++)
		{
			for (iq = 0;iq<n;iq++)
				v[ip][iq]=0.0;
			v[ip][ip]=1.0;
		}
		for (ip = 0;ip<n;ip++)
		{
			b[ip]=d[ip]=a[ip][ip];
			z[ip]=0.0;
		}
		for (i = 1;i<=50;i++)
		{
			sm = 0.0;
			for (ip = 0;ip<n-1;ip++)
			{
				for (iq = ip+1;iq<n;iq++)
					sm += Math.abs(a[ip][iq]);
			}
			if (sm == 0.0)
			{
				RefObject<VecDoub_IO> tempRefObject = new RefObject<VecDoub_IO>(d);
				RefObject<MatDoub_IO> tempRefObject2 = new RefObject<MatDoub_IO>(v);
				GlobalMembersEigen_sym.eigsrt(tempRefObject, tempRefObject2);
				d = tempRefObject.argvalue;
				v = tempRefObject2.argvalue;
				return;
			}
			if (i < 4)
				tresh = 0.2 *sm/(n *n);
			else
				tresh = 0.0;
			for (ip = 0;ip<n-1;ip++)
			{
				for (iq = ip+1;iq<n;iq++)
				{
					g = 100.0 *Math.abs(a[ip][iq]);
					if (i > 4 && g <= EPS *Math.abs(d[ip]) && g <= EPS *Math.abs(d[iq]))
							a[ip][iq]=0.0;
					else if (Math.abs(a[ip][iq]) > tresh)
					{
						h = d[iq]-d[ip];
						if (g <= EPS *Math.abs(h))
							t = (a[ip][iq])/h;
						else
						{
							theta = 0.5 *h/(a[ip][iq]);
							t = 1.0/(Math.abs(theta)+Math.sqrt(1.0+theta *theta));
							if (theta < 0.0)
								t = -t;
						}
						c = 1.0/Math.sqrt(1+t *t);
						s = t *c;
						tau = s/(1.0+c);
						h = t *a[ip][iq];
						z[ip] -= h;
						z[iq] += h;
						d[ip] -= h;
						d[iq] += h;
						a[ip][iq]=0.0;
						for (j = 0;j<ip;j++)
						{
							RefObject<MatDoub_IO> tempRefObject3 = new RefObject<MatDoub_IO>(a);
							rot(tempRefObject3, s, tau, j, ip, j, iq);
							a = tempRefObject3.argvalue;
						}
						for (j = ip+1;j<iq;j++)
						{
							RefObject<MatDoub_IO> tempRefObject4 = new RefObject<MatDoub_IO>(a);
							rot(tempRefObject4, s, tau, ip, j, j, iq);
							a = tempRefObject4.argvalue;
						}
						for (j = iq+1;j<n;j++)
						{
							RefObject<MatDoub_IO> tempRefObject5 = new RefObject<MatDoub_IO>(a);
							rot(tempRefObject5, s, tau, ip, j, iq, j);
							a = tempRefObject5.argvalue;
						}
						for (j = 0;j<n;j++)
						{
							RefObject<MatDoub_IO> tempRefObject6 = new RefObject<MatDoub_IO>(v);
							rot(tempRefObject6, s, tau, j, ip, j, iq);
							v = tempRefObject6.argvalue;
						}
						++nrot;
					}
				}
			}
			for (ip = 0;ip<n;ip++)
			{
				b[ip] += z[ip];
				d[ip]=b[ip];
				z[ip]=0.0;
			}
		}
		throw("Too many iterations in routine jacobi");
	}
	public final void rot(RefObject<MatDoub_IO> a, Doub s, Doub tau, Int i, Int j, Int k, Int l)
	{
		Doub g = a.argvalue[i][j];
		Doub h = a.argvalue[k][l];
		a.argvalue[i][j]=g-s*(h+g *tau);
		a.argvalue[k][l]=h+s*(g-h *tau);
	}
}