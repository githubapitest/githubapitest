package com.google.code.numericalrecipes;
public class GlobalMembersPolcoef
{
	public static void polcoe(RefObject<VecDoub_I> x, RefObject<VecDoub_I> y, RefObject<VecDoub_O> cof)
	{
		Int k = new Int();
		Int j = new Int();
		Int i = new Int();
		Int n = x.argvalue.size();
		Doub phi = new Doub();
		Doub ff = new Doub();
		Doub b = new Doub();
		VecDoub s = new VecDoub(n);
		for (i = 0;i<n;i++)
			s[i]=cof.argvalue[i]=0.0;
		s[n-1]= -x.argvalue[0];
		for (i = 1;i<n;i++)
		{
			for (j = n-1-i;j<n-1;j++)
				s[j] -= x.argvalue[i]*s[j+1];
			s[n-1] -= x.argvalue[i];
		}
		for (j = 0;j<n;j++)
		{
			phi = n;
			for (k = n-1;k>0;k--)
				phi = k *s[k]+x.argvalue[j]*phi;
			ff = y.argvalue[j]/phi;
			b = 1.0;
			for (k = n-1;k>=0;k--)
			{
				cof.argvalue[k] += b *ff;
				b = s[k]+x.argvalue[j]*b;
			}
		}
	}
	public static void polcof(RefObject<VecDoub_I> xa, RefObject<VecDoub_I> ya, RefObject<VecDoub_O> cof)
	{
		Int k = new Int();
		Int j = new Int();
		Int i = new Int();
		Int n = xa.argvalue.size();
		Doub xmin = new Doub();
		VecDoub x = new VecDoub(n);
		VecDoub y = new VecDoub(n);
		for (j = 0;j<n;j++)
		{
			x[j]=xa.argvalue[j];
			y[j]=ya.argvalue[j];
		}
		for (j = 0;j<n;j++)
		{
			VecDoub x_t = new VecDoub(n-j);
			VecDoub y_t = new VecDoub(n-j);
			for (k = 0;k<n-j;k++)
			{
				x_t[k]=x[k];
				y_t[k]=y[k];
			}
			Poly_interp interp = new Poly_interp(x,y,n-j);
			cof.argvalue[j] = interp.rawinterp(0,0.);
			xmin = 1.0e99;
			k = -1;
			for (i = 0;i<n-j;i++)
			{
				if (Math.abs(x[i]) < xmin)
				{
					xmin = Math.abs(x[i]);
					k = i;
				}
				if (x[i] != 0.0)
					y[i]=(y[i]-cof.argvalue[j])/x[i];
			}
			for (i = k+1;i<n-j;i++)
			{
				y[i-1]=y[i];
				x[i-1]=x[i];
			}
		}
	}
}