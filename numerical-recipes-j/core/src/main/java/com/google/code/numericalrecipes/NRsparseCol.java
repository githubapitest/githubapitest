package com.google.code.numericalrecipes;
public class NRsparseCol
{
	public Int nrows = new Int();
	public Int nvals = new Int();
	public VecInt row_ind = new VecInt();
	public VecDoub val = new VecDoub();

	public NRsparseCol(Int m,Int nnvals)
	{
		nrows = m;
		nvals = nnvals;
		row_ind = new VecInt(nnvals,0);
		val = new VecDoub(nnvals,0.0);
	}

	public NRsparseCol()
	{
		nrows = 0;
		nvals = 0;
		super();
		super();
	}

	public final void resize(Int m, Int nnvals)
	{
		nrows = m;
		nvals = nnvals;
		row_ind.assign(nnvals,0);
		val.assign(nnvals,0.0);
	}

}