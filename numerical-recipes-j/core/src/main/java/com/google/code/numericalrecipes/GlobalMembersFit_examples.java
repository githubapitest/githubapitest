package com.google.code.numericalrecipes;
public class GlobalMembersFit_examples
{
	public static Int fpoly_np = 10;

	public static VecDoub fpoly(Doub x)
	{
		Int j = new Int();
		VecDoub p = new VecDoub(fpoly_np);
		p[0]=1.0;
		for (j = 1;j<fpoly_np;j++)
			p[j]=p[j-1]*x;
		return p;
	}
	public static Int fleg_nl = 10;

	public static VecDoub fleg(Doub x)
	{
		Int j = new Int();
		Doub twox = new Doub();
		Doub f2 = new Doub();
		Doub f1 = new Doub();
		Doub d = new Doub();
		VecDoub pl = new VecDoub(fleg_nl);
		pl[0]=1.;
		pl[1]=x;
		if (fleg_nl > 2)
		{
			twox = 2.*x;
			f2 = x;
			d = 1.;
			for (j = 2;j<fleg_nl;j++)
			{
				f1 = d++;
				f2+=twox;
				pl[j]=(f2 *pl[j-1]-f1 *pl[j-2])/d;
			}
		}
		return pl;
	}
	public static void fgauss(Doub x, RefObject<VecDoub_I> a, RefObject<Doub> y, RefObject<VecDoub_O> dyda)
	{
		Int i = new Int();
		Int na = a.argvalue.size();
		Doub fac = new Doub();
		Doub ex = new Doub();
		Doub arg = new Doub();
		y.argvalue = 0.;
		for (i = 0;i<na-1;i+=3)
		{
			arg = (x-a.argvalue[i+1])/a.argvalue[i+2];
			ex = Math.exp(-SQR(arg));
			fac = a.argvalue[i]*ex *2.*arg;
			y.argvalue += a.argvalue[i]*ex;
			dyda.argvalue[i]=ex;
			dyda.argvalue[i+1]=fac/a.argvalue[i+2];
			dyda.argvalue[i+2]=fac *arg/a.argvalue[i+2];
		}
	}
}