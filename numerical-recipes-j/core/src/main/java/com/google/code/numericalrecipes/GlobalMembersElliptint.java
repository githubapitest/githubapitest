package com.google.code.numericalrecipes;
public class GlobalMembersElliptint
{
	public static Doub rc(Doub x, Doub y)
	{
		final Doub ERRTOL = 0.0012;
		final Doub THIRD = 1.0/3.0;
		final Doub C1 = 0.3;
		final Doub C2 = 1.0/7.0;
		final Doub C3 = 0.375;
		final Doub C4 = 9.0/22.0;
		final Doub TINY = 5.0 *numeric_limits<Doub>.min();
		final Doub BIG = 0.2 *numeric_limits<Doub>.max();
		final Doub COMP1 = 2.236/Math.sqrt(TINY);
		final Doub COMP2 = SQR(TINY *BIG)/25.0;
		Doub alamb = new Doub();
		Doub ave = new Doub();
		Doub s = new Doub();
		Doub w = new Doub();
		Doub xt = new Doub();
		Doub yt = new Doub();
		if (x < 0.0 || y == 0.0 || (x+Math.abs(y)) < TINY || (x+Math.abs(y)) > BIG || (y<-COMP1 && x > 0.0 && x < COMP2))
			throw("invalid arguments in rc");
		if (y > 0.0)
		{
			xt = x;
			yt = y;
			w = 1.0;
		}
		else
		{
			xt = x-y;
			yt = -y;
			w = Math.sqrt(x)/Math.sqrt(xt);
		}
		do
		{
			alamb = 2.0 *Math.sqrt(xt)*Math.sqrt(yt)+yt;
			xt = 0.25*(xt+alamb);
			yt = 0.25*(yt+alamb);
			ave = THIRD*(xt+yt+yt);
			s = (yt-ave)/ave;
		} while (Math.abs(s) > ERRTOL);
		return w*(1.0+s *s*(C1+s*(C2+s*(C3+s *C4))))/Math.sqrt(ave);
	}
	public static Doub rd(Doub x, Doub y, Doub z)
	{
		final Doub ERRTOL = 0.0015;
		final Doub C1 = 3.0/14.0;
		final Doub C2 = 1.0/6.0;
		final Doub C3 = 9.0/22.0;
		final Doub C4 = 3.0/26.0;
		final Doub C5 = 0.25 *C3;
		final Doub C6 = 1.5 *C4;
		final Doub TINY = 2.0 *Math.pow(numeric_limits<Doub>.max(),-2./3.);
		final Doub BIG = 0.1 *ERRTOL *Math.pow(numeric_limits<Doub>.min(),-2./3.);
		Doub alamb = new Doub();
		Doub ave = new Doub();
		Doub delx = new Doub();
		Doub dely = new Doub();
		Doub delz = new Doub();
		Doub ea = new Doub();
		Doub eb = new Doub();
		Doub ec = new Doub();
		Doub ed = new Doub();
		Doub ee = new Doub();
		Doub fac = new Doub();
		Doub sqrtx = new Doub();
		Doub sqrty = new Doub();
		Doub sqrtz = new Doub();
		Doub sum = new Doub();
		Doub xt = new Doub();
		Doub yt = new Doub();
		Doub zt = new Doub();
		if (MIN(x,y) < 0.0 || MIN(x+y,z) < TINY || MAX(MAX(x,y),z) > BIG)
			throw("invalid arguments in rd");
		xt = x;
		yt = y;
		zt = z;
		sum = 0.0;
		fac = 1.0;
		do
		{
			sqrtx = Math.sqrt(xt);
			sqrty = Math.sqrt(yt);
			sqrtz = Math.sqrt(zt);
			alamb = sqrtx*(sqrty+sqrtz)+sqrty *sqrtz;
			sum += fac/(sqrtz*(zt+alamb));
			fac = 0.25 *fac;
			xt = 0.25*(xt+alamb);
			yt = 0.25*(yt+alamb);
			zt = 0.25*(zt+alamb);
			ave = 0.2*(xt+yt+3.0 *zt);
			delx = (ave-xt)/ave;
			dely = (ave-yt)/ave;
			delz = (ave-zt)/ave;
		} while (MAX(MAX(Math.abs(delx),Math.abs(dely)),Math.abs(delz)) > ERRTOL);
		ea = delx *dely;
		eb = delz *delz;
		ec = ea-eb;
		ed = ea-6.0 *eb;
		ee = ed+ec+ec;
		return 3.0 *sum+fac*(1.0+ed*(-C1+C5 *ed-C6 *delz *ee) +delz*(C2 *ee+delz*(-C3 *ec+delz *C4 *ea)))/(ave *Math.sqrt(ave));
	}
	public static Doub rf(Doub x, Doub y, Doub z)
	{
		final Doub ERRTOL = 0.0025;
		final Doub THIRD = 1.0/3.0;
		final Doub C1 = 1.0/24.0;
		final Doub C2 = 0.1;
		final Doub C3 = 3.0/44.0;
		final Doub C4 = 1.0/14.0;
		final Doub TINY = 5.0 *numeric_limits<Doub>.min();
		final Doub BIG = 0.2 *numeric_limits<Doub>.max();
		Doub alamb = new Doub();
		Doub ave = new Doub();
		Doub delx = new Doub();
		Doub dely = new Doub();
		Doub delz = new Doub();
		Doub e2 = new Doub();
		Doub e3 = new Doub();
		Doub sqrtx = new Doub();
		Doub sqrty = new Doub();
		Doub sqrtz = new Doub();
		Doub xt = new Doub();
		Doub yt = new Doub();
		Doub zt = new Doub();
		if (MIN(MIN(x,y),z) < 0.0 || MIN(MIN(x+y,x+z),y+z) < TINY || MAX(MAX(x,y),z) > BIG)
			throw("invalid arguments in rf");
		xt = x;
		yt = y;
		zt = z;
		do
		{
			sqrtx = Math.sqrt(xt);
			sqrty = Math.sqrt(yt);
			sqrtz = Math.sqrt(zt);
			alamb = sqrtx*(sqrty+sqrtz)+sqrty *sqrtz;
			xt = 0.25*(xt+alamb);
			yt = 0.25*(yt+alamb);
			zt = 0.25*(zt+alamb);
			ave = THIRD*(xt+yt+zt);
			delx = (ave-xt)/ave;
			dely = (ave-yt)/ave;
			delz = (ave-zt)/ave;
		} while (MAX(MAX(Math.abs(delx),Math.abs(dely)),Math.abs(delz)) > ERRTOL);
		e2 = delx *dely-delz *delz;
		e3 = delx *dely *delz;
		return (1.0+(C1 *e2-C2-C3 *e3)*e2+C4 *e3)/Math.sqrt(ave);
	}
	public static Doub rj(Doub x, Doub y, Doub z, Doub p)
	{
		final Doub ERRTOL = 0.0015;
		final Doub C1 = 3.0/14.0;
		final Doub C2 = 1.0/3.0;
		final Doub C3 = 3.0/22.0;
		final Doub C4 = 3.0/26.0;
		final Doub C5 = 0.75 *C3;
		final Doub C6 = 1.5 *C4;
		final Doub C7 = 0.5 *C2;
		final Doub C8 = C3+C3;
		final Doub TINY = Math.pow(5.0 *numeric_limits<Doub>.min(),1./3.);
		final Doub BIG = 0.3 *Math.pow(0.2 *numeric_limits<Doub>.max(),1./3.);
		Doub a = new Doub();
		Doub alamb = new Doub();
		Doub alpha = new Doub();
		Doub ans = new Doub();
		Doub ave = new Doub();
		Doub b = new Doub();
		Doub beta = new Doub();
		Doub delp = new Doub();
		Doub delx = new Doub();
		Doub dely = new Doub();
		Doub delz = new Doub();
		Doub ea = new Doub();
		Doub eb = new Doub();
		Doub ec = new Doub();
		Doub ed = new Doub();
		Doub ee = new Doub();
		Doub fac = new Doub();
		Doub pt = new Doub();
		Doub rcx = new Doub();
		Doub rho = new Doub();
		Doub sqrtx = new Doub();
		Doub sqrty = new Doub();
		Doub sqrtz = new Doub();
		Doub sum = new Doub();
		Doub tau = new Doub();
		Doub xt = new Doub();
		Doub yt = new Doub();
		Doub zt = new Doub();
		if (MIN(MIN(x,y),z) < 0.0 || MIN(MIN(x+y,x+z),MIN(y+z,Math.abs(p))) < TINY || MAX(MAX(x,y),MAX(z,Math.abs(p))) > BIG)
			throw("invalid arguments in rj");
		sum = 0.0;
		fac = 1.0;
		if (p > 0.0)
		{
			xt = x;
			yt = y;
			zt = z;
			pt = p;
		}
		else
		{
			xt = MIN(MIN(x,y),z);
			zt = MAX(MAX(x,y),z);
			yt = x+y+z-xt-zt;
			a = 1.0/(yt-p);
			b = a*(zt-yt)*(yt-xt);
			pt = yt+b;
			rho = xt *zt/yt;
			tau = p *pt/yt;
			rcx = rc(rho, tau);
		}
		do
		{
			sqrtx = Math.sqrt(xt);
			sqrty = Math.sqrt(yt);
			sqrtz = Math.sqrt(zt);
			alamb = sqrtx*(sqrty+sqrtz)+sqrty *sqrtz;
			alpha = SQR(pt*(sqrtx+sqrty+sqrtz)+sqrtx *sqrty *sqrtz);
			beta = pt *SQR(pt+alamb);
			sum += fac *rc(alpha, beta);
			fac = 0.25 *fac;
			xt = 0.25*(xt+alamb);
			yt = 0.25*(yt+alamb);
			zt = 0.25*(zt+alamb);
			pt = 0.25*(pt+alamb);
			ave = 0.2*(xt+yt+zt+pt+pt);
			delx = (ave-xt)/ave;
			dely = (ave-yt)/ave;
			delz = (ave-zt)/ave;
			delp = (ave-pt)/ave;
		} while (MAX(MAX(Math.abs(delx),Math.abs(dely)), MAX(Math.abs(delz),Math.abs(delp))) > ERRTOL);
		ea = delx*(dely+delz)+dely *delz;
		eb = delx *dely *delz;
		ec = delp *delp;
		ed = ea-3.0 *ec;
		ee = eb+2.0 *delp*(ea-ec);
		ans = 3.0 *sum+fac*(1.0+ed*(-C1+C5 *ed-C6 *ee)+eb*(C7+delp*(-C8+delp *C4)) +delp *ea*(C2-delp *C3)-C2 *delp *ec)/(ave *Math.sqrt(ave));
		if (p <= 0.0)
			ans = a*(b *ans+3.0*(rcx-rf(xt, yt, zt)));
		return ans;
	}
	public static Doub ellf(Doub phi, Doub ak)
	{
		Doub s = Math.sin(phi);
		return s *rf(SQR(Math.cos(phi)), (1.0-s *ak)*(1.0+s *ak), 1.0);
	}
	public static Doub elle(Doub phi, Doub ak)
	{
		Doub cc = new Doub();
		Doub q = new Doub();
		Doub s = new Doub();
		s = Math.sin(phi);
		cc = SQR(Math.cos(phi));
		q = (1.0-s *ak)*(1.0+s *ak);
		return s*(rf(cc, q, 1.0)-(SQR(s *ak))*rd(cc, q, 1.0)/3.0);
	}
	public static Doub ellpi(Doub phi, Doub en, Doub ak)
	{
		Doub cc = new Doub();
		Doub enss = new Doub();
		Doub q = new Doub();
		Doub s = new Doub();
		s = Math.sin(phi);
		enss = en *s *s;
		cc = SQR(Math.cos(phi));
		q = (1.0-s *ak)*(1.0+s *ak);
		return s*(rf(cc, q, 1.0)-enss *rj(cc, q, 1.0, 1.0+enss)/3.0);
	}
	public static void sncndn(Doub uu, Doub emmc, RefObject<Doub> sn, RefObject<Doub> cn, RefObject<Doub> dn)
	{
		final Doub CA = 1.0e-8;
		Bool bo = new Bool();
		Int i = new Int();
		Int ii = new Int();
		Int l = new Int();
		Doub a = new Doub();
		Doub b = new Doub();
		Doub c = new Doub();
		Doub d = new Doub();
		Doub emc = new Doub();
		Doub u = new Doub();
		VecDoub em = new VecDoub(13);
		VecDoub en = new VecDoub(13);
		emc = emmc;
		u = uu;
		if (emc != 0.0)
		{
			bo = (emc < 0.0);
			if (bo != null)
			{
				d = 1.0-emc;
				emc /= -1.0/d;
				u *= (d = Math.sqrt(d));
			}
			a = 1.0;
			dn.argvalue = 1.0;
			for (i = 0;i<13;i++)
			{
				l = i;
				em[i]=a;
				en[i]=(emc = Math.sqrt(emc));
				c = 0.5*(a+emc);
				if (Math.abs(a-emc) <= CA *a)
					break;
				emc *= a;
				a = c;
			}
			u *= c;
			sn.argvalue = Math.sin(u);
			cn.argvalue = Math.cos(u);
			if (sn.argvalue != 0.0)
			{
				a = cn.argvalue/sn.argvalue;
				c *= a;
				for (ii = l;ii>=0;ii--)
				{
					b = em[ii];
					a *= c;
					c *= dn.argvalue;
					dn.argvalue = (en[ii]+a)/(b+a);
					a = c/b;
				}
				a = 1.0/Math.sqrt(c *c+1.0);
				sn.argvalue = (sn.argvalue >= 0.0 ? a : -a);
				cn.argvalue = c *sn.argvalue;
			}
			if (bo != null)
			{
				a = dn.argvalue;
				dn.argvalue = cn.argvalue;
				cn.argvalue = a;
				sn.argvalue /= d;
			}
		}
		else
		{
			cn.argvalue = 1.0/Math.cosh(u);
			dn.argvalue = cn.argvalue;
			sn.argvalue = Math.tanh(u);
		}
	}
}