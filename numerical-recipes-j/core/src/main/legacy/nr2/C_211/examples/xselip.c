
/* Driver for routine selip */

#include <stdio.h>
#include <stdlib.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define MAXSTR 80
#define NP 100

int main(void)
{
	char txt[MAXSTR];
	unsigned long i,j,k;
	float *a,*b,q;
	FILE *fp;

	a=vector(1,NP);
	b=vector(1,NP);
	if ((fp = fopen("tarray.dat","r")) == NULL)
		nrerror("Data file tarray.dat not found\n");
	fgets(txt,MAXSTR,fp);
	for (i=1;i<=NP;i++) fscanf(fp,"%f",&a[i]);
	fclose(fp);
	printf("\noriginal array:\n");
	for (i=0;i<=9;i++) {
		for (j=1;j<=10;j++) printf("%7.2f",a[10*i+j]);
		printf("\n");
	}
	for (i=1;i<=NP;i++) b[i]=selip(i,100,a);
	printf("\nsorted array:\n");
	for (i=0;i<=9;i++) {
		for (j=1;j<=10;j++) printf("%7.2f",b[10*i+j]);
		printf("\n");
	}
	for (;;) {
		printf("Input k\n");
		if (scanf("%lu",&k) == EOF) break;
		q=selip(k,NP,a);
		printf("Element in sort position %lu is %6.2f\n",k,q);
	}
	free_vector(b,1,NP);
	free_vector(a,1,NP);
	printf("Normal completion\n");
	return 0;
}
#undef NRANSI
