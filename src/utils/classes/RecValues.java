package utils.classes;

public class RecValues {
    private int[] intVal;

    public RecValues(int[] intVal) {
        this.intVal = new int[intVal.length];
        if (intVal.length >= 0) System.arraycopy(intVal, 0, this.intVal, 0, intVal.length);
    }

    public int[] getIntVal() {
        return intVal;
    }

    public int get(int index){
        return index < intVal.length ? intVal[index] : -1;
    }
}
