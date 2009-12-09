
/* Driver for routine rf */

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
	float val,x,y,z;
	FILE *fp;

	if ((fp = fopen("fncval.dat","r")) == NULL)
		nrerror("Data file fncval.dat not found\n");
	fgets(txt,MAXSTR,fp);
	while (strncmp(txt,"Elliptic Integral First Kind RF",31)) {
		fgets(txt,MAXSTR,fp);
		if (feof(fp)) nrerror("Data not found in fncval.dat\n");
	}
	fscanf(fp,"%d %*s",&nval);
	printf("\n%s\n",txt);
	printf("%7s %8s %8s %16s %20s\n","x","y","z","actual","rf(x,y,z)");
	for (i=1;i<=nval;i++) {
		fscanf(fp,"%f %f %f %f",&x,&y,&z,&val);
		printf("%8.2f %8.2f %8.2f %18.6e %18.6e\n",x,y,z,val,rf(x,y,z));
	}
	fclose(fp);
	return 0;
}
#undef NRANSI
