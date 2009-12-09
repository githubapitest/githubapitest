
/* Driver for routine qrdcmp */

#include <stdio.h>
#include <stdlib.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 20
#define MAXSTR 80

int main(void)
{
	int j,k,l,m,n,sing;
	float *x,**a,**ai,**b,*c,*d;
	char dummy[MAXSTR];
	FILE *fp;

	x=vector(1,NP);
	a=matrix(1,NP,1,NP);
	b=matrix(1,NP,1,NP);
	ai=matrix(1,NP,1,NP);
	c=vector(1,NP);
	d=vector(1,NP);
	if ((fp = fopen("matrx1.dat","r")) == NULL)
		nrerror("Data file matrx1.dat not found\n");
	while (!feof(fp)) {
		fgets(dummy,MAXSTR,fp);
		fgets(dummy,MAXSTR,fp);
		fscanf(fp,"%d %d ",&n,&m);
		fgets(dummy,MAXSTR,fp);
		for (k=1;k<=n;k++)
			for (l=1;l<=n;l++) fscanf(fp,"%f ",&a[k][l]);
		fgets(dummy,MAXSTR,fp);
		for (l=1;l<=m;l++)
			for (k=1;k<=n;k++) fscanf(fp,"%f ",&b[k][l]);
		/* Save matrix a for later testing */
		for (l=1;l<=n;l++)
			for (k=1;k<=n;k++) ai[k][l]=a[k][l];
		/* Do qr decomposition */
		qrdcmp(a,n,c,d,&sing);
		if (sing) fprintf(stderr,"Singularity in QR decomposition.\n");
		/* Solve equations for each right-hand vector */
		for (k=1;k<=m;k++) {
			for (l=1;l<=n;l++) x[l]=b[l][k];
			qrsolv(a,n,c,d,x);
			/* Test results with original matrix */
			printf("right-hand side vector:\n");
			for (l=1;l<=n;l++)
				printf("%12.6f",b[l][k]);
			printf("\n%s%s\n","result of matrix applied",
				" to sol'n vector");
			for (l=1;l<=n;l++) {
				b[l][k]=0.0;
				for (j=1;j<=n;j++)
					b[l][k] += (ai[l][j]*x[j]);
			}
			for (l=1;l<=n;l++)
				printf("%12.6f",b[l][k]);
			printf("\n*********************************\n");
		}
		printf("press RETURN for next problem:\n");
		(void) getchar();
	}
	fclose(fp);
	free_vector(d,1,NP);
	free_vector(c,1,NP);
	free_matrix(ai,1,NP,1,NP);
	free_matrix(b,1,NP,1,NP);
	free_matrix(a,1,NP,1,NP);
	free_vector(x,1,NP);
	return 0;
}
#undef NRANSI
