
/* Driver for routine betai */

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
	float a,b,x,val;
	FILE *fp;

	if ((fp = fopen("fncval.dat","r")) == NULL)
		nrerror("Data file fncval.dat not found\n");
	fgets(txt,MAXSTR,fp);
	while (strncmp(txt,"Incomplete Beta Function",24)) {
		fgets(txt,MAXSTR,fp);
		if (feof(fp)) nrerror("Data not found in fncval.dat\n");
	}
	fscanf(fp,"%d %*s",&nval);
	printf("\n%s\n",txt);
	printf("%5s %10s %12s %14s %13s \n",
		"a","b","x","actual","betai(x)");
	for (i=1;i<=nval;i++) {
		fscanf(fp,"%f %f %f %f",&a,&b,&x,&val);
		printf("%6.2f %12.6f %12.6f %12.6f %12.6f\n",
			a,b,x,val,betai(a,b,x));
	}
	fclose(fp);
	return 0;
}
#undef NRANSI
