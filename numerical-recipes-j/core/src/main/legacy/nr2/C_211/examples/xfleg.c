
/* Driver for routine fleg */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NVAL 5
#define DX 0.2
#define NPOLY 5

int main(void)
{
	int i,j;
	float x,*afunc;

	afunc=vector(1,NPOLY);
	printf("\n%3s\n","Legendre polynomials");
	printf("%9s %9s %9s %9s %9s\n","n=1","n=2","n=3","n=4","n=5");
	for (i=1;i<=NVAL;i++) {
		x=i*DX;
		fleg(x,afunc,NPOLY);
		printf("x =%5.2f\n",x);
		for (j=1;j<=NPOLY;j++) printf("%10.4f",afunc[j]);
		printf("  routine FLEG\n");
		for (j=1;j<=NPOLY;j++) printf("%10.4f",plgndr(j-1,0,x));
		printf("  routine PLGNDR\n\n");
	}
	free_vector(afunc,1,NPOLY);
	return 0;
}
#undef NRANSI
