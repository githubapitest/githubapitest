
/* Driver for routine mmid */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NVAR 4
#define X1 1.0
#define HTOT 0.5

void derivs(float x,float y[],float dydx[])
{
	dydx[1] = -y[2];
	dydx[2]=y[1]-(1.0/x)*y[2];
	dydx[3]=y[2]-(2.0/x)*y[3];
	dydx[4]=y[3]-(3.0/x)*y[4];
}

int main(void)
{
	int i;
	float b1,b2,b3,b4,xf=X1+HTOT,*y,*yout,*dydx;

	y=vector(1,NVAR);
	yout=vector(1,NVAR);
	dydx=vector(1,NVAR);
	y[1]=bessj0(X1);
	y[2]=bessj1(X1);
	y[3]=bessj(2,X1);
	y[4]=bessj(3,X1);
	derivs(X1,y,dydx);
	b1=bessj0(xf);
	b2=bessj1(xf);
	b3=bessj(2,xf);
	b4=bessj(3,xf);
	printf("First four Bessel functions:\n");
	for (i=5;i<=50;i+=5) {
		mmid(y,dydx,NVAR,X1,HTOT,i,yout,derivs);
		printf("\n%s %5.2f %s %5.2f %s %2d %s \n",
			"x=",X1," to ",X1+HTOT," in ",i," steps");
		printf("%14s %9s\n","integration","bessj");
		printf("%12.6f %12.6f\n",yout[1],b1);
		printf("%12.6f %12.6f\n",yout[2],b2);
		printf("%12.6f %12.6f\n",yout[3],b3);
		printf("%12.6f %12.6f\n",yout[4],b4);
		printf("\nPress RETURN to continue...\n");
		(void) getchar();
	}
	free_vector(dydx,1,NVAR);
	free_vector(yout,1,NVAR);
	free_vector(y,1,NVAR);
	return 0;
}
#undef NRANSI
