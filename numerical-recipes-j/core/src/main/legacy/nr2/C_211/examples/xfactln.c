
/* Driver for routine factln */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define MAXSTR 80

int main(void)
{
	char txt[MAXSTR];
	int i,n,nval;
	float val;
	FILE *fp;

	if ((fp = fopen("fncval.dat","r")) == NULL)
		nrerror("Data file fncval.dat not found\n");
	fgets(txt,MAXSTR,fp);
	while (strncmp(txt,"N-factorial",11)) {
		fgets(txt,MAXSTR,fp);
		if (feof(fp)) nrerror("Data not found in fncval.dat\n");
	}
	fscanf(fp,"%d %*s",&nval);
	printf("\nlog of n_factorial\n");
	printf("\n%6s %19s %21s\n","n","actual","factln(n)");
	for (i=1;i<=nval;i++) {
		fscanf(fp,"%d %f",&n,&val);
		printf("%6d %20.7f %20.7f\n",n,log(val),factln(n));
	}
	fclose(fp);
	return 0;
}
#undef NRANSI
