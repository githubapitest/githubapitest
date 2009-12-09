package com.google.code.numericalrecipes;
public class GlobalMembersDynpro
{
	public static VecInt dynpro(VecInt nstate, Doub cost(Int jj, Int kk, Int ii))
	{
		final Doub BIG = 1.e99;
		final Doub EPS = numeric_limits<Doub>.epsilon();
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int nstage = nstate.size() - 1;
		Doub a = new Doub();
		Doub b = new Doub();
		VecInt answer = new VecInt(nstage+1);
		if (nstate[0] != 1 || nstate[nstage] != 1)
			throw("One state allowed in first and last stages.");
		Doub[] best = new Doub[nstage+1];
		best[0] = new Doub[nstate[0]];
		best[0][0] = 0.;
		for (i = 1; i<=nstage; i++)
		{
			best[i] = new Doub[nstate[i]];
			for (k = 0; k<nstate[i]; k++)
			{
				b = BIG;
				for (j = 0; j<nstate[i-1]; j++)
				{
					if ((a = best[i-1][j] + cost(j,k,i-1)) < b)
						b = a;
				}
				best[i][k] = b;
			}
		}
		answer[nstage] = answer[0] = 0;
		for (i = nstage-1; i>0; i--)
		{
			k = answer[i+1];
			b = best[i+1][k];
			for (j = 0; j<nstate[i]; j++)
			{
				Doub temp = best[i][j] + cost(j,k,i);
				if (Math.abs(b - temp) <= EPS *Math.abs(temp))
					break;
			}
			answer[i] = j;
		}
		for (i = nstage; i>=0; i--)
			best[i] = null;
		best = null;
		return answer;
	}
}