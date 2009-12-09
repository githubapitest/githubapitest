
/* Driver for routine sort3 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#define NRANSI
#include "nr.h"
#include "nrutil.h"

#define NLEN 64

int main(void)
{
	int i,j;
	char dummy[NLEN],amsg[NLEN+1],bmsg[NLEN+1],cmsg[NLEN+1];
	float *a,*b,*c;
	FILE *fp;

	a=vector(1,NLEN);
	b=vector(1,NLEN);
	c=vector(1,NLEN);
	(void) strcpy(amsg,"I'd rather have a bottle in front of");
	(void) strcat(amsg," me than a frontal lobotomy.");
	printf("\noriginal message:\n%s\n",amsg);
	/* read array of random numbers */
	if ((fp = fopen("tarray.dat","r")) == NULL)
		nrerror("Data file tarray.dat not found\n");
	fgets(dummy,NLEN,fp);
	for (i=1;i<=NLEN;i++) fscanf(fp,"%f",&a[i]);
	fclose(fp);
	/* create array b and array c */
	for (i=1;i<=NLEN;i++) {
		b[i]=i;
		c[i]=NLEN+1-i;
	}
	/* sort array a while mixing b and c */
	sort3(NLEN,a,b,c);
	/* scramble message according to array b */
	bmsg[NLEN]=amsg[NLEN];  /* null terminating character */
	for (i=1;i<=NLEN;i++) {
		j=b[i];
		bmsg[i-1]=amsg[j-1];
	}
	printf("\nscrambled message:\n%s\n",bmsg);
	/* unscramble according to array c */
	cmsg[NLEN]=amsg[NLEN];
	for (i=1;i<=NLEN;i++) {
		j=c[i];
		cmsg[j-1]=bmsg[i-1];
	}
	printf("\nmirrored message:\n%s\n",cmsg);
	free_vector(c,1,NLEN);
	free_vector(b,1,NLEN);
	free_vector(a,1,NLEN);
	return 0;
}
#undef NRANSI
