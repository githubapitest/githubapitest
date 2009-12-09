package com.google.code.numericalrecipes;
public class Bracketmethod
{
	public Doub ax = new Doub();
	public Doub bx = new Doub();
	public Doub cx = new Doub();
	public Doub fa = new Doub();
	public Doub fb = new Doub();
	public Doub fc = new Doub();
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template <class T>
	public final <T> void bracket(Doub a, Doub b, RefObject<T> func)
	{
		final Doub GOLD = 1.618034;
		final Doub GLIMIT = 100.0;
		final Doub TINY = 1.0e-20;
		ax = a;
		bx = b;
		Doub fu = new Doub();
		fa = func.argvalue(ax);
		fb = func.argvalue(bx);
		if (fb > fa)
		{
			SWAP(ax,bx);
			SWAP(fb,fa);
		}
		cx = bx+GOLD*(bx-ax);
		fc = func.argvalue(cx);
		while (fb > fc)
		{
			Doub r = (bx-ax)*(fb-fc);
			Doub q = (bx-cx)*(fb-fa);
			Doub u = bx-((bx-cx)*q-(bx-ax)*r)/ (2.0 *SIGN(MAX(Math.abs(q-r),TINY),q-r));
			Doub ulim = bx+GLIMIT*(cx-bx);
			if ((bx-u)*(u-cx) > 0.0)
			{
				fu = func.argvalue(u);
				if (fu < fc)
				{
					ax = bx;
					bx = u;
					fa = fb;
					fb = fu;
					return;
				}
				else if (fu > fb)
				{
					cx = u;
					fc = fu;
					return;
				}
				u = cx+GOLD*(cx-bx);
				fu = func.argvalue(u);
			}
			else if ((cx-u)*(u-ulim) > 0.0)
			{
				fu = func.argvalue(u);
				if (fu < fc)
				{
					RefObject<Doub> tempRefObject = new RefObject<Doub>(bx);
					RefObject<Doub> tempRefObject2 = new RefObject<Doub>(cx);
					RefObject<Doub> tempRefObject3 = new RefObject<Doub>(u);
					shft3(tempRefObject, tempRefObject2, tempRefObject3, u+GOLD*(u-cx));
					bx = tempRefObject.argvalue;
					cx = tempRefObject2.argvalue;
					u = tempRefObject3.argvalue;
					RefObject<Doub> tempRefObject4 = new RefObject<Doub>(fb);
					RefObject<Doub> tempRefObject5 = new RefObject<Doub>(fc);
					RefObject<Doub> tempRefObject6 = new RefObject<Doub>(fu);
					shft3(tempRefObject4, tempRefObject5, tempRefObject6, func.argvalue(u));
					fb = tempRefObject4.argvalue;
					fc = tempRefObject5.argvalue;
					fu = tempRefObject6.argvalue;
				}
			}
			else if ((u-ulim)*(ulim-cx) >= 0.0)
			{
				u = ulim;
				fu = func.argvalue(u);
			}
			else
			{
				u = cx+GOLD*(cx-bx);
				fu = func.argvalue(u);
			}
			RefObject<Doub> tempRefObject7 = new RefObject<Doub>(ax);
			RefObject<Doub> tempRefObject8 = new RefObject<Doub>(bx);
			RefObject<Doub> tempRefObject9 = new RefObject<Doub>(cx);
			shft3(tempRefObject7, tempRefObject8, tempRefObject9, u);
			ax = tempRefObject7.argvalue;
			bx = tempRefObject8.argvalue;
			cx = tempRefObject9.argvalue;
			RefObject<Doub> tempRefObject10 = new RefObject<Doub>(fa);
			RefObject<Doub> tempRefObject11 = new RefObject<Doub>(fb);
			RefObject<Doub> tempRefObject12 = new RefObject<Doub>(fc);
			shft3(tempRefObject10, tempRefObject11, tempRefObject12, fu);
			fa = tempRefObject10.argvalue;
			fb = tempRefObject11.argvalue;
			fc = tempRefObject12.argvalue;
		}
	}
	public final void shft2(RefObject<Doub> a, RefObject<Doub> b, Doub c)
	{
		a.argvalue = b.argvalue;
		b.argvalue = c;
	}
	public final void shft3(RefObject<Doub> a, RefObject<Doub> b, RefObject<Doub> c, Doub d)
	{
		a.argvalue = b.argvalue;
		b.argvalue = c.argvalue;
		c.argvalue = d;
	}
	public final void mov3(RefObject<Doub> a, RefObject<Doub> b, RefObject<Doub> c, Doub d, Doub e, Doub f)
	{
		a.argvalue = d;
		b.argvalue = e;
		c.argvalue = f;
	}
}