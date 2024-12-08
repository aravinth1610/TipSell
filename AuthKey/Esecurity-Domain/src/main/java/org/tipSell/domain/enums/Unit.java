package org.tipSell.domain.enums;

public enum Unit {

	days("DAYS"), hours("HOURS"), minutes("MINUTES"), seconds("SECONDS");

	private String value;

	Unit(String value) {
		this.value = value;
	}

	public String getTokeUnitValue() {
		return value;
	}
	
	public static Unit fromUnit(String unit) {
		Unit unitValue = null;
		for (Unit units : Unit.values()) {
			if (units.value.equalsIgnoreCase(unit)) {
				unitValue = units;
				return unitValue;
			}
		}
		return unitValue;
	}
	

}
