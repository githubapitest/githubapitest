
/* Driver for routine erffc */

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
	float x,val;
	FILE *fp;

	if ((fp = fopen("fncval.dat","r")) == NULL)
		nrerror("Data file fncval.dat not found\n");
	fgets(txt,MAXSTR,fp);
	while (strncmp(txt,"Error Function",14)) {
		fgets(txt,MAXSTR,fp);
		if (feof(fp)) nrerror("Data not found in fncval.dat\n");
	}
	fscanf(fp,"%d %*s",&nval);
	printf("\ncomplementary error function\n");
	printf("%5s %12s %13s\n","x","actual","erfc(x)");
	for (i=1;i<=nval;i++) {
		fscanf(fp,"%f %f",&x,&val);
		val=1.0-val;
		printf("%6.2f %12.7f %12.7f\n",x,val,erffc(x));
	}
	fclose(fp);
	return 0;
}
#undef NRANSI
