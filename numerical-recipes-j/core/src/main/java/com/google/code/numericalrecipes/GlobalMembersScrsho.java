package com.google.code.numericalrecipes;
public class GlobalMembersScrsho
{
	public static <T> void scrsho(RefObject<T> fx)
	{
		final Int RES = 500;
		final Doub XLL = 75.;
		final Doub XUR = 525.;
		final Doub YLL = 250.;
		final Doub YUR = 700.;
		byte plotfilename = tmpnam(null);
		VecDoub xx = new VecDoub(RES);
		VecDoub yy = new VecDoub(RES);
		Doub x1 = new Doub();
		Doub x2 = new Doub();
		Int i = new Int();
		for (;;)
		{
			Doub ymax = -9.99e99;
			Doub ymin = 9.99e99;
			Doub del = new Doub();
			System.out.print("\n");
			System.out.print("Enter x1 x2 (x1=x2 to stop):");
			System.out.print("\n");
			cin >> x1 >> x2;
			if (x1 == x2)
				break;
			for (i = 0;i<RES;i++)
			{
				xx[i] = x1 + i*(x2-x1)/(RES-1.);
				yy[i] = fx.argvalue(xx[i]);
				if (yy[i] > ymax)
					ymax = yy[i];
				if (yy[i] < ymin)
					ymin = yy[i];
			}
			del = 0.05*((ymax-ymin)+(ymax == ymin != null ? Math.abs(ymax) : 0.));
			PSpage pg = new PSpage(plotfilename);
			PSplot plot = new PSplot(pg,XLL,XUR,YLL,YUR);
			plot.setlimits(x1,x2,ymin-del,ymax+del);
			plot.frame();
			plot.autoscales();
			plot.lineplot(xx,yy);
			if (ymax *ymin < 0.)
				plot.lineseg(x1,0.,x2,0.);
			plot.display();
		}
		remove(plotfilename);
	}
}
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class T>
