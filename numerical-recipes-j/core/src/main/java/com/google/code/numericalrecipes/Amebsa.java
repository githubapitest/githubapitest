package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
public class Amebsa<T>
{
	public T funk;
	public final Doub ftol = new Doub();
	public Ranq1 ran = new Ranq1();
	public Doub yb = new Doub();
	public Int ndim = new Int();
	public VecDoub pb = new VecDoub();
	public Int mpts = new Int();
	public VecDoub y = new VecDoub();
	public MatDoub p = new MatDoub();
	public Doub tt = new Doub();
	public Amebsa(RefObject<VecDoub_I> point, Doub del, RefObject<T> funkk, Doub ftoll)
	{
		funk = funkk.argvalue;
		ftol = ftoll;
		ran = 1234;
		yb = numeric_limits<Doub>.max();
		ndim = point.argvalue.size();
		pb = ndim;
		mpts = ndim+1;
		y = mpts;
		p = new MatDoub(mpts,ndim);
		for (Int i = 0;i<mpts;i++)
		{
			for (Int j = 0;j<ndim;j++)
				p[i][j]=point.argvalue[j];
			if (i != 0)
				p[i][i-1] += del;
		}
		inity();
	}
	public Amebsa(RefObject<VecDoub_I> point, RefObject<VecDoub_I> dels, RefObject<T> funkk, Doub ftoll)
	{
		funk = funkk.argvalue;
		ftol = ftoll;
		ran = 1234;
		yb = numeric_limits<Doub>.max();
		ndim = point.argvalue.size();
		pb = ndim;
		mpts = ndim+1;
		y = mpts;
		p = new MatDoub(mpts,ndim);
		for (Int i = 0;i<mpts;i++)
		{
			for (Int j = 0;j<ndim;j++)
				p[i][j]=point.argvalue[j];
			if (i != 0)
				p[i][i-1] += dels.argvalue[i-1];
		}
		inity();
	}
	public Amebsa(RefObject<MatDoub_I> pp, RefObject<T> funkk, Doub ftoll)
	{
		funk = funkk.argvalue;
		ftol = ftoll;
		ran = 1234;
		yb = numeric_limits<Doub>.max();
		ndim = pp.argvalue.ncols();
		pb = ndim;
		mpts = pp.argvalue.nrows();
		y = mpts;
		p = pp.argvalue;
		inity();
	}

