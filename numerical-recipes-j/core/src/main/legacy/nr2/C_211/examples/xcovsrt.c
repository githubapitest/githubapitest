
/* Driver for routine covsrt */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define MA 10
#define MFIT 5

int main(void)
{
	int i,j,*ia;
	float **covar;

	ia=ivector(1,MA);
	covar=matrix(1,MA,1,MA);
	for (i=1;i<=MA;i++)
		for (j=1;j<=MA;j++) {
			covar[i][j]=0.0;
			if ((i <= MFIT) && (j <= MFIT))
				covar[i][j]=i+j-1;
		}
	printf("\noriginal matrix\n");
	for (i=1;i<=MA;i++) {
		for (j=1;j<=MA;j++) printf("%4.1f",covar[i][j]);
		printf("\n");
	}
	printf("press RETURN to continue...\n");
	(void) getchar();
	printf("\nTest #1 - full fitting\n");
	for (i=1;i<=MA;i++) ia[i]=1;
	covsrt(covar,MA,ia,MA);
	for (i=1;i<=MA;i++) {
		for (j=1;j<=MA;j++) printf("%4.1f",covar[i][j]);
		printf("\n");
	}
	printf("press RETURN to continue...\n");
	(void) getchar();
	printf("\nTest #2 - spread\n");
	for (i=1;i<=MA;i++)
		for (j=1;j<=MA;j++) {
			covar[i][j]=0.0;
			if ((i <= MFIT) && (j <= MFIT)) covar[i][j]=i+j-1;
		}
	for (i=1;i<=MA;i+=2) ia[i]=0;
	covsrt(covar,MA,ia,MFIT);
	for (i=1;i<=MA;i++) {
		for (j=1;j<=MA;j++) printf("%4.1f",covar[i][j]);
		printf("\n");
	}
	free_matrix(covar,1,MA,1,MA);
	free_ivector(ia,1,MA);
	return 0;
}
#undef NRANSI
