
/* Driver for routine svdcmp */

#include <stdio.h>
#include <stdlib.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 20
#define MP 20
#define MAXSTR 80

int main(void)
{
	int j,k,l,m,n;
	float *w,**a,**u,**v;
	char dummy[MAXSTR];
	FILE *fp;

	w=vector(1,NP);
	a=matrix(1,MP,1,NP);
	u=matrix(1,MP,1,NP);
	v=matrix(1,NP,1,NP);
	/* read input matrices */
	if ((fp = fopen("matrx3.dat","r")) == NULL)
		nrerror("Data file matrx3.dat not found\n");
	while (!feof(fp)) {
		fgets(dummy,MAXSTR,fp);
		fgets(dummy,MAXSTR,fp);
		fscanf(fp,"%d %d ",&m,&n);
		fgets(dummy,MAXSTR,fp);
		/* copy original matrix into u */
		for (k=1;k<=m;k++)
			for (l=1;l<=n;l++) {
				fscanf(fp,"%f ",&a[k][l]);
				u[k][l]=a[k][l];
			}
		/* perform decomposition */
		svdcmp(u,m,n,w,v);
		/* write results */
		printf("Decomposition matrices:\n");
		printf("Matrix u\n");
		for (k=1;k<=m;k++) {
			for (l=1;l<=n;l++)
				printf("%12.6f",u[k][l]);
			printf("\n");
		}
		printf("Diagonal of matrix w\n");
		for (k=1;k<=n;k++)
			printf("%12.6f",w[k]);
		printf("\nMatrix v-transpose\n");
		for (k=1;k<=n;k++) {
			for (l=1;l<=n;l++)
				printf("%12.6f",v[l][k]);
			printf("\n");
		}
		printf("\nCheck product against original matrix:\n");
		printf("Original matrix:\n");
		for (k=1;k<=m;k++) {
			for (l=1;l<=n;l++)
				printf("%12.6f",a[k][l]);
			printf("\n");
		}
		printf("Product u*w*(v-transpose):\n");
		for (k=1;k<=m;k++) {
			for (l=1;l<=n;l++) {
				a[k][l]=0.0;
				for (j=1;j<=n;j++)
					a[k][l] += u[k][j]*w[j]*v[l][j];
			}
			for (l=1;l<=n;l++) printf("%12.6f",a[k][l]);
			printf("\n");
		}
		printf("***********************************\n");
		printf("press RETURN for next problem\n");
		(void) getchar();
	}
	fclose(fp);
	free_matrix(v,1,NP,1,NP);
	free_matrix(u,1,MP,1,NP);
	free_matrix(a,1,MP,1,NP);
	free_vector(w,1,NP);
	return 0;
}
#undef NRANSI
