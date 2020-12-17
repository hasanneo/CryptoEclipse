import java.math.BigInteger;

/**
 * PUBLIC KEY E : PUBLIC EXPONENT R : P * Q
 */
public class RSAPublicKey {
    private BigInteger e;
    private BigInteger r;

    public RSAPublicKey(BigInteger e, BigInteger r) {
        this.e = e;
        this.r = r;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getR() {
        return r;
    }
    @Override
    	public String toString() {
    		// TODO Auto-generated method stub
    		return "E:"+e.toString()+"\nR:"+r.toString();
    	}
}
