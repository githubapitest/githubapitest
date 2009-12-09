package com.google.code.numericalrecipes;
public class GlobalMembersPsplotexample
{
	public static void psplot_example()
	{
		VecDoub x1 = new VecDoub(500);
		VecDoub x2 = new VecDoub(500);
		VecDoub y1 = new VecDoub(500);
		VecDoub y2 = new VecDoub(500);
		VecDoub y3 = new VecDoub(500);
		VecDoub y4 = new VecDoub(500);
		for (Int i = 0;i<500;i++)
		{
			x1[i] = 5.*i/499.;
			y1[i] = Math.exp(-0.5 *x1[i]);
			y2[i] = Math.exp(-0.5 *SQR(x1[i]));
			y3[i] = Math.exp(-0.5 *Math.sqrt(5.-x1[i]));
			x2[i] = Math.cos(0.062957 *i);
			y4[i] = Math.sin(0.088141 *i);
		}

		PSpage pg = new PSpage("d:\\nr3\\newchap20\\myplot.ps");
		PSplot plot1 = new PSplot(pg,100.,500.,100.,500.);
		plot1.setlimits(0., 5., 0., 1.);
		plot1.frame();
		plot1.autoscales();
		plot1.xlabel("abscissa");
		plot1.ylabel("ordinate");
	RefObject<VecDoub> tempRefObject = new RefObject<VecDoub>(x1);
	RefObject<VecDoub> tempRefObject2 = new RefObject<VecDoub>(y1);
		plot1.lineplot(tempRefObject, tempRefObject2);
		x1 = tempRefObject.argvalue;
		y1 = tempRefObject2.argvalue;
		plot1.setdash("2 4");
	RefObject<VecDoub> tempRefObject3 = new RefObject<VecDoub>(x1);
	RefObject<VecDoub> tempRefObject4 = new RefObject<VecDoub>(y2);
		plot1.lineplot(tempRefObject3, tempRefObject4);
		x1 = tempRefObject3.argvalue;
		y2 = tempRefObject4.argvalue;
		plot1.setdash("6 2 4 2");
	RefObject<VecDoub> tempRefObject5 = new RefObject<VecDoub>(x1);
	RefObject<VecDoub> tempRefObject6 = new RefObject<VecDoub>(y3);
		plot1.lineplot(tempRefObject5, tempRefObject6);
		x1 = tempRefObject5.argvalue;
		y3 = tempRefObject6.argvalue;
		plot1.setdash("");
		plot1.pointsymbol(1., Math.exp(-0.5), 72, 16.);
		plot1.pointsymbol(2., Math.exp(-1.), 108, 12.);
		plot1.pointsymbol(2., Math.exp(-2.), 115, 12.);
		plot1.label("dingbat 72",1.1,Math.exp(-0.5));
		plot1.label("dingbat 108",2.1,Math.exp(-1.));
		plot1.label("dingbat 115",2.1,Math.exp(-2.));

		PSplot plot2 = new PSplot(pg,325.,475.,325.,475.);
		plot2.clear();
		plot2.setlimits(-1.2, 1.2, -1.2, 1.2);
		plot2.frame();
		plot2.scales(1.,0.5,1.,0.5);
	RefObject<VecDoub> tempRefObject7 = new RefObject<VecDoub>(x2);
	RefObject<VecDoub> tempRefObject8 = new RefObject<VecDoub>(y4);
		plot2.lineplot(tempRefObject7, tempRefObject8);
		x2 = tempRefObject7.argvalue;
		y4 = tempRefObject8.argvalue;

		pg.close();
		pg.display();
	}
}