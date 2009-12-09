package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
public class StepperStoerm<D> implements StepperBS<D>
{
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.x;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.xold;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.y;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.dydx;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.dense;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.n;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.KMAXX;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.IMAXX;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.nseq;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.cost;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.mu;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.errfac;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.ysave;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.fsave;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.dens;
//C++ TO JAVA CONVERTER TODO TASK: There is no equivalent in JavaCode to C++ 'using' declarations which operate on base class members:
	using StepperBS<D>.neqn;
	public MatDoub ysavep = new MatDoub();
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	StepperStoerm(RefObject<VecDoub_IO> yy, RefObject<VecDoub_IO> dydxx, RefObject<Doub> xx, Doub atol, Doub rtol, boolean dens);
	public final void dy(RefObject<VecDoub_I> y, Doub htot, Int k, RefObject<VecDoub_O> yend, RefObject<Int> ipt, RefObject<D> derivs)
	{
		VecDoub ytemp = new VecDoub(n);
		Int nstep = nseq[k];
		Doub h = htot/nstep;
		Doub h2 = 2.0 *h;
		for (Int i = 0;i<neqn;i++)
		{
			ytemp[i]=y.argvalue[i];
			Int ni = neqn+i;
			ytemp[ni]=y.argvalue[ni]+h *dydx[i];
		}
		Doub xnew = x;
		Int nstp2 = nstep/2;
		for (Int nn = 1;nn<=nstp2;nn++)
		{
			if (dense && nn == (nstp2+1)/2)
			{
				for (Int i = 0;i<neqn;i++)
				{
					ysavep[k][i]=ytemp[neqn+i];
					ysave[k][i]=ytemp[i]+h *ytemp[neqn+i];
				}
			}
			for (Int i = 0;i<neqn;i++)
				ytemp[i] += h2 *ytemp[neqn+i];
			xnew += h2;
			derivs.argvalue(xnew,ytemp,yend.argvalue);
			if (dense && Math.abs(nn-(nstp2+1)/2) < k+1)
			{
				ipt.argvalue++;
				for (Int i = 0;i<neqn;i++)
					fsave[ipt.argvalue][i]=yend.argvalue[i];
			}
			if (nn != nstp2)
			{
				for (Int i = 0;i<neqn;i++)
					ytemp[neqn+i] += h2 *yend.argvalue[i];
			}
		}
		for (Int i = 0;i<neqn;i++)
		{
			Int ni = neqn+i;
			yend.argvalue[ni]=ytemp[ni]+h *yend.argvalue[i];
			yend.argvalue[i]=ytemp[i];
		}
	}
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	void prepare_dense(Doub h, RefObject<VecDoub_I> dydxnew, RefObject<VecDoub_I> ysav, RefObject<VecDoub_I> scale, Int k, RefObject<Doub> error);
	public final Doub dense_out(Int i, Doub x, Doub h)
	{
		Doub theta = (x-xold)/h;
		Doub theta1 = 1.0-theta;
		Int neqn = n/2;
		if (i>=neqn)
			throw("no dense output for y' in StepperStoerm");
		Doub yinterp = dens[i]+theta*(dens[neqn+i]+theta1*(dens[2 *neqn+i]+ theta*(dens[3 *neqn+i]+theta1*(dens[4 *neqn+i]*theta+ dens[5 *neqn+i]*theta1))));
		if (mu<0)
			return yinterp;
		Doub theta05 = theta-0.5;
		Doub t4 = theta *theta1;
		Doub c = dens[neqn*(mu+6)+i];
		for (Int j = mu;j>0; j--)
			c = dens[neqn*(j+5)+i]+c *theta05/j;
		yinterp += t4 *t4 *t4 *c;
		return yinterp;
	}
	public final void dense_interp(Int n, RefObject<VecDoub_IO> y, Int imit)
	{
		Doub y0 = new Doub();
		Doub y1 = new Doub();
		Doub yp0 = new Doub();
		Doub yp1 = new Doub();
		Doub ypp0 = new Doub();
		Doub ypp1 = new Doub();
		Doub ydiff = new Doub();
		Doub ah = new Doub();
		Doub bh = new Doub();
		Doub ch = new Doub();
		Doub dh = new Doub();
		Doub eh = new Doub();
		Doub fh = new Doub();
		Doub gh = new Doub();
		Doub abh = new Doub();
		Doub gfh = new Doub();
		Doub gmf = new Doub();
		Doub ph0 = new Doub();
		Doub ph1 = new Doub();
		Doub ph2 = new Doub();
		Doub ph3 = new Doub();
		Doub ph4 = new Doub();
		Doub ph5 = new Doub();
		Doub fc1 = new Doub();
		Doub fc2 = new Doub();
		Doub fc3 = new Doub();
		VecDoub a = new VecDoub(41);
		for (Int i = 0; i<n; i++)
		{
			y0 = y.argvalue[i];
			y1 = y.argvalue[3 *n+i];
			yp0 = y.argvalue[n+i];
			yp1 = y.argvalue[4 *n+i];
			ypp0 = y.argvalue[2 *n+i]/2.0;
			ypp1 = y.argvalue[5 *n+i]/2.0;
			ydiff = y1-y0;
			ah = ydiff-yp0;
			bh = yp1-ydiff;
			ch = ah-ypp0;
			dh = bh-ah;
			eh = ypp1-bh;
			fh = dh-ch;
			gh = eh-dh;
			y.argvalue[n+i]=ydiff;
			y.argvalue[2 *n+i]=-ah;
			y.argvalue[3 *n+i]=-dh;
			y.argvalue[4 *n+i]=gh;
			y.argvalue[5 *n+i]=fh;
			if (imit < 0)
				continue;
			abh = ah+bh;
			gfh = gh+fh;
			gmf = gh-fh;
			ph0 = 0.5*(y0+y1+0.25*(-abh+0.25 *gfh));
			ph1 = ydiff+0.25*(ah-bh+0.25 *gmf);
			ph2 = abh-0.5 *gfh;
			ph3 = 6.0*(bh-ah)-3.0 *gmf;
			ph4 = 12.0 *gfh;
			ph5 = 120.0 *gmf;
			if (imit >= 1)
			{
				a[1]=64.0*(y.argvalue[7 *n+i]-ph1);
				if (imit >= 3)
				{
					a[3]=64.0*(y.argvalue[9 *n+i]-ph3+a[1]*9.0/8.0);
					if (imit >= 5)
					{
						a[5]=64.0*(y.argvalue[11 *n+i]-ph5+a[3]*15.0/4.0-a[1]*90.0);
						for (Int im = 7; im <=imit; im+=2)
						{
							fc1 = im*(im-1)*3.0/16.0;
							fc2 = fc1*(im-2)*(im-3)*4.0;
							fc3 = im*(im-1)*(im-2)*(im-3)*(im-4)*(im-5);
							a[im]=64.0*(y.argvalue[(im+6)*n+i]+fc1 *a[im-2]-fc2 *a[im-4]+fc3 *a[im-6]);
						}
					}
				}
			}
			a[0]=64.0*(y.argvalue[6 *n+i]-ph0);
			if (imit >= 2)
			{
				a[2]=64.0*(y.argvalue[n *8+i]-ph2+a[0]*3.0/8.0);
				if (imit >= 4)
				{
					a[4]=64.0*(y.argvalue[n *10+i]-ph4+a[2]*9.0/4.0-a[0]*18.0);
					for (Int im = 6; im<=imit; im+=2)
					{
						fc1 = im*(im-1)*3.0/16.0;
						fc2 = fc1*(im-2)*(im-3)*4.0;
						fc3 = im*(im-1)*(im-2)*(im-3)*(im-4)*(im-5);
						a[im]=64.0*(y.argvalue[n*(im+6)+i]+a[im-2]*fc1-a[im-4]*fc2+a[im-6]*fc3);
					}
				}
			}
			for (Int im = 0; im<=imit; im++)
				y.argvalue[n*(im+6)+i]=a[im];
		}
	}
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class D>
