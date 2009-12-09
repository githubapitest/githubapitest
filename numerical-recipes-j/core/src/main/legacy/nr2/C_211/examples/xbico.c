
/* Driver for routine bico */

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
	int i,k,n,nval;
	float binco;
	FILE *fp;

	if ((fp = fopen("fncval.dat","r")) == NULL)
		nrerror("Data file fncval.dat not found\n");
	fgets(txt,MAXSTR,fp);
	while (strncmp(txt,"Binomial Coefficients",21)) {
		fgets(txt,MAXSTR,fp);
		if (feof(fp)) nrerror("Data not found in fncval.dat\n");
	}
	fscanf(fp,"%d %*s",&nval);
	printf("\n%s\n",txt);
	printf("%6s %6s %12s %12s \n","n","k","actual","bico(n,k)");
	for (i=1;i<=nval;i++) {
		fscanf(fp,"%d %d %f ",&n,&k,&binco);
		printf("%6d %6d %12.0f %12.0f \n",n,k,binco,bico(n,k));
	}
	fclose(fp);
	return 0;
}
#undef NRANSI
