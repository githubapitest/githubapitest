
#include <math.h>

float beta(float z, float w)
{
	float gammln(float xx);

	return exp(gammln(z)+gammln(w)-gammln(z+w));
}
