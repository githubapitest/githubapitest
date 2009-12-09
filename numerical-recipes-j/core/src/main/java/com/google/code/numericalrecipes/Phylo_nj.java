package com.google.code.numericalrecipes;
public class Phylo_nj implements Phylagglom
{
	public VecDoub u = new VecDoub();
	public final void premin(RefObject<MatDoub> d, RefObject<VecInt> nextp)
	{
		Int i = new Int();
		Int j = new Int();
		Int ncurr = 0;
		Doub sum = new Doub();
		for (i = 0; i>=0; i = nextp.argvalue[i])
			ncurr++;
		for (i = 0; i>=0; i = nextp.argvalue[i])
		{
			sum = 0.;
			for (j = 0; j>=0; j = nextp.argvalue[j])
				if (i != j)
					sum += d.argvalue[i][j];
			u[i] = sum/(ncurr-2);
		}
	}
	public final Doub dminfn(RefObject<MatDoub> d, Int i, Int j)
	{
		return d.argvalue[i][j] - u[i] - u[j];
	}
	public final Doub dbranchfn(RefObject<MatDoub> d, Int i, Int j)
	{
		return 0.5*(d.argvalue[i][j]+u[i]-u[j]);
	}
	public final Doub dnewfn(RefObject<MatDoub> d, Int k, Int i, Int j, Int ni, Int nj)
	{
		return 0.5*(d.argvalue[i][k] + d.argvalue[j][k] - d.argvalue[i][j]);
	}
	public final void drootbranchfn(RefObject<MatDoub> d, Int i, Int j, Int ni, Int nj, RefObject<Doub> bi, RefObject<Doub> bj)
	{
		bi.argvalue = d.argvalue[i][j]*(nj - 1 + 1.e-15)/(ni + nj -2 + 2.e-15);
		bj.argvalue = d.argvalue[i][j]*(ni - 1 + 1.e-15)/(ni + nj -2 + 2.e-15);
	}
	public Phylo_nj(MatDoub dist)
	{
		this(dist, -1);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Phylo_nj(const MatDoub &dist, Int fsr = -1) : Phylagglom(dist,fsr), u(n)
	public Phylo_nj(MatDoub dist, Int fsr)
	{
		super(GlobalMembersPhylo.dist, fsr);
		u = n;
		makethetree(GlobalMembersPhylo.dist);
	}
}