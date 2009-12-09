
/* Driver for routine fpoly */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NVAL 15
#define DX 0.1
#define NPOLY 5

int main(void)
{
	int i,j;
	float x,*afunc;

	afunc=vector(1,NPOLY);
	printf("\n%38s\n","powers of x");
	printf("%8s %10s %9s %9s %9s %9s\n",
		"x","x**0","x**1","x**2","x**3","x**4");
	for (i=1;i<=NVAL;i++) {
		x=i*DX;
		fpoly(x,afunc,NPOLY);
		printf("%10.4f",x);
		for (j=1;j<=NPOLY;j++) printf("%10.4f",afunc[j]);
		printf("\n");
	}
	free_vector(afunc,1,NPOLY);
	return 0;
}
#undef NRANSI
