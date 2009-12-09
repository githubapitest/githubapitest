package com.google.code.numericalrecipes;
public class Output
{
	public Int kmax = new Int();
	public Int nvar = new Int();
	public Int nsave = new Int();
	public boolean dense;
	public Int count = new Int();
	public Doub x1 = new Doub();
	public Doub x2 = new Doub();
	public Doub xout = new Doub();
	public Doub dxout = new Doub();
	public VecDoub xsave = new VecDoub();
	public MatDoub ysave = new MatDoub();
	public Output()
	{
		kmax = -1;
		dense = false;
		count = 0;
	}
	public Output(Int nsavee)
	{
		kmax = 500;
		nsave = nsavee;
		count = 0;
		xsave = kmax;
		dense = nsave > 0 ? true : false;
	}
	public final void init(Int neqn, Doub xlo, Doub xhi)
	{
		nvar = neqn;
		if (kmax == -1)
			return;
		ysave.resize(nvar,kmax);
		if (dense)
		{
			x1 = xlo;
			x2 = xhi;
			xout = x1;
			dxout = (x2-x1)/nsave;
		}
	}
	public final void resize()
	{
		Int kold = kmax;
		kmax *= 2;
		VecDoub tempvec = new VecDoub(xsave);
		xsave.resize(kmax);
		for (Int k = 0; k<kold; k++)
			xsave[k]=tempvec[k];
		MatDoub tempmat = new MatDoub(ysave);
		ysave.resize(nvar,kmax);
		for (Int i = 0; i<nvar; i++)
			for (Int k = 0; k<kold; k++)
				ysave[i][k]=tempmat[i][k];
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class Stepper>
	public final <Stepper> void save_dense(RefObject<Stepper> s, Doub xout, Doub h)
	{
		if (count == kmax)
			resize();
		for (Int i = 0;i<nvar;i++)
			ysave[i][count]=s.argvalue.dense_out(i,xout,h);
		xsave[count++]=xout;
	}
	public final void save(Doub x, RefObject<VecDoub_I> y)
	{
		if (kmax <= 0)
			return;
		if (count == kmax)
			resize();
		for (Int i = 0;i<nvar;i++)
			ysave[i][count]=y.argvalue[i];
		xsave[count++]=x;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class Stepper>
	public final <Stepper> void out(Int nstp, Doub x, RefObject<VecDoub_I> y, RefObject<Stepper> s, Doub h)
	{
		if (!dense)
			throw("dense output not set in Output!");
		if (nstp == -1)
		{
			save(x, y);
			xout += dxout;
		}
		else
		{
			while ((x-xout)*(x2-x1) > 0.0)
			{
				save_dense(s, xout, h);
				xout += dxout;
			}
		}
	}
}