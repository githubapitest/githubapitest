package com.google.code.numericalrecipes;
public class GlobalMembersSphoot
{

	public static Int main_sphoot()
	{
		final Int N2 = 1;
		final Int MM = 3;
		Bool check = new Bool();
		VecDoub v = new VecDoub(N2);
		Int j = new Int();
		Int m = 3;
		Int n = 5;
		Doub[] c2 = {1.5,-1.5,0.0};
		Int nvar = 3;
		Doub dx = 1.0e-8;
		for (j = 0;j<MM;j++)
		{
			Doub gmma = 1.0;
			Doub q1 = n;
			for (Int i = 1;i<=m;i++)
				gmma *= -0.5*(n+i)*(q1--/i);
			v[0] =n*(n+1)-m*(m+1)+c2[j]/2.0;
			Doub x1 = -1.0+dx;
			Doub x2 = 0.0;
			Load[] load = new Load[j](n,m,gmma,c2,dx);
			Rhs[] d = new Rhs[j](m,c2);
			Score score = new Score(n,m);
			Shoot<Load,Rhs,Score> shoot = new Shoot<Load,Rhs,Score>(nvar,x1,x2,load,d,score);
			newt(v,check,shoot);
			if (check != null)
			{
				System.out.print("shoot failed; bad initial guess");
				System.out.print("\n");
			}
			else
			{
				System.out.print("    ");
				System.out.print("mu(m,n)");
				System.out.print("\n");
				System.out.printf("%12.6f", v[0]);
				System.out.printf("%12.6f", "\n");
			}
		}
		return 0;
	}
}