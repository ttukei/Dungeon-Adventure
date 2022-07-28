package model.DungeonCharacterComponents;

public final class DamageRange {

    /**
     * The maximum damage that can be dealt.
     */
    private int myUpperBound;

    /**
     * The minimum damage that can be dealt.
     */
    private int myLowerBound;

    /**
     * Creates an instance of damage range.
     * @param theLowerBound the minimum damage that can be dealt.
     * @param theUpperBound the maximum damage that can be dealt.
     */
    public DamageRange(final int theLowerBound, final int theUpperBound) {
        this.myLowerBound = theLowerBound;
        this.myUpperBound = theUpperBound;
    }

    public int getMyUpperBound() {
        return myUpperBound;
    }

    public void setMyUpperBound(int myUpperBound) {
        this.myUpperBound = myUpperBound;
    }

    public int getMyLowerBound() {
        return myLowerBound;
    }

    public void setMyLowerBound(int myLowerBound) {
        this.myLowerBound = myLowerBound;
    }

    @Override
    public String toString() {
        return "DamageRange{upper bound = " + getMyUpperBound() + " lower bound =" + getMyLowerBound() + "}";
    }
}
