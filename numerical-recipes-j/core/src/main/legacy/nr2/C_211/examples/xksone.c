
/* Driver for routine ksone */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NPTS 1000
#define EPS 0.1

float func(float x)
{
	return 1.0 - erfcc(x/sqrt(2.0));
}

int main(void)
{
	long idum=(-5);
	int i,j;
	float d,factr,prob,varnce,*data;

	data=vector(1,NPTS);
	printf("%19s %16s %15s\n\n",
		"variance ratio","k-s statistic","probability");
	for (i=1;i<=11;i++) {
		varnce=1.0+(i-1)*EPS;
		factr=sqrt(varnce);
		for (j=1;j<=NPTS;j++)
			data[j]=factr*fabs(gasdev(&idum));
		ksone(data,NPTS,func,&d,&prob);
		printf("%16.6f %16.6f %16.6f \n",varnce,d,prob);
	}
	free_vector(data,1,NPTS);
	return 0;
}
#undef NRANSI
