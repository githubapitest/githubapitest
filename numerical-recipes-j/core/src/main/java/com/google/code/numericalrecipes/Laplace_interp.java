package com.google.code.numericalrecipes;
public class Laplace_interp implements Linbcg
{
	public MatDoub mat;
	public Int ii = new Int();
	public Int jj = new Int();
	public Int nn = new Int();
	public Int iter = new Int();
	public VecDoub b = new VecDoub();
	public VecDoub y = new VecDoub();
	public VecDoub mask = new VecDoub();

	public Laplace_interp(RefObject<MatDoub_IO> matrix)
	{
		mat = matrix.argvalue;
		ii = mat.nrows();
		jj = mat.ncols();
		nn = ii *jj;
		iter = 0;
		b = nn;
		y = nn;
		mask = nn;
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Doub vl = 0.;
		for (k = 0;k<nn;k++)
		{
			i = k/jj;
			j = k - i *jj;
			if (mat[i][j] < 1.e99)
			{
				b[k] = y[k] = vl = mat[i][j];
				mask[k] = 1;
			}
			else
			{
				b[k] = 0.;
				y[k] = vl;
				mask[k] = 0;
			}
		}
	}

	public final void asolve(RefObject<VecDoub_I> b, RefObject<VecDoub_O> x, Int itrnsp)
	{
		Int i = new Int();
		Int n = b.argvalue.size();
		for (i = 0;i<n;i++)
			x.argvalue[i] = b.argvalue[i];
	}
	public final void atimes(RefObject<VecDoub_I> x, RefObject<VecDoub_O> r, Int itrnsp)
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Int n = r.argvalue.size();
		Int jjt = new Int();
		Int it = new Int();
		Doub del = new Doub();
		for (k = 0;k<n;k++)
			r.argvalue[k] = 0.;
		for (k = 0;k<n;k++)
		{
			i = k/jj;
			j = k - i *jj;
			if (mask[k])
			{
				r.argvalue[k] += x.argvalue[k];
			}
			else if (i>0 && i<ii-1 && j>0 && j<jj-1)
			{
				if (itrnsp != null)
				{
					r.argvalue[k] += x.argvalue[k];
					del = -0.25 *x.argvalue[k];
					r.argvalue[k-1] += del;
					r.argvalue[k+1] += del;
					r.argvalue[k-jj] += del;
					r.argvalue[k+jj] += del;
				}
				else
				{
					r.argvalue[k] = x.argvalue[k] - 0.25*(x.argvalue[k-1]+x.argvalue[k+1]+x.argvalue[k+jj]+x.argvalue[k-jj]);
				}
			}
			else if (i>0 && i<ii-1)
			{
				if (itrnsp != null)
				{
					r.argvalue[k] += x.argvalue[k];
					del = -0.5 *x.argvalue[k];
					r.argvalue[k-jj] += del;
					r.argvalue[k+jj] += del;
				}
				else
				{
					r.argvalue[k] = x.argvalue[k] - 0.5*(x.argvalue[k+jj]+x.argvalue[k-jj]);
				}
			}
			else if (j>0 && j<jj-1)
			{
				if (itrnsp != null)
				{
					r.argvalue[k] += x.argvalue[k];
					del = -0.5 *x.argvalue[k];
					r.argvalue[k-1] += del;
					r.argvalue[k+1] += del;
				}
				else
				{
					r.argvalue[k] = x.argvalue[k] - 0.5*(x.argvalue[k+1]+x.argvalue[k-1]);
				}
			}
			else
			{
				jjt = i == 0 ? jj : -jj;
				it = j == 0 ? 1 : -1;
				if (itrnsp != null)
				{
					r.argvalue[k] += x.argvalue[k];
					del = -0.5 *x.argvalue[k];
					r.argvalue[k+jjt] += del;
					r.argvalue[k+it] += del;
				}
				else
				{
					r.argvalue[k] = x.argvalue[k] - 0.5*(x.argvalue[k+jjt]+x.argvalue[k+it]);
				}
			}
		}
	}

	public final Doub solve(Doub tol)
	{
		return solve(tol, -1);
	}
	public final Doub solve()
	{
		return solve(1.e-6, -1);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Doub solve(Doub tol=1.e-6, Int itmax=-1)
	public final Doub solve(Doub tol, Int itmax)
	{
		Int i = new Int();
		Int j = new Int();
		Int k = new Int();
		Doub err = new Doub();
		if (itmax <= 0)
			itmax = 2 *MAX(ii,jj);
		Linbcg.solve(b,y,1,tol,itmax,iter,err);
		for (k = 0,i = 0;i<ii;i++)
			for (j = 0;j<jj;j++)
				mat[i][j] = y[k++];
		return err;
	}
}