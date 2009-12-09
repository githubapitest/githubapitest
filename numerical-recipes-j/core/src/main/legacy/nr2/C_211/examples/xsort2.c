
/* Driver for routine sort2 */

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
	int i,j;
	float *a,*b;
	FILE *fp;

	a=vector(1,NP);
	b=vector(1,NP);
	if ((fp = fopen("tarray.dat","r")) == NULL)
		nrerror("Data file tarray.dat not found\n");
	fgets(txt,MAXSTR,fp);
	for (i=1;i<=NP;i++) fscanf(fp,"%f",&a[i]);
	fclose(fp);
	/* generate b-array */
	for (i=1;i<=NP;i++) b[i]=i;
	/* sort a and mix b */
	sort2(NP,a,b);
	printf("\nAfter sorting a and mixing b, array a is:\n");
	for (i=0;i<=9;i++) {
		for (j=1;j<=10;j++) printf("%7.2f",a[10*i+j]);
		printf("\n");
	}
	printf("\n... and array b is:\n");
	for (i=0;i<=9;i++) {
		for (j=1;j<=10;j++) printf("%7.2f",b[10*i+j]);
		printf("\n");
	}
	printf("press return to continue...\n");
	(void) getchar();
	/* sort b and mix a */
	sort2(NP,b,a);
	printf("\nAfter sorting b and mixing a, array a is:\n");
	for (i=0;i<=9;i++) {
		for (j=1;j<=10;j++) printf("%7.2f",a[10*i+j]);
		printf("\n");
	}
	printf("\n... and array b is:\n");
	for (i=0;i<=9;i++) {
		for (j=1;j<=10;j++) printf("%7.2f",b[10*i+j]);
		printf("\n");
	}
	free_vector(b,1,NP);
	free_vector(a,1,NP);
	return 0;
}
#undef NRANSI
