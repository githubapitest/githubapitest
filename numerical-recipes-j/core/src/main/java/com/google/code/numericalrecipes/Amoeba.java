package com.google.code.numericalrecipes;
public class Amoeba
{
	public final Doub ftol = new Doub();
	public Int nfunc = new Int();
	public Int mpts = new Int();
	public Int ndim = new Int();
	public Doub fmin = new Doub();
	public VecDoub y = new VecDoub();
	public MatDoub p = new MatDoub();
	public Amoeba(Doub ftoll)
	{
		ftol = ftoll;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public final <T> VecDoub minimize(RefObject<VecDoub_I> point, Doub del, RefObject<T> func)
	{
		VecDoub dels = new VecDoub(point.argvalue.size(),del);
		RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(point);
		RefObject<VecDoub_I> tempRefObject2 = new RefObject<VecDoub_I>(dels);
		RefObject<T> tempRefObject3 = new RefObject<T>(func);
		return minimize(tempRefObject, tempRefObject2, tempRefObject3);
		point.argvalue = tempRefObject.argvalue;
		dels = tempRefObject2.argvalue;
		func.argvalue = tempRefObject3.argvalue;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public final <T> VecDoub minimize(RefObject<VecDoub_I> point, RefObject<VecDoub_I> dels, RefObject<T> func)
	{
		Int ndim = point.argvalue.size();
		MatDoub pp = new MatDoub(ndim+1,ndim);
		for (Int i = 0;i<ndim+1;i++)
		{
			for (Int j = 0;j<ndim;j++)
				pp[i][j]=point.argvalue[j];
			if (i !=0)
				pp[i][i-1] += dels.argvalue[i-1];
		}
		RefObject<MatDoub_I> tempRefObject = new RefObject<MatDoub_I>(pp);
		return minimize(tempRefObject, func);
		pp = tempRefObject.argvalue;
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public final <T> VecDoub minimize(RefObject<MatDoub_I> pp, RefObject<T> func)
	{
		final Int NMAX = 5000;
		final Doub TINY = 1.0e-10;
		Int ihi = new Int();
		Int ilo = new Int();
		Int inhi = new Int();
		mpts = pp.argvalue.nrows();
		ndim = pp.argvalue.ncols();
		VecDoub psum = new VecDoub(ndim);
		VecDoub pmin = new VecDoub(ndim);
		VecDoub x = new VecDoub(ndim);
		p = pp.argvalue;
		y.resize(mpts);
		for (Int i = 0;i<mpts;i++)
		{
			for (Int j = 0;j<ndim;j++)
				x[j]=p[i][j];
			y[i]=func.argvalue(x);
		}
		nfunc = 0;
		RefObject<MatDoub_I> tempRefObject = new RefObject<MatDoub_I>(p);
		RefObject<VecDoub_O> tempRefObject2 = new RefObject<VecDoub_O>(psum);
		get_psum(tempRefObject, tempRefObject2);
		p = tempRefObject.argvalue;
		psum = tempRefObject2.argvalue;
		for (;;)
		{
			ilo = 0;
			ihi = y[0]>y[1] ? (inhi = 1,0) : (inhi = 0,1);
			for (Int i = 0;i<mpts;i++)
			{
				if (y[i] <= y[ilo])
					ilo = i;
				if (y[i] > y[ihi])
				{
					inhi = ihi;
					ihi = i;
				}
				else if (y[i] > y[inhi] && i != ihi)
					inhi = i;
			}
			Doub rtol = 2.0 *Math.abs(y[ihi]-y[ilo])/(Math.abs(y[ihi])+Math.abs(y[ilo])+TINY);
			if (rtol < ftol)
			{
				SWAP(y[0],y[ilo]);
				for (Int i = 0;i<ndim;i++)
				{
					SWAP(p[0][i],p[ilo][i]);
					pmin[i]=p[0][i];
				}
				fmin = y[0];
				return pmin;
			}
			if (nfunc >= NMAX)
				throw("NMAX exceeded");
			nfunc += 2;
			RefObject<MatDoub_IO> tempRefObject3 = new RefObject<MatDoub_IO>(p);
			RefObject<VecDoub_O> tempRefObject4 = new RefObject<VecDoub_O>(y);
			RefObject<VecDoub_IO> tempRefObject5 = new RefObject<VecDoub_IO>(psum);
			Doub ytry = amotry(tempRefObject3, tempRefObject4, tempRefObject5, ihi, -1.0, func);
			p = tempRefObject3.argvalue;
			y = tempRefObject4.argvalue;
			psum = tempRefObject5.argvalue;
			if (ytry <= y[ilo])
			{
				RefObject<MatDoub_IO> tempRefObject6 = new RefObject<MatDoub_IO>(p);
				RefObject<VecDoub_O> tempRefObject7 = new RefObject<VecDoub_O>(y);
				RefObject<VecDoub_IO> tempRefObject8 = new RefObject<VecDoub_IO>(psum);
				ytry = amotry(tempRefObject6, tempRefObject7, tempRefObject8, ihi, 2.0, func);
				p = tempRefObject6.argvalue;
				y = tempRefObject7.argvalue;
				psum = tempRefObject8.argvalue;
			}
			else if (ytry >= y[inhi])
			{
				Doub ysave = y[ihi];
				RefObject<MatDoub_IO> tempRefObject9 = new RefObject<MatDoub_IO>(p);
				RefObject<VecDoub_O> tempRefObject10 = new RefObject<VecDoub_O>(y);
				RefObject<VecDoub_IO> tempRefObject11 = new RefObject<VecDoub_IO>(psum);
				ytry = amotry(tempRefObject9, tempRefObject10, tempRefObject11, ihi, 0.5, func);
				p = tempRefObject9.argvalue;
				y = tempRefObject10.argvalue;
				psum = tempRefObject11.argvalue;
				if (ytry >= ysave)
				{
					for (Int i = 0;i<mpts;i++)
					{
						if (i != ilo)
						{
							for (Int j = 0;j<ndim;j++)
								p[i][j]=psum[j]=0.5*(p[i][j]+p[ilo][j]);
							y[i]=func.argvalue(psum);
						}
					}
					nfunc += ndim;
					RefObject<MatDoub_I> tempRefObject12 = new RefObject<MatDoub_I>(p);
					RefObject<VecDoub_O> tempRefObject13 = new RefObject<VecDoub_O>(psum);
					get_psum(tempRefObject12, tempRefObject13);
					p = tempRefObject12.argvalue;
					psum = tempRefObject13.argvalue;
				}
			}
			else
				--nfunc;
		}
	}
	public final void get_psum(RefObject<MatDoub_I> p, RefObject<VecDoub_O> psum)
	{
		for (Int j = 0;j<ndim;j++)
		{
			Doub sum = 0.0;
			for (Int i = 0;i<mpts;i++)
				sum += p.argvalue[i][j];
			psum.argvalue[j]=sum;
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public final <T> Doub amotry(RefObject<MatDoub_IO> p, RefObject<VecDoub_O> y, RefObject<VecDoub_IO> psum, Int ihi, Doub fac, RefObject<T> func)
	{
		VecDoub ptry = new VecDoub(ndim);
		Doub fac1 = (1.0-fac)/ndim;
		Doub fac2 = fac1-fac;
		for (Int j = 0;j<ndim;j++)
			ptry[j]=psum.argvalue[j]*fac1-p.argvalue[ihi][j]*fac2;
		Doub ytry = func.argvalue(ptry);
		if (ytry < y.argvalue[ihi])
		{
			y.argvalue[ihi]=ytry;
			for (Int j = 0;j<ndim;j++)
			{
				psum.argvalue[j] += ptry[j]-p.argvalue[ihi][j];
				p.argvalue[ihi][j]=ptry[j];
			}
		}
		return ytry;
	}
}