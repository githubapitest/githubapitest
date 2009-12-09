
/* Driver for routine tptest */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NPTS 500
#define EPS 0.01
#define NSHFT 11
#define ANOISE 0.3

int main(void)
{
	long idum=(-5);
	int i,j;
	float ave1,ave2,ave3;
	float offset,prob1,prob2,shift,t1,t2;
	float var1,var2,var3,*data1,*data2,*data3;

	data1=vector(1,NPTS);
	data2=vector(1,NPTS);
	data3=vector(1,NPTS);
	printf("%29s %31s\n","Correlated:","Uncorrelated:");
	printf("%7s %11s %17s %11s %17s\n",
		"Shift","t","Probability","t","Probability");
	offset=(NSHFT/2)*EPS;
	for (j=1;j<=NPTS;j++) {
		data1[j]=gasdev(&idum);
		data2[j]=data1[j]+ANOISE*gasdev(&idum);
		data3[j]=gasdev(&idum);
		data3[j] += ANOISE*gasdev(&idum);
	}
	avevar(data1,NPTS,&ave1,&var1);
	avevar(data2,NPTS,&ave2,&var2);
	avevar(data3,NPTS,&ave3,&var3);
	for (j=1;j<=NPTS;j++) {
		data1[j] -= ave1-offset;
		data2[j] -= ave2;
		data3[j] -= ave3;
	}
	for (i=1;i<=NSHFT;i++) {
		shift=i*EPS;
		for (j=1;j<=NPTS;j++) {
			data2[j] += EPS;
			data3[j] += EPS;
		}
		tptest(data1,data2,NPTS,&t1,&prob1);
		tptest(data1,data3,NPTS,&t2,&prob2);
		printf("%6.2f %14.4f %12.4f %16.4f %12.4f\n",
			shift,t1,prob1,t2,prob2);
	}
	free_vector(data3,1,NPTS);
	free_vector(data2,1,NPTS);
	free_vector(data1,1,NPTS);
	return 0;
}
#undef NRANSI
