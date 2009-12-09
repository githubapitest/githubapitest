package com.google.code.numericalrecipes;
public class GlobalMembersMnewt
{
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
	//void usrfun(RefObject<VecDoub_I> x, RefObject<VecDoub_O> fvec, RefObject<MatDoub_O> fjac);

	public static void mnewt(Int ntrial, RefObject<VecDoub_IO> x, Doub tolx, Doub tolf)
	{
		Int i = new Int();
		Int n = x.argvalue.size();
		VecDoub p = new VecDoub(n);
		VecDoub fvec = new VecDoub(n);
		MatDoub fjac = new MatDoub(n,n);
		for (Int k = 0;k<ntrial;k++)
		{
		RefObject<VecDoub_O> tempRefObject = new RefObject<VecDoub_O>(fvec);
		RefObject<MatDoub_O> tempRefObject2 = new RefObject<MatDoub_O>(fjac);
			usrfun(x, tempRefObject, tempRefObject2);
			fvec = tempRefObject.argvalue;
			fjac = tempRefObject2.argvalue;
			Doub errf = 0.0;
			for (i = 0;i<n;i++)
				errf += Math.abs(fvec[i]);
			if (errf <= tolf)
				return;
			for (i = 0;i<n;i++)
				p[i] = -fvec[i];
			LUdcmp alu = new LUdcmp(fjac);
			alu.solve(p,p);
			Doub errx = 0.0;
			for (i = 0;i<n;i++)
			{
				errx += Math.abs(p[i]);
				x.argvalue[i] += p[i];
			}
			if (errx <= tolx)
				return;
		}
		return;
	}
}