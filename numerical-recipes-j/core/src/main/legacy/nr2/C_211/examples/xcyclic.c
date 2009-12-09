
/* Driver for routine cyclic */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N 20

int main(void)
{
	float alpha,beta,d,*a,*b,*c,*r,*x,**aa;
	int i,j,*indx;
	long idum=(-23);

	indx=ivector(1,N);
	a=vector(1,N);
	b=vector(1,N);
	c=vector(1,N);
	r=vector(1,N);
	x=vector(1,N);
	aa=matrix(1,N,1,N);
	for (i=1;i<=N;i++)
		for (j=1;j<=N;j++) aa[i][j]=0.0;
	for (i=1;i<=N;i++) {
		b[i]=ran2(&idum);
		aa[i][i]=b[i];
		r[i]=ran2(&idum);
	}
	for (i=1;i<N;i++) {
		a[i+1]=ran2(&idum);
		aa[i+1][i]=a[i+1];
		c[i]=ran2(&idum);
		aa[i][i+1]=c[i];
	}
	alpha=ran2(&idum);
	aa[N][1]=alpha;
	beta=ran2(&idum);
	aa[1][N]=beta;
	cyclic(a,b,c,alpha,beta,r,x,N);
	ludcmp(aa,N,indx,&d);
	lubksb(aa,N,indx,r);
	for (i=1;i<=N;i++) printf("%4d  %12.6e\n",i,(x[i]-r[i])/(x[i]+r[i]));
	free_matrix(aa,1,N,1,N);
	free_vector(x,1,N);
	free_vector(r,1,N);
	free_vector(c,1,N);
	free_vector(b,1,N);
	free_vector(a,1,N);
	free_ivector(indx,1,N);
	return 0;
}
#undef NRANSI
