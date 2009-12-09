
/* Driver for routine wt1 */
#include <stdio.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NMAX 512
#define NCEN 333
#define NWID 33

int main(void)
{
	unsigned long i,nused;
	int itest,k;
	float *u,*v,*w,frac,thresh,tmp;

	u=vector(1,NMAX);
	v=vector(1,NMAX);
	w=vector(1,NMAX);
	for (;;) {
		printf("Enter k (4, -4, 12, or 20) and frac (0.0 to 1.0):\n");
		if (scanf("%d %f",&k,&frac) == EOF) break;
		frac=FMIN(1.0,FMAX(0.0,frac));
		itest=(k == -4 ? 1 : 0);
		if (k < 0) k = -k;
		if (k != 4 && k != 12 && k != 20) continue;
		for (i=1;i<=NMAX;i++)
			w[i]=v[i]=(i > NCEN-NWID && i < NCEN+NWID ?
				((float)(i-NCEN+NWID)*(float)(NCEN+NWID-i))/(NWID*NWID) : 0.0);
		if (!itest) pwtset(k);
		wt1(v,NMAX,1,itest ? daub4 : pwt);
		for (i=1;i<=NMAX;i++) u[i]=fabs(v[i]);
		thresh=select((int)((1.0-frac)*NMAX),NMAX,u);
		nused=0;
		for (i=1;i<=NMAX;i++) {
			if (fabs(v[i]) <= thresh)
				v[i]=0.0;
			else
				nused++;
		}
		wt1(v,NMAX,-1,itest ? daub4 : pwt);
		for (thresh=0.0,i=1;i<=NMAX;i++)
			if ((tmp=fabs(v[i]-w[i])) > thresh) thresh=tmp;
		printf("k,NMAX,nused= %d %d %d\n",k,NMAX,nused);
		printf("discrepancy= %12.6f\n",thresh);
	}
	free_vector(w,1,NMAX);
	free_vector(v,1,NMAX);
	free_vector(u,1,NMAX);
	return 0;
}
#undef NRANSI
