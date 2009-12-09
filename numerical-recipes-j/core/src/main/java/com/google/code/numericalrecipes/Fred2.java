package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class G, class K>
public class Fred2<G, K>
{
	public final Doub a = new Doub();
	public final Doub b = new Doub();
	public final Int n = new Int();
	public G g;
	public K ak;
	public VecDoub t = new VecDoub();
	public VecDoub f = new VecDoub();
	public VecDoub w = new VecDoub();
	public Fred2(Doub aa, Doub bb, Int nn, RefObject<G> gg, RefObject<K> akk)
	{
		a = aa;
		b = bb;
		n = nn;
		g = gg.argvalue;
		ak = akk.argvalue;
		t = n;
		f = n;
		w = n;
		MatDoub omk = new MatDoub(n,n);
		gauleg(a,b,t,w);
		for (Int i = 0;i<n;i++)
		{
			for (Int j = 0;j<n;j++)
				omk[i][j]=Doub(i == j)-ak(t[i],t[j])*w[j];
			f[i]=g(t[i]);
		}
		LUdcmp alu = new LUdcmp(omk);
		alu.solve(f,f);
	}

	public final Doub fredin(Doub x)
	{
		Doub sum = 0.0;
		for (Int i = 0;i<n;i++)
			sum += ak(x,t[i])*w[i]*f[i];
		return g(x)+sum;
	}
}