package com.google.code.numericalrecipes;
public class MParith
{

	public final void mpadd(RefObject<VecUchar_O> w, RefObject<VecUchar_I> u, RefObject<VecUchar_I> v)
	{
		Int j = new Int();
		Int n = u.argvalue.size();
		Int m = v.argvalue.size();
		Int p = w.argvalue.size();
		Int n_min = MIN(n,m);
		Int p_min = MIN(n_min,p-1);
		Uint ireg = 0;
		for (j = p_min-1;j>=0;j--)
		{
			ireg = u.argvalue[j]+v.argvalue[j]+hibyte(ireg);
			w.argvalue[j+1]=lobyte(ireg);
		}
		w.argvalue[0]=hibyte(ireg);
		if (p > p_min+1)
			for (j = p_min+1;j<p;j++)
				w.argvalue[j]=0;
	}

	public final void mpsub(RefObject<Int> is, RefObject<VecUchar_O> w, RefObject<VecUchar_I> u, RefObject<VecUchar_I> v)
	{
		Int j = new Int();
		Int n = u.argvalue.size();
		Int m = v.argvalue.size();
		Int p = w.argvalue.size();
		Int n_min = MIN(n,m);
		Int p_min = MIN(n_min,p-1);
		Uint ireg = 256;
		for (j = p_min-1;j>=0;j--)
		{
			ireg = 255+u.argvalue[j]-v.argvalue[j]+hibyte(ireg);
			w.argvalue[j]=lobyte(ireg);
		}
		is.argvalue = hibyte(ireg)-1;
		if (p > p_min)
			for (j = p_min;j<p;j++)
				w.argvalue[j]=0;
	}

	public final void mpsad(RefObject<VecUchar_O> w, RefObject<VecUchar_I> u, Int iv)
	{
		Int j = new Int();
		Int n = u.argvalue.size();
		Int p = w.argvalue.size();
		Uint ireg = 256 *iv;
		for (j = n-1;j>=0;j--)
		{
			ireg = u.argvalue[j]+hibyte(ireg);
			if (j+1 < p)
				w.argvalue[j+1]=lobyte(ireg);
		}
		w.argvalue[0]=hibyte(ireg);
		for (j = n+1;j<p;j++)
			w.argvalue[j]=0;
	}

	public final void mpsmu(RefObject<VecUchar_O> w, RefObject<VecUchar_I> u, Int iv)
	{
		Int j = new Int();
		Int n = u.argvalue.size();
		Int p = w.argvalue.size();
		Uint ireg = 0;
		for (j = n-1;j>=0;j--)
		{
			ireg = u.argvalue[j]*iv+hibyte(ireg);
			if (j < p-1)
				w.argvalue[j+1]=lobyte(ireg);
		}
		w.argvalue[0]=hibyte(ireg);
		for (j = n+1;j<p;j++)
			w.argvalue[j]=0;
	}

	public final void mpsdv(RefObject<VecUchar_O> w, RefObject<VecUchar_I> u, Int iv, RefObject<Int> ir)
	{
		Int i = new Int();
		Int j = new Int();
		Int n = u.argvalue.size();
		Int p = w.argvalue.size();
		Int p_min = MIN(n,p);
		ir.argvalue = 0;
		for (j = 0;j<p_min;j++)
		{
			i = 256 *ir.argvalue+u.argvalue[j];
			w.argvalue[j]=Uchar(i/iv);
			ir.argvalue = i % iv;
		}
		if (p > p_min)
			for (j = p_min;j<p;j++)
				w.argvalue[j]=0;
	}

	public final void mpneg(RefObject<VecUchar_IO> u)
	{
		Int j = new Int();
		Int n = u.argvalue.size();
		Uint ireg = 256;
		for (j = n-1;j>=0;j--)
		{
			ireg = 255-u.argvalue[j]+hibyte(ireg);
			u.argvalue[j]=lobyte(ireg);
		}
	}