	public final void inity()
	{
		VecDoub x = new VecDoub(ndim);
		for (Int i = 0;i<mpts;i++)
		{
			for (Int j = 0;j<ndim;j++)
				x[j]=p[i][j];
			y[i]=funk(x);
		}
	}
	public final Bool anneal(RefObject<Int> iter, Doub temperature)
	{
		VecDoub psum = new VecDoub(ndim);
		tt = -temperature;
		RefObject<MatDoub_I> tempRefObject = new RefObject<MatDoub_I>(p);
		RefObject<VecDoub_O> tempRefObject2 = new RefObject<VecDoub_O>(psum);
		get_psum(tempRefObject, tempRefObject2);
		p = tempRefObject.argvalue;
		psum = tempRefObject2.argvalue;
		for (;;)
		{
			Int ilo = 0;
			Int ihi = 1;
			Doub ylo = y[0]+tt *Math.log(ran.doub());
			Doub ynhi = ylo;
			Doub yhi = y[1]+tt *Math.log(ran.doub());
			if (ylo > yhi)
			{
				ihi = 0;
				ilo = 1;
				ynhi = yhi;
				yhi = ylo;
				ylo = ynhi;
			}
			for (Int i = 3;i<=mpts;i++)
			{
				Doub yt = y[i-1]+tt *Math.log(ran.doub());
				if (yt <= ylo)
				{
					ilo = i-1;
					ylo = yt;
				}
				if (yt > yhi)
				{
					ynhi = yhi;
					ihi = i-1;
					yhi = yt;
				}
				else if (yt > ynhi)
				{
					ynhi = yt;
				}
			}
			Doub rtol = 2.0 *Math.abs(yhi-ylo)/(Math.abs(yhi)+Math.abs(ylo));
			if (rtol < ftol || iter.argvalue < 0)
			{
				SWAP(y[0],y[ilo]);
				for (Int n = 0;n<ndim;n++)
					SWAP(p[0][n],p[ilo][n]);
				if (rtol < ftol)
					return true;
				else
					return false;
			}
			iter.argvalue -= 2;
			RefObject<MatDoub_IO> tempRefObject3 = new RefObject<MatDoub_IO>(p);
			RefObject<VecDoub_O> tempRefObject4 = new RefObject<VecDoub_O>(y);
			RefObject<VecDoub_IO> tempRefObject5 = new RefObject<VecDoub_IO>(psum);
			RefObject<Doub> tempRefObject6 = new RefObject<Doub>(yhi);
			Doub ytry = amotsa(tempRefObject3, tempRefObject4, tempRefObject5, ihi, tempRefObject6, -1.0);
			p = tempRefObject3.argvalue;
			y = tempRefObject4.argvalue;
			psum = tempRefObject5.argvalue;
			yhi = tempRefObject6.argvalue;
			if (ytry <= ylo)
			{
				RefObject<MatDoub_IO> tempRefObject7 = new RefObject<MatDoub_IO>(p);
				RefObject<VecDoub_O> tempRefObject8 = new RefObject<VecDoub_O>(y);
				RefObject<VecDoub_IO> tempRefObject9 = new RefObject<VecDoub_IO>(psum);
				RefObject<Doub> tempRefObject10 = new RefObject<Doub>(yhi);
				ytry = amotsa(tempRefObject7, tempRefObject8, tempRefObject9, ihi, tempRefObject10, 2.0);
				p = tempRefObject7.argvalue;
				y = tempRefObject8.argvalue;
				psum = tempRefObject9.argvalue;
				yhi = tempRefObject10.argvalue;
			}
			else if (ytry >= ynhi)
			{
				Doub ysave = yhi;
				RefObject<MatDoub_IO> tempRefObject11 = new RefObject<MatDoub_IO>(p);
				RefObject<VecDoub_O> tempRefObject12 = new RefObject<VecDoub_O>(y);
				RefObject<VecDoub_IO> tempRefObject13 = new RefObject<VecDoub_IO>(psum);
				RefObject<Doub> tempRefObject14 = new RefObject<Doub>(yhi);
				ytry = amotsa(tempRefObject11, tempRefObject12, tempRefObject13, ihi, tempRefObject14, 0.5);
				p = tempRefObject11.argvalue;
				y = tempRefObject12.argvalue;
				psum = tempRefObject13.argvalue;
				yhi = tempRefObject14.argvalue;
				if (ytry >= ysave)
				{
					for (Int i = 0;i<mpts;i++)
					{
						if (i != ilo)
						{
							for (Int j = 0;j<ndim;j++)
							{
								psum[j]=0.5*(p[i][j]+p[ilo][j]);
								p[i][j]=psum[j];
							}
							y[i]=funk(psum);
						}
					}
					iter.argvalue -= ndim;
					RefObject<MatDoub_I> tempRefObject15 = new RefObject<MatDoub_I>(p);
					RefObject<VecDoub_O> tempRefObject16 = new RefObject<VecDoub_O>(psum);
					get_psum(tempRefObject15, tempRefObject16);
					p = tempRefObject15.argvalue;
					psum = tempRefObject16.argvalue;
				}
			}
			else
				++iter.argvalue;
		}
	}
	public final void get_psum(RefObject<MatDoub_I> p, RefObject<VecDoub_O> psum)
	{
		for (Int n = 0;n<ndim;n++)
		{
			Doub sum = 0.0;
			for (Int m = 0;m<mpts;m++)
				sum += p.argvalue[m][n];
			psum.argvalue[n]=sum;
		}
	}
	public final Doub amotsa(RefObject<MatDoub_IO> p, RefObject<VecDoub_O> y, RefObject<VecDoub_IO> psum, Int ihi, RefObject<Doub> yhi, Doub fac)
	{
		VecDoub ptry = new VecDoub(ndim);
		Doub fac1 = (1.0-fac)/ndim;
		Doub fac2 = fac1-fac;
		for (Int j = 0;j<ndim;j++)
			ptry[j]=psum.argvalue[j]*fac1-p.argvalue[ihi][j]*fac2;
		Doub ytry = funk(ptry);
		if (ytry <= yb)
		{
			for (Int j = 0;j<ndim;j++)
				pb[j]=ptry[j];
			yb = ytry;
		}
		Doub yflu = ytry-tt *Math.log(ran.doub());
		if (yflu < yhi.argvalue)
		{
			y.argvalue[ihi]=ytry;
			yhi.argvalue = yflu;
			for (Int j = 0;j<ndim;j++)
			{
				psum.argvalue[j] += ptry[j]-p.argvalue[ihi][j];
				p.argvalue[ihi][j]=ptry[j];
			}
		}
		return yflu;
	}
}