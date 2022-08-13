package model.RoomItemComponents;

public class PillarOO extends RoomItem{

    private PillarsOO myPillarType;

    public PillarOO(PillarsOO theTypeOfPillar) {
        super(theTypeOfPillar.name());
        this.myPillarType = theTypeOfPillar;
    }

    @Override
    public String toString() {
        return myPillarType.toString();
    }
}
