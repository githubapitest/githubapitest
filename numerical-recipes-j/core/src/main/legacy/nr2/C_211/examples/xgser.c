
/* Driver for routine gser */

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
	float a,gamser,gln,val,x;
	FILE *fp;

	if ((fp = fopen("fncval.dat","r")) == NULL)
		nrerror("Data file fncval.dat not found\n");
	fgets(txt,MAXSTR,fp);
	while (strncmp(txt,"Incomplete Gamma Function",25)) {
		fgets(txt,MAXSTR,fp);
		if (feof(fp)) nrerror("Data not found in fncval.dat\n");
	}
	fscanf(fp,"%d %*s",&nval);
	printf("\n%s\n",txt);
	printf("%4s %11s %14s %14s %12s %8s\n","a","x",
		"actual","gser(a,x)","gammln(a)","gln");
	for (i=1;i<=nval;i++) {
		fscanf(fp,"%f %f %f",&a,&x,&val);
		gser(&gamser,a,x,&gln);
		printf("%6.2f %12.6f %12.6f %12.6f %12.6f %12.6f\n",
			a,x,val,gamser,gammln(a),gln);
	}
	fclose(fp);
	return 0;
}
#undef NRANSI
