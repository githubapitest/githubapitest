package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
public class Sphcirc<Int DIM>
{
	public Point<DIM> center = new Point<DIM>();
	public Doub radius = new Doub();
	public Sphcirc()
	{
	}
	public Sphcirc(Point<DIM> mycenter, Doub myradius)
	{
		center = mycenter;
		radius = myradius;
	}
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean operator == (const Sphcirc &s) const
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	boolean operator == (Sphcirc s)
	{
		return (radius == s.radius && center == s.center);
	}
	public final Int isinbox(Box<DIM> box)
	{
		for (Int i = 0; i<DIM; i++)
		{
			if ((center.x[i] - radius < box.lo.x[i]) || (center.x[i] + radius > box.hi.x[i]))
				return 0;
		}
		return 1;
	}
	public final Int contains(Point<DIM> point)
	{
		if (dist(point,center) > radius)
			return 0;
		else
			return 1;
	}
	public final Int collides(Sphcirc<DIM> circ)
	{
		if (dist(circ.center,center) > circ.radius+radius)
			return 0;
		else
			return 1;
	}
}