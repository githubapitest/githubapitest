
/* Driver for routine select */

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
	unsigned long i,k;
	float *a,*b,q,s;
	FILE *fp;

	a=vector(1,NP);
	b=vector(1,NP);
	if ((fp = fopen("tarray.dat","r")) == NULL)
		nrerror("Data file tarray.dat not found\n");
	fgets(txt,MAXSTR,fp);
	for (i=1;i<=NP;i++) fscanf(fp,"%f",&a[i]);
	fclose(fp);
	for (;;) {
		printf("Input k\n");
		if (scanf("%lu",&k) == EOF) break;
		for (i=1;i<=NP;i++) b[i]=a[i];
		s=selip(k,NP,a);
		q=select(k,NP,b);
		printf("Element in sort position %lu is %6.2f\n",k,q);
		printf("Cross-check from SELIP routine %6.2f\n",s);
	}
	free_vector(b,1,NP);
	free_vector(a,1,NP);
	printf("Normal completion\n");
	return 0;
}
#undef NRANSI
