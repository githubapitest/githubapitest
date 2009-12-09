package com.google.code.numericalrecipes;
public class GlobalMembersQroot
{
	public static void qroot(RefObject<VecDoub_I> p, RefObject<Doub> b, RefObject<Doub> c, Doub eps)
	{
		final Int ITMAX = 20;
		final Doub TINY = 1.0e-14;
		Doub sc = new Doub();
		Doub sb = new Doub();
		Doub s = new Doub();
		Doub rc = new Doub();
		Doub rb = new Doub();
		Doub r = new Doub();
		Doub dv = new Doub();
		Doub delc = new Doub();
		Doub delb = new Doub();
		Int n = p.argvalue.size()-1;
		VecDoub d = new VecDoub(3);
		VecDoub q = new VecDoub(n+1);
		VecDoub qq = new VecDoub(n+1);
		VecDoub rem = new VecDoub(n+1);
		d[2]=1.0;
		for (Int iter = 0;iter<ITMAX;iter++)
		{
			d[1]=b.argvalue;
			d[0]=c.argvalue;
			poldiv(p.argvalue,d,q,rem);
			s = rem[0];
			r = rem[1];
			poldiv(q,d,qq,rem);
			sb = -c.argvalue*(rc = -rem[1]);
			rb = -b.argvalue *rc+(sc = -rem[0]);
			dv = 1.0/(sb *rc-sc *rb);
			delb = (r *sc-s *rc)*dv;
			delc = (-r *sb+s *rb)*dv;
			b.argvalue += (delb = (r *sc-s *rc)*dv);
			c.argvalue += (delc = (-r *sb+s *rb)*dv);
			if ((Math.abs(delb) <= eps *Math.abs(b.argvalue) || Math.abs(b.argvalue) < TINY) && (Math.abs(delc) <= eps *Math.abs(c.argvalue) || Math.abs(c.argvalue) < TINY))
			{
				return;
			}
		}
		throw("Too many iterations in routine qroot");
	}
}