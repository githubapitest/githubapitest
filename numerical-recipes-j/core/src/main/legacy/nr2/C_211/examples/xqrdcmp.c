
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
	int i,j,k,l,m,n,sing;
	float con,**a,*c,*d,**q,**qt,**r,**x;
	char dummy[MAXSTR];
	FILE *fp;

	a=matrix(1,NP,1,NP);
	c=vector(1,NP);
	d=vector(1,NP);
	q=matrix(1,NP,1,NP);
	qt=matrix(1,NP,1,NP);
	r=matrix(1,NP,1,NP);
	x=matrix(1,NP,1,NP);
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
			for (k=1;k<=n;k++) fscanf(fp,"%f ",&x[k][l]);
		/* Print out a-matrix for comparison with product of
		   Q and R decomposition matrices */
		printf("Original matrix:\n");
		for (k=1;k<=n;k++) {
			for (l=1;l<=n;l++) printf("%12.6f",a[k][l]);
			printf("\n");
		}
		/* Perform the decomposition */
		qrdcmp(a,n,c,d,&sing);
		if (sing) fprintf(stderr,"Singularity in QR decomposition.\n");
		/* find the Q and R matrices */
		for (k=1;k<=n;k++) {
			for (l=1;l<=n;l++) {
				if (l > k) {
					r[k][l]=a[k][l];
					q[k][l]=0.0;
				} else if (l < k) {
					r[k][l]=q[k][l]=0.0;
				} else {
					r[k][l]=d[k];
					q[k][l]=1.0;
				}
			}
		}
		for (i=n-1;i>=1;i--) {
			for (con=0.0,k=i;k<=n;k++) con += a[k][i]*a[k][i];
			con /= 2.0;
			for (k=i;k<=n;k++) {
				for (l=i;l<=n;l++) {
					qt[k][l]=0.0;
					for (j=i;j<=n;j++) {
						qt[k][l] += q[j][l]*a[k][i]*a[j][i]/con;
					}
				}
			}
			for (k=i;k<=n;k++)
				for (l=i;l<=n;l++) q[k][l] -= qt[k][l];
		}
		/* compute product of Q and R matrices for comparison
		   with original matrix. */
		for (k=1;k<=n;k++) {
			for (l=1;l<=n;l++) {
				x[k][l]=0.0;
				for (j=1;j<=n;j++)
					x[k][l] += q[k][j]*r[j][l];
			}
		}
		printf("\nProduct of Q and R matrices:\n");
		for (k=1;k<=n;k++) {
			for (l=1;l<=n;l++) printf("%12.6f",x[k][l]);
			printf("\n");
		}
		printf("\nQ matrix of the decomposition:\n");
		for (k=1;k<=n;k++) {
			for (l=1;l<=n;l++) printf("%12.6f",q[k][l]);
			printf("\n");
		}
		printf("\nR matrix of the decomposition:\n");
		for (k=1;k<=n;k++) {
			for (l=1;l<=n;l++) printf("%12.6f",r[k][l]);
			printf("\n");
		}
		printf("\n***********************************\n");
		printf("press return for next problem:\n");
		(void) getchar();
	}
	fclose(fp);
	free_matrix(x,1,NP,1,NP);
	free_matrix(r,1,NP,1,NP);
	free_matrix(qt,1,NP,1,NP);
	free_matrix(q,1,NP,1,NP);
	free_vector(d,1,NP);
	free_vector(c,1,NP);
	free_matrix(a,1,NP,1,NP);
	return 0;
}
#undef NRANSI
