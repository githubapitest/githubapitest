
/* Driver for routine indexx */

#include <stdio.h>
#include <stdlib.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 100
#define MAXSTR 80

int main(void)
{
	char txt[MAXSTR];
	unsigned long i,j,*indx;
	float *a;
	FILE *fp;

	indx=lvector(1,NP);
	a=vector(1,NP);
	if ((fp = fopen("tarray.dat","r")) == NULL)
		nrerror("Data file tarray.dat not found\n");
	fgets(txt,MAXSTR,fp);
	for (i=1;i<=NP;i++) fscanf(fp,"%f",&a[i]);
	fclose(fp);
	indexx(NP,a,indx);
	printf("\noriginal array:\n");
	for (i=0;i<=9;i++) {
		for (j=1;j<=10;j++) printf("%7.2f",a[10*i+j]);
		printf("\n");
	}
	printf("\nsorted array:\n");
	for (i=0;i<=9;i++) {
		for (j=1;j<=10;j++) printf("%7.2f",a[indx[10*i+j]]);
		printf("\n");
	}
	free_vector(a,1,NP);
	free_lvector(indx,1,NP);
	return 0;
}
#undef NRANSI
