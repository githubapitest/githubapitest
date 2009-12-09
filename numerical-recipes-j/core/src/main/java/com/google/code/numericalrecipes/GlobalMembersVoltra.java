package com.google.code.numericalrecipes;
public class GlobalMembersVoltra
{
	public static <G, K> void voltra(Doub t0, Doub h, RefObject<G> g, RefObject<K> ak, RefObject<VecDoub_O> t, RefObject<MatDoub_O> f)
	{
		Int m = f.argvalue.nrows();
		Int n = f.argvalue.ncols();
		VecDoub b = new VecDoub(m);
		MatDoub a = new MatDoub(m,m);
		t.argvalue[0]=t0;
		for (Int k = 0;k<m;k++)
			f.argvalue[k][0]=g.argvalue(k,t.argvalue[0]);
		for (Int i = 1;i<n;i++)
		{
			t.argvalue[i]=t.argvalue[i-1]+h;
			for (Int k = 0;k<m;k++)
			{
				Doub sum = g.argvalue(k,t.argvalue[i]);
				for (Int l = 0;l<m;l++)
				{
					sum += 0.5 *h *ak.argvalue(k,l,t.argvalue[i],t.argvalue[0])*f.argvalue[l][0];
					for (Int j = 1;j<i;j++)
						sum += h *ak.argvalue(k,l,t.argvalue[i],t.argvalue[j])*f.argvalue[l][j];
					if (k == l)
						a[k][l]=1.0-0.5 *h *ak.argvalue(k,l,t.argvalue[i],t.argvalue[i]);
					else
						a[k][l] = -0.5 *h *ak.argvalue(k,l,t.argvalue[i],t.argvalue[i]);
				}
				b[k]=sum;
			}
			LUdcmp alu = new LUdcmp(a);
			alu.solve(b,b);
			for (Int k = 0;k<m;k++)
				f.argvalue[k][i]=b[k];
		}
	}
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class G, class K>
