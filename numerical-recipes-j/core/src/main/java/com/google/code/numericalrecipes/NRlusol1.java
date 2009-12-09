package com.google.code.numericalrecipes;
public class NRlusol
{
	public void load_col(Int col, RefObject<VecInt> row_ind, RefObject<VecDoub> val)
	{
		Int nz = row_ind.argvalue.size()-1;
		Int status = LUSOL_loadColumn(LUSOL, row_ind.argvalue[0], col, val.argvalue[0], nz, 0);
	}
}