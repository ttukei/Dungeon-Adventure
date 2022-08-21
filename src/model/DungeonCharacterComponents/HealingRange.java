package model.DungeonCharacterComponents;

public final class HealingRange {

    /**
     * The maximum health that can be regained.
     */
    private int myUpperBound;

    /**
     * The minimum health that can be regained.
     */
    private int myLowerBound;

    /**
     * Creates an instance of damage range.
     * @param theLowerBound the minimum damage that can be dealt.
     * @param theUpperBound the maximum damage that can be dealt.
     */
    public HealingRange(final int theLowerBound, final int theUpperBound) {
        this.myLowerBound = theLowerBound;
        this.myUpperBound = theUpperBound;
    }

    public int getMyUpperBound() {
        return myUpperBound;
    }

    public void setMyUpperBound(final int myUpperBound) {
        this.myUpperBound = myUpperBound;
    }

    public int getMyLowerBound() {
        return myLowerBound;
    }

    public void setMyLowerBound(final int myLowerBound) {
        this.myLowerBound = myLowerBound;
    }

    @Override
    public String toString() {
        return "HealingRange{" +
                "myUpperBound=" + myUpperBound +
                ", myLowerBound=" + myLowerBound +
                '}';
    }
}
