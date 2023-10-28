package domain;

import java.io.Serializable;

public class LaptopComputer implements Serializable {

    static final long serialVersionUID = 1L;
    private final String screenSize;
    private final BaseComputer baseComputer;
    private static LaptopComputer instance = null;

    // Private constructor
    private LaptopComputer(BaseComputer baseComputer, String screenSize) {
        this.baseComputer = baseComputer;
        this.screenSize = screenSize;
    }

    // Public static method to get instance
    public static LaptopComputer getInstance(BaseComputer baseComputer, String screenSize) {
        if (instance == null) {
            instance = new LaptopComputer(baseComputer, screenSize);
        }
        return instance;
    }

    // Getters
    public String getScreenSize() {
        return screenSize;
    }

    public BaseComputer getBaseComputer() {
        return baseComputer;
    }
	@Override
    public String toString() {
        return baseComputer.toString() + "\n" +
                "Screen Size: " + screenSize;
    }

	public static LaptopComputer getInstance(String cpu, String ram, String disk, String screen) {
		return null;
	}
}