
/* Driver for routine spctrm */

#include <stdio.h>
#include <stdlib.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define M 16
#define TRUE 1
#define FALSE 0

int main(void)
{
	int j,k,ovrlap;
	float *p,*q;
	FILE *fp;

	p=vector(1,M);
	q=vector(1,M);
	if ((fp = fopen("spctrl.dat","r")) == NULL)
		nrerror("Data file spctrl.dat not found\n");
	k=8;
	ovrlap=TRUE;
	spctrm(fp,p,M,k,ovrlap);
	rewind(fp);
	k=16;
	ovrlap=FALSE;
	spctrm(fp,q,M,k,ovrlap);
	fclose(fp);
	printf("\nSpectrum of data in file spctrl.dat\n");
	printf("%13s %s %5s %s\n"," ","overlapped "," ","non-overlapped");
	for (j=1;j<=M;j++)
		printf("%3d %5s %13f %5s %13f\n",j," ",p[j]," ",q[j]);
	free_vector(q,1,M);
	free_vector(p,1,M);
	return 0;
}
#undef NRANSI
