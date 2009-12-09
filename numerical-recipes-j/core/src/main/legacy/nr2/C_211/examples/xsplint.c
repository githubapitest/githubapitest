
/* Driver for routine splint */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NP 10
#define PI 3.1415926

int main(void)
{
	int i,nfunc;
	float f,x,y,yp1,ypn,*xa,*ya,*y2;

	xa=vector(1,NP);
	ya=vector(1,NP);
	y2=vector(1,NP);
	for (nfunc=1;nfunc<=2;nfunc++) {
		if (nfunc == 1) {
			printf("\nsine function from 0 to pi\n");
			for (i=1;i<=NP;i++) {
				xa[i]=i*PI/NP;
				ya[i]=sin(xa[i]);
			}
			yp1=cos(xa[1]);
			ypn=cos(xa[NP]);
		} else if (nfunc == 2) {
			printf("\nexponential function from 0 to 1\n");
			for (i=1;i<=NP;i++) {
				xa[i]=1.0*i/NP;
				ya[i]=exp(xa[i]);
			}
			yp1=exp(xa[1]);
			ypn=exp(xa[NP]);
		} else {
			break;
		}
		/* Call spline to get second derivatives */
		spline(xa,ya,NP,yp1,ypn,y2);
		/* Call splint for interpolations */
		printf("\n%9s %13s %17s\n","x","f(x)","interpolation");
		for (i=1;i<=10;i++) {
			if (nfunc == 1) {
				x=(-0.05+i/10.0)*PI;
				f=sin(x);
			} else if (nfunc == 2) {
				x = -0.05+i/10.0;
				f=exp(x);
			}
			splint(xa,ya,y2,NP,x,&y);
			printf("%12.6f %12.6f %12.6f\n",x,f,y);
		}
		printf("\n***********************************\n");
		printf("Press RETURN\n");
		(void) getchar();
	}
	free_vector(y2,1,NP);
	free_vector(ya,1,NP);
	free_vector(xa,1,NP);
	return 0;
}
#undef NRANSI
