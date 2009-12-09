
/* Driver for routine svdfit */

#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NPT 100
#define SPREAD 0.02
#define NPOL 5

int main(void)
{
	long idum=(-911);
	int i;
	float chisq,*x,*y,*sig,*a,*w,**cvm,**u,**v;

	x=vector(1,NPT);
	y=vector(1,NPT);
	sig=vector(1,NPT);
	a=vector(1,NPOL);
	w=vector(1,NPOL);
	cvm=matrix(1,NPOL,1,NPOL);
	u=matrix(1,NPT,1,NPOL);
	v=matrix(1,NPOL,1,NPOL);
	for (i=1;i<=NPT;i++) {
		x[i]=0.02*i;
		y[i]=1.0+x[i]*(2.0+x[i]*(3.0+x[i]*(4.0+x[i]*5.0)));
		y[i] *= (1.0+SPREAD*gasdev(&idum));
		sig[i]=y[i]*SPREAD;
	}
	svdfit(x,y,sig,NPT,a,NPOL,u,v,w,&chisq,fpoly);
	svdvar(v,NPOL,w,cvm);
	printf("\npolynomial fit:\n\n");
	for (i=1;i<=NPOL;i++)
		printf("%12.6f %s %10.6f\n",a[i],"  +-",sqrt(cvm[i][i]));
	printf("\nChi-squared %12.6f\n",chisq);
	svdfit(x,y,sig,NPT,a,NPOL,u,v,w,&chisq,fleg);
	svdvar(v,NPOL,w,cvm);
	printf("\nLegendre polynomial fit:\n\n");
	for (i=1;i<=NPOL;i++)
		printf("%12.6f %s %10.6f\n",a[i],"  +-",sqrt(cvm[i][i]));
	printf("\nChi-squared %12.6f\n",chisq);
	free_matrix(v,1,NPOL,1,NPOL);
	free_matrix(u,1,NPT,1,NPOL);
	free_matrix(cvm,1,NPOL,1,NPOL);
	free_vector(w,1,NPOL);
	free_vector(a,1,NPOL);
	free_vector(sig,1,NPT);
	free_vector(y,1,NPT);
	free_vector(x,1,NPT);
	return 0;
}
#undef NRANSI
