package com.google.code.numericalrecipes;
public class GlobalMembersSor
{
	public static void sor(RefObject<MatDoub_I> a, RefObject<MatDoub_I> b, RefObject<MatDoub_I> c, RefObject<MatDoub_I> d, RefObject<MatDoub_I> e, RefObject<MatDoub_I> f, RefObject<MatDoub_IO> u, Doub rjac)
	{
		final Int MAXITS = 1000;
		final Doub EPS = 1.0e-13;
		Doub anormf = 0.0;
		Doub omega = 1.0;
		Int jmax = a.argvalue.nrows();
		for (Int j = 1;j<jmax-1;j++)
			for (Int l = 1;l<jmax-1;l++)
				anormf += Math.abs(f.argvalue[j][l]);
		for (Int n = 0;n<MAXITS;n++)
		{
			Doub anorm = 0.0;
			Int jsw = 1;
			for (Int ipass = 0;ipass<2;ipass++)
			{
				Int lsw = jsw;
				for (Int j = 1;j<jmax-1;j++)
				{
					for (Int l = lsw;l<jmax-1;l+=2)
					{
						Doub resid = a.argvalue[j][l]*u.argvalue[j+1][l]+b.argvalue[j][l]*u.argvalue[j-1][l] +c.argvalue[j][l]*u.argvalue[j][l+1]+d.argvalue[j][l]*u.argvalue[j][l-1] +e.argvalue[j][l]*u.argvalue[j][l]-f.argvalue[j][l];
						anorm += Math.abs(resid);
						u.argvalue[j][l] -= omega *resid/e.argvalue[j][l];
					}
					lsw = 3-lsw;
				}
				jsw = 3-jsw;
				omega = (n == 0 && ipass == 0 ? 1.0/(1.0-0.5 *rjac *rjac) : 1.0/(1.0-0.25 *rjac *rjac *omega));
			}
			if (anorm < EPS *anormf)
				return;
		}
		throw("MAXITS exceeded");
	}
}