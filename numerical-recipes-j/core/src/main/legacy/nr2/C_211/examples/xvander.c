
/* Driver for routine vander */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N 5

int main(void)
{
	int i,j;
	double sum=0.0,*w,*term;
	static double x[]={0.0,1.0,1.5,2.0,2.5,3.0};
	static double q[]={0.0,1.0,1.5,2.0,2.5,3.0};

	w=dvector(1,N);
	term=dvector(1,N);
	vander(x,w,q,N);
	printf("\nSolution vector:\n");
	for (i=1;i<=N;i++)
		printf("%7s%1d%2s %12f \n","w[",i,"]=",w[i]);
	printf("\nTest of solution vector:\n");
	printf("%14s %11s\n","mtrx*sol'n","original");
	for (i=1;i<=N;i++) {
		term[i]=w[i];
		sum += w[i];
	}
	printf("%12.4f %12.4f\n",sum,q[1]);
	for (i=2;i<=N;i++) {
		sum=0.0;
		for (j=1;j<=N;j++) {
			term[j] *= x[j];
			sum += term[j];
		}
		printf("%12.4f %12.4f\n",sum,q[i]);
	}
	free_dvector(term,1,N);
	free_dvector(w,1,N);
	return 0;
}
#undef NRANSI
