package com.google.code.numericalrecipes;
public class GlobalMembersTridag
{
	public static void tridag(RefObject<VecDoub_I> a, RefObject<VecDoub_I> b, RefObject<VecDoub_I> c, RefObject<VecDoub_I> r, RefObject<VecDoub_O> u)
	{
		Int j = new Int();
		Int n = a.argvalue.size();
		Doub bet = new Doub();
		VecDoub gam = new VecDoub(n);
		if (b.argvalue[0] == 0.0)
			throw("Error 1 in tridag");
		u.argvalue[0]=r.argvalue[0]/(bet = b.argvalue[0]);
		for (j = 1;j<n;j++)
		{
			gam[j]=c.argvalue[j-1]/bet;
			bet = b.argvalue[j]-a.argvalue[j]*gam[j];
			if (bet == 0.0)
				throw("Error 2 in tridag");
			u.argvalue[j]=(r.argvalue[j]-a.argvalue[j]*u.argvalue[j-1])/bet;
		}
		for (j = (n-2);j>=0;j--)
			u.argvalue[j] -= gam[j+1]*u.argvalue[j+1];
	}
	public static void cyclic(RefObject<VecDoub_I> a, RefObject<VecDoub_I> b, RefObject<VecDoub_I> c, Doub alpha, Doub beta, RefObject<VecDoub_I> r, RefObject<VecDoub_O> x)
	{
		Int i = new Int();
		Int n = a.argvalue.size();
		Doub fact = new Doub();
		Doub gamma = new Doub();
		if (n <= 2)
			throw("n too small in cyclic");
		VecDoub bb = new VecDoub(n);
		VecDoub u = new VecDoub(n);
		VecDoub z = new VecDoub(n);
		gamma = -b.argvalue[0];
		bb[0]=b.argvalue[0]-gamma;
		bb[n-1]=b.argvalue[n-1]-alpha *beta/gamma;
		for (i = 1;i<n-1;i++)
			bb[i]=b.argvalue[i];
	RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(bb);
		tridag(a, tempRefObject, c, r, x);
		bb = tempRefObject.argvalue;
		u[0]=gamma;
		u[n-1]=alpha;
		for (i = 1;i<n-1;i++)
			u[i]=0.0;
	RefObject<VecDoub_I> tempRefObject2 = new RefObject<VecDoub_I>(bb);
	RefObject<VecDoub_I> tempRefObject3 = new RefObject<VecDoub_I>(u);
	RefObject<VecDoub_O> tempRefObject4 = new RefObject<VecDoub_O>(z);
		tridag(a, tempRefObject2, c, tempRefObject3, tempRefObject4);
		bb = tempRefObject2.argvalue;
		u = tempRefObject3.argvalue;
		z = tempRefObject4.argvalue;
		fact = (x.argvalue[0]+beta *x.argvalue[n-1]/gamma)/ (1.0+z[0]+beta *z[n-1]/gamma);
		for (i = 0;i<n;i++)
			x.argvalue[i] -= fact *z[i];
	}
}