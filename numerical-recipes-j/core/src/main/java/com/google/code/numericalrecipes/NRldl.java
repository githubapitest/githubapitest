package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: Extern blocks are not supported in Java.
extern "C"
{
//C++ TO JAVA CONVERTER WARNING: The following #include directive was ignored:
//	#include "ldl.h"
//C++ TO JAVA CONVERTER WARNING: The following #include directive was ignored:
//	#include "amd.h"
}

public class NRldl
{
	public Doub[] Info = new Doub[AMD_INFO];
	public Int lnz = new Int();
	public Int n = new Int();
	public Int nz = new Int();
	public VecInt PP = new VecInt();
	public VecInt PPinv = new VecInt();
	public VecInt PPattern = new VecInt();
	public VecInt LLnz = new VecInt();
	public VecInt LLp = new VecInt();
	public VecInt PParent = new VecInt();
	public VecInt FFlag = new VecInt();
	public VecInt LLi;
	public VecDoub YY = new VecDoub();
	public VecDoub DD = new VecDoub();
	public VecDoub LLx;
	public Doub Ax;
	public Doub Lx;
	public Doub B;
	public Doub D;
	public Doub X;
	public Doub Y;
	public Int Ai;
	public Int Ap;
	public Int Li;
	public Int Lp;
	public Int P;
	public Int Pinv;
	public Int Flag;
	public Int Pattern;
	public Int Lnz;
	public Int Parent;
	public NRldl(RefObject<NRsparseMat> adat)
	{
		n = adat.argvalue.ncols;
		nz = adat.argvalue.nvals;
		Ap = adat.argvalue.col_ptr[0];
		Ai = adat.argvalue.row_ind[0];
		Ax = adat.argvalue.val[0];
		PP = n;
		PPinv = n;
		PPattern = n;
		LLnz = n;
		LLp = n+1;
		PParent = n;
		FFlag = n;
		YY = n;
		DD = n;
		Y = YY[0];
		D = DD[0];
		P = PP[0];
		Pinv = PPinv[0];
		Pattern = PPattern[0];
		Lnz = LLnz[0];
		Lp = LLp[0];
		Parent = PParent[0];
		Flag = FFlag[0];
	}
	public final void order()
	{
		if (amd_order (n, Ap, Ai, P, (Doub) null, Info) != AMD_OK)
			throw("call to AMD failed");
		amd_control ((Doub) null);
		//amd_info (Info);
		ldl_symbolic (n, Ap, Ai, Lp, Parent, Lnz, Flag, P, Pinv);
		lnz = Lp [n];
		// find # of nonzeros in L, and flop count for ldl_numeric
		Doub flops = 0;
		for (Int j = 0 ; j < n ; j++)
			flops += ((Doub) Lnz [j]) * (Lnz [j] + 2);
		System.out.print("Nz in L: ");
		System.out.print(lnz);
		System.out.print(" Flop count: ");
		System.out.print(flops);
		System.out.print("\n");
		// --------------------------------------------------------------
		// allocate remainder of L, of size lnz
		// --------------------------------------------------------------
		LLi = new VecInt(lnz);
		LLx = new VecDoub(lnz);
		Li = (LLi)[0];
		Lx = (LLx)[0];
	}
	public final void factorize()
	{
		// --------------------------------------------------------------
		// numeric factorization to get Li, Lx, and D
		// --------------------------------------------------------------
		Int dd = ldl_numeric (n, Ap, Ai, Ax, Lp, Parent, Lnz, Li, Lx, D, Y, Flag, Pattern, P, Pinv);
		if (dd != n)
			throw("Factorization failed since diagonal is zero.");
	}
	public final void solve(RefObject<VecDoub_O> y, RefObject<VecDoub> rhs)
	{
		B = rhs.argvalue[0];
		X = y.argvalue[0];
		// solve Ax=b
		// the factorization is LDL' = PAP'
		ldl_perm (n, Y, B, P); // y = Pb
		ldl_lsolve (n, Y, Lp, Li, Lx); // y = L\y
		ldl_dsolve (n, Y, D); // y = D\y
		ldl_ltsolve (n, Y, Lp, Li, Lx); // y = L'\y
		ldl_permt (n, X, Y, P); // x = P'y
	}
	public void Dispose()
	{
		LLx = null;
		LLi = null;
	}
}