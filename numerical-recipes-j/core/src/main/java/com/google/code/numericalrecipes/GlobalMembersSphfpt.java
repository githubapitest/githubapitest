package com.google.code.numericalrecipes;
public class GlobalMembersSphfpt
{

	public static Int main_sphfpt()
	{
		final Int N1 = 2;
		final Int N2 = 1;
		final Int NTOT = N1+N2;
		final Int MM = 3;
		Bool check = new Bool();
		VecDoub v = new VecDoub(NTOT);
		Int j = new Int();
		Int m = 3;
		Int n = 5;
		Int n2 = N2;
		Doub[] c2 = {1.5,-1.5,0.0};
		Int nvar = NTOT;
		Doub dx = 1.0e-8;
		for (j = 0;j<MM;j++)
		{
			Doub gmma = 1.0;
			Doub q1 = n;
			for (Int i = 1;i<=m;i++)
				gmma *= -0.5*(n+i)*(q1--/i);
			v[0] =n*(n+1)-m*(m+1)+c2[j]/2.0;
			v[2]=v[0];
			v[1] =gmma*(1.0-(v[2]-c2[j])*dx/(2*(m+1)));
			Doub x1 = -1.0+dx;
			Doub x2 = 1.0-dx;
			Doub xf = 0.0;
			Load1[] load1 = new Load1[j](n,m,gmma,c2,dx);
			Load2[] load2 = new Load2[j](m,c2);
			Rhsfpt[] d = new Rhsfpt[j](m,c2);
			Score score = new Score();
			Shootf<Load1,Load2,Rhsfpt,Score> shootf = new Shootf<Load1,Load2,Rhsfpt,Score>(nvar,n2,x1,x2,xf,load1, load2,d,score);
			newt(v,check,shootf);
			if (check != null)
			{
				System.out.print("shootf failed; bad initial guess");
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