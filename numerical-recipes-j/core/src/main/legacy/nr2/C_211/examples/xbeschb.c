
/* Driver for routine beschb */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

int main(void)
{
	double gam1,gam2,gampl,gammi,x,xgam1,xgam2,xgampl,xgammi;

	for (;;) {
		printf("Enter x:\n");
		if (scanf("%lf",&x) == EOF) break;
		beschb(x,&xgam1,&xgam2,&xgampl,&xgammi);
		printf("%5s\n%17s %16s %17s %15s\n%17s %16s %17s %15s\n",
			"x","gam1","gam2","gampl","gammi","xgam1","xgam2","xgampl","xgammi");
		gampl=1/exp(gammln((float)(1+x)));
		gammi=1/exp(gammln((float)(1-x)));
		gam1=(gammi-gampl)/(2*x);
		gam2=(gammi+gampl)/2;
		printf("%5.2f\n\t%16.6e %16.6e %16.6e %16.6e\n",x,gam1,gam2,gampl,gammi);
		printf("\t%16.6e %16.6e %16.6e %16.6e\n",xgam1,xgam2,xgampl,xgammi);
	}
	return 0;
}
#undef NRANSI
