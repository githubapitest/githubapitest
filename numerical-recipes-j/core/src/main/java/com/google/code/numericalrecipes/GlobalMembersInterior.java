package com.google.code.numericalrecipes;
public class GlobalMembersInterior
{
	public static Doub dotprod(RefObject<VecDoub_I> x, RefObject<VecDoub_I> y)
	{
		Doub sum = 0.0;
		for (Int i = 0;i<x.argvalue.size();i++)
			sum += x.argvalue[i]*y.argvalue[i];
		return sum;
	}

	public static Int intpt(NRsparseMat a, RefObject<VecDoub_I> b, RefObject<VecDoub_I> c, RefObject<VecDoub_O> x)
	{
		final Int MAXITS = 200;
		final Doub EPS = 1.0e-6;
		final Doub SIGMA = 0.9;
		final Doub DELTA = 0.02;
		final Doub BIG = numeric_limits<Doub>.max();
		Int i = new Int();
		Int j = new Int();
		Int iter = new Int();
		Int status = new Int();
		Int m = a.nrows;
		Int n = a.ncols;
		VecDoub y = new VecDoub(m);
		VecDoub z = new VecDoub(n);
		VecDoub ax = new VecDoub(m);
		VecDoub aty = new VecDoub(n);
		VecDoub rp = new VecDoub(m);
		VecDoub rd = new VecDoub(n);
		VecDoub d = new VecDoub(n);
		VecDoub dx = new VecDoub(n);
		VecDoub dy = new VecDoub(m);
		VecDoub dz = new VecDoub(n);
		VecDoub rhs = new VecDoub(m);
		VecDoub tempm = new VecDoub(m);
		VecDoub tempn = new VecDoub(n);
		NRsparseMat at = a.transpose();
		ADAT adat = new ADAT(a,at);
		NRldl solver = new NRldl(adat.ref());
		solver.order();
		Doub rpfact = 1.0+Math.sqrt(dotprod(b, b));
		Doub rdfact = 1.0+Math.sqrt(dotprod(c, c));
		for (j = 0;j<n;j++)
		{
			x.argvalue[j]=1000.0;
			z[j]=1000.0;
		}
		for (i = 0;i<m;i++)
		{
			y[i]=1000.0;
		}
		Doub normrp_old = BIG;
		Doub normrd_old = BIG;
		System.out.printf("%4d", "iter");
		System.out.printf("%12d", "Primal obj.");
		System.out.printf("%9d", "||r_p||");
		System.out.printf("%13d", "Dual obj.");
		System.out.printf("%11d", "||r_d||");
		System.out.printf("%13d", "duality gap");
		System.out.printf("%16d", "normalized gap");
		System.out.printf("%16d", "\n");
		for (iter = 0;iter<MAXITS;iter++)
		{
			ax = a.ax(x.argvalue);
			for (i = 0;i<m;i++)
				rp[i]=ax[i]-b.argvalue[i];
		RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(rp);
		RefObject<VecDoub_I> tempRefObject2 = new RefObject<VecDoub_I>(rp);
			Doub normrp = Math.sqrt(dotprod(tempRefObject, tempRefObject2))/rpfact;
			rp = tempRefObject.argvalue;
			rp = tempRefObject2.argvalue;
			aty = at.ax(y);
			for (j = 0;j<n;j++)
				rd[j]=aty[j]+z[j]-c.argvalue[j];
		RefObject<VecDoub_I> tempRefObject3 = new RefObject<VecDoub_I>(rd);
		RefObject<VecDoub_I> tempRefObject4 = new RefObject<VecDoub_I>(rd);
			Doub normrd = Math.sqrt(dotprod(tempRefObject3, tempRefObject4))/rdfact;
			rd = tempRefObject3.argvalue;
			rd = tempRefObject4.argvalue;
		RefObject<VecDoub_I> tempRefObject5 = new RefObject<VecDoub_I>(z);
			Doub gamma = dotprod(x, tempRefObject5);
			z = tempRefObject5.argvalue;
			Doub mu = DELTA *gamma/n;
			Doub primal_obj = dotprod(c, x);
		RefObject<VecDoub_I> tempRefObject6 = new RefObject<VecDoub_I>(y);
			Doub dual_obj = dotprod(b, tempRefObject6);
			y = tempRefObject6.argvalue;
			Doub gamma_norm = gamma/(1.0+Math.abs(primal_obj));
			 System.out.printf("%3.4e", iter);
			 System.out.printf("%12.4e", primal_obj);
			 System.out.printf("%12.4e", normrp);
			 System.out.printf("%12.4e", dual_obj);
			 System.out.printf("%12.4e", normrd);
			 System.out.printf("%12.4e", gamma);
			 System.out.printf("%12.4e", gamma_norm);
			 System.out.printf("%12.4e", "\n");
			if (normrp < EPS && normrd < EPS && gamma_norm < EPS)
				return status = 0;
			if (normrp > 1000 *normrp_old && normrp > EPS)
				return status = 1;
			if (normrd > 1000 *normrd_old && normrd > EPS)
				return status = 2;
			for (j = 0;j<n;j++)
				d[j]=x.argvalue[j]/z[j];
			adat.updateD(d);
			solver.factorize();
			for (j = 0;j<n;j++)
				tempn[j]=x.argvalue[j]-mu/z[j]-d[j]*rd[j];
			tempm = a.ax(tempn);
			for (i = 0;i<m;i++)
				rhs[i]=-rp[i]+tempm[i];
		RefObject<VecDoub_O> tempRefObject7 = new RefObject<VecDoub_O>(dy);
		RefObject<VecDoub> tempRefObject8 = new RefObject<VecDoub>(rhs);
			solver.solve(tempRefObject7, tempRefObject8);
			dy = tempRefObject7.argvalue;
			rhs = tempRefObject8.argvalue;
			tempn = at.ax(dy);
			for (j = 0;j<n;j++)
				dz[j]=-tempn[j]-rd[j];
			for (j = 0;j<n;j++)
				dx[j]=-d[j]*dz[j]+mu/z[j]-x.argvalue[j];
			Doub alpha_p = 1.0;
			for (j = 0;j<n;j++)
				if (x.argvalue[j]+alpha_p *dx[j] < 0.0)
					alpha_p = -x.argvalue[j]/dx[j];
			Doub alpha_d = 1.0;
			for (j = 0;j<n;j++)
				if (z[j]+alpha_d *dz[j] < 0.0)
					alpha_d = -z[j]/dz[j];
			alpha_p = MIN(alpha_p *SIGMA,1.0);
			alpha_d = MIN(alpha_d *SIGMA,1.0);
			for (j = 0;j<n;j++)
			{
				x.argvalue[j]+=alpha_p *dx[j];
				z[j]+=alpha_d *dz[j];
			}
			for (i = 0;i<m;i++)
				y[i]+=alpha_d *dy[i];
			normrp_old = normrp;
			normrd_old = normrd;
		}
		return status = 3;
	}
}