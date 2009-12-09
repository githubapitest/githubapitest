
/* Driver for routine frenel */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define MAXSTR 80

int main(void)
{
	char txt[MAXSTR];
	int i,nval;
	float c,s,x,xc,xs;
	FILE *fp;

	if ((fp = fopen("fncval.dat","r")) == NULL)
		nrerror("Data file fncval.dat not found\n");
	fgets(txt,MAXSTR,fp);
	while (strncmp(txt,"Fresnel Integrals",17)) {
		fgets(txt,MAXSTR,fp);
		if (feof(fp)) nrerror("Data not found in fncval.dat\n");
	}
	fscanf(fp,"%d %*s",&nval);
	printf("\n%s\n",txt);
	printf("%5s %12s %14s %16s %14s \n",
		"x","actual","c(x)","actual","s(x)");
	for (i=1;i<=nval;i++) {
		fscanf(fp,"%f %f %f",&x,&xs,&xc);
		frenel(x,&s,&c);
		printf("%6.2f %15.6e %15.6e %15.6e %15.6e\n",
			x,xs,s,xc,c);
	}
	fclose(fp);
	return 0;
}
#undef NRANSI
