package com.google.code.numericalrecipes;
public class GlobalMembersStochsim
{
	public static void sparmatfill(RefObject<NRvector<NRsparseCol>> sparmat, RefObject<MatDoub> fullmat)
	{
		Int n = new Int();
		Int m = new Int();
		Int nz = new Int();
		Int nn = fullmat.argvalue.nrows();
		Int mm = fullmat.argvalue.ncols();
		if (sparmat.argvalue.size() != mm)
			throw("bad sizes");
		for (m = 0;m<mm;m++)
		{
			for (nz = n = 0;n<nn;n++)
				if (fullmat.argvalue[n][m])
					nz++;
			sparmat.argvalue[m].resize(nn,nz);
			for (nz = n = 0;n<nn;n++)
				if (fullmat.argvalue[n][m])
				{
				sparmat.argvalue[m].row_ind[nz] = n;
				sparmat.argvalue[m].val[nz++] = fullmat.argvalue[n][m];
			}
		}
	}
}