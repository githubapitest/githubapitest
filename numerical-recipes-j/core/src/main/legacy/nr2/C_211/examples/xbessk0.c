
/* Driver for routine bessk0 */

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
	while (strncmp(txt,"Modified Bessel Function K0",27)) {
		fgets(txt,MAXSTR,fp);
		if (feof(fp)) nrerror("Data not found in fncval.dat\n");
	}
	fscanf(fp,"%d %*s",&nval);
	printf("\n%s\n",txt);
	printf("%5s %13s %18s \n","x","actual","bessk0(x)");
	for (i=1;i<=nval;i++) {
		fscanf(fp,"%f %f",&x,&val);
		printf("%6.2f %16.7e %16.7e\n",x,val,bessk0(x));
	}
	fclose(fp);
	return 0;
}
#undef NRANSI