	public final void mpmov(RefObject<VecUchar_O> u, RefObject<VecUchar_I> v)
	{
		Int j = new Int();
		Int n = u.argvalue.size();
		Int m = v.argvalue.size();
		Int n_min = MIN(n,m);
		for (j = 0;j<n_min;j++)
			u.argvalue[j]=v.argvalue[j];
		if (n > n_min)
			for(j = n_min;j<n-1;j++)
				u.argvalue[j]=0;
	}

	public final void mplsh(RefObject<VecUchar_IO> u)
	{
		Int j = new Int();
		Int n = u.argvalue.size();
		for (j = 0;j<n-1;j++)
			u.argvalue[j]=u.argvalue[j+1];
		u.argvalue[n-1]=0;
	}

	public final Uchar lobyte(Uint x)
	{
		return (x & 0xff);
	}
	public final Uchar hibyte(Uint x)
	{
		return ((x >> 8) & 0xff);
	}

	public final void mpmul(RefObject<VecUchar_O> w, RefObject<VecUchar_I> u, RefObject<VecUchar_I> v)
	{
		final Doub RX = 256.0;
		Int j = new Int();
		Int nn = 1;
		Int n = u.argvalue.size();
		Int m = v.argvalue.size();
		Int p = w.argvalue.size();
		Int n_max = MAX(m,n);
		Doub cy = new Doub();
		Doub t = new Doub();
		while (nn < n_max)
			nn <<= 1;
		nn <<= 1;
		VecDoub a = new VecDoub(nn,0.0);
		VecDoub b = new VecDoub(nn,0.0);
		for (j = 0;j<n;j++)
			a[j]=u.argvalue[j];
		for (j = 0;j<m;j++)
			b[j]=v.argvalue[j];
		realft(a,1);
		realft(b,1);
		b[0] *= a[0];
		b[1] *= a[1];
		for (j = 2;j<nn;j+=2)
		{
			b[j]=(t = b[j])*a[j]-b[j+1]*a[j+1];
			b[j+1]=t *a[j+1]+b[j+1]*a[j];
		}
		realft(b,-1);
		cy = 0.0;
		for (j = nn-1;j>=0;j--)
		{
			t = b[j]/(nn >> 1)+cy+0.5;
			cy = Uint(t/RX);
			b[j]=t-cy *RX;
		}
		if (cy >= RX)
			throw("cannot happen in mpmul");
		for (j = 0;j<p;j++)
			w.argvalue[j]=0;
		w.argvalue[0]=Uchar(cy);
		for (j = 1;j<MIN(n+m,p);j++)
			w.argvalue[j]=Uchar(b[j-1]);
	}
	public final void mpinv(RefObject<VecUchar_O> u, RefObject<VecUchar_I> v)
	{
		final Int MF = 4;
		final Doub BI = 1.0/256.0;
		Int i = new Int();
		Int j = new Int();
		Int n = u.argvalue.size();
		Int m = v.argvalue.size();
		Int mm = MIN(MF,m);
		Doub fu = new Doub();
		Doub fv = new Doub(v.argvalue[mm-1]);
		VecUchar s = new VecUchar(n+m);
		VecUchar r = new VecUchar(2 *n+m);
		for (j = mm-2;j>=0;j--)
		{
			fv *= BI;
			fv += v.argvalue[j];
		}
		fu = 1.0/fv;
		for (j = 0;j<n;j++)
		{
			i = (Int)fu;
			u.argvalue[j]=Uchar(i);
			fu = 256.0*(fu-i);
		}
		for (;;)
		{
			RefObject<VecUchar_O> tempRefObject = new RefObject<VecUchar_O>(s);
			mpmul(tempRefObject, u, v);
			s = tempRefObject.argvalue;
			RefObject<VecUchar_IO> tempRefObject2 = new RefObject<VecUchar_IO>(s);
			mplsh(tempRefObject2);
			s = tempRefObject2.argvalue;
			RefObject<VecUchar_IO> tempRefObject3 = new RefObject<VecUchar_IO>(s);
			mpneg(tempRefObject3);
			s = tempRefObject3.argvalue;
			s[0] += Uchar(2);
			RefObject<VecUchar_O> tempRefObject4 = new RefObject<VecUchar_O>(r);
			RefObject<VecUchar_I> tempRefObject5 = new RefObject<VecUchar_I>(s);
			mpmul(tempRefObject4, tempRefObject5, u);
			r = tempRefObject4.argvalue;
			s = tempRefObject5.argvalue;
			RefObject<VecUchar_IO> tempRefObject6 = new RefObject<VecUchar_IO>(r);
			mplsh(tempRefObject6);
			r = tempRefObject6.argvalue;
			RefObject<VecUchar_I> tempRefObject7 = new RefObject<VecUchar_I>(r);
			mpmov(u, tempRefObject7);
			r = tempRefObject7.argvalue;
			for (j = 1;j<n-1;j++)
				if (s[j] != 0)
					break;
			if (j == n-1)
				return;
		}
	}
	public final void mpdiv(RefObject<VecUchar_O> q, RefObject<VecUchar_O> r, RefObject<VecUchar_I> u, RefObject<VecUchar_I> v)
	{
		final Int MACC = 1;
		Int i = new Int();
		Int is = new Int();
		Int mm = new Int();
		Int n = u.argvalue.size();
		Int m = v.argvalue.size();
		Int p = r.argvalue.size();
		Int n_min = MIN(m,p);
		if (m > n)
			throw("Divisor longer than dividend in mpdiv");
		mm = m+MACC;
		VecUchar s = new VecUchar(mm);
		VecUchar rr = new VecUchar(mm);
		VecUchar ss = new VecUchar(mm+1);
		VecUchar qq = new VecUchar(n-m+1);
		VecUchar t = new VecUchar(n);
		RefObject<VecUchar_O> tempRefObject = new RefObject<VecUchar_O>(s);
		mpinv(tempRefObject, v);
		s = tempRefObject.argvalue;
		RefObject<VecUchar_O> tempRefObject2 = new RefObject<VecUchar_O>(rr);
		RefObject<VecUchar_I> tempRefObject3 = new RefObject<VecUchar_I>(s);
		mpmul(tempRefObject2, tempRefObject3, u);
		rr = tempRefObject2.argvalue;
		s = tempRefObject3.argvalue;
		RefObject<VecUchar_O> tempRefObject4 = new RefObject<VecUchar_O>(ss);
		RefObject<VecUchar_I> tempRefObject5 = new RefObject<VecUchar_I>(rr);
		mpsad(tempRefObject4, tempRefObject5, 1);
		ss = tempRefObject4.argvalue;
		rr = tempRefObject5.argvalue;
		RefObject<VecUchar_IO> tempRefObject6 = new RefObject<VecUchar_IO>(ss);
		mplsh(tempRefObject6);
		ss = tempRefObject6.argvalue;
		RefObject<VecUchar_IO> tempRefObject7 = new RefObject<VecUchar_IO>(ss);
		mplsh(tempRefObject7);
		ss = tempRefObject7.argvalue;
		RefObject<VecUchar_O> tempRefObject8 = new RefObject<VecUchar_O>(qq);
		RefObject<VecUchar_I> tempRefObject9 = new RefObject<VecUchar_I>(ss);
		mpmov(tempRefObject8, tempRefObject9);
		qq = tempRefObject8.argvalue;
		ss = tempRefObject9.argvalue;
		RefObject<VecUchar_I> tempRefObject10 = new RefObject<VecUchar_I>(qq);
		mpmov(q, tempRefObject10);
		qq = tempRefObject10.argvalue;
		RefObject<VecUchar_O> tempRefObject11 = new RefObject<VecUchar_O>(t);
		RefObject<VecUchar_I> tempRefObject12 = new RefObject<VecUchar_I>(qq);
		mpmul(tempRefObject11, tempRefObject12, v);
		t = tempRefObject11.argvalue;
		qq = tempRefObject12.argvalue;
		RefObject<VecUchar_IO> tempRefObject13 = new RefObject<VecUchar_IO>(t);
		mplsh(tempRefObject13);
		t = tempRefObject13.argvalue;
		RefObject<Int> tempRefObject14 = new RefObject<Int>(is);
		RefObject<VecUchar_O> tempRefObject15 = new RefObject<VecUchar_O>(t);
		RefObject<VecUchar_I> tempRefObject16 = new RefObject<VecUchar_I>(t);
		mpsub(tempRefObject14, tempRefObject15, u, tempRefObject16);
		is = tempRefObject14.argvalue;
		t = tempRefObject15.argvalue;
		t = tempRefObject16.argvalue;
		if (is != 0)
			throw("MACC too small in mpdiv");
		for (i = 0;i<n_min;i++)
			r.argvalue[i]=t[i+n-m];
		if (p>m)
			for (i = m;i<p;i++)
				r.argvalue[i]=0;
	}
	public final void mpsqrt(RefObject<VecUchar_O> w, RefObject<VecUchar_O> u, RefObject<VecUchar_I> v)
	{
		final Int MF = 3;
		final Doub BI = 1.0/256.0;
		Int i = new Int();
		Int ir = new Int();
		Int j = new Int();
		Int n = u.argvalue.size();
		Int m = v.argvalue.size();
		Int mm = MIN(m,MF);
		VecUchar r = new VecUchar(2 *n);
		VecUchar x = new VecUchar(n+m);
		VecUchar s = new VecUchar(2 *n+m);
		VecUchar t = new VecUchar(3 *n+m);
		Doub fu = new Doub();
		Doub fv = new Doub(v.argvalue[mm-1]);
		for (j = mm-2;j>=0;j--)
		{
			fv *= BI;
			fv += v.argvalue[j];
		}
		fu = 1.0/Math.sqrt(fv);
		for (j = 0;j<n;j++)
		{
			i = (Int)fu;
			u.argvalue[j]=Uchar(i);
			fu = 256.0*(fu-i);
		}
		for (;;)
		{
			RefObject<VecUchar_O> tempRefObject = new RefObject<VecUchar_O>(r);
			mpmul(tempRefObject, u, u);
			r = tempRefObject.argvalue;
			RefObject<VecUchar_IO> tempRefObject2 = new RefObject<VecUchar_IO>(r);
			mplsh(tempRefObject2);
			r = tempRefObject2.argvalue;
			RefObject<VecUchar_O> tempRefObject3 = new RefObject<VecUchar_O>(s);
			RefObject<VecUchar_I> tempRefObject4 = new RefObject<VecUchar_I>(r);
			mpmul(tempRefObject3, tempRefObject4, v);
			s = tempRefObject3.argvalue;
			r = tempRefObject4.argvalue;
			RefObject<VecUchar_IO> tempRefObject5 = new RefObject<VecUchar_IO>(s);
			mplsh(tempRefObject5);
			s = tempRefObject5.argvalue;
			RefObject<VecUchar_IO> tempRefObject6 = new RefObject<VecUchar_IO>(s);
			mpneg(tempRefObject6);
			s = tempRefObject6.argvalue;
			s[0] += Uchar(3);
			RefObject<VecUchar_O> tempRefObject7 = new RefObject<VecUchar_O>(s);
			RefObject<VecUchar_I> tempRefObject8 = new RefObject<VecUchar_I>(s);
			RefObject<Int> tempRefObject9 = new RefObject<Int>(ir);
			mpsdv(tempRefObject7, tempRefObject8, 2, tempRefObject9);
			s = tempRefObject7.argvalue;
			s = tempRefObject8.argvalue;
			ir = tempRefObject9.argvalue;
			for (j = 1;j<n-1;j++)
			{
				if (s[j] != 0)
				{
					RefObject<VecUchar_O> tempRefObject10 = new RefObject<VecUchar_O>(t);
					RefObject<VecUchar_I> tempRefObject11 = new RefObject<VecUchar_I>(s);
					mpmul(tempRefObject10, tempRefObject11, u);
					t = tempRefObject10.argvalue;
					s = tempRefObject11.argvalue;
					RefObject<VecUchar_IO> tempRefObject12 = new RefObject<VecUchar_IO>(t);
					mplsh(tempRefObject12);
					t = tempRefObject12.argvalue;
					RefObject<VecUchar_I> tempRefObject13 = new RefObject<VecUchar_I>(t);
					mpmov(u, tempRefObject13);
					t = tempRefObject13.argvalue;
					break;
				}
			}
			if (j<n-1)
				continue;
			RefObject<VecUchar_O> tempRefObject14 = new RefObject<VecUchar_O>(x);
			mpmul(tempRefObject14, u, v);
			x = tempRefObject14.argvalue;
			RefObject<VecUchar_IO> tempRefObject15 = new RefObject<VecUchar_IO>(x);
			mplsh(tempRefObject15);
			x = tempRefObject15.argvalue;
			RefObject<VecUchar_I> tempRefObject16 = new RefObject<VecUchar_I>(x);
			mpmov(w, tempRefObject16);
			x = tempRefObject16.argvalue;
			return;
		}
	}
	public final void mp2dfr(RefObject<VecUchar_IO> a, RefObject<String> s)
	{
		final Uint IAZ = 48;
		String buffer = new String(new char[4]);
		Int j = new Int();
		Int m = new Int();
	
		Int n = a.argvalue.size();
		m = (Int)(2.408 *n);
		String.format(buffer,"%d",a.argvalue[0]);
		s.argvalue = buffer;
		s.argvalue += '.';
		mplsh(a);
		for (j = 0;j<m;j++)
		{
			mpsmu(a, a, 10);
			s.argvalue += a.argvalue[0]+IAZ;
			mplsh(a);
		}
	}
	public final String mppi(Int np)
	{
		final Uint IAOFF = 48;
		final Uint MACC = 2;
		Int ir = new Int();
		Int j = new Int();
		Int n = np+MACC;
		Uchar mm = new Uchar();
		String s;
		VecUchar x = new VecUchar(n);
		VecUchar y = new VecUchar(n);
		VecUchar sx = new VecUchar(n);
		VecUchar sxi = new VecUchar(n);
		VecUchar z = new VecUchar(n);
		VecUchar t = new VecUchar(n);
		VecUchar pi = new VecUchar(n);
		VecUchar ss = new VecUchar(2 *n);
		VecUchar tt = new VecUchar(2 *n);
		t[0]=2;
		for (j = 1;j<n;j++)
			t[j]=0;
		RefObject<VecUchar_O> tempRefObject = new RefObject<VecUchar_O>(x);
		RefObject<VecUchar_O> tempRefObject2 = new RefObject<VecUchar_O>(x);
		RefObject<VecUchar_I> tempRefObject3 = new RefObject<VecUchar_I>(t);
		mpsqrt(tempRefObject, tempRefObject2, tempRefObject3);
		x = tempRefObject.argvalue;
		x = tempRefObject2.argvalue;
		t = tempRefObject3.argvalue;
		RefObject<VecUchar_O> tempRefObject4 = new RefObject<VecUchar_O>(pi);
		RefObject<VecUchar_I> tempRefObject5 = new RefObject<VecUchar_I>(t);
		RefObject<VecUchar_I> tempRefObject6 = new RefObject<VecUchar_I>(x);
		mpadd(tempRefObject4, tempRefObject5, tempRefObject6);
		pi = tempRefObject4.argvalue;
		t = tempRefObject5.argvalue;
		x = tempRefObject6.argvalue;
		RefObject<VecUchar_IO> tempRefObject7 = new RefObject<VecUchar_IO>(pi);
		mplsh(tempRefObject7);
		pi = tempRefObject7.argvalue;
		RefObject<VecUchar_O> tempRefObject8 = new RefObject<VecUchar_O>(sx);
		RefObject<VecUchar_O> tempRefObject9 = new RefObject<VecUchar_O>(sxi);
		RefObject<VecUchar_I> tempRefObject10 = new RefObject<VecUchar_I>(x);
		mpsqrt(tempRefObject8, tempRefObject9, tempRefObject10);
		sx = tempRefObject8.argvalue;
		sxi = tempRefObject9.argvalue;
		x = tempRefObject10.argvalue;
		RefObject<VecUchar_O> tempRefObject11 = new RefObject<VecUchar_O>(y);
		RefObject<VecUchar_I> tempRefObject12 = new RefObject<VecUchar_I>(sx);
		mpmov(tempRefObject11, tempRefObject12);
		y = tempRefObject11.argvalue;
		sx = tempRefObject12.argvalue;
		for (;;)
		{
			RefObject<VecUchar_O> tempRefObject13 = new RefObject<VecUchar_O>(z);
			RefObject<VecUchar_I> tempRefObject14 = new RefObject<VecUchar_I>(sx);
			RefObject<VecUchar_I> tempRefObject15 = new RefObject<VecUchar_I>(sxi);
			mpadd(tempRefObject13, tempRefObject14, tempRefObject15);
			z = tempRefObject13.argvalue;
			sx = tempRefObject14.argvalue;
			sxi = tempRefObject15.argvalue;
			RefObject<VecUchar_IO> tempRefObject16 = new RefObject<VecUchar_IO>(z);
			mplsh(tempRefObject16);
			z = tempRefObject16.argvalue;
			RefObject<VecUchar_O> tempRefObject17 = new RefObject<VecUchar_O>(x);
			RefObject<VecUchar_I> tempRefObject18 = new RefObject<VecUchar_I>(z);
			RefObject<Int> tempRefObject19 = new RefObject<Int>(ir);
			mpsdv(tempRefObject17, tempRefObject18, 2, tempRefObject19);
			x = tempRefObject17.argvalue;
			z = tempRefObject18.argvalue;
			ir = tempRefObject19.argvalue;
			RefObject<VecUchar_O> tempRefObject20 = new RefObject<VecUchar_O>(sx);
			RefObject<VecUchar_O> tempRefObject21 = new RefObject<VecUchar_O>(sxi);
			RefObject<VecUchar_I> tempRefObject22 = new RefObject<VecUchar_I>(x);
			mpsqrt(tempRefObject20, tempRefObject21, tempRefObject22);
			sx = tempRefObject20.argvalue;
			sxi = tempRefObject21.argvalue;
			x = tempRefObject22.argvalue;
			RefObject<VecUchar_O> tempRefObject23 = new RefObject<VecUchar_O>(tt);
			RefObject<VecUchar_I> tempRefObject24 = new RefObject<VecUchar_I>(y);
			RefObject<VecUchar_I> tempRefObject25 = new RefObject<VecUchar_I>(sx);
			mpmul(tempRefObject23, tempRefObject24, tempRefObject25);
			tt = tempRefObject23.argvalue;
			y = tempRefObject24.argvalue;
			sx = tempRefObject25.argvalue;
			RefObject<VecUchar_IO> tempRefObject26 = new RefObject<VecUchar_IO>(tt);
			mplsh(tempRefObject26);
			tt = tempRefObject26.argvalue;
			RefObject<VecUchar_O> tempRefObject27 = new RefObject<VecUchar_O>(tt);
			RefObject<VecUchar_I> tempRefObject28 = new RefObject<VecUchar_I>(tt);
			RefObject<VecUchar_I> tempRefObject29 = new RefObject<VecUchar_I>(sxi);
			mpadd(tempRefObject27, tempRefObject28, tempRefObject29);
			tt = tempRefObject27.argvalue;
			tt = tempRefObject28.argvalue;
			sxi = tempRefObject29.argvalue;
			RefObject<VecUchar_IO> tempRefObject30 = new RefObject<VecUchar_IO>(tt);
			mplsh(tempRefObject30);
			tt = tempRefObject30.argvalue;
			x[0]++;
			y[0]++;
			RefObject<VecUchar_O> tempRefObject31 = new RefObject<VecUchar_O>(ss);
			RefObject<VecUchar_I> tempRefObject32 = new RefObject<VecUchar_I>(y);
			mpinv(tempRefObject31, tempRefObject32);
			ss = tempRefObject31.argvalue;
			y = tempRefObject32.argvalue;
			RefObject<VecUchar_O> tempRefObject33 = new RefObject<VecUchar_O>(y);
			RefObject<VecUchar_I> tempRefObject34 = new RefObject<VecUchar_I>(tt);
			RefObject<VecUchar_I> tempRefObject35 = new RefObject<VecUchar_I>(ss);
			mpmul(tempRefObject33, tempRefObject34, tempRefObject35);
			y = tempRefObject33.argvalue;
			tt = tempRefObject34.argvalue;
			ss = tempRefObject35.argvalue;
			RefObject<VecUchar_IO> tempRefObject36 = new RefObject<VecUchar_IO>(y);
			mplsh(tempRefObject36);
			y = tempRefObject36.argvalue;
			RefObject<VecUchar_O> tempRefObject37 = new RefObject<VecUchar_O>(tt);
			RefObject<VecUchar_I> tempRefObject38 = new RefObject<VecUchar_I>(x);
			RefObject<VecUchar_I> tempRefObject39 = new RefObject<VecUchar_I>(ss);
			mpmul(tempRefObject37, tempRefObject38, tempRefObject39);
			tt = tempRefObject37.argvalue;
			x = tempRefObject38.argvalue;
			ss = tempRefObject39.argvalue;
			RefObject<VecUchar_IO> tempRefObject40 = new RefObject<VecUchar_IO>(tt);
			mplsh(tempRefObject40);
			tt = tempRefObject40.argvalue;
			RefObject<VecUchar_O> tempRefObject41 = new RefObject<VecUchar_O>(ss);
			RefObject<VecUchar_I> tempRefObject42 = new RefObject<VecUchar_I>(pi);
			RefObject<VecUchar_I> tempRefObject43 = new RefObject<VecUchar_I>(tt);
			mpmul(tempRefObject41, tempRefObject42, tempRefObject43);
			ss = tempRefObject41.argvalue;
			pi = tempRefObject42.argvalue;
			tt = tempRefObject43.argvalue;
			RefObject<VecUchar_IO> tempRefObject44 = new RefObject<VecUchar_IO>(ss);
			mplsh(tempRefObject44);
			ss = tempRefObject44.argvalue;
			RefObject<VecUchar_O> tempRefObject45 = new RefObject<VecUchar_O>(pi);
			RefObject<VecUchar_I> tempRefObject46 = new RefObject<VecUchar_I>(ss);
			mpmov(tempRefObject45, tempRefObject46);
			pi = tempRefObject45.argvalue;
			ss = tempRefObject46.argvalue;
			mm = tt[0]-1;
			for (j = 1;j < n-1;j++)
				if (tt[j] != mm)
					break;
			if (j == n-1)
			{
				RefObject<VecUchar_IO> tempRefObject47 = new RefObject<VecUchar_IO>(pi);
				RefObject<String> tempRefObject48 = new RefObject<String>(s);
				mp2dfr(tempRefObject47, tempRefObject48);
				pi = tempRefObject47.argvalue;
				s = tempRefObject48.argvalue;
				s = s.substring(0, (Int)(2.408 *np)) + s.substring((Int)(2.408 *np) + s.length());
				return s;
			}
		}
	}
}