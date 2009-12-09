package com.google.code.numericalrecipes;
public class KDtree<Int DIM>
{
	public final Doub BIG = new Doub();
	public Int nboxes = new Int();
	public Int npts = new Int();
	public java.util.ArrayList< Point<DIM> > ptss;
	public Boxnode<DIM> boxes;
	public VecInt ptindx = new VecInt();
	public VecInt rptindx = new VecInt();
	public Doub coords;
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	KDtree(RefObject<java.util.ArrayList< Point<DIM> >> pts);
	public void Dispose()
	{
		boxes = null;
	}
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Doub disti(Int jpt, Int kpt);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Int locate(Point<DIM> pt);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Int locate(Int jpt);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Int nearest(Int jpt);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Int nearest(Point<DIM> pt);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	void nnearest(Int jpt, RefObject<Int> nn, RefObject<Doub> dn, Int n);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	static void sift_down(RefObject<Doub> heap, RefObject<Int> ndx, Int nn);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Int locatenear(Point<DIM> pt, Doub r, RefObject<Int> list, Int nmax);
}
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>

//C++ TO JAVA CONVERTER TODO TASK: The following statement was not recognized, possibly due to an unrecognized macro:
const Doub KDtree<DIM>.BIG(1.0e99);
//C++ TO JAVA CONVERTER TODO TASK: C++ template specifiers with non-type parameters cannot be converted to Java:
//ORIGINAL LINE: template<Int DIM>
