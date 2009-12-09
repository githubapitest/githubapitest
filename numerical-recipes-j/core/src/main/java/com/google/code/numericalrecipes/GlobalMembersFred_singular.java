package com.google.code.numericalrecipes;
public class GlobalMembersFred_singular
{
	public static Int main_fredex()
	{
		final Int N = 40;
		final Doub PI = 3.141592653589793238;
		VecDoub g = new VecDoub(N);
		MatDoub a = new MatDoub(N,N);
		Quad_matrix qmx = new Quad_matrix(a);
		LUdcmp alu = new LUdcmp(a);
		for (Int j = 0;j<N;j++)
			g[j]=Math.sin(j *PI/(N-1));
		alu.solve(g,g);
		for (Int j = 0;j<N;j++)
		{
			Doub x = j *PI/(N-1);
			System.out.printf("%6.2f", (j+1));
			System.out.printf("%13.6f", x);
			System.out.printf("%13.6f", g[j]);
			System.out.printf("%13.6f", "\n");
		}
		return 0;
	}
}