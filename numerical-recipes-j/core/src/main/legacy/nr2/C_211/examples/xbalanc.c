
/* Driver for routine balanc */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 5

int main(void)
{
	int i,j;
	float *c,*r,**a;

	c=vector(1,NP);
	r=vector(1,NP);
	a=matrix(1,NP,1,NP);
	for (i=1;i<=NP;i++)
		for (j=1;j<=NP;j++)
			a[i][j] = (((i & 1) && !(j & 1)) ? 100.0 : 1.0);
	/* Write norms */
	for (i=1;i<=NP;i++) {
		r[i]=c[i]=0.0;
		for (j=1;j<=NP;j++) {
			r[i] += fabs(a[i][j]);
			c[i] += fabs(a[j][i]);
		}
	}
	printf("rows:\n");
	for (i=1;i<=NP;i++) printf("%12.2f",r[i]);
	printf("\ncolumns:\n");
	for (i=1;i<=NP;i++) printf("%12.2f",c[i]);
	printf("\n\n***** Balancing matrix *****\n\n");
	balanc(a,NP);
	/* Write norms */
	for (i=1;i<=NP;i++) {
		r[i]=c[i]=0.0;
		for (j=1;j<=NP;j++) {
			r[i] += fabs(a[i][j]);
			c[i] += fabs(a[j][i]);
		}
	}
	printf("rows:\n");
	for (i=1;i<=NP;i++) printf("%12.2f",r[i]);
	printf("\ncolumns:\n");
	for (i=1;i<=NP;i++) printf("%12.2f",c[i]);
	printf("\n");
	free_matrix(a,1,NP,1,NP);
	free_vector(r,1,NP);
	free_vector(c,1,NP);
	return 0;
}
#undef NRANSI
