package com.google.code.numericalrecipes;
public class GlobalMembersMarkovgen
{
public static void markovgen(MatDoub_I atrans, RefObject<VecInt_O> out, Int istart)
{
	markovgen(atrans, out, istart, 1);
}
public static void markovgen(MatDoub_I atrans, RefObject<VecInt_O> out)
{
	markovgen(atrans, out, 0, 1);
}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: void markovgen(const MatDoub_I &atrans, VecInt_O &out, Int istart=0, Int seed=1)
	public static void markovgen(MatDoub_I atrans, RefObject<VecInt_O> out, Int istart, Int seed)
	{
		Int i = new Int();
		Int ilo = new Int();
		Int ihi = new Int();
		Int ii = new Int();
		Int j = new Int();
		Int m = atrans.nrows();
		Int n = out.argvalue.size();
		MatDoub cum = new MatDoub(atrans);
		Doub r = new Doub();
		Ran ran = new Ran(seed);
		if (m != atrans.ncols())
			throw("transition matrix must be square");
		for (i = 0; i<m; i++)
		{
			for (j = 1; j<m; j++)
				cum[i][j] += cum[i][j-1];
			if (Math.abs(cum[i][m-1]-1.) > 0.01)
				throw("transition matrix rows must sum to 1");
		}
		j = istart;
		out.argvalue[0] = j;
		for (ii = 1; ii<n; ii++)
		{
			r = ran.doub()/cum[j][m-1];
			ilo = 0;
			ihi = m;
			while (ihi-ilo > 1)
			{
				i = (ihi+ilo) >> 1;
				if (r>cum[j][i-1])
					ilo = i;
				else
					ihi = i;
			}
			out.argvalue[ii] = j = ilo;
		}
	}
}