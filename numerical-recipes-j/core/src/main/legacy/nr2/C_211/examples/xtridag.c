
/* Driver for routine tridag */

#include <stdio.h>
#include <stdlib.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 20
#define MAXSTR 80

int main(void)
{
	unsigned long k,n;
	float *diag,*superd,*subd,*rhs,*u;
	char dummy[MAXSTR];
	FILE *fp;

	diag=vector(1,NP);
	superd=vector(1,NP);
	subd=vector(1,NP);
	rhs=vector(1,NP);
	u=vector(1,NP);
	if ((fp = fopen("matrx2.dat","r")) == NULL)
		nrerror("Data file matrx2.dat not found\n");
	while (!feof(fp)) {
		fgets(dummy,MAXSTR,fp);
		fgets(dummy,MAXSTR,fp);
		fscanf(fp,"%ld ",&n);
		fgets(dummy,MAXSTR,fp);
		for (k=1;k<=n;k++) fscanf(fp,"%f ",&diag[k]);
		fgets(dummy,MAXSTR,fp);
		for (k=1;k<n;k++) fscanf(fp,"%f ",&superd[k]);
		fgets(dummy,MAXSTR,fp);
		for (k=2;k<=n;k++) fscanf(fp,"%f ",&subd[k]);
		fgets(dummy,MAXSTR,fp);
		for (k=1;k<=n;k++) fscanf(fp,"%f ",&rhs[k]);
		/* carry out solution */
		tridag(subd,diag,superd,rhs,u,n);
		printf("\nThe solution vector is:\n");
		for (k=1;k<=n;k++) printf("%12.6f",u[k]);
		printf("\n");
		/* test solution */
		printf("\n(matrix)*(sol'n vector) should be:\n");
		for (k=1;k<=n;k++) printf("%12.6f",rhs[k]);
		printf("\n");
		printf("actual result is:\n");
		for (k=1;k<=n;k++) {
			if (k == 1)
				rhs[k]=diag[1]*u[1]+superd[1]*u[2];
			else if (k == n)
				rhs[k]=subd[n]*u[n-1]+diag[n]*u[n];
			else
				rhs[k]=subd[k]*u[k-1]+diag[k]*u[k]
					+superd[k]*u[k+1];
		}
		for (k=1;k<=n;k++) printf("%12.6f",rhs[k]);
		printf("\n");
		printf("***********************************\n");
		printf("press return for next problem:\n");
		(void) getchar();
	}
	fclose(fp);
	free_vector(u,1,NP);
	free_vector(rhs,1,NP);
	free_vector(subd,1,NP);
	free_vector(superd,1,NP);
	free_vector(diag,1,NP);
	return 0;
}
#undef NRANSI
