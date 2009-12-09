
/* Driver for routine ttest */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NPTS 1024
#define MPTS 512
#define EPS 0.02
#define NSHFT 10

int main(void)
{
	long idum=(-5);
	int i,j;
	float prob,t,*data1,*data2;

	data1=vector(1,NPTS);
	data2=vector(1,MPTS);
	/* Generate gaussian distributed data */
	printf("%6s %8s %16s\n","shift","t","probability");
	for (i=1;i<=NPTS;i++) data1[i]=gasdev(&idum);
	for (i=1;i<=MPTS;i++) data2[i]=(NSHFT/2.0)*EPS+gasdev(&idum);
	for (i=1;i<=NSHFT+1;i++) {
		ttest(data1,NPTS,data2,MPTS,&t,&prob);
		printf("%6.2f %10.2f %10.2f\n",(i-1)*EPS,t,prob);
		for (j=1;j<=NPTS;j++) data1[j] += EPS;
	}
	free_vector(data2,1,MPTS);
	free_vector(data1,1,NPTS);
	return 0;
}
#undef NRANSI
