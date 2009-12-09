package com.google.code.numericalrecipes;
public class GlobalMembersKsdist
{
	public static Doub invxlogx(Doub y)
	{
		final Doub ooe = 0.367879441171442322;
		Doub t = new Doub();
		Doub u = new Doub();
		Doub to = 0.;
		if (y >= 0.|| y <= -ooe)
			throw("no such inverse value");
		 if (y < -0.2)
			 u = Math.log(ooe-Math.sqrt(2 *ooe*(y+ooe)));
		else
			u = -10.;
		do
		{
			u += (t = (Math.log(y/u)-u)*(u/(1.+u)));
			if (t < 1.e-8 && Math.abs(t+to)<0.01 *Math.abs(t))
				break;
			to = t;
		} while (Math.abs(t/u) > 1.e-15);
		return Math.exp(u);
	}
}