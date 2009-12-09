package com.google.code.numericalrecipes;
public class Machar
{
	public Int ibeta = new Int();
	public Int it = new Int();
	public Int irnd = new Int();
	public Int ngrd = new Int();
	public Int machep = new Int();
	public Int negep = new Int();
	public Int iexp = new Int();
	public Int minexp = new Int();
	public Int maxexp = new Int();
	public Doub eps = new Doub();
	public Doub epsneg = new Doub();
	public Doub xmin = new Doub();
	public Doub xmax = new Doub();

	public Machar ()
	{
		Int i = new Int();
		Int itemp = new Int();
		Int iz = new Int();
		Int j = new Int();
		Int k = new Int();
		Int mx = new Int();
		Int nxres = new Int();
		Doub a = new Doub();
		Doub b = new Doub();
		Doub beta = new Doub();
		Doub betah = new Doub();
		Doub betain = new Doub();
		Doub one = new Doub();
		Doub t = new Doub();
		Doub temp = new Doub();
		Doub temp1 = new Doub();
		Doub tempa = new Doub();
		Doub two = new Doub();
		Doub y = new Doub();
		Doub z = new Doub();
		Doub zero = new Doub();
		one = Doub(1);
		two = one+one;
		zero = one-one;
		a = one;
		do
		{
			a += a;
			temp = a+one;
			temp1 = temp-a;
		} while (temp1-one == zero);
		b = one;
		do
		{
			b += b;
			temp = a+b;
			itemp = (Int)(temp-a);
		} while (itemp == 0);
		ibeta = itemp;
		beta = Doub(ibeta);
		it = 0;
		b = one;
		do
		{
			++it;
			b *= beta;
			temp = b+one;
			temp1 = temp-b;
		} while (temp1-one == zero);
		irnd = 0;
		betah = beta/two;
		temp = a+betah;
		if (temp-a != zero)
			irnd = 1;
		tempa = a+beta;
		temp = tempa+betah;
		if (irnd == 0 && temp-tempa != zero)
			irnd = 2;
		negep = it+3;
		betain = one/beta;
		a = one;
		for (i = 1;i<=negep;i++)
			a *= betain;
		b = a;
		for (;;)
		{
			temp = one-a;
			if (temp-one != zero)
				break;
			a *= beta;
			--negep;
		}
		negep = -negep;
		epsneg = a;
		machep = -it-3;
		a = b;
		for (;;)
		{
			temp = one+a;
			if (temp-one != zero)
				break;
			a *= beta;
			++machep;
		}
		eps = a;
		ngrd = 0;
		temp = one+eps;
		if (irnd == 0 && temp *one-one != zero)
			ngrd = 1;
		i = 0;
		k = 1;
		z = betain;
		t = one+eps;
		nxres = 0;
		for (;;)
		{
			y = z;
			z = y *y;
			a = z *one;
			temp = z *t;
			if (a+a == zero || Math.abs(z) >= y)
				break;
			temp1 = temp *betain;
			if (temp1 *beta == z)
				break;
			++i;
			k += k;
		}
		if (ibeta != 10)
		{
			iexp = i+1;
			mx = k+k;
		}
		else
		{
			iexp = 2;
			iz = ibeta;
			while (k >= iz)
			{
				iz *= ibeta;
				++iexp;
			}
			mx = iz+iz-1;
		}
		for (;;)
		{
			xmin = y;
			y *= betain;
			a = y *one;
			temp = y *t;
			if (a+a != zero && Math.abs(y) < xmin)
			{
				++k;
				temp1 = temp *betain;
				if (temp1 *beta == y && temp != y)
				{
					nxres = 3;
					xmin = y;
					break;
				}
			}
			else
				break;
		}
		minexp = -k;
		if (mx <= k+k-3 && ibeta != 10)
		{
			mx += mx;
			++iexp;
		}
		maxexp = mx+minexp;
		irnd += nxres;
		if (irnd >= 2)
			maxexp -= 2;
		i = maxexp+minexp;
		if (ibeta == 2 && i == null)
			--maxexp;
		if (i > 20)
			--maxexp;
		if (a != y)
			maxexp -= 2;
		xmax = one-epsneg;
		if (xmax *one != xmax)
			xmax = one-beta *epsneg;
		xmax /= (xmin *beta *beta *beta);
		i = maxexp+minexp+3;
		for (j = 1;j<=i;j++)
		{
			if (ibeta == 2)
				xmax += xmax;
			else
				xmax *= beta;
		}
	}

	public final void report()
	{
		System.out.print("quantity:  numeric_limits<Doub> says  (we calculate)");
		System.out.print("\n");
		System.out.print("radix:  ");
		System.out.print(numeric_limits<Doub>.radix);
		System.out.print("  (");
		System.out.print(ibeta);
		System.out.print(")");
		System.out.print("\n");
		System.out.print("mantissa digits:  ");
		System.out.print(numeric_limits<Doub>.digits);
		System.out.print("  (");
		System.out.print(it);
		System.out.print(")");
		System.out.print("\n");
		System.out.print("round style:  ");
		System.out.print(numeric_limits<Doub>.round_style);
		System.out.print("  (");
		System.out.print(irnd);
		System.out.print(") [our 5 == IEEE 1]");
		System.out.print("\n");
		System.out.print("guard digits:  ");
		System.out.print("[not in numeric_limits]");
		System.out.print("  (");
		System.out.print(ngrd);
		System.out.print(")");
		System.out.print("\n");
		System.out.print("epsilon:  ");
		System.out.print(numeric_limits<Doub>.epsilon());
		System.out.print("  (");
		System.out.print(eps);
		System.out.print(")");
		System.out.print("\n");
		System.out.print("neg epsilon:  ");
		System.out.print("[not in numeric_limits]");
		System.out.print("  (");
		System.out.print(epsneg);
		System.out.print(")");
		System.out.print("\n");
		System.out.print("epsilon power:  ");
		System.out.print("[not in numeric_limits]");
		System.out.print("  (");
		System.out.print(machep);
		System.out.print(")");
		System.out.print("\n");
		System.out.print("neg epsilon power:  ");
		System.out.print("[not in numeric_limits]");
		System.out.print("  (");
		System.out.print(negep);
		System.out.print(")");
		System.out.print("\n");
		System.out.print("exponent digits:  ");
		System.out.print("[not in numeric_limits]");
		System.out.print("  (");
		System.out.print(iexp);
		System.out.print(")");
		System.out.print("\n");
		System.out.print("min exponent:  ");
		System.out.print(numeric_limits<Doub>.min_exponent);
		System.out.print("  (");
		System.out.print(minexp);
		System.out.print(")");
		System.out.print("\n");
		System.out.print("max exponent:  ");
		System.out.print(numeric_limits<Doub>.max_exponent);
		System.out.print("  (");
		System.out.print(maxexp);
		System.out.print(")");
		System.out.print("\n");
		System.out.print("minimum:  ");
		System.out.print(numeric_limits<Doub>.min());
		System.out.print("  (");
		System.out.print(xmin);
		System.out.print(")");
		System.out.print("\n");
		System.out.print("maximum:  ");
		System.out.print(numeric_limits<Doub>.max());
		System.out.print("  (");
		System.out.print(xmax);
		System.out.print(")");
		System.out.print("\n");
	}
}