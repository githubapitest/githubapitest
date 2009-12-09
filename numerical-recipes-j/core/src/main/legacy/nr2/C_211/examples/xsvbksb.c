
/* Driver for routine svbksb, which calls routine svdcmp */

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
	float wmax,wmin,*w,*x,*c;
	float **a,**b,**u,**v;
	char dummy[MAXSTR];
	FILE *fp;

	w=vector(1,NP);
	x=vector(1,NP);
	c=vector(1,NP);
	a=matrix(1,NP,1,NP);
	b=matrix(1,NP,1,MP);
	u=matrix(1,NP,1,NP);
	v=matrix(1,NP,1,NP);
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
		/* copy a into u */
		for (k=1;k<=n;k++)
			for (l=1;l<=n;l++) u[k][l]=a[k][l];
		/* decompose matrix a */
		svdcmp(u,n,n,w,v);
		/* find maximum singular value */
		wmax=0.0;
		for (k=1;k<=n;k++)
			if (w[k] > wmax) wmax=w[k];
		/* define "small" */
		wmin=wmax*(1.0e-6);
		/* zero the "small" singular values */
		for (k=1;k<=n;k++)
			if (w[k] < wmin) w[k]=0.0;
		/* backsubstitute for each right-hand side vector */
		for (l=1;l<=m;l++) {
			printf("\nVector number %2d\n",l);
			for (k=1;k<=n;k++) c[k]=b[k][l];
			svbksb(u,w,v,n,n,c,x);
			printf(" solution vector is:\n");
			for (k=1;k<=n;k++) printf("%12.6f",x[k]);
			printf("\n original right-hand side vector:\n");
			for (k=1;k<=n;k++) printf("%12.6f",c[k]);
			printf("\n (matrix)*(sol'n vector):\n");
			for (k=1;k<=n;k++) {
				c[k]=0.0;
				for (j=1;j<=n;j++)
					c[k] += a[k][j]*x[j];
			}
			for (k=1;k<=n;k++) printf("%12.6f",c[k]);
			printf("\n");
		}
		printf ("***********************************\n");
		printf("press RETURN for next problem\n");
		(void) getchar();
	}
	fclose(fp);
	free_matrix(v,1,NP,1,NP);
	free_matrix(u,1,NP,1,NP);
	free_matrix(b,1,NP,1,MP);
	free_matrix(a,1,NP,1,NP);
	free_vector(c,1,NP);
	free_vector(x,1,NP);
	free_vector(w,1,NP);
	return 0;
}
#undef NRANSI
