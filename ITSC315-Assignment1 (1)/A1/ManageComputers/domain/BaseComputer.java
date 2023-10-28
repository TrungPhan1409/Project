package domain;

import java.io.Serializable;

public final class BaseComputer implements Serializable {

    static final long serialVersionUID = 8670031469446950176L;
    private final String type;
    private final String cpuType;
    private final String ramSize;
    private final String diskSize;
    private static BaseComputer instance = null;

    // Private constructor
    private BaseComputer(String type, String cpuType, String ramSize, String diskSize) {
        this.type = type;
        this.cpuType = cpuType;
        this.ramSize = ramSize;
        this.diskSize = diskSize;
    }

    // Public static method to get instance
    public static BaseComputer getInstance(String type, String cpuType, String ramSize, String diskSize) {
        if (instance == null) {
            instance = new BaseComputer(type, cpuType, ramSize, diskSize);
        }
        return instance;
    }

    // Getters
    public String getCpuType() {
        return cpuType;
    }

    public String getRamSize() {
        return ramSize;
    }

    public String getType() {
        return type;
    }

    public String getDiskSize() {
        return diskSize;
    }

    @Override
    public String toString() {
        return "Type: " + type + "\n" +
                "CPU Type: " + cpuType + "\n" +
                "RAM Size: " + ramSize + "\n" +
                "Disk Size: " + diskSize;
    }
}