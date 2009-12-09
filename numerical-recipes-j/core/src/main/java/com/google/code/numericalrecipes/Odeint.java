package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class Stepper>
public class Odeint<Stepper>
{
	public static final Int MAXSTP = 50000;
	public Doub EPS = new Doub();
	public Int nok = new Int();
	public Int nbad = new Int();
	public Int nvar = new Int();
	public Doub x1 = new Doub();
	public Doub x2 = new Doub();
	public Doub hmin = new Doub();
	public boolean dense;
	public VecDoub y = new VecDoub();
	public VecDoub dydx = new VecDoub();
	public VecDoub ystart;
	public Output out;
	public typename Stepper.Dtype &derivs = new typename();
	public Stepper s = new Stepper();
	public Int nstp = new Int();
	public Doub x = new Doub();
	public Doub h = new Doub();
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Odeint(RefObject<VecDoub_IO> ystartt, Doub xx1, Doub xx2, Doub atol, Doub rtol, Doub h1, Doub hminn, RefObject<Output> outt, RefObject<typename Stepper::Dtype> derivss);
	public final void integrate()
	{
		derivs(x,y,dydx);
		if (dense)
		{
			RefObject<VecDoub_I> tempRefObject = new RefObject<VecDoub_I>(y);
			RefObject<Stepper> tempRefObject2 = new RefObject<Stepper>(s);
			out.out(-1, x, tempRefObject, tempRefObject2, h);
			y = tempRefObject.argvalue;
			s = tempRefObject2.argvalue;
		}
		else
		{
			RefObject<VecDoub_I> tempRefObject3 = new RefObject<VecDoub_I>(y);
			out.save(x, tempRefObject3);
			y = tempRefObject3.argvalue;
		}
		for (nstp = 0;nstp<MAXSTP;nstp++)
		{
			if ((x+h *1.0001-x2)*(x2-x1) > 0.0)
				h = x2-x;
			s.step(h,derivs);
			if (s.hdid == h)
				++nok;
				else
					++nbad;
			if (dense)
			{
				RefObject<VecDoub_I> tempRefObject4 = new RefObject<VecDoub_I>(y);
				RefObject<Stepper> tempRefObject5 = new RefObject<Stepper>(s);
				out.out(nstp, x, tempRefObject4, tempRefObject5, s.hdid);
				y = tempRefObject4.argvalue;
				s = tempRefObject5.argvalue;
			}
			else
			{
				RefObject<VecDoub_I> tempRefObject6 = new RefObject<VecDoub_I>(y);
				out.save(x, tempRefObject6);
				y = tempRefObject6.argvalue;
			}
			if ((x-x2)*(x2-x1) >= 0.0)
			{
				for (Int i = 0;i<nvar;i++)
					ystart[i]=y[i];
				if (out.kmax > 0 && Math.abs(out.xsave[out.getCount()-1]-x2) > 100.0 *Math.abs(x2)*EPS)
				{
					RefObject<VecDoub_I> tempRefObject7 = new RefObject<VecDoub_I>(y);
					out.save(x, tempRefObject7);
					y = tempRefObject7.argvalue;
				}
				return;
			}
			if (Math.abs(s.hnext) <= hmin)
				throw("Step size too small in Odeint");
			h = s.hnext;
		}
		throw("Too many steps in routine Odeint");
	}
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class Stepper>
