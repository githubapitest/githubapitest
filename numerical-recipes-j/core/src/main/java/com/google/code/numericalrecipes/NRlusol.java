package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: Extern blocks are not supported in Java.
extern "C"
{
//C++ TO JAVA CONVERTER WARNING: The following #include directive was ignored:
//	#include "lusol.h"
}

public class NRlusol
{
	public LUSOLrec LUSOL;
	public Int inform = new Int();

	public NRlusol(Int m, Int nz)
	{
		LUSOL = LUSOL_create(stdout, 0, LUSOL_PIVMOD_TPP, 0);
		LUSOL.luparm[LUSOL_IP_SCALAR_NZA] = 10;
		LUSOL.parmlu[LUSOL_RP_FACTORMAX_Lij] = 5.0;
		LUSOL.parmlu[LUSOL_RP_UPDATEMAX_Lij] = 5.0;
		LUSOL_sizeto(LUSOL, m, m, nz);
		LUSOL.m = m;
		LUSOL.n = m;
		LUSOL.nelem = nz;
	}
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	void load_col(Int col, RefObject<VecInt> row_ind, RefObject<VecDoub> val);
	public final void factorize()
	{
		LU1FAC(LUSOL, inform);
		if (inform > LUSOL_INFORM_SERIOUS)
		{
			System.out.print("    Error:");
			System.out.print("\n");
			System.out.print(LUSOL_informstr(LUSOL, inform));
			System.out.print("\n");
			throw("LUSOL exiting");
		}
	}
	public final VecDoub solve(RefObject<VecDoub> rhs)
	{
		VecDoub x = new VecDoub(rhs.argvalue.size());
		VecDoub y = rhs.argvalue;
		LU6SOL(LUSOL, LUSOL_SOLVE_Aw_v, y[0], x[0], null, inform);
		return x;
	}
	public final VecDoub solvet(RefObject<VecDoub> rhs)
	{
		VecDoub x = new VecDoub(rhs.argvalue.size());
		VecDoub y = rhs.argvalue;
		LU6SOL(LUSOL, LUSOL_SOLVE_Atv_w, x[0], y[0], null, inform);
		return x;
	}
	public final VecDoub linv(RefObject<VecDoub> rhs)
	{
		VecDoub x = rhs.argvalue;
		LU6SOL(LUSOL, LUSOL_SOLVE_Lv_v, x[0], x[0], null, inform);
		return x;
	}
	public final VecDoub uinv(RefObject<VecDoub> rhs)
	{
		VecDoub x = new VecDoub(rhs.argvalue.size());
		LU6SOL(LUSOL, LUSOL_SOLVE_Uw_v, rhs.argvalue[0], x[0], null, inform);
		return x;
	}
	public final VecDoub linvt(RefObject<VecDoub> rhs)
	{
		VecDoub x = rhs.argvalue;
		LU6SOL(LUSOL, LUSOL_SOLVE_Ltv_v, x[0], x[0], null, inform);
		return x;
	}
	public final VecDoub uinvt(RefObject<VecDoub> rhs)
	{
		VecDoub x = new VecDoub(rhs.argvalue.size());
		VecDoub y = rhs.argvalue;
		LU6SOL(LUSOL, LUSOL_SOLVE_Utv_w, x[0], y[0], null, inform);
		return x;
	}
	public final void update(RefObject<VecDoub> x, Int i, RefObject<Int> ok)
	{
		Doub DIAG = new Doub();
		Doub VNORM = new Doub();
		LU8RPC(LUSOL, LUSOL_UPDATE_OLDNONEMPTY, LUSOL_UPDATE_USEPREPARED, i, x.argvalue[0], null, ok.argvalue, DIAG, VNORM);
	}
	public final void clear()
	{
		LUSOL_clear(LUSOL, 1);
	}
	public void Dispose()
	{
		LUSOL_free(LUSOL);
	}
}