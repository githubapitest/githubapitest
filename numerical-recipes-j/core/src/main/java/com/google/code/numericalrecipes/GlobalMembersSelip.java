package com.google.code.numericalrecipes;
public class GlobalMembersSelip
{
	public static Doub selip(Int k, RefObject<VecDoub_I> arr)
	{
		final Int M = 64;
		final Doub BIG = .99e99;
		Int i = new Int();
		Int j = new Int();
		Int jl = new Int();
		Int jm = new Int();
		Int ju = new Int();
		Int kk = new Int();
		Int mm = new Int();
		Int nlo = new Int();
		Int nxtmm = new Int();
		Int n = arr.argvalue.size();
		Doub ahi = new Doub();
		Doub alo = new Doub();
		Doub sum = new Doub();
		VecInt isel = new VecInt(M+2);
		VecDoub sel = new VecDoub(M+2);
		if (k < 0 || k > n-1)
			throw("bad input to selip");
		kk = k;
		ahi = BIG;
		alo = -BIG;
		for (;;)
		{
			mm = nlo = 0;
			sum = 0.0;
			nxtmm = M+1;
			for (i = 0;i<n;i++)
			{
				if (arr.argvalue[i] >= alo && arr.argvalue[i] <= ahi)
				{
					mm++;
					if (arr.argvalue[i] == alo)
						nlo++;
					if (mm <= M)
						sel[mm-1]=arr.argvalue[i];
					else if (mm == nxtmm)
					{
						nxtmm = mm+mm/M;
						sel[(i+2+mm+kk) % M]=arr.argvalue[i];
					}
					sum += arr.argvalue[i];
				}
			}
			if (kk < nlo)
			{
				return alo;
			}
			else if (mm < M+1)
			{
				shell(sel,mm);
				ahi = sel[kk];
				return ahi;
			}
			sel[M]=sum/mm;
			shell(sel,M+1);
			sel[M+1]=ahi;
			for (j = 0;j<M+2;j++)
				isel[j]=0;
			for (i = 0;i<n;i++)
			{
				if (arr.argvalue[i] >= alo && arr.argvalue[i] <= ahi)
				{
					jl = 0;
					ju = M+2;
					while (ju-jl > 1)
					{
						jm = (ju+jl)/2;
						if (arr.argvalue[i] >= sel[jm-1])
							jl = jm;
						else
							ju = jm;
					}
					isel[ju-1]++;
				}
			}
			j = 0;
			while (kk >= isel[j])
			{
				alo = sel[j];
				kk -= isel[j++];
			}
			ahi = sel[j];
		}
	}
}