package com.google.code.numericalrecipes;
public class GlobalMembersInterp_2d
{
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Int[] wt_d = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, -3, 0, 0, 3, 0, 0, 0, 0,-2, 0, 0,-1, 0, 0, 0, 0, 2, 0, 0,-2, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,-3, 0, 0, 3, 0, 0, 0, 0,-2, 0, 0,-1, 0, 0, 0, 0, 2, 0, 0,-2, 0, 0, 0, 0, 1, 0, 0, 1, -3, 3, 0, 0,-2,-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-3, 3, 0, 0,-2,-1, 0, 0, 9,-9, 9,-9, 6, 3,-3,-6, 6,-6,-3, 3, 4, 2, 1, 2, -6, 6,-6, 6,-4,-2, 2, 4,-3, 3, 3,-3,-2,-1,-1,-2, 2,-2, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2,-2, 0, 0, 1, 1, 0, 0, -6, 6,-6, 6,-3,-3, 3, 3,-4, 4, 2,-2,-2,-2,-1,-1, 4,-4, 4,-4, 2, 2,-2,-2, 2,-2,-2, 2, 1, 1, 1, 1};
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private MatInt wt = new MatInt(16,16,wt_d);
	public static void bcucof(RefObject<VecDoub_I> y, RefObject<VecDoub_I> y1, RefObject<VecDoub_I> y2, RefObject<VecDoub_I> y12, Doub d1, Doub d2, RefObject<MatDoub_O> c)
	{
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Int wt_d[16 *16]= {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, -3, 0, 0, 3, 0, 0, 0, 0,-2, 0, 0,-1, 0, 0, 0, 0, 2, 0, 0,-2, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,-3, 0, 0, 3, 0, 0, 0, 0,-2, 0, 0,-1, 0, 0, 0, 0, 2, 0, 0,-2, 0, 0, 0, 0, 1, 0, 0, 1, -3, 3, 0, 0,-2,-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-3, 3, 0, 0,-2,-1, 0, 0, 9,-9, 9,-9, 6, 3,-3,-6, 6,-6,-3, 3, 4, 2, 1, 2, -6, 6,-6, 6,-4,-2, 2, 4,-3, 3, 3,-3,-2,-1,-1,-2, 2,-2, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2,-2, 0, 0, 1, 1, 0, 0, -6, 6,-6, 6,-3,-3, 3, 3,-4, 4, 2,-2,-2,-2,-1,-1, 4,-4, 4,-4, 2, 2,-2,-2, 2,-2,-2, 2, 1, 1, 1, 1};
		Int l = new Int();
		Int k = new Int();
		Int j = new Int();
		Int i = new Int();
		Doub xx = new Doub();
		Doub d1d2 = d1 *d2;
		VecDoub cl = new VecDoub(16);
		VecDoub x = new VecDoub(16);
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static MatInt wt(16,16,wt_d);
		for (i = 0;i<4;i++)
		{
			x[i]=y.argvalue[i];
			x[i+4]=y1.argvalue[i]*d1;
			x[i+8]=y2.argvalue[i]*d2;
			x[i+12]=y12.argvalue[i]*d1d2;
		}
		for (i = 0;i<16;i++)
		{
			xx = 0.0;
			for (k = 0;k<16;k++)
				xx += wt[i][k]*x[k];
			cl[i]=xx;
		}
		l = 0;
		for (i = 0;i<4;i++)
			for (j = 0;j<4;j++)
				c.argvalue[i][j]=cl[l++];
	}
	public static void bcuint(RefObject<VecDoub_I> y, RefObject<VecDoub_I> y1, RefObject<VecDoub_I> y2, RefObject<VecDoub_I> y12, Doub x1l, Doub x1u, Doub x2l, Doub x2u, Doub x1, Doub x2, RefObject<Doub> ansy, RefObject<Doub> ansy1, RefObject<Doub> ansy2)
	{
		Int i = new Int();
		Doub t = new Doub();
		Doub u = new Doub();
		Doub d1 = x1u-x1l;
		Doub d2 = x2u-x2l;
		MatDoub c = new MatDoub(4,4);
	RefObject<MatDoub_O> tempRefObject = new RefObject<MatDoub_O>(c);
		bcucof(y, y1, y2, y12, d1, d2, tempRefObject);
		c = tempRefObject.argvalue;
		if (x1u == x1l || x2u == x2l)
			throw("Bad input in routine bcuint");
		t = (x1-x1l)/d1;
		u = (x2-x2l)/d2;
		ansy.argvalue = ansy2.argvalue = ansy1.argvalue = 0.0;
		for (i = 3;i>=0;i--)
		{
			ansy.argvalue = t *ansy.argvalue+((c[i][3]*u+c[i][2])*u+c[i][1])*u+c[i][0];
			ansy2.argvalue = t *ansy2.argvalue+(3.0 *c[i][3]*u+2.0 *c[i][2])*u+c[i][1];
			ansy1.argvalue = u *ansy1.argvalue+(3.0 *c[3][i]*t+2.0 *c[2][i])*t+c[1][i];
		}
		ansy1.argvalue /= d1;
		ansy2.argvalue /= d2;
	}
}