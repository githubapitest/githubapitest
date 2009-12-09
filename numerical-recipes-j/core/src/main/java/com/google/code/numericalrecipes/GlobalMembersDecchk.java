package com.google.code.numericalrecipes;
public class GlobalMembersDecchk
{
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Int[][] ip = {{0,1,5,8,9,4,2,7},{1,5,8,9,4,2,7,0}, {2,7,0,1,5,8,9,4},{3,6,3,6,3,6,3,6},{4,2,7,0,1,5,8,9}, {5,8,9,4,2,7,0,1},{6,3,6,3,6,3,6,3},{7,0,1,5,8,9,4,2}, {8,9,4,2,7,0,1,5},{9,4,2,7,0,1,5,8}};
//C++ TO JAVA CONVERTER NOTE: This was formerly a static local variable declaration (not allowed in Java):
private Int[][] ij = {{0,1,2,3,4,5,6,7,8,9},{1,2,3,4,0,6,7,8,9,5}, {2,3,4,0,1,7,8,9,5,6},{3,4,0,1,2,8,9,5,6,7},{4,0,1,2,3,9,5,6,7,8}, {5,9,8,7,6,0,4,3,2,1},{6,5,9,8,7,1,0,4,3,2},{7,6,5,9,8,2,1,0,4,3}, {8,7,6,5,9,3,2,1,0,4},{9,8,7,6,5,4,3,2,1,0}};
	public static Bool decchk(String str, RefObject<Byte> ch)
	{
		byte c;
		Int j = new Int();
		Int k = 0;
		Int m = 0;
		Int n = str.length();
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Int ip[10][8]={{0,1,5,8,9,4,2,7},{1,5,8,9,4,2,7,0}, {2,7,0,1,5,8,9,4},{3,6,3,6,3,6,3,6},{4,2,7,0,1,5,8,9}, {5,8,9,4,2,7,0,1},{6,3,6,3,6,3,6,3},{7,0,1,5,8,9,4,2}, {8,9,4,2,7,0,1,5},{9,4,2,7,0,1,5,8}};
	//C++ TO JAVA CONVERTER NOTE: This static local variable declaration (not allowed in Java) has been moved just prior to the method:
	//	static Int ij[10][10]={{0,1,2,3,4,5,6,7,8,9},{1,2,3,4,0,6,7,8,9,5}, {2,3,4,0,1,7,8,9,5,6},{3,4,0,1,2,8,9,5,6,7},{4,0,1,2,3,9,5,6,7,8}, {5,9,8,7,6,0,4,3,2,1},{6,5,9,8,7,1,0,4,3,2},{7,6,5,9,8,2,1,0,4,3}, {8,7,6,5,9,3,2,1,0,4},{9,8,7,6,5,4,3,2,1,0}};
		for (j = 0;j<n;j++)
		{
			c = str.charAt(j);
			if (c >= 48 && c <= 57)
				k = ij[k][ip[(c+2) % 10][7 & m++]];
		}
		for (j = 0;j<10;j++)
			if (ij[k][ip[j][m & 7]] == 0)
				break;
		ch.argvalue = (byte)(j+48);
		return k == 0;
	}
}