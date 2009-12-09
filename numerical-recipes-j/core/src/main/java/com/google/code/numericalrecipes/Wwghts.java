package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class Q>
public class Wwghts<Q>
{
	public Doub h = new Doub();
	public Int n = new Int();
	public Q quad;
	public VecDoub wghts = new VecDoub();
	public Wwghts(Doub hh, Int nn, RefObject<Q> q)
	{
		h = hh;
		n = nn;
		quad = q.argvalue;
		wghts = n;
	}
	public final VecDoub weights()
	{
		Int k = new Int();
		Doub fac = new Doub();
		Doub hi = 1.0/h;
		for (Int j = 0;j<n;j++)
			wghts[j]=0.0;
		if (n >= 4)
		{
			VecDoub wold = new VecDoub(4);
			VecDoub wnew = new VecDoub(4);
			VecDoub w = new VecDoub(4);
			wold = quad.kermom(0.0);
			Doub b = 0.0;
			for (Int j = 0;j<n-3;j++)
			{
				Doub c = j;
				Doub a = b;
				b = a+h;
				if (j == n-4)
					b = (n-1)*h;
				wnew = quad.kermom(b);
				for (fac = 1.0,k = 0;k<4;k++,fac*=hi)
					w[k]=(wnew[k]-wold[k])*fac;
				wghts[j] += (((c+1.0)*(c+2.0)*(c+3.0)*w[0] -(11.0+c*(12.0+c *3.0))*w[1]+3.0*(c+2.0)*w[2]-w[3])/6.0);
				wghts[j+1] += ((-c*(c+2.0)*(c+3.0)*w[0] +(6.0+c*(10.0+c *3.0))*w[1]-(3.0 *c+5.0)*w[2]+w[3])*0.5);
				wghts[j+2] += ((c*(c+1.0)*(c+3.0)*w[0] -(3.0+c*(8.0+c *3.0))*w[1]+(3.0 *c+4.0)*w[2]-w[3])*0.5);
				wghts[j+3] += ((-c*(c+1.0)*(c+2.0)*w[0] +(2.0+c*(6.0+c *3.0))*w[1]-3.0*(c+1.0)*w[2]+w[3])/6.0);
				for (k = 0;k<4;k++)
					wold[k]=wnew[k];
			}
		}
		else if (n == 3)
		{
			VecDoub wold = new VecDoub(3);
			VecDoub wnew = new VecDoub(3);
			VecDoub w = new VecDoub(3);
			wold = quad.kermom(0.0);
			wnew = quad.kermom(h+h);
			w[0]=wnew[0]-wold[0];
			w[1]=hi*(wnew[1]-wold[1]);
			w[2]=hi *hi*(wnew[2]-wold[2]);
			wghts[0]=w[0]-1.5 *w[1]+0.5 *w[2];
			wghts[1]=2.0 *w[1]-w[2];
			wghts[2]=0.5*(w[2]-w[1]);
		}
		else if (n == 2)
		{
			VecDoub wold = new VecDoub(2);
			VecDoub wnew = new VecDoub(2);
			VecDoub w = new VecDoub(2);
			wold = quad.kermom(0.0);
			wnew = quad.kermom(h);
			wghts[0]=wnew[0]-wold[0]-(wghts[1]=hi*(wnew[1]-wold[1]));
		}
		return wghts;
	}
}