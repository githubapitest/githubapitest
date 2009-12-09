package com.google.code.numericalrecipes;
public class GlobalMembersRatlsq
{
	public static Ratfn ratlsq(Doub fn(Doub), Doub a, Doub b, Int mm, Int kk, RefObject<Doub> dev)
	{
		final Int NPFAC = 8;
		final Int MAXIT = 5;
		final Doub BIG = 1.0e99;
		final Doub PIO2 = 1.570796326794896619;
		Int i = new Int();
		Int it = new Int();
		Int j = new Int();
		Int ncof = mm+kk+1;
		Int npt = NPFAC *ncof;
		Doub devmax = new Doub();
		Doub e = new Doub();
		Doub hth = new Doub();
		Doub power = new Doub();
		Doub sum = new Doub();
		VecDoub bb = new VecDoub(npt);
		VecDoub coff = new VecDoub(ncof);
		VecDoub ee = new VecDoub(npt);
		VecDoub fs = new VecDoub(npt);
		VecDoub wt = new VecDoub(npt);
		VecDoub xs = new VecDoub(npt);
		MatDoub u = new MatDoub(npt,ncof);
		Ratfn ratbest = new Ratfn(coff,mm+1,kk+1);
		dev.argvalue = BIG;
		for (i = 0;i<npt;i++)
		{
			if (i < (npt/2)-1)
			{
				hth = PIO2 *i/(npt-1.0);
				xs[i]=a+(b-a)*SQR(Math.sin(hth));
			}
			else
			{
				hth = PIO2*(npt-i)/(npt-1.0);
				xs[i]=b-(b-a)*SQR(Math.sin(hth));
			}
			fs[i]=fn(xs[i]);
			wt[i]=1.0;
			ee[i]=1.0;
		}
		e = 0.0;
		for (it = 0;it<MAXIT;it++)
		{
			for (i = 0;i<npt;i++)
			{
				power = wt[i];
				bb[i]=power*(fs[i]+SIGN(e,ee[i]));
				for (j = 0;j<mm+1;j++)
				{
					u[i][j]=power;
					power *= xs[i];
				}
				power = -bb[i];
				for (j = mm+1;j<ncof;j++)
				{
					power *= xs[i];
					u[i][j]=power;
				}
			}
			SVD svd = new SVD(u);
			svd.solve(bb,coff);
			devmax = sum = 0.0;
			Ratfn rat = new Ratfn(coff,mm+1,kk+1);
			for (j = 0;j<npt;j++)
			{
				ee[j]=rat(xs[j])-fs[j];
				wt[j]=Math.abs(ee[j]);
				sum += wt[j];
				if (wt[j] > devmax)
					devmax = wt[j];
			}
			e = sum/npt;
			if (devmax <= dev.argvalue)
			{
				ratbest = rat;
				dev.argvalue = devmax;
			}
			System.out.print(" ratlsq iteration= ");
			System.out.print(it);
			System.out.print("  max error= ");
			System.out.printf("%10d", devmax);
			System.out.printf("%10d", "\n");
		}
		return ratbest;
	}
}