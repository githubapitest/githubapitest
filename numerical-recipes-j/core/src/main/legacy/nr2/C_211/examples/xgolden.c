
/* Driver for routine golden */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"

#define TOL 1.0e-6
#define EQL 1.0e-3

float func(float x)
{
	return bessj0(x);
}

int main(void)
{
	int i,iflag,j,nmin=0;
	float ax,bx,cx,fa,fb,fc,xmin,gold,amin[21];

	printf("Minima of the function bessj0\n");
	printf("%10s %8s %17s %12s\n","min. #","x","bessj0(x)","bessj1(x)");
	for (i=1;i<=100;i++) {
		ax=i;
		bx=i+1.0;
		mnbrak(&ax,&bx,&cx,&fa,&fb,&fc,func);
		gold=golden(ax,bx,cx,func,TOL,&xmin);
		if (nmin == 0) {
			amin[1]=xmin;
			nmin=1;
			printf("%7d %15.6f %12.6f %12.6f\n",
				nmin,xmin,bessj0(xmin),bessj1(xmin));
		} else {
			iflag=0;
			for (j=1;j<=nmin;j++)
				if (fabs(xmin-amin[j]) <= EQL*xmin) iflag=1;
			if (iflag == 0) {
				amin[++nmin]=xmin;
				printf("%7d %15.6f %12.6f %12.6f\n",
					nmin,xmin,bessj0(xmin),bessj1(xmin));
			}
		}
	}
	return 0;
}
#undef NRANSI
