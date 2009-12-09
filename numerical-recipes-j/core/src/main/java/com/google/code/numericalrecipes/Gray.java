package com.google.code.numericalrecipes;
public class Gray
{

	public final Uint gray(Uint n)
	{
		return n ^ (n >> 1);
	}

	public final Uint invgray(Uint n)
	{
		Int ish = 1;
		Uint ans = n;
		Uint idiv = new Uint();
		for (;;)
		{
			ans ^= (idiv = ans >> ish);
			if (idiv <= 1 || ish == 16)
				return ans;
			ish <<= 1;
		}
	}
}