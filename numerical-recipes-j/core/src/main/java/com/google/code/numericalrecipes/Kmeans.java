package com.google.code.numericalrecipes;
public class Kmeans
{
	public Int nn = new Int();
	public Int mm = new Int();
	public Int kk = new Int();
	public Int nchg = new Int();
	public MatDoub data = new MatDoub();
	public MatDoub means = new MatDoub();
	public VecInt assign = new VecInt();
	public VecInt count = new VecInt();
	public Kmeans(RefObject<MatDoub> ddata, RefObject<MatDoub> mmeans)
	{
		nn = ddata.argvalue.nrows();
		mm = ddata.argvalue.ncols();
		kk = mmeans.argvalue.nrows();
		data = ddata.argvalue;
		means = mmeans.argvalue;
		assign = nn;
		count = kk;
		estep();
		mstep();
	}
	public final Int estep()
	{
		Int k = new Int();
		Int m = new Int();
		Int n = new Int();
		Int kmin = new Int();
		Doub dmin = new Doub();
		Doub d = new Doub();
		nchg = 0;
		for (k = 0;k<kk;k++)
			count[k] = 0;
		for (n = 0;n<nn;n++)
		{
			dmin = 9.99e99;
			for (k = 0;k<kk;k++)
			{
				for (d = 0.,m = 0; m<mm; m++)
					d += SQR(data[n][m]-means[k][m]);
				if (d < dmin)
				{
					dmin = d;
					kmin = k;
				}
			}
			if (kmin != assign[n])
				nchg++;
			assign[n] = kmin;
			count[kmin]++;
		}
		return nchg;
	}
	public final void mstep()
	{
		Int n = new Int();
		Int k = new Int();
		Int m = new Int();
		for (k = 0;k<kk;k++)
			for (m = 0;m<mm;m++)
				means[k][m] = 0.;
		for (n = 0;n<nn;n++)
			for (m = 0;m<mm;m++)
				means[assign[n]][m] += data[n][m];
		for (k = 0;k<kk;k++)
		{
			if (count[k] > 0)
				for (m = 0;m<mm;m++)
					means[k][m] /= count[k];
		}
	}
}