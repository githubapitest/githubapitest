
/* Driver for routine hypgeo */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"
#include "complex.h"

fcomplex Clog(fcomplex a)
{
	fcomplex b;

	b.r=log(Cabs(a));
	b.i=atan2(a.i,a.r);
	return b;
}

int main(void)
{
	fcomplex a,b,c,z,zi,q1,q2,q3,q4;
	float x,y;

	a=Complex(0.5,0.0);
	b=Complex(1.0,0.0);
	c=Complex(1.5,0.0);
	for (;;) {
		printf("INPUT X,Y OF COMPLEX ARGUMENT:\n");
		if (scanf("%f %f",&x,&y) == EOF) break;
		z=Complex(x,y);
		q1=hypgeo(a,b,c,Cmul(z,z));
		q2=RCmul(0.5,Cdiv(Clog(Cdiv(Cadd(b,z),Csub(b,z))),z));
		q3=hypgeo(a,b,c,RCmul(-1.0,Cmul(z,z)));
		zi=Complex(-y,x);
		q4=RCmul(0.5,Cdiv(Clog(Cdiv(Cadd(b,zi),Csub(b,zi))),zi));
		printf("2F1(0.5,1.0,1.5;z^2) =%12.6f %12.6f\n",q1.r,q1.i);
		printf("check using log form: %12.6f %12.6f\n",q2.r,q2.i);
		printf("2F1(0.5,1.0,1.5;-z^2)=%12.6f %12.6f\n",q3.r,q3.i);
		printf("check using log form: %12.6f %12.6f\n",q4.r,q4.i);
	}
	return 0;
}
#undef NRANSI
