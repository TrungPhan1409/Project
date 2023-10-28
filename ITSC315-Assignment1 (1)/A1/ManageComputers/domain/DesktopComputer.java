package domain;

import java.io.Serializable;

public class DesktopComputer implements Serializable {

    static final long serialVersionUID = 1L;
    private final String gpu;
    private final BaseComputer baseComputer;
    private static DesktopComputer instance = null;

    // Private constructor
    private DesktopComputer(BaseComputer baseComputer, String gpu) {
        this.baseComputer = baseComputer;
        this.gpu = gpu;
    }

    // Public static method to get instance
    public static DesktopComputer getInstance(BaseComputer baseComputer, String gpu) {
        if (instance == null) {
            instance = new DesktopComputer(baseComputer, gpu);
        }
        return instance;
    }

    // Getters
    public String getGpu() {
        return gpu;
    }

    public BaseComputer getBaseComputer() {
        return baseComputer;
    }
    @Override
    public String toString() {
        return baseComputer.toString() + "\n" +
                "GPU: " + gpu;
    }

    public static DesktopComputer getInstance(String cpu, String ram, String disk, String gpu) {
        return null;
    }
}