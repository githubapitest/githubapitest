package com.google.code.numericalrecipes;
public class ADAT
{
	public final NRsparseMat a;
	public final NRsparseMat at;
	public NRsparseMat adat;

	public ADAT(NRsparseMat A, NRsparseMat AT)
	{
		a = new NRsparseMat(A);
		at = new NRsparseMat(AT);
		Int h = new Int();
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int l = new Int();
		Int nvals = new Int();
		Int m = AT.ncols;
		VecInt done = new VecInt(m);
		for (i = 0;i<m;i++)
			done[i]=-1;
		nvals = 0;
		for (j = 0;j<m;j++)
		{
			for (i = AT.col_ptr[j];i<AT.col_ptr[j+1];i++)
			{
				k = AT.row_ind[i];
				for (l = A.col_ptr[k];l<A.col_ptr[k+1];l++)
				{
					h = A.row_ind[l];
					if (done[h] != j)
					{
						done[h]=j;
						nvals++;
					}
				}
			}
		}
		adat = new NRsparseMat(m,m,nvals);
		for (i = 0;i<m;i++)
			done[i]=-1;
		nvals = 0;
		for (j = 0;j<m;j++)
		{
			adat.col_ptr[j]=nvals;
			for (i = AT.col_ptr[j];i<AT.col_ptr[j+1];i++)
			{
				k = AT.row_ind[i];
				for (l = A.col_ptr[k];l<A.col_ptr[k+1];l++)
				{
					h = A.row_ind[l];
					if (done[h] != j)
					{
						done[h]=j;
						adat.row_ind[nvals]=h;
						nvals++;
					}
				}
			}
		}
		adat.col_ptr[m]=nvals;
		for (j = 0;j<m;j++)
		{
			i = adat.col_ptr[j];
			Int size = adat.col_ptr[j+1]-i;
			if (size > 1)
			{
				VecInt[] col = new VecInt[i](size, adat.row_ind);
				GlobalMembersSparse.sort(col);
				for (k = 0;k<size;k++)
					adat.row_ind[i+k] =col[k];
			}
		}
	}
	public final void updateD(VecDoub D)
	{
		Int h = new Int();
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int l = new Int();
		Int m = a.nrows;
		Int n = a.ncols;
		VecDoub temp = new VecDoub(n);
		VecDoub temp2 = new VecDoub(m,0.0);
		for (i = 0;i<m;i++)
		{
			for (j = at.col_ptr[i];j< at.col_ptr[i+1];j++)
			{
				k = at.row_ind[j];
				temp[k]=at.val[j]*D[k];
			}
			for (j = at.col_ptr[i];j<at.col_ptr[i+1];j++)
			{
				k = at.row_ind[j];
				for (l = a.col_ptr[k];l<a.col_ptr[k+1];l++)
				{
					h = a.row_ind[l];
					temp2[h] += temp[k]*a.val[l];
				}
			}
			for (j = adat.col_ptr[i];j<adat.col_ptr[i+1];j++)
			{
				k = adat.row_ind[j];
				adat.val[j]=temp2[k];
				temp2[k]=0.0;
			}
		}
	}
	public final NRsparseMat  ref()
	{
		return adat;
	}
	public void Dispose()
	{
		adat.Dispose();
	}
}