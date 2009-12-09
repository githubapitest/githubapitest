
/* Driver for routine hpsel */

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
	float *a,*heap,check;
	FILE *fp;

	a=vector(1,NP);
	heap=vector(1,NP);
	if ((fp = fopen("tarray.dat","r")) == NULL)
		nrerror("Data file tarray.dat not found\n");
	fgets(txt,MAXSTR,fp);
	for (i=1;i<=NP;i++) fscanf(fp,"%f",&a[i]);
	fclose(fp);
	for (;;) {
		printf("Input k\n");
		if (scanf("%lu",&k) == EOF) break;
		hpsel(k,NP,a,heap);
		check=select(NP+1-k,NP,a);
		printf("heap[1], check= %6.2f %6.2f\n",heap[1],check);
		printf("heap of numbers of size %lu\n",k);
		for (i=1;i<=k;i++) printf("%lu   %6.2f\n",i,heap[i]);
	}
	free_vector(heap,1,NP);
	free_vector(a,1,NP);
	printf("Normal completion\n");
	return 0;
}
#undef NRANSI
