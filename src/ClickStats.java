import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClickStats {
    @JsonProperty("units")
    private int units;

    @JsonProperty("unit_reference")
    private String unitReference;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("total_clicks")
    private int totalClicks;

    public int getUnits() { return units; }
    public void setUnits(int units) { this.units = units; }

    public String getUnitReference() { return unitReference; }
    public void setUnitReference(String unitReference) { this.unitReference = unitReference; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public int getTotalClicks() { return totalClicks; }
    public void setTotalClicks(int totalClicks) { this.totalClicks = totalClicks; }
}