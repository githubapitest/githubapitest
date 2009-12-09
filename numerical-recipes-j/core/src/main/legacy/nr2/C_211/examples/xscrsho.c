
/* Driver for routine scrsho */

#include <stdio.h>
#define NRANSI
#include "nr.h"

static float fx(float x)
{
	return bessj0(x);
}

int main(void)
{
	scrsho(fx);
	return 0;
}
#undef NRANSI
