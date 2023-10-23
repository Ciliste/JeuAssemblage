package model;

public enum EnumTypeListen {
    
    FILE("file");

    public final String typeListen;

    private EnumTypeListen(String typeListen) {
        this.typeListen = typeListen;
    }
}
