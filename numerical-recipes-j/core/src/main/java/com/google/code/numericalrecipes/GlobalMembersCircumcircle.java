package com.google.code.numericalrecipes;
public class GlobalMembersCircumcircle
{

	public static Circle circumcircle(Point<2> a, Point<2> b, Point<2> c)
	{
		Doub a0 = new Doub();
		Doub a1 = new Doub();
		Doub c0 = new Doub();
		Doub c1 = new Doub();
		Doub det = new Doub();
		Doub asq = new Doub();
		Doub csq = new Doub();
		Doub ctr0 = new Doub();
		Doub ctr1 = new Doub();
		Doub rad2 = new Doub();
		a0 = a.x[0] - b.x[0];
		a1 = a.x[1] - b.x[1];
		c0 = c.x[0] - b.x[0];
		c1 = c.x[1] - b.x[1];
		det = a0 *c1 - c0 *a1;
		if (det == 0.0)
			throw("no circle thru colinear points");
		det = 0.5/det;
		asq = a0 *a0 + a1 *a1;
		csq = c0 *c0 + c1 *c1;
		ctr0 = det*(asq *c1 - csq *a1);
		ctr1 = det*(csq *a0 - asq *c0);
		rad2 = ctr0 *ctr0 + ctr1 *ctr1;
		return new Circle(Point<2>(ctr0 + b.x[0], ctr1 + b.x[1]), Math.sqrt(rad2));
	}
}