
/* Driver for routine voltra */
#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

float g(int k,float t)
{
	return (k == 1 ? cosh(t)+t*sin(t) : 2.0*sin(t)+t*(SQR(sin(t))+exp(t)));
}

float ak(int k,int l,float t,float s)
{
	return ((k == 1) ?
		(l == 1 ? -exp(t-s) : -cos(t-s)) :
		(l == 1 ? -exp(t+s) : -t*cos(s)));
}

#define N 30
#define H 0.05
#define M 2

int main(void)
{
	int nn;
	float t0=0.0,*t,**f;

	t=vector(1,N);
	f=matrix(1,M,1,N);
	voltra(N,M,t0,H,t,f,g,ak);
	/* exact soln is f[1]=exp(-t), f[2]=2sin(t) */
	printf("  abscissa, voltra answer1, real answer1,");
	printf(" voltra answer2, real answer2\n");
	for (nn=1;nn<=N;nn++)
		printf("%12.6f %12.6f %12.6f %12.6f %12.6f\n",
			t[nn],f[1][nn],exp(-t[nn]),f[2][nn],2.0*sin(t[nn]));
	free_vector(t,1,N);
	free_matrix(f,1,M,1,N);
	return 0;
}
#undef NRANSI
