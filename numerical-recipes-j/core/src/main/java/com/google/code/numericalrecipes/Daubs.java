package com.google.code.numericalrecipes;
public class Daubs implements Wavelet
{
	public Int ncof = new Int();
	public Int ioff = new Int();
	public Int joff = new Int();
	public VecDoub cc = new VecDoub();
	public VecDoub cr = new VecDoub();
	public static Doub[] c4 = new Doub[4];
	public static Doub[] c12 = new Doub[12];
	public static Doub[] c20 = new Doub[20];
	public Daubs(Int n)
	{
		ncof = n;
		cc = n;
		cr = n;
		Int i = new Int();
		ioff = joff = -(n >> 1);
		// ioff = -2; joff = -n + 2;
		if (n == 4)
			for (i = 0; i<n; i++)
				cc[i] = c4[i];
		else if (n == 12)
			for (i = 0; i<n; i++)
				cc[i] = c12[i];
		else if (n == 20)
			for (i = 0; i<n; i++)
				cc[i] = c20[i];
		else
			throw("n not yet implemented in Daubs");
		Doub sig = -1.0;
		for (i = 0; i<n; i++)
		{
			cr[n-1-i]=sig *cc[i];
			sig = -sig;
		}
	}
	public final void filt(RefObject<VecDoub_IO> a, Int n, Int isign)
	{
		Doub ai = new Doub();
		Doub ai1 = new Doub();
		Int i = new Int();
		Int ii = new Int();
		Int j = new Int();
		Int jf = new Int();
		Int jr = new Int();
		Int k = new Int();
		Int n1 = new Int();
		Int ni = new Int();
		Int nj = new Int();
		Int nh = new Int();
		Int nmod = new Int();
		if (n < 4)
			return;
		VecDoub wksp = new VecDoub(n);
		nmod = ncof *n;
		n1 = n-1;
		nh = n >> 1;
		for (j = 0;j<n;j++)
			wksp[j]=0.0;
		if (isign >= 0)
		{
			for (ii = 0,i = 0;i<n;i+=2,ii++)
			{
				ni = i+1+nmod+ioff;
				nj = i+1+nmod+joff;
				for (k = 0;k<ncof;k++)
				{
					jf = n1 & (ni+k+1);
					jr = n1 & (nj+k+1);
					wksp[ii] += cc[k]*a.argvalue[jf];
					wksp[ii+nh] += cr[k]*a.argvalue[jr];
				}
			}
		}
		else
		{
			for (ii = 0,i = 0;i<n;i+=2,ii++)
			{
				ai = a.argvalue[ii];
				ai1 = a.argvalue[ii+nh];
				ni = i+1+nmod+ioff;
				nj = i+1+nmod+joff;
				for (k = 0;k<ncof;k++)
				{
					jf = n1 & (ni+k+1);
					jr = n1 & (nj+k+1);
					wksp[jf] += cc[k]*ai;
					wksp[jr] += cr[k]*ai1;
				}
			}
		}
		for (j = 0;j<n;j++)
			a.argvalue[j] = wksp[j];
	}
}