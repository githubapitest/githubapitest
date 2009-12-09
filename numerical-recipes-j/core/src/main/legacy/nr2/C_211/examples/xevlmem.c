
/* Driver for routine evlmem */

#include <stdio.h>
#include <stdlib.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N 1000
#define M 10
#define NFDT 16

int main(void)
{
	int i;
	float fdt,pm,*cof,*data;
	FILE *fp;

	cof=vector(1,M);
	data=vector(1,N);
	if ((fp = fopen("spctrl.dat","r")) == NULL)
		nrerror("Data file spctrl.dat not found\n");
	for (i=1;i<=N;i++) fscanf(fp,"%f",&data[i]);
	fclose(fp);
	memcof(data,N,M,&pm,cof);
	printf("Power spectum estimate of data in spctrl.dat\n");
	printf("     f*delta      power\n");
	for (fdt=0.0;fdt<=0.5;fdt+=0.5/NFDT)
		printf("%12.6f %12.6f\n",fdt,evlmem(fdt,cof,M,pm));
	free_vector(data,1,N);
	free_vector(cof,1,M);
	return 0;
}
#undef NRANSI
