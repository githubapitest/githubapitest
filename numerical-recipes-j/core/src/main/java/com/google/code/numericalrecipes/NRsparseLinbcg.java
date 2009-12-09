package com.google.code.numericalrecipes;
public class NRsparseLinbcg implements Linbcg
{
	public NRsparseMat mat;
	public Int n = new Int();
	public NRsparseLinbcg(RefObject<NRsparseMat> matrix)
	{
		mat = matrix.argvalue;
		n = mat.nrows;
	}
	public final void atimes(RefObject<VecDoub_I> x, RefObject<VecDoub_O> r, Int itrnsp)
	{
		if (itrnsp != null)
			r.argvalue = mat.atx(x.argvalue);
		else
			r.argvalue = mat.ax(x.argvalue);
	}
	public final void asolve(RefObject<VecDoub_I> b, RefObject<VecDoub_O> x, Int itrnsp)
	{
		Int i = new Int();
		Int j = new Int();
		Doub diag = new Doub();
		for (i = 0;i<n;i++)
		{
			diag = 0.0;
			for (j = mat.col_ptr[i];j<mat.col_ptr[i+1];j++)
				if (mat.row_ind[j] == i)
				{
					diag = mat.val[j];
					break;
				}
			x.argvalue[i]=(diag != 0.0 ? b.argvalue[i]/diag : b.argvalue[i]);
		}
	}
}