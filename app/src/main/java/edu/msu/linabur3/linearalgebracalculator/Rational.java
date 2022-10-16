package edu.msu.linabur3.linearalgebracalculator;

/**
 * Class to represent a rational number
 *
 * Rational numbers are of the form p/q where p and q are integers and q is non-zero
 */
public class Rational {

    /**
     * The top number of the rational number (numerator)
     */
    private int p;

    /**
     * The bottom number of the rational number (denominator)
     */
    private int q;

    /**
     * Get the numerator
     *
     * @return the numerator of this rational number
     */
    public int getTop() {return p;}

    /**
     * Get the denominator
     *
     * @return the denominator of this rational number
     */
    public int getBot() {return q;}

    /**
     * Constructor to create an integer but represented as a rational
     *
     * @param a the value for this rational number
     */
    Rational(int a)
    {
        // If making integer, denominator is 1
        p = a;
        q = 1;
    }

    /**
     * Constructor to create a rational with denominator
     *
     * @param a the numerator
     * @param b the denominator
     */
    Rational(int a, int b)
    {
        // If making integer, denominator is 1
        p = a;
        q = b;
    }

    /**
     * Private function to reduce rational number
     */
    private void reduce()
    {
        // Check for zero number
        if (p == 0)
        {
            q = 1;
            return;
        }

        // GCD algorithm by chema989 from stackoverflow
        int a, b;
        a = p;
        b = q;
        while(b!=0)
        {
            int t = b;
            b = a%b;
            a = t;
        }

        // Reduce numerator and denominator by GCD
        p = p / a;
        q = q / a;

        // If negative on denominator, flip for neatness
        if(q < 0)
        {
            q = Math.abs(q);
            p = -p;
        }

    }

    /**
     * Add two rational numbers
     *
     * @param r the rational number to add to this one
     * @return the result of addition of this rational number to a
     */
    public Rational add(Rational r)
    {
        Rational temp = new Rational(1,1);
        temp.p = (p * r.q) + (r.p * q);
        temp.q = q * r.q;

        temp.reduce();

        return temp;
    }

    /**
     * Subtract two rational numbers
     *
     * @param r the rational number to subtract from this one
     * @return the result of subtraction of this rational number to a
     */
    public Rational subtract(Rational r)
    {
        Rational temp = new Rational(1,1);
        temp.p = (p * r.q) - (r.p * q);
        temp.q = q * r.q;

        temp.reduce();

        return temp;
    }

    /**
     * Multiply two rational numbers
     *
     * @param r the rational number to multiply to this one
     * @return the result of multiplication of this rational number to a
     */
    public Rational multiply(Rational r)
    {
        Rational temp = new Rational(1,1);
        temp.p = p * r.p;
        temp.q = q * r.q;

        temp.reduce();

        return temp;
    }

    /**
     * Divide two rational numbers
     *
     * @param r the rational number to divide into this one
     * @return the result of division of this rational number to a
     */
    public Rational divide(Rational r)
    {
        Rational temp = new Rational(1,1);

        // Error condition, cannot divide by zero
        if(r.p == 0)
            return null;

        temp.p = p * r.q;
        temp.q = q * r.p;

        temp.reduce();

        return temp;
    }

    /**
     * Compare two rational numbers
     *
     * @param r the rational number to compare to thsi one
     * @return True if this number and r are equal, false if not
     */
    public boolean isEqual(Rational r)
    {
        return (p == r.p && q == r.q);
    }

    /**
     * Compare two rational numbers
     *
     * @param r the rational number to compare to thsi one
     * @return True if this number and r are equal, false if not
     */
    public boolean notEqual(Rational r)
    {
        return (p != r.p || q != r.q);
    }

    /**
     * Compare rational number to an integer
     *
     * @param r the integer number to compare to this one
     * @return True if this number and r are equal, false if not
     */
    public boolean isEqual(int r)
    {
        if(q!=1)
            return false;

        return (p == r);
    }

    /**
     * Compare number to a rational
     *
     * @param r the rational number to compare to this one
     * @return True if this number and r are equal, false if not
     */
    public boolean notEqual(int r)
    {
        if (q!=1)
            return true;

        return (p != r);
    }


}
