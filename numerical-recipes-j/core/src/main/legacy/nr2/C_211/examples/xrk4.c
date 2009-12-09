
/* Driver for routine rk4 */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N 4

void derivs(float x,float y[],float dydx[])
{
	dydx[1] = -y[2];
	dydx[2]=y[1]-(1.0/x)*y[2];
	dydx[3]=y[2]-(2.0/x)*y[3];
	dydx[4]=y[3]-(3.0/x)*y[4];
}

int main(void)
{
	int i,j;
	float h,x=1.0,*y,*dydx,*yout;

	y=vector(1,N);
	dydx=vector(1,N);
	yout=vector(1,N);
	y[1]=bessj0(x);
	y[2]=bessj1(x);
	y[3]=bessj(2,x);
	y[4]=bessj(3,x);
	derivs(x,y,dydx);
	printf("\n%16s %5s %12s %12s %12s\n",
		"Bessel function:","j0","j1","j3","j4");
	for (i=1;i<=5;i++) {
		h=0.2*i;
		rk4(y,dydx,N,x,h,yout,derivs);
		printf("\nfor a step size of: %6.2f\n",h);
		printf("%12s","rk4:");
		for (j=1;j<=4;j++) printf(" %12.6f",yout[j]);
		printf("\n%12s %12.6f %12.6f %12.6f %12.6f\n","actual:",
			bessj0(x+h),bessj1(x+h),bessj(2,x+h),bessj(3,x+h));
	}
	free_vector(yout,1,N);
	free_vector(dydx,1,N);
	free_vector(y,1,N);
	return 0;
}
#undef NRANSI
