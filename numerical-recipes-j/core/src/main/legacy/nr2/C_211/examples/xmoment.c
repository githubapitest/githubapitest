
/* Driver for routine moment */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define PI 3.14159265
#define NPTS 5000
#define NBIN 100
#define NPPNB (NPTS+NBIN)

int main(void)
{
	int i=0,k,nlim;
	float adev,ave,curt,sdev,skew,vrnce,x,*data;

	data=vector(1,NPPNB);
	for (x=PI/NBIN;x<=PI;x+=PI/NBIN) {
		nlim=(int) (0.5+sin(x)*PI/2.0*NPTS/NBIN);
		for (k=1;k<=nlim;k++) data[++i]=x;
	}
	printf("moments of a sinusoidal distribution\n\n");
	moment(data,i,&ave,&adev,&sdev,&vrnce,&skew,&curt);
	printf("%39s %11s\n\n","calculated","expected");
	printf("%s %17s %12.4f %12.4f\n","Mean :"," ",ave,PI/2.0);
	printf("%s %4s %12.4f %12.4f\n",
		"Average Deviation :"," ",adev,(PI/2.0)-1.0);
	printf("%s %3s %12.4f %12.4f\n",
		"Standard Deviation :"," ",sdev,0.683667);
	printf("%s %13s %12.4f %12.4f\n",
		"Variance :"," ",vrnce,0.467401);
	printf("%s %13s %12.4f %12.4f\n",
		"Skewness :"," ",skew,0.0);
	printf("%s %13s %12.4f %12.4f\n",
		"Kurtosis :"," ",curt,-0.806249);
	free_vector(data,1,NPPNB);
	return 0;
}
#undef NRANSI
