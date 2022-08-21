package model.RoomItemComponents;

/**
 * Sets up pillars that will get passed in constructor
 */
public class PillarOO extends RoomItem{

    /** The enum type of pillar. */
    private PillarsOO myPillarType;

    /**
     * Constructs the pillar that was passed to the parent class
     *
     * @param theTypeOfPillar the enum pillar
     */
    public PillarOO(final PillarsOO theTypeOfPillar) {
        super(theTypeOfPillar.name());
        setType(RoomItems.PILLAR);
        this.myPillarType = theTypeOfPillar;
    }

    @Override
    /**
     * Returns the enum name of the pillar as string.
     */
    public String toString() {
        return myPillarType.toString();
    }
}
