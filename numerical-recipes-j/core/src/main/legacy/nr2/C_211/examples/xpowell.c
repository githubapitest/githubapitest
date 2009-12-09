
/* Driver for routine powell */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NDIM 3
#define FTOL 1.0e-6

float func(float x[])
{
	return 0.5-bessj0(SQR(x[1]-1.0)+SQR(x[2]-2.0)+SQR(x[3]-3.0));
}

int main(void)
{
	int i,iter,j;
	float fret,**xi;
	static float p[]={0.0,1.5,1.5,2.5};

	xi=matrix(1,NDIM,1,NDIM);
	for (i=1;i<=NDIM;i++)
		for (j=1;j<=NDIM;j++)
			xi[i][j]=(i == j ? 1.0 : 0.0);
	powell(p,xi,NDIM,FTOL,&iter,&fret,func);
	printf("Iterations: %3d\n\n",iter);
	printf("Minimum found at: \n");
	for (i=1;i<=NDIM;i++) printf("%12.6f",p[i]);
	printf("\n\nMinimum function value = %12.6f \n\n",fret);
	printf("True minimum of function is at:\n");
	printf("%12.6f %12.6f %12.6f\n",1.0,2.0,3.0);
	free_matrix(xi,1,NDIM,1,NDIM);
	return 0;
}
#undef NRANSI
