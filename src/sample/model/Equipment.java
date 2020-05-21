package sample.model;

public class Equipment {
    private int id = 0;
    private double poleDistance = 0.0;
    private String equipment = "";
    private String MPCarrierMedium = "";
    private String magTech = "";
    private String UVLightIntensity = "";
    private String distanceOfLight = "";

    public Equipment(int id, double poleDistance, String equipment,
                     String MPCarrierMedium, String magTech,
                     String UVLightIntensity, String distanceOfLight) {
        this.id = id;
        this.poleDistance = poleDistance;
        this.equipment = equipment;
        this.MPCarrierMedium = MPCarrierMedium;
        this.magTech = magTech;
        this.UVLightIntensity = UVLightIntensity;
        this.distanceOfLight = distanceOfLight;
    }

    public Equipment(double poleDistance, String equipment,
                     String MPCarrierMedium, String magTech,
                     String UVLightIntensity, String distanceOfLight) {
        this.poleDistance = poleDistance;
        this.equipment = equipment;
        this.MPCarrierMedium = MPCarrierMedium;
        this.magTech = magTech;
        this.UVLightIntensity = UVLightIntensity;
        this.distanceOfLight = distanceOfLight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPoleDistance() {
        return poleDistance;
    }

    public void setPoleDistance(double poleDistance) {
        this.poleDistance = poleDistance;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getMPCarrierMedium() {
        return MPCarrierMedium;
    }

    public void setMPCarrierMedium(String MPCarrierMedium) {
        this.MPCarrierMedium = MPCarrierMedium;
    }

    public String getMagTech() {
        return magTech;
    }

    public void setMagTech(String magTech) {
        this.magTech = magTech;
    }

    public String getUVLightIntensity() {
        return UVLightIntensity;
    }

    public void setUVLightIntensity(String UVLightIntensity) {
        this.UVLightIntensity = UVLightIntensity;
    }

    public String getDistanceOfLight() {
        return distanceOfLight;
    }

    public void setDistanceOfLight(String distanceOfLight) {
        this.distanceOfLight = distanceOfLight;
    }
}
