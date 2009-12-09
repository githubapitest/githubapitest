package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
public class Box<Int DIM>
{
	public Point<DIM> lo = new Point<DIM>();
	public Point<DIM> hi = new Point<DIM>();
	public Box()
	{
	}
	public Box(Point<DIM> mylo, Point<DIM> myhi)
	{
		lo = new Point(mylo);
		hi = new Point(myhi);
	}
}
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
