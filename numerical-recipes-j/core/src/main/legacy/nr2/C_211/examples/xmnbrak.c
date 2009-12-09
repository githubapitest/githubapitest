
/* Driver for routine mnbrak */

#include <stdio.h>
#define NRANSI
#include "nr.h"

float func(float x)
{
	return bessj0(x);
}

int main(void)
{
	float ax,bx,cx,fa,fb,fc;
	int i;

	for (i=1;i<=10;i++) {
		ax=i*0.5;
		bx=(i+1.0)*0.5;
		mnbrak(&ax,&bx,&cx,&fa,&fb,&fc,func);
		printf("%14s %12s %12s\n","a","b","c");
		printf("%3s %14.6f %12.6f %12.6f\n","x",ax,bx,cx);
		printf("%3s %14.6f %12.6f %12.6f\n","f",fa,fb,fc);
	}
	return 0;
}
#undef NRANSI
