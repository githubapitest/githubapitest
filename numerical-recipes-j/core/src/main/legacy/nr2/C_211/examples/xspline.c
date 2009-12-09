
/* Driver for routine spline */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N 20
#define PI 3.1415926

int main(void)
{
	int i;
	float yp1,ypn,*x,*y,*y2;

	x=vector(1,N);
	y=vector(1,N);
	y2=vector(1,N);
	printf("\nsecond-derivatives for sin(x) from 0 to pi\n");
	/* Generate array for interpolation */
	for (i=1;i<=20;i++) {
		x[i]=i*PI/N;
		y[i]=sin(x[i]);
	}
	/* calculate 2nd derivative with spline */
	yp1=cos(x[1]);
	ypn=cos(x[N]);
	spline(x,y,N,yp1,ypn,y2);
	/* test result */
	printf("%23s %16s\n","spline","actual");
	printf("%11s %14s %16s\n","angle","2nd deriv","2nd deriv");
	for (i=1;i<=N;i++)
		printf("%10.2f %16.6f %16.6f\n",x[i],y2[i],-sin(x[i]));
	free_vector(y2,1,N);
	free_vector(y,1,N);
	free_vector(x,1,N);
	return 0;
}
#undef NRANSI
