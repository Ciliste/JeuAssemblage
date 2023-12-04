package utils;

public enum ETypeListen {
    
    AREAVIEW("areaview"),
    PLAYVIEW("playview"),
    PIECEVIEW("pieceview");


    public final String typeListen;

    private ETypeListen(String typeListen) {
        this.typeListen = typeListen;
    }
}
