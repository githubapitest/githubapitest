
/* Driver for routine wtn */
#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define EPS 1.0e-06
#define NX 8
#define NY 16

int main(void)
{
	unsigned long i,j,l,nerror=0,ntot=NX*NY;
	float *a,*aorg;
	static unsigned long ndim[]={0,NX,NY};

	aorg=vector(1,ntot);
	a=vector(1,ntot);
	pwtset(12);
	for (i=1;i<=NX;i++)
		for (j=1;j<=NY;j++) {
			l=i+(j-1)*NX;
			aorg[l]=a[l]=(i == j ? -1.0 : 1.0/sqrt(fabs((float)(i-j))));
		}
	wtn(a,ndim,2,1,pwt);
	/* here, one might set the smallest components to zero, encode and transmit
	the remaining components as a compressed form of the "image" */
	wtn(a,ndim,2,-1,pwt);
	for (l=1;l<=ntot;l++) {
		if (fabs(aorg[l]-a[l]) >= EPS) {
			printf("Compare Error at element %ld\n",l);
			nerror++;
		}
	}
	if (nerror) printf("Number of comparision errors: %ld\n",nerror);
	else printf("transform-inverse transform check OK\n");
	free_vector(a,1,ntot);
	free_vector(aorg,1,ntot);
	return nerror;
}
#undef NRANSI
