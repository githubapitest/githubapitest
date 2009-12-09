
#include <math.h>
#define NRANSI
#include "nrutil.h"

float ellf(float phi, float ak)
{
	float rf(float x, float y, float z);
	float s;

	s=sin(phi);
	return s*rf(SQR(cos(phi)),(1.0-s*ak)*(1.0+s*ak),1.0);
}
#undef NRANSI
