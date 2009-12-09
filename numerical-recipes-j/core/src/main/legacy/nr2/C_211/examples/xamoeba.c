
/* Driver for routine amoeba */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define MP 4
#define NP 3
#define FTOL 1.0e-6

float func(float x[])
{
	return 0.6-bessj0(SQR(x[1]-0.5)+SQR(x[2]-0.6)+SQR(x[3]-0.7));
}

int main(void)
{
	int i,nfunc,j,ndim=NP;
	float *x,*y,**p;

	x=vector(1,NP);
	y=vector(1,MP);
	p=matrix(1,MP,1,NP);
	for (i=1;i<=MP;i++) {
		for (j=1;j<=NP;j++)
			x[j]=p[i][j]=(i == (j+1) ? 1.0 : 0.0);
		y[i]=func(x);
	}
	amoeba(p,y,ndim,FTOL,func,&nfunc);
	printf("\nNumber of function evaluations: %3d\n",nfunc);
	printf("Vertices of final 3-d simplex and\n");
	printf("function values at the vertices:\n\n");
	printf("%3s %10s %12s %12s %14s\n\n",
		"i","x[i]","y[i]","z[i]","function");
	for (i=1;i<=MP;i++) {
		printf("%3d ",i);
		for (j=1;j<=NP;j++) printf("%12.6f ",p[i][j]);
		printf("%12.6f\n",y[i]);
	}
	printf("\nTrue minimum is at (0.5,0.6,0.7)\n");
	free_matrix(p,1,MP,1,NP);
	free_vector(y,1,MP);
	free_vector(x,1,NP);
	return 0;
}
#undef NRANSI
