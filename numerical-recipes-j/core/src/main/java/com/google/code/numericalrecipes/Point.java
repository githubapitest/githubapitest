package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
public class Point<Int DIM>
{
	public Doub[] x = new Doub[DIM];
	public Point(Point p)
	{
		for (Int i = 0; i<DIM; i++)
			x[i] = p.x[i];
	}
//C++ TO JAVA CONVERTER NOTE: This 'copyFrom' method was converted from the original C++ copy assignment operator:
//ORIGINAL LINE: Point& operator = (const Point &p)
	public final Point copyFrom (Point p)
	{
		for (Int i = 0; i<DIM; i++)
			x[i] = p.x[i];
		return this;
	}
//C++ TO JAVA CONVERTER WARNING: 'const' methods are not available in Java:
//ORIGINAL LINE: boolean operator == (const Point &p) const
//C++ TO JAVA CONVERTER TODO TASK: Operators cannot be overloaded in Java:
	boolean operator == (Point p)
	{
		for (Int i = 0; i<DIM; i++)
			if (x[i] != p.x[i])
				return false;
		return true;
	}
	public Point(Doub x0, Doub x1)
	{
		this(x0, x1, 0.0);
	}
	public Point(Doub x0)
	{
		this(x0, 0.0, 0.0);
	}
	public Point()
	{
		this(0.0, 0.0, 0.0);
	}
//C++ TO JAVA CONVERTER NOTE: Java does not allow default values for parameters. Overloaded methods are inserted above.
//ORIGINAL LINE: Point(Doub x0 = 0.0, Doub x1 = 0.0, Doub x2 = 0.0)
	public Point(Doub x0, Doub x1, Doub x2)
	{
		x[0] = x0;
		if (DIM > 1)
			x[1] = x1;
		if (DIM > 2)
			x[2] = x2;
		if (DIM > 3)
			throw("Point not implemented for DIM > 3");
	}
}