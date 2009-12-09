package com.google.code.numericalrecipes;
public class GlobalMembersQuasinewton
{
	public static <T> void dfpmin(RefObject<VecDoub_IO> p, Doub gtol, RefObject<Int> iter, RefObject<Doub> fret, RefObject<T> funcd)
	{
		final Int ITMAX = 200;
		final Doub EPS = numeric_limits<Doub>.epsilon();
		final Doub TOLX = 4 *EPS;
		final Doub STPMX = 100.0;
		Bool check = new Bool();
		Doub den = new Doub();
		Doub fac = new Doub();
		Doub fad = new Doub();
		Doub fae = new Doub();
		Doub fp = new Doub();
		Doub stpmax = new Doub();
		Doub sum = 0.0;
		Doub sumdg = new Doub();
		Doub sumxi = new Doub();
		Doub temp = new Doub();
		Doub test = new Doub();
		Int n = p.argvalue.size();
		VecDoub dg = new VecDoub(n);
		VecDoub g = new VecDoub(n);
		VecDoub hdg = new VecDoub(n);
		VecDoub pnew = new VecDoub(n);
		VecDoub xi = new VecDoub(n);
		MatDoub hessin = new MatDoub(n,n);
		fp = funcd.argvalue(p.argvalue);
		funcd.argvalue.df(p.argvalue,g);
		for (Int i = 0;i<n;i++)
		{
			for (Int j = 0;j<n;j++)
				hessin[i][j]=0.0;
			hessin[i][i]=1.0;
			xi[i] = -g[i];
			sum += p.argvalue[i]*p.argvalue[i];
		}
		stpmax = STPMX *MAX(Math.sqrt(sum),Doub(n));
		for (Int its = 0;its<ITMAX;its++)
		{
			iter.argvalue = its;
			lnsrch(p.argvalue,fp,g,xi,pnew,fret.argvalue,stpmax,check,funcd.argvalue);
			fp = fret.argvalue;
			for (Int i = 0;i<n;i++)
			{
				xi[i]=pnew[i]-p.argvalue[i];
				p.argvalue[i]=pnew[i];
			}
			test = 0.0;
			for (Int i = 0;i<n;i++)
			{
				temp = Math.abs(xi[i])/MAX(Math.abs(p.argvalue[i]),1.0);
				if (temp > test)
					test = temp;
			}
			if (test < TOLX)
				return;
			for (Int i = 0;i<n;i++)
				dg[i]=g[i];
			funcd.argvalue.df(p.argvalue,g);
			test = 0.0;
			den = MAX(fret.argvalue,1.0);
			for (Int i = 0;i<n;i++)
			{
				temp = Math.abs(g[i])*MAX(Math.abs(p.argvalue[i]),1.0)/den;
				if (temp > test)
					test = temp;
			}
			if (test < gtol)
				return;
			for (Int i = 0;i<n;i++)
				dg[i]=g[i]-dg[i];
			for (Int i = 0;i<n;i++)
			{
				hdg[i]=0.0;
				for (Int j = 0;j<n;j++)
					hdg[i] += hessin[i][j]*dg[j];
			}
			fac = fae = sumdg = sumxi = 0.0;
			for (Int i = 0;i<n;i++)
			{
				fac += dg[i]*xi[i];
				fae += dg[i]*hdg[i];
				sumdg += SQR(dg[i]);
				sumxi += SQR(xi[i]);
			}
			if (fac > Math.sqrt(EPS *sumdg *sumxi))
			{
				fac = 1.0/fac;
				fad = 1.0/fae;
				for (Int i = 0;i<n;i++)
					dg[i]=fac *xi[i]-fad *hdg[i];
				for (Int i = 0;i<n;i++)
				{
					for (Int j = i;j<n;j++)
					{
						hessin[i][j] += fac *xi[i]*xi[j] -fad *hdg[i]*hdg[j]+fae *dg[i]*dg[j];
						hessin[j][i]=hessin[i][j];
					}
				}
			}
			for (Int i = 0;i<n;i++)
			{
				xi[i]=0.0;
				for (Int j = 0;j<n;j++)
					xi[i] -= hessin[i][j]*g[j];
			}
		}
		throw("too many iterations in dfpmin");
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
}