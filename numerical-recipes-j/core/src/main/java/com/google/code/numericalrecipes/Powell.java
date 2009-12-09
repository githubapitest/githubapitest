package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
public class Powell<T> implements Linemethod<T>
{
	public Int iter = new Int();
	public Doub fret = new Doub();
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using Linemethod<T>.func;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using Linemethod<T>.linmin;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using Linemethod<T>.p;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using Linemethod<T>.xi;
	public final Doub ftol = new Doub();
	public Powell(RefObject<T> func)
	{
		this(func, 3.0e-8);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Powell(T &func, const Doub ftoll=3.0e-8) : Linemethod<T>(func), ftol(ftoll)
	public Powell(RefObject<T> func, Doub ftoll)
	{
		super(func.argvalue);
		ftol = ftoll;
	}
	public final VecDoub minimize(RefObject<VecDoub_I> pp)
	{
		Int n = pp.argvalue.size();
		MatDoub ximat = new MatDoub(n,n,0.0);
		for (Int i = 0;i<n;i++)
			ximat[i][i]=1.0;
		RefObject<MatDoub_IO> tempRefObject = new RefObject<MatDoub_IO>(ximat);
		return minimize(pp, tempRefObject);
		ximat = tempRefObject.argvalue;
	}
	public final VecDoub minimize(RefObject<VecDoub_I> pp, RefObject<MatDoub_IO> ximat)
	{
		final Int ITMAX = 200;
		final Doub TINY = 1.0e-25;
		Doub fptt = new Doub();
		Int n = pp.argvalue.size();
		p=pp.argvalue;
		VecDoub pt = new VecDoub(n);
		VecDoub ptt = new VecDoub(n);
		xi.resize(n);
		fret = func(p);
		for (Int j = 0;j<n;j++)
			pt[j]=p[j];
		for (iter = 0;;++iter)
		{
			Doub fp = fret;
			Int ibig = 0;
			Doub del = 0.0;
			for (Int i = 0;i<n;i++)
			{
				for (Int j = 0;j<n;j++)
					xi[j]=ximat.argvalue[j][i];
				fptt = fret;
				fret = linmin();
				if (fptt-fret > del)
				{
					del = fptt-fret;
					ibig = i+1;
				}
			}
			if (2.0*(fp-fret) <= ftol*(Math.abs(fp)+Math.abs(fret))+TINY)
			{
				return p;
			}
			if (iter == ITMAX)
				throw("powell exceeding maximum iterations.");
			for (Int j = 0;j<n;j++)
			{
				ptt[j]=2.0 *p[j]-pt[j];
				xi[j]=p[j]-pt[j];
				pt[j]=p[j];
			}
			fptt = func(ptt);
			if (fptt < fp)
			{
				Doub t = 2.0*(fp-2.0 *fret+fptt)*SQR(fp-fret-del)-del *SQR(fp-fptt);
				if (t < 0.0)
				{
					fret = linmin();
					for (Int j = 0;j<n;j++)
					{
						ximat.argvalue[j][ibig-1]=ximat.argvalue[j][n-1];
						ximat.argvalue[j][n-1]=xi[j];
					}
				}
			}
		}
	}
}