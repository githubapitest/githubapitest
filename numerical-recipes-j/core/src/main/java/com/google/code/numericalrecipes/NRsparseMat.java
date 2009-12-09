package com.google.code.numericalrecipes;
public class NRsparseMat
{
	public Int nrows = new Int();
	public Int ncols = new Int();
	public Int nvals = new Int();
	public VecInt col_ptr = new VecInt();
	public VecInt row_ind = new VecInt();
	public VecDoub val = new VecDoub();

	public NRsparseMat()
	{
		nrows = 0;
		ncols = 0;
		nvals = 0;
		super();
		super();
		super();
	}
	public NRsparseMat(Int m, Int n, Int nnvals)
	{
		nrows = m;
		ncols = n;
		nvals = nnvals;
		col_ptr = new VecInt(n+1,0);
		row_ind = new VecInt(nnvals,0);
		val = new VecDoub(nnvals,0.0);
	}
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: VecDoub ax(const VecDoub &x) const
	public final VecDoub ax(VecDoub x)
	{
		VecDoub y = new VecDoub(nrows,0.0);
		for (Int j = 0;j<ncols;j++)
		{
			for (Int i = col_ptr[j];i<col_ptr[j+1];i++)
				y[row_ind[i]] += val[i]*x[j];
		}
		return y;
	}
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: VecDoub atx(const VecDoub &x) const
	public final VecDoub atx(VecDoub x)
	{
		VecDoub y = new VecDoub(ncols);
		for (Int i = 0;i<ncols;i++)
		{
			y[i]=0.0;
			for (Int j = col_ptr[i];j<col_ptr[i+1];j++)
				y[i] += val[j]*x[row_ind[j]];
		}
		return y;
	}
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: NRsparseMat transpose() const
	public final NRsparseMat transpose()
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int index = new Int();
		Int m = nrows;
		Int n = ncols;
		NRsparseMat at = new NRsparseMat(n, m, nvals);
		VecInt count = new VecInt(m,0);
		for (i = 0;i<n;i++)
			for (j = col_ptr[i];j<col_ptr[i+1];j++)
			{
				k = row_ind[j];
				count[k]++;
			}
		for (j = 0;j<m;j++)
			at.col_ptr[j+1]=at.col_ptr[j]+count[j];
		for(j = 0;j<m;j++)
			count[j]=0;
		for (i = 0;i<n;i++)
			for (j = col_ptr[i];j<col_ptr[i+1];j++)
			{
				k = row_ind[j];
				index = at.col_ptr[k]+count[k];
				at.row_ind[index]=i;
				at.val[index]=val[j];
				count[k]++;
			}
		return at;
	}
}