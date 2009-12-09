
/* Driver for routine chsone */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NBINS 10
#define NPTS 2000

int main(void)
{
	long idum=(-15);
	int i,ibin,j;
	float chsq,df,prob,x,*bins,*ebins;

	bins=vector(1,NBINS);
	ebins=vector(1,NBINS);
	for (j=1;j<=NBINS;j++) bins[j]=0.0;
	for (i=1;i<=NPTS;i++) {
		x=expdev(&idum);
		ibin=(int) (x*NBINS/3.0)+1;
		if (ibin <= NBINS) ++bins[ibin];
	}
	for (i=1;i<=NBINS;i++)
		ebins[i]=3.0*NPTS/NBINS*exp(-3.0*(i-0.5)/NBINS);
	chsone(bins,ebins,NBINS,0,&df,&chsq,&prob);
	printf("%15s %15s\n","expected","observed");
	for (i=1;i<=NBINS;i++)
		printf("%14.2f %15.2f\n",ebins[i],bins[i]);
	printf("\n%19s %10.4f\n","chi-squared:",chsq);
	printf("%19s %10.4f\n","probability:",prob);
	free_vector(ebins,1,NBINS);
	free_vector(bins,1,NBINS);
	return 0;
}
#undef NRANSI
