
/* Driver for routine gauleg */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NPOINT 10
#define X1 0.0
#define X2 1.0
#define X3 10.0

float func(float x)
{
	return x*exp(-x);
}

int main(void)
{
	int i;
	float xx=0.0;
	float *x,*w;

	x=vector(1,NPOINT);
	w=vector(1,NPOINT);
	gauleg(X1,X2,x,w,NPOINT);
	printf("\n%2s %10s %12s\n","#","x[i]","w[i]");
	for (i=1;i<=NPOINT;i++)
		printf("%2d %12.6f %12.6f\n",i,x[i],w[i]);
	/* Demonstrate the use of gauleg for integration */
	gauleg(X1,X3,x,w,NPOINT);
	for (i=1;i<=NPOINT;i++)
		xx += (w[i]*func(x[i]));
	printf("\nIntegral from GAULEG: %12.6f\n",xx);
	printf("Actual value: %12.6f\n",
		(1.0+X1)*exp(-X1)-(1.0+X3)*exp(-X3));
	free_vector(w,1,NPOINT);
	free_vector(x,1,NPOINT);
	return 0;
}
#undef NRANSI
