
/* Driver for routine memcof */

#include <stdio.h>
#include <stdlib.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N 1000
#define M 10

int main(void)
{
	int i;
	float pm,*cof,*data;
	FILE *fp;

	cof=vector(1,M);
	data=vector(1,N);
	if ((fp = fopen("spctrl.dat","r")) == NULL)
		nrerror("Data file spctrl.dat not found\n");
	for (i=1;i<=N;i++) fscanf(fp,"%f",&data[i]);
	fclose(fp);
	memcof(data,N,M,&pm,cof);
	printf("Coefficients for spectral estimation of spctrl.dat\n\n");
	for (i=1;i<=M;i++) printf("a[%2d] = %12.6f \n",i,cof[i]);
	printf("\na0 =%12.6f\n",pm);
	free_vector(data,1,N);
	free_vector(cof,1,M);
	return 0;
}
#undef NRANSI
