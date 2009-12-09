
/* Driver for routine rank */

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
	unsigned long i,j,k,l,*indx,*irank;
	float *a,b[11];
	FILE *fp;

	indx=lvector(1,NP);
	irank=lvector(1,NP);
	a=vector(1,NP);
	if ((fp = fopen("tarray.dat","r")) == NULL)
		nrerror("Data file tarray.dat not found\n");
	fgets(txt,MAXSTR,fp);
	for (i=1;i<=NP;i++) fscanf(fp,"%f",&a[i]);
	fclose(fp);
	indexx(NP,a,indx);
	rank(NP,indx,irank);
	printf("original array is:\n");
	for (i=0;i<=9;i++) {
		for (j=1;j<=10;j++) printf("%7.2f",a[10*i+j]);
		printf("\n");
	}
	printf("table of ranks is:\n");
	for (i=0;i<=9;i++) {
		for (j=1;j<=10;j++) printf("%7d",irank[10*i+j]);
		printf("\n");
	}
	printf("press return to continue...\n");
	(void) getchar();
	printf("array sorted according to rank table:\n");
	for (i=0;i<=9;i++) {
		for (j=1;j<=10;j++) {
			k=10*i+j;
			for (l=1;l<=NP;l++)
				if (irank[l] == k) b[j]=a[l];
		}
		for (j=1;j<=10;j++) printf("%7.2f",b[j]);
		printf("\n");
	}
	free_vector(a,1,NP);
	free_lvector(irank,1,NP);
	free_lvector(indx,1,NP);
	return 0;
}
#undef NRANSI
