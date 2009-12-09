
/* Driver for routine banmul */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 7
#define M1 2
#define M2 1
#define MP (M1+1+M2)

int main(void)
{
	unsigned long i,j,k;
	float **a,**aa,*ax,*b,*x;

	a=matrix(1,NP,1,MP);
	aa=matrix(1,NP,1,NP);
	ax=vector(1,NP);
	b=vector(1,NP);
	x=vector(1,NP);
	for (i=1;i<=M1;i++) for (j=1;j<=NP;j++) a[j][i]=10.0*j+i;
	/* Lower band */
	for (i=1;i<=NP;i++) a[i][M1+1]=i;
	/* Diagonal */
	for (i=1;i<=M2;i++) for (j=1;j<=NP;j++) a[j][M1+1+i]=0.1*j+i;
	/* Upper band */
	for (i=1;i<=NP;i++) {
		for (j=1;j<=NP;j++) {
			k=i-M1-1;
			if (j>=LMAX(1,1+k) && j<=LMIN(M1+M2+1+k,NP))
			aa[i][j]=a[i][j-k];
			else aa[i][j]=0.0;
		}
	}
	for (i=1;i<=NP;i++) x[i]=i/10.0;
	banmul(a,NP,M1,M2,x,b);
	for (i=1;i<=NP;i++) {
		for (ax[i]=0.0,j=1;j<=NP;j++) ax[i] += aa[i][j]*x[j];
	}
	printf("\tReference vector\tbanmul vector\n");
	for (i=1;i<=NP;i++) printf("\t%12.4f\t%12.4f\n",ax[i],b[i]);
	free_vector(x,1,NP);
	free_vector(b,1,NP);
	free_vector(ax,1,NP);
	free_matrix(aa,1,NP,1,NP);
	free_matrix(a,1,NP,1,MP);
	return 0;
}
#undef NRANSI
