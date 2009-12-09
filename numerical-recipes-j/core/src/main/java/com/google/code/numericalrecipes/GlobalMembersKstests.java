package com.google.code.numericalrecipes;
public class GlobalMembersKstests
{
	public static void ksone(RefObject<VecDoub_IO> data, Doub func(Doub), RefObject<Doub> d, RefObject<Doub> prob)
	{
		Int j = new Int();
		Int n = data.argvalue.size();
		Doub dt = new Doub();
		Doub en = new Doub();
		Doub ff = new Doub();
		Doub fn = new Doub();
		Doub fo = 0.0;
		KSdist ks = new KSdist();
		sort(data.argvalue);
		en = n;
		d.argvalue = 0.0;
		for (j = 0;j<n;j++)
		{
			fn = (j+1)/en;
			ff = func(data.argvalue[j]);
			dt = MAX(Math.abs(fo-ff),Math.abs(fn-ff));
			if (dt > d.argvalue)
				d.argvalue = dt;
			fo = fn;
		}
		en = Math.sqrt(en);
		prob.argvalue = ks.qks((en+0.12+0.11/en)*d.argvalue);
	}
	public static void kstwo(RefObject<VecDoub_IO> data1, RefObject<VecDoub_IO> data2, RefObject<Doub> d, RefObject<Doub> prob)
	{
		Int j1 = 0;
		Int j2 = 0;
		Int n1 = data1.argvalue.size();
		Int n2 = data2.argvalue.size();
		Doub d1 = new Doub();
		Doub d2 = new Doub();
		Doub dt = new Doub();
		Doub en1 = new Doub();
		Doub en2 = new Doub();
		Doub en = new Doub();
		Doub fn1 = 0.0;
		Doub fn2 = 0.0;
		KSdist ks = new KSdist();
		sort(data1.argvalue);
		sort(data2.argvalue);
		en1 = n1;
		en2 = n2;
		d.argvalue = 0.0;
		while (j1 < n1 && j2 < n2)
		{
			if ((d1 = data1.argvalue[j1]) <= (d2 = data2.argvalue[j2]))
				do
					fn1 = ++j1/en1;
				while (j1 < n1 && d1 == data1.argvalue[j1]);
			if (d2 <= d1)
				do
					fn2 = ++j2/en2;
				while (j2 < n2 && d2 == data2.argvalue[j2]);
			if ((dt = Math.abs(fn2-fn1)) > d.argvalue)
				d.argvalue = dt;
		}
		en = Math.sqrt(en1 *en2/(en1+en2));
		prob.argvalue = ks.qks((en+0.12+0.11/en)*d.argvalue);
	}
}