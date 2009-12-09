package com.google.code.numericalrecipes;
//C++ TO JAVA CONVERTER TODO TASK: The original C++ template specifier was replaced with a Java generic specifier, which may not produce the same behavior:
//ORIGINAL LINE: template<class elT, Int DIM>
public class Qotree<elT, Int DIM>
{
	public static final Int PMAX = 32/DIM;
	public static final Int QO = (1 << DIM);
	public static final Int QL = (QO - 2);
	public Int maxd = new Int();
	public Doub[] blo = new Doub[DIM];
	public Doub[] bscale = new Doub[DIM];
	public Mhash<Int,elT,Hashfn1> elhash = new Mhash<Int,elT,Hashfn1>();
	public Hash<Int,Int,Hashfn1> pophash = new Hash<Int,Int,Hashfn1>();
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Qotree(Int nh, Int nv, Int maxdep);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	void setouterbox(Point<DIM> lo, Point<DIM> hi);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Box<DIM> qobox(Int k);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Int qowhichbox(elT tobj);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Int qostore(elT tobj);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Int qoerase(elT tobj);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Int qoget(Int k, RefObject<elT> list, Int nmax);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Int qodump(RefObject<Int> k, RefObject<elT> list, Int nmax);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Int qocontainspt(Point<DIM> pt, RefObject<elT> list, Int nmax);
//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
//	Int qocollides(elT qt, RefObject<elT> list, Int nmax);
}