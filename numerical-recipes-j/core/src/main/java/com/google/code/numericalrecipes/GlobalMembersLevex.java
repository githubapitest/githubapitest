package com.google.code.numericalrecipes;
public class GlobalMembersLevex
{
	public static Doub func(Doub x)
	{
		if (x == 0.0)
			return 0.0;
		else
		{
			Bessel bess = new Bessel();
			return x *bess.jnu(0.0,x)/(1.0+x *x);
		}
	}

	public static Int main_levex()
	{
		final Doub PI = 3.141592653589793;
		Int nterm = 12;
		Doub beta = 1.0;
		Doub a = 0.0;
		Doub b = 0.0;
		Doub sum = 0.0;
		Levin series = new Levin(100,0.0);
		System.out.printf("%5d", "N");
		System.out.printf("%19d", "Sum (direct)");
		System.out.printf("%21d", "Sum (Levin)");
		System.out.printf("%21d", "\n");
		for (Int n = 0; n<=nterm; n++)
		{
			b+=PI;
			Doub s = qromb(func,a,b,1.e-8);
			a = b;
			sum+=s;
			Doub omega = (beta+n)*s;
			Doub ans = series.next(sum,omega,beta);
			System.out.printf("%5d", n);
			System.out.printf("%21.14f", sum);
			System.out.printf("%21.14f", ans);
			System.out.printf("%21.14f", "\n");
		}
		return 0;
	}
}