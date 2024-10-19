
public class ComplexNumber {
    public final double re;
    public final double im;

    public ComplexNumber(double real, double imaginary) {
        this.re = real;
        this.im = imaginary;
    }

    public ComplexNumber sum(ComplexNumber other) {
        if (other == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
        double newRe = this.re + other.re;
        double newIm = this.im + other.im;
        return new ComplexNumber(newRe, newIm);
    }

    public ComplexNumber subtract(ComplexNumber other) {
        if (other == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
        double newRe = this.re - other.re;
        double newIm = this.im - other.im;
        return new ComplexNumber(newRe, newIm);
    }

    public ComplexNumber mult(ComplexNumber other) {
        if (other == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
        double newRe = (this.re * other.re) - (this.im * other.im);
        double newIm = (this.re * other.im) + (this.im * other.re);
        return new ComplexNumber(newRe, newIm);
    }

    public ComplexNumber mult(double scalar) {
        return new ComplexNumber(this.re * scalar, this.im * scalar);
    }

    @Override
    public String toString() {
        return re + " + " + im + "i";
    }
}
