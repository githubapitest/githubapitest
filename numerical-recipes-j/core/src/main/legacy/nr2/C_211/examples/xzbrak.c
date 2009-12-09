
/* Driver for routine zbrak */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N 100
#define NBMAX 20
#define X1 1.0
#define X2 50.0

static float fx(float x)
{
	return bessj0(x);
}

int main(void)
{
	int i,nb=NBMAX;
	float *xb1,*xb2;

	xb1=vector(1,NBMAX);
	xb2=vector(1,NBMAX);
	zbrak(fx,X1,X2,N,xb1,xb2,&nb);
	printf("\nbrackets for roots of bessj0:\n");
	printf("%21s %10s %16s %10s\n","lower","upper","f(lower)","f(upper)");
	for (i=1;i<=nb;i++)
		printf("%s %2d  %10.4f %10.4f %3s %10.4f %10.4f\n",
			"  root ",i,xb1[i],xb2[i]," ",
			fx(xb1[i]),fx(xb2[i]));
	free_vector(xb2,1,NBMAX);
	free_vector(xb1,1,NBMAX);
	return 0;
}
#undef NRANSI
