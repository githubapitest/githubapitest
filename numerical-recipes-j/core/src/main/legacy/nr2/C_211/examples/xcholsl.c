
/* Driver for routine cholsl */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N 3

int main(void)
{
	int i,j,k;
	float sum,**a,**atest,**chol,*p,*x;
	static float aorig[N+1][N+1]=
		{0.0,0.0,0.0,0.0,
		0.0,100.0,15.0,0.01,
		0.0,15.0,2.3,0.01,
		0.0,0.01,0.01,1.0};
	static float b[N+1]={0.0,0.4,0.02,99.0};

	a=matrix(1,N,1,N);
	atest=matrix(1,N,1,N);
	chol=matrix(1,N,1,N);
	p=vector(1,N);
	x=vector(1,N);
	for (i=1;i<=N;i++)
		for (j=1;j<=N;j++) a[i][j]=aorig[i][j];
	choldc(a,N,p);
	printf("Original matrix:\n");
	for (i=1;i<=N;i++) {
		for (j=1;j<=N;j++) {
			chol[i][j]=((i > j) ? a[i][j] : (i == j ? p[i] : 0.0));
			if (i > j) chol[i][j]=a[i][j];
			else chol[i][j]=(i == j ? p[i] : 0.0);
			printf("%16.6e",aorig[i][j]);
		}
		printf("\n");
	}
	printf("\n");
	printf("Product of Cholesky factors:\n");
	for (i=1;i<=N;i++) {
		for (j=1;j<=N;j++) {
			for (sum=0.0,k=1;k<=N;k++) sum += chol[i][k]*chol[j][k];
			atest[i][j]=sum;
			printf("%16.6e",atest[i][j]);
		}
		printf("\n");
	}
	printf("\n");
	printf("Check solution vector:\n");
	cholsl(a,N,p,b,x);
	for (i=1;i<=N;i++) {
		for (sum=0.0,j=1;j<=N;j++) sum += aorig[i][j]*x[j];
		p[i]=sum;
		printf("%16.6e%16.6e\n",p[i],b[i]);
	}
	free_vector(x,1,N);
	free_vector(p,1,N);
	free_matrix(chol,1,N,1,N);
	free_matrix(atest,1,N,1,N);
	free_matrix(a,1,N,1,N);
	return 0;
}
#undef NRANSI
