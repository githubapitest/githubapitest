package com.google.code.numericalrecipes;
public class Difeq
{
	public final Int mm;
	public final Int n;
	public final Int mpt;
	public final Doub h;
	public final Doub c2;
	public final Doub anorm;
	public final VecDoub x;
	public Difeq(Int mmm, Int nn, Int mptt, Doub hh, Doub cc2, Doub anormm, RefObject<VecDoub_I> xx)
	{
		mm = mmm;
		n = nn;
		mpt = mptt;
		h = hh;
		c2 = cc2;
		anorm = anormm;
		x = xx.argvalue;
	}

	public final void smatrix(Int k, Int k1, Int k2, Int jsf, Int is1, Int isf, RefObject<VecInt_I> indexv, RefObject<MatDoub_O> s, RefObject<MatDoub_I> y)
	{
		Doub temp = new Doub();
		Doub temp1 = new Doub();
		Doub temp2 = new Doub();

		if (k == k1)
		{
			if ((n+mm & 1) != 0)
			{
				s.argvalue[2][3+indexv.argvalue[0]]=1.0;
				s.argvalue[2][3+indexv.argvalue[1]]=0.0;
				s.argvalue[2][3+indexv.argvalue[2]]=0.0;
				s.argvalue[2][jsf]=y.argvalue[0][0];
			}
			else
			{
				s.argvalue[2][3+indexv.argvalue[0]]=0.0;
				s.argvalue[2][3+indexv.argvalue[1]]=1.0;
				s.argvalue[2][3+indexv.argvalue[2]]=0.0;
				s.argvalue[2][jsf]=y.argvalue[1][0];
			}
		}
		else if (k > k2-1)
		{
			s.argvalue[0][3+indexv.argvalue[0]] = -(y.argvalue[2][mpt-1]-c2)/(2.0*(mm+1.0));
			s.argvalue[0][3+indexv.argvalue[1]]=1.0;
			s.argvalue[0][3+indexv.argvalue[2]] = -y.argvalue[0][mpt-1]/(2.0*(mm+1.0));
			s.argvalue[0][jsf]=y.argvalue[1][mpt-1]-(y.argvalue[2][mpt-1]-c2)*y.argvalue[0][mpt-1]/ (2.0*(mm+1.0));
			s.argvalue[1][3+indexv.argvalue[0]]=1.0;
			s.argvalue[1][3+indexv.argvalue[1]]=0.0;
			s.argvalue[1][3+indexv.argvalue[2]]=0.0;
			s.argvalue[1][jsf]=y.argvalue[0][mpt-1]-anorm;
		}
		else
		{
			s.argvalue[0][indexv.argvalue[0]] = -1.0;
			s.argvalue[0][indexv.argvalue[1]] = -0.5 *h;
			s.argvalue[0][indexv.argvalue[2]]=0.0;
			s.argvalue[0][3+indexv.argvalue[0]]=1.0;
			s.argvalue[0][3+indexv.argvalue[1]] = -0.5 *h;
			s.argvalue[0][3+indexv.argvalue[2]]=0.0;
			temp1 = x[k]+x[k-1];
			temp = h/(1.0-temp1 *temp1 *0.25);
			temp2 = 0.5*(y.argvalue[2][k]+y.argvalue[2][k-1])-c2 *0.25 *temp1 *temp1;
			s.argvalue[1][indexv.argvalue[0]]=temp *temp2 *0.5;
			s.argvalue[1][indexv.argvalue[1]] = -1.0-0.5 *temp*(mm+1.0)*temp1;
			s.argvalue[1][indexv.argvalue[2]]=0.25 *temp*(y.argvalue[0][k]+y.argvalue[0][k-1]);
			s.argvalue[1][3+indexv.argvalue[0]]=s.argvalue[1][indexv.argvalue[0]];
			s.argvalue[1][3+indexv.argvalue[1]]=2.0+s.argvalue[1][indexv.argvalue[1]];
			s.argvalue[1][3+indexv.argvalue[2]]=s.argvalue[1][indexv.argvalue[2]];
			s.argvalue[2][indexv.argvalue[0]]=0.0;
			s.argvalue[2][indexv.argvalue[1]]=0.0;
			s.argvalue[2][indexv.argvalue[2]] = -1.0;
			s.argvalue[2][3+indexv.argvalue[0]]=0.0;
			s.argvalue[2][3+indexv.argvalue[1]]=0.0;
			s.argvalue[2][3+indexv.argvalue[2]]=1.0;
			s.argvalue[0][jsf]=y.argvalue[0][k]-y.argvalue[0][k-1]-0.5 *h*(y.argvalue[1][k]+y.argvalue[1][k-1]);
			s.argvalue[1][jsf]=y.argvalue[1][k]-y.argvalue[1][k-1]-temp*((x[k]+x[k-1]) *0.5*(mm+1.0)*(y.argvalue[1][k]+y.argvalue[1][k-1])-temp2 *0.5*(y.argvalue[0][k]+y.argvalue[0][k-1]));
			s.argvalue[2][jsf]=y.argvalue[2][k]-y.argvalue[2][k-1];
		}
	}
}