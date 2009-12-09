package com.google.code.numericalrecipes;
public class GlobalMembersVegas
{
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Int i = new Int();
Int it = new Int();
Int j = new Int();
Int k = new Int();
Int mds = new Int();
Int nd = new Int();
Int ndo = new Int();
Int ng = new Int();
Int npg = new Int();
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Doub calls = new Doub();
Doub dv2g = new Doub();
Doub dxg = new Doub();
Doub f = new Doub();
Doub f2 = new Doub();
Doub f2b = new Doub();
Doub fb = new Doub();
Doub rc = new Doub();
Doub ti = new Doub();
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Doub tsi = new Doub();
Doub wgt = new Doub();
Doub xjac = new Doub();
Doub xn = new Doub();
Doub xnd = new Doub();
Doub xo = new Doub();
Doub schi = new Doub();
Doub si = new Doub();
Doub swgt = new Doub();
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private VecInt ia = new VecInt(MXDIM);
VecInt kg = new VecInt(MXDIM);
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private VecDoub dt = new VecDoub(MXDIM);
VecDoub dx = new VecDoub(MXDIM);
VecDoub r = new VecDoub(NDMX);
VecDoub x = new VecDoub(MXDIM);
VecDoub xin = new VecDoub(NDMX);
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private MatDoub d = new MatDoub(NDMX,MXDIM);
MatDoub di = new MatDoub(NDMX,MXDIM);
MatDoub xi = new MatDoub(MXDIM,NDMX);
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Ran ran_vegas = new Ran(RANSEED);
	public static void vegas(RefObject<VecDoub_I> regn, Doub fxn(VecDoub_I , Doub), Int init, Int ncall, Int itmx, Int nprn, RefObject<Doub> tgral, RefObject<Doub> sd, RefObject<Doub> chi2a)
	{
		final Int NDMX = 50;
		final Int MXDIM = 10;
		final Int RANSEED = 5330;
		final Doub ALPH = 1.5;
		final Doub TINY = 1.0e-30;
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Int i,it,j,k,mds,nd,ndo,ng,npg;
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Doub calls,dv2g,dxg,f,f2,f2b,fb,rc,ti;
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Doub tsi,wgt,xjac,xn,xnd,xo,schi,si,swgt;
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static VecInt ia(MXDIM),kg(MXDIM);
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static VecDoub dt(MXDIM),dx(MXDIM),r(NDMX),x(MXDIM),xin(NDMX);
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static MatDoub d(NDMX,MXDIM),di(NDMX,MXDIM),xi(MXDIM,NDMX);
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Ran ran_vegas(RANSEED);

		Int ndim = regn.argvalue.size()/2;
		if (init <= 0)
		{
			mds = ndo = 1;
			for (j = 0;j<ndim;j++)
				xi[j][0]=1.0;
		}
		if (init <= 1)
			si = swgt = schi = 0.0;
		if (init <= 2)
		{
			nd = NDMX;
			ng = 1;
			if (mds != 0)
			{
				ng = (Int)(Math.pow(ncall/2.0+0.25,1.0/ndim));
				mds = 1;
				if ((2 *ng-NDMX) >= 0)
				{
					mds = -1;
					npg = ng/NDMX+1;
					nd = ng/npg;
					ng = npg *nd;
				}
			}
			for (k = 1,i = 0;i<ndim;i++)
				k *= ng;
			npg = MAX((Int)(ncall/k),2);
			calls = Doub(npg)*Doub(k);
			dxg = 1.0/ng;
			for (dv2g = 1,i = 0;i<ndim;i++)
				dv2g *= dxg;
			dv2g = SQR(calls *dv2g)/npg/npg/(npg-1.0);
			xnd = nd;
			dxg *= xnd;
			xjac = 1.0/calls;
			for (j = 0;j<ndim;j++)
			{
				dx[j]=regn.argvalue[j+ndim]-regn.argvalue[j];
				xjac *= dx[j];
			}
			if (nd != ndo)
			{
				for (i = 0;i<MAX(nd,ndo);i++)
					r[i]=1.0;
				for (j = 0;j<ndim;j++)
					rebin(ndo/xnd,nd,r,xin,xi,j);
				ndo = nd;
			}
			if (nprn >= 0)
			{
				System.out.print(" Input parameters for vegas");
				System.out.print("  ndim= ");
				System.out.printf("%4d", ndim);
				System.out.printf("%4d", "  ncall= ");
				System.out.printf("%8d", calls);
				System.out.printf("%8d", "\n");
				System.out.printf("%34d", "  it=");
				System.out.printf("%5d", it);
				System.out.printf("%5d", "  itmx=");
				System.out.printf("%5d", itmx);
				System.out.printf("%5d", "\n");
				System.out.printf("%34d", "  nprn=");
				System.out.printf("%5d", nprn);
				System.out.printf("%5d", "  ALPH=");
				System.out.printf("%9d", ALPH);
				System.out.printf("%9d", "\n");
				System.out.printf("%34d", "  mds=");
				System.out.printf("%5d", mds);
				System.out.printf("%5d", "  nd=");
				System.out.printf("%5d", nd);
				System.out.printf("%5d", "\n");
				for (j = 0;j<ndim;j++)
				{
					System.out.printf("%30d", " x1[");
					System.out.printf("%2d", j);
					System.out.printf("%2d", "]= ");
					System.out.printf("%11d", regn.argvalue[j]);
					System.out.printf("%11d", " xu[");
					System.out.printf("%2d", j);
					System.out.printf("%2d", "]= ");
					System.out.printf("%11d", regn.argvalue[j+ndim]);
					System.out.printf("%11d", "\n");
				}
			}
		}
		for (it = 0;it<itmx;it++)
		{
			ti = tsi = 0.0;
			for (j = 0;j<ndim;j++)
			{
				kg[j]=1;
				for (i = 0;i<nd;i++)
					d[i][j]=di[i][j]=0.0;
			}
			for (;;)
			{
				fb = f2b = 0.0;
				for (k = 0;k<npg;k++)
				{
					wgt = xjac;
					for (j = 0;j<ndim;j++)
					{
						xn = (kg[j]-ran_vegas.doub())*dxg+1.0;
						ia[j]=MAX(MIN((Int)xn,NDMX),1);
						if (ia[j] > 1)
						{
							xo = xi[j][ia[j]-1]-xi[j][ia[j]-2];
							rc = xi[j][ia[j]-2]+(xn-ia[j])*xo;
						}
						else
						{
							xo = xi[j][ia[j]-1];
							rc = (xn-ia[j])*xo;
						}
						x[j]=regn.argvalue[j]+rc *dx[j];
						wgt *= xo *xnd;
					}
					f = wgt *fxn(x,wgt);
					f2 = f *f;
					fb += f;
					f2b += f2;
					for (j = 0;j<ndim;j++)
					{
						di[ia[j]-1][j] += f;
						if (mds >= 0)
							d[ia[j]-1][j] += f2;
					}
				}
				f2b = Math.sqrt(f2b *npg);
				f2b = (f2b-fb)*(f2b+fb);
				if (f2b <= 0.0)
					f2b = TINY;
				ti += fb;
				tsi += f2b;
				if (mds < 0)
				{
					for (j = 0;j<ndim;j++)
						d[ia[j]-1][j] += f2b;
				}
				for (k = ndim-1;k>=0;k--)
				{
					kg[k] %= ng;
					if (++kg[k] != 1)
						break;
				}
				if (k < 0)
					break;
			}
			tsi *= dv2g;
			wgt = 1.0/tsi;
			si += wgt *ti;
			schi += wgt *ti *ti;
			swgt += wgt;
			tgral.argvalue = si/swgt;
			chi2a.argvalue = (schi-si *tgral.argvalue)/(it+0.0001);
			if (chi2a.argvalue < 0.0)
				chi2a.argvalue = 0.0;
			sd.argvalue = Math.sqrt(1.0/swgt);
			tsi = Math.sqrt(tsi);
			if (nprn >= 0)
			{
				System.out.printf("%11d", " iteration no. ");
				System.out.printf("%3d", (it+1));
				System.out.printf("%3d", " : integral = ");
				System.out.printf("%14d", ti);
				System.out.printf("%14d", " +/- ");
				System.out.printf("%9d", tsi);
				System.out.printf("%9d", "\n");
				System.out.printf("%9d", " all iterations:  ");
				System.out.printf("%9d", " integral =");
				System.out.printf("%14d", tgral.argvalue);
				System.out.printf("%14d", "+-");
				System.out.printf("%9d", sd.argvalue);
				System.out.printf("%9d", " chi**2/IT n =");
				System.out.printf("%9d", chi2a.argvalue);
				System.out.printf("%9d", "\n");
				if (nprn != 0)
				{
					for (j = 0;j<ndim;j++)
					{
						System.out.printf("%9d", " DATA FOR axis  ");
						System.out.printf("%2d", j);
						System.out.printf("%2d", "\n");
						System.out.printf("%2d", "     X      delta i          X      delta i");
						System.out.printf("%2d", "          X       deltai");
						System.out.printf("%2d", "\n");
						for (i = nprn/2;i<nd-2;i += nprn+2)
						{
							System.out.printf("%8d", xi[j][i]);
							System.out.printf("%12d", di[i][j]);
							System.out.printf("%12d", xi[j][i+1]);
							System.out.printf("%12d", di[i+1][j]);
							System.out.printf("%12d", xi[j][i+2]);
							System.out.printf("%12d", di[i+2][j]);
							System.out.printf("%12d", "\n");
						}
					}
				}
			}
			for (j = 0;j<ndim;j++)
			{
				xo = d[0][j];
				xn = d[1][j];
				d[0][j]=(xo+xn)/2.0;
				dt[j]=d[0][j];
				for (i = 2;i<nd;i++)
				{
					rc = xo+xn;
					xo = xn;
					xn = d[i][j];
					d[i-1][j] = (rc+xn)/3.0;
					dt[j] += d[i-1][j];
				}
				d[nd-1][j]=(xo+xn)/2.0;
				dt[j] += d[nd-1][j];
			}
			for (j = 0;j<ndim;j++)
			{
				rc = 0.0;
				for (i = 0;i<nd;i++)
				{
					if (d[i][j] < TINY)
						d[i][j]=TINY;
					r[i]=Math.pow((1.0-d[i][j]/dt[j])/ (Math.log(dt[j])-Math.log(d[i][j])),ALPH);
					rc += r[i];
				}
				rebin(rc/xnd,nd,r,xin,xi,j);
			}
		}
	}
}