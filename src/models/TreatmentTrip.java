package models;

public class TreatmentTrip extends Trip {

    private final String treatmentProfile;
    private final boolean medicalSupervision;

    public TreatmentTrip(Location departure, Location destination, double price,
            int durationInDays, TransportType transportType, MealPlan mealPlan,
            String treatmentProfile, boolean medicalSupervision) {
        super(departure, destination, price, durationInDays, transportType, mealPlan);
        if (treatmentProfile == null || treatmentProfile.isBlank()) {
            throw new IllegalArgumentException("Treatment profile can't be empty");
        }
        this.treatmentProfile = treatmentProfile.trim();
        this.medicalSupervision = medicalSupervision;
    }

    public String getTreatmentProfile() {
        return treatmentProfile;
    }

    public boolean hasMedicalSupervision() {
        return medicalSupervision;
    }

    @Override
    public TripType getType() {
        return TripType.TREATMENT;
    }

    @Override
    protected String getDetails() {
        return "Profile: " + treatmentProfile
                + ". Medical supervision: " + medicalSupervision;
    }
}
