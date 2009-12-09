
/* Driver for routine mprove */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N 5
#define NP N

int main(void)
{
	int i,j,*indx;
	long idum=(-13);
	float d,*x,**a,**aa;
	static float ainit[NP][NP]=
		{1.0,2.0,3.0,4.0,5.0,
		2.0,3.0,4.0,5.0,1.0,
		1.0,1.0,1.0,1.0,1.0,
		4.0,5.0,1.0,2.0,3.0,
		5.0,1.0,2.0,3.0,4.0};
	static float b[N+1]={0.0,1.0,1.0,1.0,1.0,1.0};

	indx=ivector(1,N);
	x=vector(1,N);
	a=convert_matrix(&ainit[0][0],1,N,1,N);
	aa=matrix(1,N,1,N);
	for (i=1;i<=N;i++) {
		x[i]=b[i];
		for (j=1;j<=N;j++)
			aa[i][j]=a[i][j];
	}
	ludcmp(aa,N,indx,&d);
	lubksb(aa,N,indx,x);
	printf("\nSolution vector for the equations:\n");
	for (i=1;i<=N;i++) printf("%12.6f",x[i]);
	printf("\n");
	/* now phoney up x and let mprove fix it */
	for (i=1;i<=N;i++) x[i] *= (1.0+0.2*ran3(&idum));
	printf("\nSolution vector with noise added:\n");
	for (i=1;i<=N;i++) printf("%12.6f",x[i]);
	printf("\n");
	mprove(a,aa,N,indx,b,x);
	printf("\nSolution vector recovered by mprove:\n");
	for (i=1;i<=N;i++) printf("%12.6f",x[i]);
	printf("\n");
	free_matrix(aa,1,N,1,N);
	free_convert_matrix(a,1,N,1,N);
	free_vector(x,1,N);
	free_ivector(indx,1,N);
	return 0;
}
#undef NRANSI
