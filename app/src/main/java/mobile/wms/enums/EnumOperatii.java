package mobile.wms.enums;

public enum EnumOperatii {

    LOGIN("login"), TASK_USER("taskUser"), MAT_RECEPT("materialReceptie"), SAVE_PREGATIRE("salveazaTaskPregatire");

    private final String numeOp;

    EnumOperatii(String numeOp) {
        this.numeOp = numeOp;
    }

    public String getNumeOp() {
        return numeOp;
    }

    @Override
    public String toString() {
        return numeOp;
    }
}
