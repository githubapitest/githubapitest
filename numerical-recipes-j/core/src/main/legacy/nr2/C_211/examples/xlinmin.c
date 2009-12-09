
/* Driver for routine linmin */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NDIM 3
#define PIO2 1.5707963

float func(float x[])
{
	int i;
	float f=0.0;

	for (i=1;i<=3;i++) f += (x[i]-1.0)*(x[i]-1.0);
	return f;
}

int main(void)
{
	int i,j;
	float fret,sr2,x,*p,*xi;

	p=vector(1,NDIM);
	xi=vector(1,NDIM);
	printf("\nMinimum of a 3-d quadratic centered\n");
	printf("at (1.0,1.0,1.0). Minimum is found\n");
	printf("along a series of radials.\n\n");
	printf("%9s %12s %12s %14s \n","x","y","z","minimum");
	for (i=0;i<=10;i++) {
		x=PIO2*i/10.0;
		sr2=sqrt(2.0);
		xi[1]=sr2*cos(x);
		xi[2]=sr2*sin(x);
		xi[3]=1.0;
		p[1]=p[2]=p[3]=0.0;
		linmin(p,xi,NDIM,&fret,func);
		for (j=1;j<=3;j++) printf("%12.6f ",p[j]);
		printf("%12.6f\n",fret);
	}
	free_vector(xi,1,NDIM);
	free_vector(p,1,NDIM);
	return 0;
}
#undef NRANSI
