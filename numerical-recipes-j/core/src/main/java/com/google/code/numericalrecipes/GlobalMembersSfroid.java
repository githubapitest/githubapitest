package com.google.code.numericalrecipes;
public class GlobalMembersSfroid
{
	public static Int main_sfroid()
	{
		final Int M = 40;
		final Int MM = 4;
		final Int NE = 3;
		final Int NB = 1;
		final Int NYJ = NE;
		final Int NYK = M+1;
		Int mm = 3;
		Int n = 5;
		Int mpt = M+1;
		VecInt indexv = new VecInt(NE);
		VecDoub x = new VecDoub(M+1);
		VecDoub scalv = new VecDoub(NE);
		MatDoub y = new MatDoub(NYJ,NYK);
		Int itmax = 100;
		Doub[] c2 = {16.0,20.0,-16.0,-20.0};
		Doub conv = 1.0e-14;
		Doub slowc = 1.0;
		Doub h = 1.0/M;
		if ((n+mm & 1) != 0)
		{
			indexv[0]=0;
			indexv[1]=1;
			indexv[2]=2;
		}
		else
		{
			indexv[0]=1;
			indexv[1]=0;
			indexv[2]=2;
		}
		Doub anorm = 1.0;
		if (mm != 0)
		{
			Doub q1 = n;
			for (Int i = 1;i<=mm;i++)
				anorm = -0.5 *anorm*(n+i)*(q1--/i);
		}
		for (Int k = 0;k<M;k++)
		{
			x[k]=k *h;
			Doub fac1 = 1.0-x[k]*x[k];
			Doub fac2 = Math.exp((-mm/2.0)*Math.log(fac1));
			y[0][k]=plgndr(n,mm,x[k])*fac2;
			Doub deriv = -((n-mm+1)*plgndr(n+1,mm,x[k])- (n+1)*x[k]*plgndr(n,mm,x[k]))/fac1;
			y[1][k]=mm *x[k]*y[0][k]/fac1+deriv *fac2;
			y[2][k]=n*(n+1)-mm*(mm+1);
		}
		x[M]=1.0;
		y[0][M]=anorm;
		y[2][M]=n*(n+1)-mm*(mm+1);
		y[1][M]=y[2][M]*y[0][M]/(2.0*(mm+1.0));
		scalv[0]=Math.abs(anorm);
		scalv[1]=(y[1][M] > scalv[0] ? y[1][M] : scalv[0]);
		scalv[2]=(y[2][M] > 1.0 ? y[2][M] : 1.0);
		for (Int j = 0;j<MM;j++)
		{
			Difeq[] difeq = new Difeq[j](mm,n,mpt,h,c2,anorm,x);
			Solvde solvde = new Solvde(itmax,conv,slowc,scalv,indexv,NB,y,difeq);
			System.out.print("\n");
			System.out.print(" m = ");
			System.out.printf("%3d", mm);
			System.out.printf("%3d", "  n = ");
			System.out.printf("%3d", n);
			System.out.printf("%3d", "  c**2 = ");
			System.out.printf("%7.3f", c2[j]);
			System.out.printf("%7.3f", " lamda = ");
			System.out.printf("%7.6f", (y[2][0]+mm*(mm+1)));
			System.out.printf("%7.6f", "\n");
		}
		return 0;
	}
}