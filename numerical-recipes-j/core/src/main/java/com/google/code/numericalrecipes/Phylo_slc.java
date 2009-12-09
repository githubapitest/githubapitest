package com.google.code.numericalrecipes;
public class Phylo_slc implements Phylagglom
{
	public final void premin(RefObject<MatDoub> d, RefObject<VecInt> nextp)
	{
	}
	public final Doub dminfn(RefObject<MatDoub> d, Int i, Int j)
	{
		return d.argvalue[i][j];
	}
	public final Doub dbranchfn(RefObject<MatDoub> d, Int i, Int j)
	{
		return 0.5 *d.argvalue[i][j];
	}
	public final Doub dnewfn(RefObject<MatDoub> d, Int k, Int i, Int j, Int ni, Int nj)
	{
		return MIN(d.argvalue[i][k],d.argvalue[j][k]);
	}
	public final void drootbranchfn(RefObject<MatDoub> d, Int i, Int j, Int ni, Int nj, RefObject<Doub> bi, RefObject<Doub> bj)
	{
		bi.argvalue = bj.argvalue = 0.5 *d.argvalue[i][j];
	}
	public Phylo_slc(MatDoub dist)
		{
		super(GlobalMembersPhylo.dist);
			makethetree(GlobalMembersPhylo.dist);
		}
}