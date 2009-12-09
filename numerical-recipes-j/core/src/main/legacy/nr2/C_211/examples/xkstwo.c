
/* Driver for routine kstwo */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define N1 2000
#define N2 1000
#define EPS 0.1

int main(void)
{
	long idum=(-1357);
	int i,j;
	float d,factr,prob,varnce,*data1,*data2;

	data1=vector(1,N1);
	data2=vector(1,N2);
	for (i=1;i<=N1;i++) data1[i]=gasdev(&idum);
	printf("%18s %15s %14s\n",
		"variance ratio","k-s statistic","probability");
	for (i=1;i<=11;i++) {
		varnce=1.0+(i-1)*EPS;
		factr=sqrt(varnce);
		for (j=1;j<=N2;j++)
				data2[j]=factr*gasdev(&idum);
		kstwo(data1,N1,data2,N2,&d,&prob);
		printf("%15.6f %15.6f %15.6f\n",varnce,d,prob);
	}
	free_vector(data2,1,N2);
	free_vector(data1,1,N1);
	return 0;
}
#undef NRANSI
