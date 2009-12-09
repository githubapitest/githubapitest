
/* Driver for routine ddpoly */

#include <stdio.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NC 5
#define ND NC-1
#define NP 20

int main(void)
{
	int i,j,k;
	float x,pwr,*pd,**d;
	static float c[NC+1]={-1.0,5.0,-10.0,10.0,-5.0,1.0};
	static char *a[ND+1]={"polynomial:", "first deriv:",
		"second deriv:","third deriv:","fourth deriv:"};

	pd=vector(0,ND);
	d=matrix(0,ND,1,NP);
	for (i=1;i<=NP;i++) {
		x=0.1*i;
		ddpoly(c,NC,x,pd,ND);
		for (j=0;j<=ND;j++) d[j][i]=pd[j];
	}
	for (i=0;i<=ND;i++) {
		printf("%6s %s \n"," ",a[i]);
		printf("%12s %17s %15s\n","x","DDPOLY","actual");
		for (j=1;j<=NP;j++) {
			x=0.1*j;
			pwr=1.0;
			for (k=1;k<=NC-i;k++) pwr *= x-1.0;
			printf("%15.6f %15.6f %15.6f\n",x,d[i][j],
				(factrl(NC)/factrl(NC-i))*pwr);
		}
		printf("press ENTER to continue...\n");
		(void) getchar();
	}
	free_matrix(d,0,ND,1,NP);
	free_vector(pd,0,ND);
	return 0;
}
#undef NRANSI
