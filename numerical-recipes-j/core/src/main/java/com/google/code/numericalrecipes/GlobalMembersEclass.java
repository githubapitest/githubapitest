package com.google.code.numericalrecipes;
public class GlobalMembersEclass
{
	public static void eclass(RefObject<VecInt_O> nf, RefObject<VecInt_I> lista, RefObject<VecInt_I> listb)
	{
		Int l = new Int();
		Int k = new Int();
		Int j = new Int();
		Int n = nf.argvalue.size();
		Int m = lista.argvalue.size();
		for (k = 0;k<n;k++)
			nf.argvalue[k]=k;
		for (l = 0;l<m;l++)
		{
			j = lista.argvalue[l];
			while (nf.argvalue[j] != j)
				j = nf.argvalue[j];
			k = listb.argvalue[l];
			while (nf.argvalue[k] != k)
				k = nf.argvalue[k];
			if (j != k)
				nf.argvalue[j]=k;
		}
		for (j = 0;j<n;j++)
			while (nf.argvalue[j] != nf.argvalue[nf.argvalue[j]])
				nf.argvalue[j]=nf.argvalue[nf.argvalue[j]];
	}
	public static void eclazz(RefObject<VecInt_O> nf, Bool equiv(Int, const Int))
	{
		Int kk = new Int();
		Int jj = new Int();
		Int n = nf.argvalue.size();
		nf.argvalue[0]=0;
		for (jj = 1;jj<n;jj++)
		{
			nf.argvalue[jj]=jj;
			for (kk = 0;kk<jj;kk++)
			{
				nf.argvalue[kk]=nf.argvalue[nf.argvalue[kk]];
				if (equiv(jj+1,kk+1))
					nf.argvalue[nf.argvalue[nf.argvalue[kk]]]=jj;
			}
		}
		for (jj = 0;jj<n;jj++)
			nf.argvalue[jj]=nf.argvalue[nf.argvalue[jj]];
	}
}