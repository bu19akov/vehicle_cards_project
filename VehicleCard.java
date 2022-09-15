package a12132881;

import java.util.*;

public class VehicleCard implements Comparable<VehicleCard> {
	public enum Category {
		ECONOMY_MPG("Miles/Gallon"), 
		CYLINDERS_CNT("Zylinder"), 
		DISPLACEMENT_CCM("Hubraum[cc]"), 
		POWER_HP("Leistung[hp]"), 
		WEIGHT_LBS("Gewicht[lbs]") {
			public boolean isInverted() {
				return true;
			}
		},
		ACCELERATION("Beschleunigung") {
			public boolean isInverted() {
				return true;
			}
		},
		YEAR("Baujahr[19xx]");
		
		private final String categoryName;
		
		private Category(final String categoryName) {
			if (categoryName == null || categoryName.isEmpty())
				throw new IllegalArgumentException("illegal categoryName");
			this.categoryName = categoryName;
		}
		
		public boolean isInverted() { 
			return false;
		}
		
		public int bonus(final Double value) { 
			int help = value.intValue();
			if (isInverted())
				return -help;
			else
				return help;
		}
		
		@Override
		public String toString() { 
			return categoryName;
		} 
	}
	
	private String name;
	private Map<Category, Double> categories;
	
	public VehicleCard(final String name, final Map<Category, Double> categories) {
		if (name == null || name.isEmpty())
			throw new IllegalArgumentException("illegal name");
		if (categories == null)
			throw new IllegalArgumentException("categories is null");
		for (Category categ : Category.values()) {
			Boolean help = false;
			for (Map.Entry<Category, Double> entry : categories.entrySet()) {
				if (entry.getKey() == categ)
					help = true;
			}
			if (help == false)
				throw new IllegalArgumentException("categories not contain all values");
		}
		for (Map.Entry<Category, Double> entry : categories.entrySet()) {
			if (entry.getValue() == null || entry.getValue() <= 0 )
				throw new IllegalArgumentException("categories contains <= 0");
		}
		this.name = name;
		Map<Category, Double> shallow = new HashMap<>(categories);
		this.categories = shallow;
	}

	public String getName() {
		return this.name;
	}
	
	public Map<Category, Double> getCategories() {
		Map<Category, Double> shallow = new HashMap<>(this.categories);
		return shallow;
	}
	

	public static Map<Category, Double> newMap(double economy, double cylinders , double displacement , double power , double weight , double acceleration, double year) {
		Map<Category, Double> help = new HashMap<>();
		help.put(Category.ECONOMY_MPG, economy);
		help.put(Category.CYLINDERS_CNT, cylinders);
		help.put(Category.DISPLACEMENT_CCM, displacement);
		help.put(Category.POWER_HP, power);
		help.put(Category.WEIGHT_LBS, weight);
		help.put(Category.ACCELERATION, acceleration);
		help.put(Category.YEAR, year);
		return help;
	}
	
	@Override
	public int compareTo(final VehicleCard other) {
		return this.totalBonus() - other.totalBonus();
	}
	
	public int totalBonus(){
		int bon = 0;
		for (Map.Entry<Category, Double> entry : categories.entrySet()) {
			bon += entry.getKey().bonus(entry.getValue());
		}
		return bon;
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(this.name, this.totalBonus());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VehicleCard && ((VehicleCard) obj).getName() == this.getName() && ((VehicleCard) obj).totalBonus() == this.totalBonus())
			return true;
		else return false;
	}

	@Override
	public String toString () {
		List<String> list = new ArrayList<>();
		Map<Category, Double> help = this.getCategories();
		for (Map.Entry<Category, Double> entry : help.entrySet()) {
			list.add(entry.getKey() + "=" + entry.getValue());
		}
		String out = String.join(", ", list);
		return "- " + name + "(" + this.totalBonus() + ")" + " -> {" + out + "}";
	}
}
