package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
public class Boxnode<Int DIM> implements Box<DIM>
{
	public Int mom = new Int();
	public Int dau1 = new Int();
	public Int dau2 = new Int();
	public Int ptlo = new Int();
	public Int pthi = new Int();
	public Boxnode()
	{
	}
	public Boxnode(Point<DIM> mylo, Point<DIM> myhi, Int mymom, Int myd1, Int myd2, Int myptlo, Int mypthi)
	{
		super(mylo, myhi);
		mom = mymom;
		dau1 = myd1;
		dau2 = myd2;
		ptlo = myptlo;
		pthi = mypthi;
	}
}