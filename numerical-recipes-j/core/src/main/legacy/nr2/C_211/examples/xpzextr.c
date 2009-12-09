
/* Driver for routine pzextr */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NV 4
#define IMAXX 10

float **d,*x;   /* defining declaration */

int main(void)
{
	int i,iest,j;
	float dum,xest,*dy,*yest,*yz;

	dy=vector(1,NV);
	yest=vector(1,NV);
	yz=vector(1,NV);
	x=vector(1,IMAXX);
	d=matrix(1,IMAXX,1,IMAXX);
	/* Feed values from a rational function */
	/* fn(x)=(1-x+x**3)/(x+1)**n */
	for (i=1;i<=IMAXX;i++) {
		iest=i;
		xest=1.0/i;
		dum=1.0-xest+xest*xest*xest;
		for (j=1;j<=NV;j++) {
			dum /= (xest+1.0);
			yest[j]=dum;
		}
		pzextr(iest,xest,yest,yz,dy,NV);
		printf("\ni = %2d",i);
		printf("\nExtrap. function:");
		for (j=1;j<=NV;j++) printf("%12.6f",yz[j]);
		printf("\nEstimated error: ");
		for (j=1;j<=NV;j++) printf("%12.6f",dy[j]);
		printf("\n");
	}
	printf("\nactual values: %14.6f %11.6f %11.6f %11.6f\n",
		1.0,1.0,1.0,1.0);
	free_matrix(d,1,IMAXX,1,IMAXX);
	free_vector(x,1,IMAXX);
	free_vector(yz,1,NV);
	free_vector(yest,1,NV);
	free_vector(dy,1,NV);
	return 0;
}
#undef NRANSI
