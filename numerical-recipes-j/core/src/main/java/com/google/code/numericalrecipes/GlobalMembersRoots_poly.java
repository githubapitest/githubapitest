package com.google.code.numericalrecipes;
public class GlobalMembersRoots_poly
{
	public static void laguer(RefObject<VecComplex_I> a, RefObject<Complex> x, RefObject<Int> its)
	{
		final Int MR = 8;
		final Int MT = 10;
		final Int MAXIT = MT *MR;
		final Doub EPS = numeric_limits<Doub>.epsilon();
		Doub[] frac = {0.0,0.5,0.25,0.75,0.13,0.38,0.62,0.88,1.0};
		Complex dx = new Complex();
		Complex x1 = new Complex();
		Complex b = new Complex();
		Complex d = new Complex();
		Complex f = new Complex();
		Complex g = new Complex();
		Complex h = new Complex();
		Complex sq = new Complex();
		Complex gp = new Complex();
		Complex gm = new Complex();
		Complex g2 = new Complex();
		Int m = a.argvalue.size()-1;
		for (Int iter = 1;iter<=MAXIT;iter++)
		{
			its.argvalue = iter;
			b = a.argvalue[m];
			Doub err = Math.abs(b);
			d = f = 0.0;
			Doub abx = Math.abs(x.argvalue);
			for (Int j = m-1;j>=0;j--)
			{
				f = x.argvalue *f+d;
				d = x.argvalue *d+b;
				b = x.argvalue *b+a.argvalue[j];
				err = Math.abs(b)+abx *err;
			}
			err *= EPS;
			if (Math.abs(b) <= err)
				return;
			g = d/b;
			g2 = g *g;
			h = g2-2.0 *f/b;
			sq = Math.sqrt(Doub(m-1)*(Doub(m)*h-g2));
			gp = g+sq;
			gm = g-sq;
			Doub abp = Math.abs(gp);
			Doub abm = Math.abs(gm);
			if (abp < abm)
				gp = gm;
			dx = MAX(abp,abm) > 0.0 ? Doub(m)/gp : polar(1+abx,Doub(iter));
			x1 = x.argvalue-dx;
			if (x.argvalue == x1)
				return;
			if (iter % MT != 0)
				x.argvalue = x1;
			else
				x.argvalue -= frac[iter/MT]*dx;
		}
		throw("too many iterations in laguer");
	}
	public static void zroots(RefObject<VecComplex_I> a, RefObject<VecComplex_O> roots, Bool polish)
	{
		final Doub EPS = 1.0e-14;
		Int i = new Int();
		Int its = new Int();
		Complex x = new Complex();
		Complex b = new Complex();
		Complex c = new Complex();
		Int m = a.argvalue.size()-1;
		VecComplex ad = new VecComplex(m+1);
		for (Int j = 0;j<=m;j++)
			ad[j]=a.argvalue[j];
		for (Int j = m-1;j>=0;j--)
		{
			x = 0.0;
			VecComplex ad_v = new VecComplex(j+2);
			for (Int jj = 0;jj<j+2;jj++)
				ad_v[jj]=ad[jj];
		RefObject<VecComplex_I> tempRefObject = new RefObject<VecComplex_I>(ad_v);
		RefObject<Complex> tempRefObject2 = new RefObject<Complex>(x);
		RefObject<Int> tempRefObject3 = new RefObject<Int>(its);
			laguer(tempRefObject, tempRefObject2, tempRefObject3);
			ad_v = tempRefObject.argvalue;
			x = tempRefObject2.argvalue;
			its = tempRefObject3.argvalue;
			if (Math.abs(imag(x)) <= 2.0 *EPS *Math.abs(real(x)))
				x = Complex(real(x),0.0);
			roots.argvalue[j]=x;
			b = ad[j+1];
			for (Int jj = j;jj>=0;jj--)
			{
				c = ad[jj];
				ad[jj]=b;
				b = x *b+c;
			}
		}
		if (polish != null)
			for (Int j = 0;j<m;j++)
		{
			RefObject<Int> tempRefObject4 = new RefObject<Int>(its);
				laguer(a, roots.argvalue[j], tempRefObject4);
				its = tempRefObject4.argvalue;
			}
		for (Int j = 1;j<m;j++)
		{
			x = roots.argvalue[j];
			for (i = j-1;i>=0;i--)
			{
				if (real(roots.argvalue[i]) <= real(x))
					break;
				roots.argvalue[i+1]=roots.argvalue[i];
			}
			roots.argvalue[i+1]=x;
		}
	}
}