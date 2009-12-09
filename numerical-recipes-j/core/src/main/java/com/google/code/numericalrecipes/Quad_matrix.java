package com.google.code.numericalrecipes;
public class Quad_matrix
{
	public Int n = new Int();
	public Doub x = new Doub();
	public Quad_matrix(RefObject<MatDoub_O> a)
	{
		n = a.argvalue.nrows();
		final Doub PI = 3.14159263589793238;
		VecDoub wt = new VecDoub(n);
		Doub h = PI/(n-1);
		Wwghts<Quad_matrix> w = new Wwghts<Quad_matrix>(h,n, this);
		for (Int j = 0;j<n;j++)
		{
			x = j *h;
			wt = w.weights();
			Doub cx = Math.cos(x);
			for (Int k = 0;k<n;k++)
				a.argvalue[j][k]=wt[k]*cx *Math.cos(k *h);
			++a.argvalue[j][j];
		}
	}
	public final VecDoub kermom(Doub y)
	{
		Doub d = new Doub();
		Doub df = new Doub();
		Doub clog = new Doub();
		Doub x2 = new Doub();
		Doub x3 = new Doub();
		Doub x4 = new Doub();
		Doub y2 = new Doub();
		VecDoub w = new VecDoub(4);
		if (y >= x)
		{
			d = y-x;
			df = 2.0 *Math.sqrt(d)*d;
			w[0]=df/3.0;
			w[1]=df*(x/3.0+d/5.0);
			w[2]=df*((x/3.0 + 0.4 *d)*x + d *d/7.0);
			w[3]=df*(((x/3.0 + 0.6 *d)*x + 3.0 *d *d/7.0)*x+d *d *d/9.0);
		}
		else
		{
			x3 = (x2 = x *x)*x;
			x4 = x2 *x2;
			y2 = y *y;
			d = x-y;
			w[0]=d*((clog = Math.log(d))-1.0);
			w[1] = -0.25*(3.0 *x+y-2.0 *clog*(x+y))*d;
			w[2]=(-11.0 *x3+y*(6.0 *x2+y*(3.0 *x+2.0 *y)) +6.0 *clog*(x3-y *y2))/18.0;
			w[3]=(-25.0 *x4+y*(12.0 *x3+y*(6.0 *x2+y* (4.0 *x+3.0 *y)))+12.0 *clog*(x4-(y2 *y2)))/48.0;
		}
		return w;
	}
}