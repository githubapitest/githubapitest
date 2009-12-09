
/* Driver for routine elle */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define MAXSTR 80

#define FAC (3.1415926535/180.0)

int main(void)
{
	char txt[MAXSTR];
	int i,nval;
	float ak,alpha,phi,val;
	FILE *fp;

	if ((fp = fopen("fncval.dat","r")) == NULL)
		nrerror("Data file fncval.dat not found\n");
	fgets(txt,MAXSTR,fp);
	while (strncmp(txt,"Legendre Elliptic Integral Second Kind",38)) {
		fgets(txt,MAXSTR,fp);
		if (feof(fp)) nrerror("Data not found in fncval.dat\n");
	}
	fscanf(fp,"%d %*s",&nval);
	printf("\n%s\n",txt);
	printf("%5s %10s %11s %22s\n","phi","sin(alpha)","actual","elle(phi,ak)");
	for (i=1;i<=nval;i++) {
		fscanf(fp,"%f %f %f",&phi,&alpha,&val);
		alpha=alpha*FAC;
		ak=sin(alpha);
		phi=phi*FAC;
		printf("%6.2f %6.2f %18.6e %18.6e\n",phi,ak,val,elle(phi,ak));
	}
	fclose(fp);
	return 0;
}
#undef NRANSI
