package com.google.code.numericalrecipes;
public class GlobalMembersLinpredict
{
	public static void memcof(RefObject<VecDoub_I> data, RefObject<Doub> xms, RefObject<VecDoub_O> d)
	{
		Int k = new Int();
		Int j = new Int();
		Int i = new Int();
		Int n = data.argvalue.size();
		Int m = d.argvalue.size();
		Doub p = 0.0;
		VecDoub wk1 = new VecDoub(n);
		VecDoub wk2 = new VecDoub(n);
		VecDoub wkm = new VecDoub(m);
		for (j = 0;j<n;j++)
			p += SQR(data.argvalue[j]);
		xms.argvalue = p/n;
		wk1[0]=data.argvalue[0];
		wk2[n-2]=data.argvalue[n-1];
		for (j = 1;j<n-1;j++)
		{
			wk1[j]=data.argvalue[j];
			wk2[j-1]=data.argvalue[j];
		}
		for (k = 0;k<m;k++)
		{
			Doub num = 0.0;
			Doub denom = 0.0;
			for (j = 0;j<(n-k-1);j++)
			{
				num += (wk1[j]*wk2[j]);
				denom += (SQR(wk1[j])+SQR(wk2[j]));
			}
			d.argvalue[k]=2.0 *num/denom;
			xms.argvalue *= (1.0-SQR(d.argvalue[k]));
			for (i = 0;i<k;i++)
				d.argvalue[i]=wkm[i]-d.argvalue[k]*wkm[k-1-i];
			if (k == m-1)
				return;
			for (i = 0;i<=k;i++)
				wkm[i]=d.argvalue[i];
			for (j = 0;j<(n-k-2);j++)
			{
				wk1[j] -= (wkm[k]*wk2[j]);
				wk2[j]=wk2[j+1]-wkm[k]*wk1[j+1];
			}
		}
		throw("never get here in memcof");
	}
	public static void fixrts(RefObject<VecDoub_IO> d)
	{
		Bool polish = true;
		Int i = new Int();
		Int j = new Int();
		Int m = d.argvalue.size();
		VecComplex a = new VecComplex(m+1);
		VecComplex roots = new VecComplex(m);
		a[m]=1.0;
		for (j = 0;j<m;j++)
			a[j]= -d.argvalue[m-1-j];
		zroots(a,roots,polish);
		for (j = 0;j<m;j++)
			if (Math.abs(roots[j]) > 1.0)
				roots[j]=1.0/conj(roots[j]);
		a[0]= -roots[0];
		a[1]=1.0;
		for (j = 1;j<m;j++)
		{
			a[j+1]=1.0;
			for (i = j;i>=1;i--)
				a[i]=a[i-1]-roots[j]*a[i];
			a[0]= -roots[j]*a[0];
		}
		for (j = 0;j<m;j++)
			d.argvalue[m-1-j] = -real(a[j]);
	}
	public static void predic(RefObject<VecDoub_I> data, RefObject<VecDoub_I> d, RefObject<VecDoub_O> future)
	{
		Int k = new Int();
		Int j = new Int();
		Int ndata = data.argvalue.size();
		Int m = d.argvalue.size();
		Int nfut = future.argvalue.size();
		Doub sum = new Doub();
		Doub discrp = new Doub();
		VecDoub reg = new VecDoub(m);
		for (j = 0;j<m;j++)
			reg[j]=data.argvalue[ndata-1-j];
		for (j = 0;j<nfut;j++)
		{
			discrp = 0.0;
			sum = discrp;
			for (k = 0;k<m;k++)
				sum += d.argvalue[k]*reg[k];
			for (k = m-1;k>=1;k--)
				reg[k]=reg[k-1];
			future.argvalue[j]=reg[0]=sum;
		}
	}
	public static Doub evlmem(Doub fdt, RefObject<VecDoub_I> d, Doub xms)
	{
		Int i = new Int();
		Doub sumr = 1.0;
		Doub sumi = 0.0;
		Doub wr = 1.0;
		Doub wi = 0.0;
		Doub wpr = new Doub();
		Doub wpi = new Doub();
		Doub wtemp = new Doub();
		Doub theta = new Doub();

		Int m = d.argvalue.size();
		theta = 6.28318530717959 *fdt;
		wpr = Math.cos(theta);
		wpi = Math.sin(theta);
		for (i = 0;i<m;i++)
		{
			wr = (wtemp = wr)*wpr-wi *wpi;
			wi = wi *wpr+wtemp *wpi;
			sumr -= d.argvalue[i]*wr;
			sumi -= d.argvalue[i]*wi;
		}
		return xms/(sumr *sumr+sumi *sumi);
	}
}