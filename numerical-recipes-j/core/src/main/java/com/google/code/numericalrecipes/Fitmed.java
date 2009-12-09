package com.google.code.numericalrecipes;
public class Fitmed
{
	public Int ndata = new Int();
	public Doub a = new Doub();
	public Doub b = new Doub();
	public Doub abdev = new Doub();
	public VecDoub_I x;
	public VecDoub_I y;

	public Fitmed(RefObject<VecDoub_I> xx, RefObject<VecDoub_I> yy)
	{
		ndata = xx.argvalue.size();
		x = xx.argvalue;
		y = yy.argvalue;
		Int j = new Int();
		Doub b1 = new Doub();
		Doub b2 = new Doub();
		Doub del = new Doub();
		Doub f = new Doub();
		Doub f1 = new Doub();
		Doub f2 = new Doub();
		Doub sigb = new Doub();
		Doub temp = new Doub();
		Doub sx = 0.0;
		Doub sy = 0.0;
		Doub sxy = 0.0;
		Doub sxx = 0.0;
		Doub chisq = 0.0;
		for (j = 0;j<ndata;j++)
		{
			sx += x[j];
			sy += y[j];
			sxy += x[j]*y[j];
			sxx += SQR(x[j]);
		}
		del = ndata *sxx-sx *sx;
		a = (sxx *sy-sx *sxy)/del;
		b = (ndata *sxy-sx *sy)/del;
		for (j = 0;j<ndata;j++)
		{
			temp = y[j]-(a+b *x[j]);
			chisq = temp *temp;
		}
		sigb = Math.sqrt(chisq/del);
		b1 = b;
		f1 = rofunc(b1);
		if (sigb > 0.0)
		{
			b2 = b+SIGN(3.0 *sigb,f1);
			f2 = rofunc(b2);
			if (b2 == b1)
			{
				abdev /= ndata;
				return;
			}
			while (f1 *f2 > 0.0)
			{
				b = b2+1.6*(b2-b1);
				b1 = b2;
				f1 = f2;
				b2 = b;
				f2 = rofunc(b2);
			}
			sigb = 0.01 *sigb;
			while (Math.abs(b2-b1) > sigb)
			{
				b = b1+0.5*(b2-b1);
				if (b == b1 || b == b2)
					break;
				f = rofunc(b);
				if (f *f1 >= 0.0)
				{
					f1 = f;
					b1 = b;
				}
				else
				{
					f2 = f;
					b2 = b;
				}
			}
		}
		abdev /= ndata;
	}

	public final Doub rofunc(Doub b)
	{
		final Doub EPS = numeric_limits<Doub>.epsilon();
		Int j = new Int();
		Doub d = new Doub();
		Doub sum = 0.0;
		VecDoub arr = new VecDoub(ndata);
		for (j = 0;j<ndata;j++)
			arr[j]=y[j]-b *x[j];
		if ((ndata & 1) == 1)
		{
			a = select((ndata-1)>>1,arr);
		}
		else
		{
			j = ndata >> 1;
			a = 0.5*(select(j-1,arr)+select(j,arr));
		}
		abdev = 0.0;
		for (j = 0;j<ndata;j++)
		{
			d = y[j]-(b *x[j]+a);
			abdev += Math.abs(d);
			if (y[j] != 0.0)
				d /= Math.abs(y[j]);
			if (Math.abs(d) > EPS)
				sum += (d >= 0.0 ? x[j] : -x[j]);
		}
		return sum;
	}
}