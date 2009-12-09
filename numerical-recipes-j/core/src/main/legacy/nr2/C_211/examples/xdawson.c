
/* Driver for routine dawson */

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
	float val,x;
	FILE *fp;

	if ((fp = fopen("fncval.dat","r")) == NULL)
		nrerror("Data file fncval.dat not found\n");
	fgets(txt,MAXSTR,fp);
	while (strncmp(txt,"Dawson integral",15)) {
		fgets(txt,MAXSTR,fp);
		if (feof(fp)) nrerror("Data not found in fncval.dat\n");
	}
	fscanf(fp,"%d %*s",&nval);
	printf("\n%s\n",txt);
	printf("%5s %12s %11s \n","x","actual","dawson(x)");
	for (i=1;i<=nval;i++) {
		fscanf(fp,"%f %f",&x,&val);
		printf("%6.2f %12.6f %12.6f\n",x,val,dawson(x));
	}
	fclose(fp);
	return 0;
}
#undef NRANSI
